# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep script execution classes
-keep class org.autojs.ltt.** { *; }

# Keep AutoJs classes
-keep class com.stardust.autojs.** { *; }
-keep class org.autojs.autojs.** { *; }

# Common Android rules
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes EnclosingMethod