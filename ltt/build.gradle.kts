plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "org.autojs.ltt"
    compileSdk = versions.compile

    defaultConfig {
        applicationId = "org.autojs.ltt"
        minSdk = versions.mini
        targetSdk = versions.target
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":autojs"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("org.apache.commons:commons-lang3:3.12.0")
}