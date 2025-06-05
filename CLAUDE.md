# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Mục đích dự án
AutoX là ứng dụng Android cho phép thực thi JavaScript scripts trên thiết bị Android. Đây là fork cải tiến từ Auto.js với tính năng automation, accessibility services, OCR (PaddleOCR/ML Kit), và OpenCV.

## Lệnh Build và Development

### Build APK
```bash
./gradlew assembleDebug            # Build debug APK
./gradlew assembleRelease          # Build release APK  
./gradlew assembleCommon           # Build common flavor (org.autojs.autoxjs)
./gradlew assembleV6               # Build v6 flavor (org.autojs.autoxjs.v6)
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

- **`:app`** - Module ứng dụng chính, chứa UI và activities
- **`:autojs`** - Core JavaScript runtime engine (Rhino), xử lý thực thi scripts
- **`:automator`** - Xử lý automation và accessibility services  
- **`:common`** - Shared utilities và base classes
- **`:inrt`** - Runtime module có thể build thành template APK độc lập
- **`:apkbuilder`** - Đóng gói JavaScript scripts thành APK
- **`:paddleocr`** - Module OCR với native C++ (JNI)

## Thông tin quan trọng

### JavaScript Engine
- Sử dụng Rhino 1.7.14 (file: `app/libs/rhino-1.7.14-jdk7.jar`)
- JavaScript modules trong `autojs/src/main/assets/modules/`
- Init script: `autojs/src/main/assets/init.js`

### Product Flavors
- **common**: Phiên bản chính thức (package: org.autojs.autoxjs)
- **v6**: Phiên bản phát triển (package: org.autojs.autoxjs.v6)

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
Đang ở branch `app` (không phải `master`)