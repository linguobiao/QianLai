package com.qianlai;

import android.content.Intent;

import com.lgb.xpro.helper.ContextHelper;
import com.lgb.xpro.helper.LoggerHelper;
import com.qianlai.global.NotifyManager;
import com.qianlai.service.BLEService;
import com.qianlai.util.KeyUtil;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by LGB on 2017/10/30.
 */

public class App extends LitePalApplication {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ContextHelper.getInstance().init(this);
        LoggerHelper.init(true);
        LitePal.initialize(this);
        NotifyManager.getInstance().initNotify(this);
        KeyUtil.initSpManager(this);
        startService(new Intent(this, BLEService.class));
//        PgyCrashManager.register(this);
    }

    public static App getInstance() {
        return instance;
    }
}
