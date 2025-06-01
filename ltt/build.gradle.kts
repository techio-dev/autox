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
        ndk {
            abiFilters.addAll(listOf("arm64-v8a", "armeabi-v7a"))
        }
    }

    splits {
        // Configures multiple APKs based on ABI.
        abi {
            // Enables building multiple APKs per ABI.
            isEnable = true

            // Resets the list of ABIs that Gradle should create APKs for to none.
            reset()

            // Specifies a list of ABIs that Gradle should create APKs for.
            include("armeabi-v7a", "arm64-v8a")

            // Specifies that we do want to also generate a universal APK that includes all ABIs.
            isUniversalApk = true
        }
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
    configurations.all {
        resolutionStrategy.force("androidx.core:core:1.9.0")
        exclude(group = "org.jetbrains", module = "annotations-java5")
    }
}

dependencies {
    implementation(project(":autojs"))
    implementation(project(":common"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("org.apache.commons:commons-lang3:3.12.0")
}