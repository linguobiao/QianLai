package com.qianlai.ble;

import com.inuker.bluetooth.library.BluetoothClient;
import com.qianlai.App;

/**
 */
public class ClientManager {

    private static BluetoothClient mClient;

    public static BluetoothClient getClient() {
        if (mClient == null) {
            synchronized (ClientManager.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(App.getInstance());
                }
            }
        }
        return mClient;
    }

    public static void clean() {
        mClient = null;
    }
}
