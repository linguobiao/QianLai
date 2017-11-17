# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-printmapping build/outputs/mapping/release/mapping.txt

#-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
#-dontoptimize
-dontpreverify

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-keep class BASE64Encoder{*;}
-keep class CharacterEncoder{*;}

-dontwarn java.awt.**
-dontwarn javax.**
-keep class java.lang.Throwable.**

-keep class android.support.**{*;}
-dontwarn android.support.**

#--------------- BEGIN: Gson防混淆 ----------
-keep class sun.misc.Unsafe { *; }
-dontnote sun.misc.**
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.**{*;}
-keep class com.google.gson.**
-keep class com.google.gson.internal.UnsafeAllocator.**
-keep class android.os.ServiceManager.**

#iTextpdf
-keep   interface com.itextpdf.**
-keep   class com.itextpdf.**
-keep   class com.itextpdf.license.LicenseKey.**
-keep   class ccm.itextpdf.license.LicenseKey.** {*;}
-keep   class com.itextpdf.text.Version.**
-keep   class ccm.itextpdf.text.Version.** {*;}
-keep   class ccm.itextpdf.** {*;}
-dontwarn com.itextpdf.**
##apache
-keep  interface org.apache.**
-keep  class org.apache.**
-keep  class org.apache.** {*;}
-dontwarn org.apache.**
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
##butterknife
-keep   class com.sun.**
-keep   class butterknife.compiler.**
-keep   interface com.sun.**
# Butterknife Configurations
# Retain generated class which implement ViewBinder.
-keep public class * implements butterknife.internal.ViewBinder {public <init>(); }

## Prevent obfuscation of types which use ButterKnife annotations since the simple name
## is used to reflectively look up the generated ViewBinder.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keep class butterknife.** {
  *;
}
##BluetoothKit
-keep   class android.bluetooth.IBluetoothManager.**
-keep   class android.bluetooth.IBluetoothGatt.**
-keep   class android.bluetooth.IBluetoothManager.**{*;}
-keep   class android.bluetooth.IBluetoothGatt.**{*;}
-keep   interface com.inuker.**
-keep   class com.inuker.**
-keep   class com.inuker.bluetooth.library.utils.hook.**
-keep   class com.inuker.bluetooth.library.utils.hook.**{*;}
-keep   class com.inuker.**{*;}
-keep   class **.inuker.**
##SweetAlter
-keep   class cn.pedant.**{*;}
-keep   class cn.pedant.**

##EventBus-----------------
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keep class app.lgb.com.guoou.bean.** { *; }
-keep class app.lgb.com.guoou.bean.**
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(Java.lang.Throwable);
}
##EventBus-----------------

##litepal
-keep class org.litepal.** {
    *;
}

-keep class * extends org.litepal.crud.DataSupport {
    *;
}

##pgyer
##-libraryjars libs/pgyer_sdk_x.x.jar
#-dontwarn com.pgyersdk.**
#-keep class com.pgyersdk.** { *; }
#
##Rxjava
#-dontwarn sun.misc.**
#-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
# long producerIndex;
# long consumerIndex;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
# rx.internal.util.atomic.LinkedQueueNode producerNode;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
# rx.internal.util.atomic.LinkedQueueNode consumerNode;
#}