# Kế hoạch triển khai module LTT

## Mục tiêu
Tạo module Android độc lập thực thi script dưới nền qua intent

## Cấu trúc module
```plaintext
AutoX/
└── ltt/
    ├── src/main/
    │   ├── java/org/autojs/ltt/
    │   │   ├── RunIntentActivity.java
    │   │   ├── ScriptIntents.java
    │   │   └── ScriptExecutionIntentService.java
    │   ├── res/
    │   └── AndroidManifest.xml
    └── build.gradle.kts
```

## File chính

### 1. RunIntentActivity.java
```java
package org.autojs.ltt;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import java.io.FileNotFoundException;

public class RunIntentActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        finish();
    }

    private void handleIntent(Intent intent) {
        // Logic xử lý intent và thực thi script
    }
}
```

### 2. ScriptIntents.java
```java
package org.autojs.ltt;

import android.content.Context;
import android.content.Intent;
import com.stardust.autojs.script.ScriptSource;
import com.stardust.autojs.script.StringScriptSource;

public class ScriptIntents {
    public static void handleIntent(Context context, Intent intent) {
        // Logic xử lý intent
    }
}
```

### 3. ScriptExecutionIntentService.java
```java
package org.autojs.ltt;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

public class ScriptExecutionIntentService extends IntentService {
    public ScriptExecutionIntentService() {
        super("ScriptExecutionIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            ScriptIntents.handleIntent(this, intent);
        }
    }
}
```

### 4. AndroidManifest.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="org.autojs.ltt">

    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    
    <application
        android:allowBackup="true"
        android:theme="@android:style/Theme.Translucent.NoTitleBar">
        
        <activity android:name=".RunIntentActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.RUN_SCRIPT"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="*/*"/>
            </intent-filter>
        </activity>

        <service android:name=".ScriptExecutionIntentService"/>
    </application>
</manifest>
```

### 5. build.gradle.kts
```kotlin
plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "org.autojs.ltt"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.autojs.ltt"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":autojs"))
    implementation("androidx.appcompat:appcompat:1.6.1")
}
```

## Các bước triển khai
1. Tạo cấu trúc thư mục module `ltt`
2. Tạo các file nguồn theo cấu trúc đã định
3. Đăng ký module trong `settings.gradle.kts`:
   ```kotlin
   include(":ltt")
   ```
4. Build và kiểm thử module

## Kiểm thử
Gửi intent từ ứng dụng khác:
```java
Intent intent = new Intent("android.intent.action.RUN_SCRIPT");
intent.setClassName("org.autojs.ltt", "org.autojs.ltt.RunIntentActivity");
intent.setData(Uri.parse("file:///sdcard/script.js"));
startActivity(intent);