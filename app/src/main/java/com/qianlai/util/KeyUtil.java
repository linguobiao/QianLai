package com.qianlai.util;

import android.content.Context;
import android.text.TextUtils;

import com.lgb.xpro.utils.AppUtils;
import com.lgb.xpro.utils.FileHelper;
import com.qianlai.global.Global;

import app.framework.util.SpManager;

/**
 * Created by LGB on 2017/11/10.
 */

public class KeyUtil {
    public static final String SP_WECHAT_PAY = "SP_WECHAT_PAY";
    public static final String KEY_WECHAT_PAY = "KEY_WECHAT_PAY";

    public static final String SP_QIANLAI = "SP_QIANLAI";
    public static final String KEY_VERSION = "KEY_VERSION";

    public static final String KEY_BOUND_MAC = "KEY_BOUND_MAC";

    public static void initSpManager(Context context) {
        String lastVersion = SpManager.getInstance().readString(SP_QIANLAI, KEY_VERSION);
        String currentVersion = AppUtils.getVersionName(context);
        if (TextUtils.isEmpty(currentVersion)) return;
        if (currentVersion.equals(lastVersion)) return;
        SpManager.getInstance().writeString(SP_QIANLAI, KEY_VERSION, currentVersion);
        FileHelper.deleteFile(FileHelper.PATH_FILE + "payLog.txt");
    }

    public static String getBoundMac() {return SpManager.getInstance().readString(SP_QIANLAI, KEY_BOUND_MAC);}
    public static void setBoundMac(String mac) {SpManager.getInstance().writeString(SP_QIANLAI, KEY_BOUND_MAC, mac);}
}
