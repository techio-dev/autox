# PhoneFarm - Android Automation App

PhoneFarm là ứng dụng Android automation độc lập được fork từ AutoX, được thiết kế đặc biệt cho phone farm management và device automation.

## 🚀 Tính năng chính

### Core Features
- **JavaScript Runtime**: Thực thi JavaScript scripts với Rhino engine
- **Accessibility Services**: Automation thông qua accessibility APIs
- **OCR Integration**: PaddleOCR và ML Kit cho text recognition
- **Computer Vision**: OpenCV cho image processing

### PhoneFarm Specialties
- **WiFi Automation**: Kết nối WiFi via ADB commands (Android 8-9)
- **Device Management**: Tối ưu cho quản lý nhiều thiết bị  
- **Phone Farm Focused**: Chuyên biệt cho automation scenarios
- **Silent Operations**: Theme.NoDisplay cho background automation

## 📱 Build Instructions

### Prerequisites
- Android Studio 2023.3.1+
- JDK 17
- Kotlin 1.6.21+
- Gradle 8.0+

### Build Commands
```bash
# Build debug APK
./gradlew :phonefarm:assembleDebug

# Build release APK  
./gradlew :phonefarm:assembleRelease

# Build specific flavors
./gradlew :phonefarm:assembleCommon           # com.phonefarm.autoxjs
./gradlew :phonefarm:assembleV6               # com.phonefarm.autoxjs.v6

# Build template APK
./gradlew buildTemplateApp
```

## 🏗️ Architecture

### Modules
- **`:phonefarm`** - Main application module với UI và activities
- **`:autojs`** - JavaScript runtime engine (Rhino)
- **`:automator`** - Automation và accessibility services  
- **`:common`** - Shared utilities
- **`:inrt`** - Independent runtime template
- **`:apkbuilder`** - Script packaging
- **`:paddleocr`** - OCR với native C++ components

### Package Structure
```
com.phonefarm.autoxjs           # Common release version
com.phonefarm.autoxjs.v6        # Development version
```

## ⚡ WiFi Automation Feature

### WiFiConnectActivity
Tính năng đặc biệt cho kết nối WiFi thông qua ADB commands:

```bash
# Basic WiFi connection
adb shell am start -n com.phonefarm.autoxjs/.external.wifi.WiFiConnectActivity \
  -e "ssid" "MyWiFi" -e "password" "password123"

# WiFi with HTTP proxy
adb shell am start -n com.phonefarm.autoxjs/.external.wifi.WiFiConnectActivity \
  -e "ssid" "MyWiFi" -e "password" "password123" \
  -e "proxy_host" "proxy.company.com" -e "proxy_port" "8080"
```

**Supported Platforms**: Android 8-9 (API 26-28)

## 🔧 Development

### Project Structure
```
phonefarm/
├── src/main/
│   ├── java/org/autojs/autojs/
│   │   ├── external/wifi/WiFiConnectActivity.java
│   │   └── ... (other activities)
│   ├── AndroidManifest.xml
│   └── assets/
├── build.gradle.kts
└── libs/
    └── rhino-1.7.14-jdk7.jar
```

### Key Configuration Files
- `phonefarm/build.gradle.kts` - Build configuration
- `phonefarm/src/main/AndroidManifest.xml` - App manifest
- `settings.gradle` - Module inclusion
- `CLAUDE.md` - Development guidance

## 📄 License

Kế thừa license từ AutoX project.

## 🤝 Contributing

PhoneFarm là independent fork phát triển riêng, không merge về AutoX master branch.

### Development Workflow
1. **Main branch**: `phonefarm` (không phải `master`)
2. **Feature development**: Tạo branches từ `phonefarm`, merge về `phonefarm`
3. Focus vào phone farm automation use cases  
4. Maintain compatibility với Android 8-9 devices
5. Independent versioning và release cycle

### Branch Strategy
```
phonefarm (main)
├── feature/wifi-enhancements
├── feature/device-management  
└── feature/automation-scripts
```

## 🎯 Use Cases

PhoneFarm được thiết kế cho:
- Phone farm automation scenarios
- Bulk device management
- WiFi configuration automation via ADB
- Silent background operations
- Device testing automation

---

**Note**: PhoneFarm là ứng dụng độc lập, không liên quan đến AutoX development roadmap.