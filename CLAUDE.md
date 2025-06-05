# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Mục đích dự án
PhoneFarm là ứng dụng Android độc lập được fork từ AutoX, chuyên hóa cho automation và device management. Ứng dụng cho phép thực thi JavaScript scripts với tính năng đặc biệt về WiFi automation, accessibility services, OCR (PaddleOCR/ML Kit), và OpenCV.

**Khác biệt với AutoX gốc:**
- Package name: `com.phonefarm.autoxjs` (thay vì `org.autojs.autoxjs`)
- Tập trung vào phone farm automation và WiFi management 
- Có WiFiConnectActivity riêng cho Android 8-9 devices
- Phát triển độc lập, không merge về AutoX master branch

## Lệnh Build và Development

### Build APK
```bash
./gradlew :phonefarm:assembleDebug            # Build debug APK
./gradlew :phonefarm:assembleRelease          # Build release APK  
./gradlew :phonefarm:assembleCommon           # Build common flavor (com.phonefarm.autoxjs)
./gradlew :phonefarm:assembleV6               # Build v6 flavor (com.phonefarm.autoxjs.v6)
```

### Build Template APK
```bash
./gradlew buildTemplateApp         # Build template APK release
./gradlew buildDebugTemplateApp    # Build template APK debug
```

### Test và Lint
```bash
./gradlew test                     # Run unit tests
./gradlew connectedAndroidTest     # Run instrumented tests
./gradlew lint                     # Run lint checks
./gradlew lintFix                 # Auto-fix lint issues
./gradlew check                   # Run all checks (tests + lint)
```

### Clean
```bash
./gradlew clean                    # Clean build directories
```

## Kiến trúc Module

Dự án gồm 7 module chính:

- **`:phonefarm`** - Module ứng dụng chính PhoneFarm, chứa UI và activities (thay thế :app)
- **`:autojs`** - Core JavaScript runtime engine (Rhino), xử lý thực thi scripts
- **`:automator`** - Xử lý automation và accessibility services  
- **`:common`** - Shared utilities và base classes
- **`:inrt`** - Runtime module có thể build thành template APK độc lập
- **`:apkbuilder`** - Đóng gói JavaScript scripts thành APK
- **`:paddleocr`** - Module OCR với native C++ (JNI)

## Thông tin quan trọng

### JavaScript Engine
- Sử dụng Rhino 1.7.14 (file: `phonefarm/libs/rhino-1.7.14-jdk7.jar`)
- JavaScript modules trong `autojs/src/main/assets/modules/`
- Init script: `autojs/src/main/assets/init.js`

### Product Flavors  
- **common**: Phiên bản chính thức (package: com.phonefarm.autoxjs)
- **v6**: Phiên bản phát triển (package: com.phonefarm.autoxjs.v6)

### Tính năng đặc biệt của PhoneFarm
- **WiFiConnectActivity**: Kết nối WiFi via ADB cho Android 8-9
- **Phone Farm Automation**: Tối ưu cho quản lý nhiều thiết bị
- **Enhanced Device Management**: Cải tiến cho automation scenarios

### Native Libraries
- OpenCV: `LocalRepo/OpenCV/opencv-4.5.5.aar`
- PaddleOCR: `LocalRepo/PaddleOCR4Android/PaddleOCR4Android-1.0.1.aar`
- Native code OCR: `paddleocr/src/main/cpp/`

### Known Issues (v6.6.1)
- App crash khi mở
- Bug thiếu file .so trong APK đóng gói
- Cần sửa code map thành forEach tại một số chỗ

### Cấu hình Build
- Min SDK: 21
- Target SDK: 28  
- Compile SDK: 34
- Kotlin: 1.6.21
- JDK: 17
- Android Studio: 2023.3.1 Patch 2

### Branch hiện tại
Đang ở branch `feature/phonefarm` - phát triển ứng dụng PhoneFarm độc lập

### Workflow PhoneFarm
- **Không merge về master**: PhoneFarm là ứng dụng riêng biệt
- **Independent development**: Phát triển tách biệt khỏi AutoX gốc
- **Phone farm focused**: Chuyên biệt cho use cases automation thiết bị