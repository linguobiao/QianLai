package com.qianlai.util;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by LGB on 2017/11/10.
 */

public class AppUtil {

//    /**
//     * 检测辅助功能是否开启<br>
//     * @param mContext
//     * @return boolean
//     */
//    private boolean isAccessibilitySettingsOn(Context mContext) {
//        int accessibilityEnabled = 0;
//        // TestService为对应的服务
//        final String service = mContext.getPackageName() + "/" + TestService.class.getCanonicalName();
//        Log.i(TAG, "service:" + service);
//        // com.z.buildingaccessibilityservices/android.accessibilityservice.AccessibilityService
//        try {
//            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(),
//                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
//            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
//        } catch (Settings.SettingNotFoundException e) {
//            Log.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
//        }
//        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
//
//        if (accessibilityEnabled == 1) {
//            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
//            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
//                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
//            // com.z.buildingaccessibilityservices/com.z.buildingaccessibilityservices.TestService
//            if (settingValue != null) {
//                mStringColonSplitter.setString(settingValue);
//                while (mStringColonSplitter.hasNext()) {
//                    String accessibilityService = mStringColonSplitter.next();
//
//                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
//                    if (accessibilityService.equalsIgnoreCase(service)) {
//                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
//                        return true;
//                    }
//                }
//            }
//        } else {
//            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
//        }
//        return false;
//    }

    // 此方法用来判断当前应用的辅助功能服务是否开启
    public static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }

}
