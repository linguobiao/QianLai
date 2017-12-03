package com.qianlai.ble;

import com.inuker.bluetooth.library.search.SearchResult;

/**
 * Created by LGB on 2017/11/21.
 */

public interface BleApi {

    interface Request {
        void scan(String boundMac, String bleName);
        void connect(String mac);
        void write(byte[] bytes);
        void clean();
    }

    interface Respone {
        void onScanResult(SearchResult device, boolean isBound);
        void onConnectResult(boolean result, String mac);
        void onWriteResult(boolean result);
        void onBleState(boolean on_off);
        void onReInit();
    }

}
