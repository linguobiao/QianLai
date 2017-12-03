package com.qianlai.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lgb.xpro.logger.Logger;
import com.qianlai.ble.BleManager;

/**
 * Created by LGB on 2017/11/20.
 */

public class BLEService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.e("BLEService onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Logger.e("BLEService onCreate");
        super.onCreate();
        BleManager.getInstance().init();
        BleManager.getInstance().scan();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.e("BLEService onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Logger.e("BLEService onDestroy");
        super.onDestroy();
    }
}
