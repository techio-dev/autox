# WiFiConnectActivity Implementation Plan

## Tổng quan

WiFiConnectActivity là một Activity được thiết kế để kết nối WiFi thông qua ADB commands với hỗ trợ HTTP proxy cho Android 8-9 (API 26-28).

## Mục tiêu

- ✅ Nhận lệnh từ ADB để kết nối WiFi
- ✅ Hỗ trợ manual HTTP proxy
- ✅ Chỉ hỗ trợ Android 8-9 (API 26-28)
- ✅ Execution đơn giản, không verification
- ✅ Silent operation (Theme.NoDisplay)

## ADB Commands

### WiFi Basic
```bash
adb shell am start -n org.autojs.autoxjs/.external.wifi.WiFiConnectActivity \
  -e "ssid" "MyWiFi" -e "password" "pass123"
```

### WiFi + Manual Proxy
```bash
adb shell am start -n org.autojs.autoxjs/.external.wifi.WiFiConnectActivity \
  -e "ssid" "MyWiFi" -e "password" "pass123" \
  -e "proxy_host" "proxy.company.com" -e "proxy_port" "8080"
```

### WiFi + Proxy + Bypass List
```bash
adb shell am start -n org.autojs.autoxjs/.external.wifi.WiFiConnectActivity \
  -e "ssid" "MyWiFi" -e "password" "pass123" \
  -e "proxy_host" "proxy.company.com" -e "proxy_port" "3128" \
  -e "proxy_bypass" "localhost,127.0.0.1,*.internal"
```

## Input Parameters

| Parameter | Required | Default | Description |
|-----------|----------|---------|-------------|
| `ssid` | ✅ | - | WiFi network name |
| `password` | ✅ | - | WiFi password |
| `security` | ❌ | "WPA2" | WPA2/WPA/WEP |
| `hidden` | ❌ | false | Hidden network |
| `proxy_host` | ❌ | "" | Proxy hostname/IP |
| `proxy_port` | ❌ | 8080 | Proxy port |
| `proxy_bypass` | ❌ | "" | Bypass domains (comma separated) |

## Implementation Workflow

1. **Validation**
   - Check Android version (API 26-28 only)
   - Validate required parameters (SSID + password)

2. **WiFi Configuration**
   - Create WifiConfiguration object
   - Set SSID, password, security type
   - Configure proxy if provided

3. **Connection Execution**
   - Call `wifiManager.addNetwork(config)`
   - Call `wifiManager.enableNetwork(networkId, true)`
   - Log result to ADB

4. **Cleanup**
   - Finish activity immediately
   - No verification or waiting

## Core Implementation

### Activity Structure
```java
public class WiFiConnectActivity extends Activity {
    // Intent extras constants
    public static final String EXTRA_SSID = "ssid";
    public static final String EXTRA_PASSWORD = "password";
    public static final String EXTRA_SECURITY = "security";
    public static final String EXTRA_HIDDEN = "hidden";
    public static final String EXTRA_PROXY_HOST = "proxy_host";
    public static final String EXTRA_PROXY_PORT = "proxy_port";
    public static final String EXTRA_PROXY_BYPASS = "proxy_bypass";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 1. Validate Android version
        // 2. Parse intent parameters
        // 3. Create WiFi configuration
        // 4. Execute connection
        // 5. Finish activity
    }
}
```

### Key Methods
```java
private void validateAndroidVersion()    // Check API 26-28
private void parseIntentParameters()     // Extract ADB parameters
private WifiConfiguration createWifiConfig()  // Create WiFi config
private ProxyInfo createProxyInfo()      // Create proxy config (optional)
private void executeConnection()         // Add + enable network
```

### Proxy Configuration
```java
private ProxyInfo createProxyInfo(String host, int port, String bypass) {
    if (TextUtils.isEmpty(bypass)) {
        return ProxyInfo.buildDirectProxy(host, port);
    } else {
        List<String> bypassList = Arrays.asList(bypass.split(","));
        return ProxyInfo.buildDirectProxy(host, port, bypassList);
    }
}
```

## File Structure

```
phonefarm/src/main/java/org/autojs/autojs/external/wifi/
└── WiFiConnectActivity.java        // Main activity (single file)
```

## AndroidManifest.xml Configuration

```xml
<activity
    android:name="org.autojs.autojs.external.wifi.WiFiConnectActivity"
    android:exported="true"
    android:theme="@android:style/Theme.NoDisplay">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

## Error Handling

### Result Types
- **SUCCESS**: Network configuration added successfully
- **INVALID_PARAMS**: Missing SSID or password
- **UNSUPPORTED_VERSION**: Not Android 8-9
- **NETWORK_ERROR**: WiFi manager failure

### Error Output
- Log messages to ADB logcat với tag `WiFiConnectActivity`
- Optional Toast messages cho debugging
- Immediate activity finish trên mọi trường hợp

## Testing Plan

### Basic Tests
```bash
# Test 1: WiFi only
adb shell am start -n org.autojs.autoxjs/.external.wifi.WiFiConnectActivity \
  -e "ssid" "TestWiFi" -e "password" "test123"

# Test 2: WiFi + proxy
adb shell am start -n org.autojs.autoxjs/.external.wifi.WiFiConnectActivity \
  -e "ssid" "TestWiFi" -e "password" "test123" \
  -e "proxy_host" "192.168.1.100" -e "proxy_port" "8080"

# Monitor logs
adb logcat | grep WiFiConnectActivity
```

### Error Cases
```bash
# Missing password
adb shell am start -n org.autojs.autoxjs/.external.wifi.WiFiConnectActivity \
  -e "ssid" "TestWiFi"

# Invalid proxy port
adb shell am start -n org.autojs.autoxjs/.external.wifi.WiFiConnectActivity \
  -e "ssid" "TestWiFi" -e "password" "test123" \
  -e "proxy_host" "proxy.com" -e "proxy_port" "99999"
```

## Permissions Required

Already available trong AndroidManifest.xml:
- ✅ `ACCESS_WIFI_STATE`
- ✅ `CHANGE_WIFI_STATE`
- ✅ `ACCESS_NETWORK_STATE`
- ✅ `INTERNET`

## Limitations & Assumptions

### What's NOT included:
- ❌ Connection verification
- ❌ Timeout handling
- ❌ Result broadcasting
- ❌ Retry logic
- ❌ PAC proxy support
- ❌ Proxy authentication
- ❌ Network priority management

### Design Assumptions:
- Android system sẽ handle actual connection process
- User chấp nhận rằng activity chỉ initiate connection
- ADB logcat đủ cho monitoring và debugging
- Proxy settings sẽ được apply khi connection thành công

## Implementation Status

- [ ] Create WiFiConnectActivity.java
- [ ] Implement parameter parsing
- [ ] Implement WiFi configuration
- [ ] Implement proxy configuration
- [ ] Add to AndroidManifest.xml
- [ ] Test với ADB commands
- [ ] Verify trên Android 8-9 devices

## Notes

- Implementation đơn giản, focused và minimal
- Không có UI components hoặc user interaction
- Silent execution phù hợp cho automation use cases
- Proxy support chỉ manual (host:port), không có authentication
- Compatible với existing AutoX architecture patterns