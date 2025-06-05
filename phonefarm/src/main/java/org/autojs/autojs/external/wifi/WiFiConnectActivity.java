package org.autojs.autojs.external.wifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ProxyInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Activity for WiFi connection automation via ADB commands
 * Supports Android 8-9 (API 26-28) with optional HTTP proxy configuration
 * 
 * Usage examples:
 * adb shell am start -n org.autojs.autoxjs/.external.wifi.WiFiConnectActivity \
 *   -e "ssid" "MyWiFi" -e "password" "password123"
 *   
 * adb shell am start -n org.autojs.autoxjs/.external.wifi.WiFiConnectActivity \
 *   -e "ssid" "MyWiFi" -e "password" "password123" \
 *   -e "proxy_host" "proxy.company.com" -e "proxy_port" "8080"
 */
public class WiFiConnectActivity extends Activity {
    
    private static final String TAG = "WiFiConnectActivity";
    
    // Intent extras
    public static final String EXTRA_SSID = "ssid";
    public static final String EXTRA_PASSWORD = "password";
    public static final String EXTRA_SECURITY = "security";
    public static final String EXTRA_HIDDEN = "hidden";
    public static final String EXTRA_PROXY_HOST = "proxy_host";
    public static final String EXTRA_PROXY_PORT = "proxy_port";
    public static final String EXTRA_PROXY_BYPASS = "proxy_bypass";
    
    // Security types
    private static final String SECURITY_WPA2 = "WPA2";
    private static final String SECURITY_WPA = "WPA";
    private static final String SECURITY_WEP = "WEP";
    private static final String SECURITY_OPEN = "OPEN";
    
    // Supported Android versions
    private static final int MIN_SUPPORTED_API = Build.VERSION_CODES.O;        // Android 8.0
    private static final int MAX_SUPPORTED_API = Build.VERSION_CODES.P;        // Android 9.0
    
    private WifiManager wifiManager;
    private String ssid;
    private String password;
    private String security;
    private boolean isHidden;
    private String proxyHost;
    private int proxyPort;
    private String proxyBypass;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(TAG, "WiFiConnectActivity started");
        
        try {
            // Step 1: Validate Android version
            validateAndroidVersion();
            
            // Step 2: Parse intent parameters
            parseIntentParameters();
            
            // Step 3: Initialize WiFi manager
            initializeWifiManager();
            
            // Step 4: Create and execute WiFi connection
            executeConnection();
            
        } catch (Exception e) {
            Log.e(TAG, "WiFi connection failed: " + e.getMessage(), e);
            showToast("WiFi connection failed: " + e.getMessage());
        } finally {
            // Always finish activity
            finish();
        }
    }
    
    private void validateAndroidVersion() {
        int currentApi = Build.VERSION.SDK_INT;
        
        if (currentApi < MIN_SUPPORTED_API || currentApi > MAX_SUPPORTED_API) {
            throw new UnsupportedOperationException(
                String.format("Unsupported Android version. Requires API %d-%d, current: %d", 
                    MIN_SUPPORTED_API, MAX_SUPPORTED_API, currentApi)
            );
        }
        
        Log.d(TAG, "Android version validated: API " + currentApi);
    }
    
    private void parseIntentParameters() {
        Intent intent = getIntent();
        
        // Required parameters
        ssid = intent.getStringExtra(EXTRA_SSID);
        password = intent.getStringExtra(EXTRA_PASSWORD);
        
        if (TextUtils.isEmpty(ssid) || TextUtils.isEmpty(password)) {
            throw new IllegalArgumentException("Both SSID and password are required");
        }
        
        // Optional parameters
        security = intent.getStringExtra(EXTRA_SECURITY);
        if (TextUtils.isEmpty(security)) {
            security = SECURITY_WPA2; // Default to WPA2
        }
        
        isHidden = intent.getBooleanExtra(EXTRA_HIDDEN, false);
        
        // Proxy parameters
        proxyHost = intent.getStringExtra(EXTRA_PROXY_HOST);
        proxyPort = intent.getIntExtra(EXTRA_PROXY_PORT, 8080);
        proxyBypass = intent.getStringExtra(EXTRA_PROXY_BYPASS);
        
        Log.d(TAG, String.format("Parameters - SSID: %s, Security: %s, Hidden: %s, Proxy: %s:%d", 
            ssid, security, isHidden, proxyHost, proxyPort));
    }
    
    private void initializeWifiManager() {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            throw new RuntimeException("WiFi service not available");
        }
        
        // Enable WiFi if disabled
        if (!wifiManager.isWifiEnabled()) {
            Log.d(TAG, "Enabling WiFi...");
            wifiManager.setWifiEnabled(true);
        }
    }
    
    private void executeConnection() {
        // Create WiFi configuration
        WifiConfiguration config = createWifiConfiguration();
        
        // Add proxy if specified
        if (!TextUtils.isEmpty(proxyHost)) {
            ProxyInfo proxyInfo = createProxyConfiguration();
            config.setHttpProxy(proxyInfo);
            Log.d(TAG, "Proxy configured: " + proxyHost + ":" + proxyPort);
        }
        
        // Add network to WiFi manager
        int networkId = wifiManager.addNetwork(config);
        if (networkId == -1) {
            throw new RuntimeException("Failed to add WiFi network configuration");
        }
        
        // Enable the network
        boolean enabled = wifiManager.enableNetwork(networkId, true);
        if (!enabled) {
            throw new RuntimeException("Failed to enable WiFi network");
        }
        
        Log.i(TAG, String.format("WiFi connection initiated successfully - SSID: %s, NetworkID: %d", 
            ssid, networkId));
        showToast("WiFi connection initiated: " + ssid);
    }
    
    private WifiConfiguration createWifiConfiguration() {
        WifiConfiguration config = new WifiConfiguration();
        
        // Basic configuration
        config.SSID = "\"" + ssid + "\"";
        config.hiddenSSID = isHidden;
        
        // Security configuration
        switch (security.toUpperCase()) {
            case SECURITY_OPEN:
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                break;
                
            case SECURITY_WEP:
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                config.wepKeys[0] = "\"" + password + "\"";
                config.wepTxKeyIndex = 0;
                break;
                
            case SECURITY_WPA:
            case SECURITY_WPA2:
            default:
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                config.preSharedKey = "\"" + password + "\"";
                break;
        }
        
        Log.d(TAG, "WiFi configuration created for SSID: " + ssid);
        return config;
    }
    
    private ProxyInfo createProxyConfiguration() {
        if (TextUtils.isEmpty(proxyBypass)) {
            return ProxyInfo.buildDirectProxy(proxyHost, proxyPort);
        } else {
            // Parse bypass list (comma-separated)
            List<String> bypassList = Arrays.asList(proxyBypass.split(","));
            // Trim whitespace from each item
            for (int i = 0; i < bypassList.size(); i++) {
                bypassList.set(i, bypassList.get(i).trim());
            }
            return ProxyInfo.buildDirectProxy(proxyHost, proxyPort, bypassList);
        }
    }
    
    private void showToast(String message) {
        // Show toast for debugging purposes
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}