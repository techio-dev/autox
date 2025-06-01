# Module LTT - Background Script Runner

## Mô tả
Module LTT là phiên bản đơn giản hóa của AutoJS chỉ tập trung vào việc chạy script dưới nền thông qua intent.

## Cấu trúc
- `RunIntentActivity`: Activity nhận intent và khởi chạy script
- `ScriptIntents`: Logic xử lý intent và thực thi script  
- `ScriptExecutionIntentService`: Service chạy nền

## Cách sử dụng

### 1. Build APK
```bash
./gradlew :ltt:assembleDebug
```

### 2. Gửi intent từ ứng dụng khác
```java
Intent intent = new Intent();
intent.setClassName("org.autojs.ltt", "org.autojs.ltt.RunIntentActivity");
intent.setData(Uri.parse("file:///path/to/script.js"));
startActivity(intent);
```

### 3. Hoặc sử dụng content URI
```java
Intent intent = new Intent();
intent.setClassName("org.autojs.ltt", "org.autojs.ltt.RunIntentActivity");
intent.setData(contentUri);
startActivity(intent);
```

## Quyền cần thiết
- `READ_EXTERNAL_STORAGE`: Đọc file script
- `WRITE_EXTERNAL_STORAGE`: Ghi log/output
- `MANAGE_EXTERNAL_STORAGE`: Quản lý storage
- `INTERNET`: Kết nối mạng nếu cần

## Lưu ý
- Module này phụ thuộc vào module `autojs` core
- Không có giao diện người dùng
- Tự động finish activity sau khi xử lý