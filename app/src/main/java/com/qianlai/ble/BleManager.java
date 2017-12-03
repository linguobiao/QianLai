package com.qianlai.ble;

import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.search.SearchResult;
import com.lgb.xpro.helper.ToastHelper;
import com.qianlai.util.KeyUtil;

/**
 * Created by LGB on 2017/7/21.
 */

public class BleManager implements BleApi.Respone{

    private BleApi.Request request;
    public static final String BLE_NAME = "QianLai_1";

    //单例
    private static class Instance {public static final BleManager instance = new BleManager();}
    public static BleManager getInstance() {return BleManager.Instance.instance;}

    public void init() {
        BaseBle.getInstance().init(this, BleHelper.options, BleHelper.UUID_SERVICE_ffe0, BleHelper.UUID_CHARACTER_ffe1);
        request = BaseBle.getInstance();
    }

    public static boolean isConnect(String mac) {
        return ClientManager.getClient().getConnectStatus(mac) == Constants.STATUS_DEVICE_CONNECTED;
    }

    public static boolean isBleOpen() {
        return ClientManager.getClient().isBluetoothOpened();
    }

    public void scan() {
        String boundMac = KeyUtil.getBoundMac();
        request.scan(boundMac, BLE_NAME);
    }

    public void connect() {
        request.connect(null);
    }

    public void write() {
        BaseBle.getInstance().write(new byte[20]);
    }

    @Override
    public void onScanResult(SearchResult device, boolean isBound) {
        if (device == null && isBound) {
            ToastHelper.getInstance().showToast("未找到绑定设备");
            return;
        }
    }

    @Override
    public void onConnectResult(boolean result, String mac) {
        if (!result) {
            ToastHelper.getInstance().showToast("连接失败");
            return;
        }
        KeyUtil.setBoundMac(mac);
        ClientManager.getClient().registerConnectStatusListener(mac,bleConnectStatusListener);
    }

    @Override
    public void onWriteResult(boolean result) {
        if (!result) {
            ToastHelper.getInstance().showToast("写入失败");
            return;
        }
    }

    @Override
    public void onBleState(boolean on_off) {
        if (on_off) {
            scan();
        }
    }

    @Override
    public void onReInit() {
        init();
    }

    private BleConnectStatusListener bleConnectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {
            if (status == Constants.STATUS_CONNECTED) {
            }
            else if (status == Constants.STATUS_DISCONNECTED) {
            }
        }
    };

    public void clean() {
        String boundMac = KeyUtil.getBoundMac();
        ClientManager.getClient().unregisterConnectStatusListener(boundMac,bleConnectStatusListener);
        ClientManager.getClient().disconnect(boundMac);
        request.clean();
    }
}
