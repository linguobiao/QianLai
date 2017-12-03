package com.qianlai.ble;

import android.text.TextUtils;

import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.lgb.xpro.logger.Logger;

import java.util.Arrays;
import java.util.UUID;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;

/**
 * Created by LGB on 2017/7/21.
 */

public class BaseBle implements BleApi.Request{

    private BleApi.Respone respone;
    private BleConnectOptions connectOpt;
    private UUID uuidService;
    private UUID uuidCharacter;
    private String boundMac;
    private String bleName;

    //单例
    private static class Instance {public static final BaseBle instance = new BaseBle();}
    public static BaseBle getInstance() {return BaseBle.Instance.instance;}

    public void init(BleApi.Respone respone, BleConnectOptions connectOpt, UUID uuidService, UUID uuidCharacter) {
        this.respone = respone;
        this.connectOpt = connectOpt;
        this.uuidService = uuidService;
        this.uuidCharacter = uuidCharacter;
        ClientManager.getClient().registerBluetoothStateListener(bluetoothStateListener);
    }

    private BluetoothStateListener bluetoothStateListener = new BluetoothStateListener() {
        @Override
        public void onBluetoothStateChanged(boolean openOrClosed) {
            respone.onBleState(openOrClosed);
        }
    };

    @Override
    public void scan(String boundMac, String bleName) {
        if (!isValueOk()) return;
        if (!ClientManager.getClient().isBluetoothOpened()) {
            ClientManager.getClient().openBluetooth();
            return;
        }
        if (!TextUtils.isEmpty(boundMac)) {
            if (ClientManager.getClient().getConnectStatus(boundMac) == Constants.STATUS_CONNECTED)
                return;
        }
        this.boundMac = boundMac;
        this.bleName = bleName;
        ClientManager.getClient().stopSearch();
        SearchRequest request = new SearchRequest.Builder().searchBluetoothLeDevice(10000, 1).build();
        ClientManager.getClient().search(request, mSearchResponse);
    }

    @Override
    public void connect(String mac) {
        if (!isValueOk()) return;
        boundMac = mac;
        ClientManager.getClient().connect(mac, connectOpt, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile profile) {
                if (code == REQUEST_SUCCESS) {
                    Logger.e("connected success");
                    openNotify();
                } else {
                    Logger.e("connected fail");
                    respone.onConnectResult(false, boundMac);
                    ClientManager.getClient().disconnect(boundMac);
                }
            }
        });
    }

    @Override
    public void write(byte[] bytes) {
        if (!isValueOk()) return;
        ClientManager.getClient().write(boundMac, uuidService, uuidCharacter, bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int code) {
                if (code == REQUEST_SUCCESS) {
                    respone.onWriteResult(true);
                } else {
                    respone.onWriteResult(false);
                }
            }
        });
    }

    @Override
    public void clean() {
        ClientManager.getClient().unregisterBluetoothStateListener(bluetoothStateListener);
        ClientManager.clean();
    }

    private final SearchResponse mSearchResponse = new SearchResponse() {

        @Override public void onSearchStarted() {Logger.e("onSearchStarted:");}

        @Override public void onDeviceFounded(SearchResult device) {
            if (device == null) return;
            Logger.e("onDeviceFounded:" + device.getName() + ",  " + device.device.getAddress());
            if (!TextUtils.isEmpty(boundMac)) {
                if (boundMac.equals(device.getAddress())) {
                    ClientManager.getClient().stopSearch();
                    respone.onScanResult(device,true);
                }
            } else if (!TextUtils.isEmpty(bleName)) {
                if (bleName.equals(device.getName())) {
                    respone.onScanResult(device,false);
                }
            } else {
                respone.onScanResult(device,false);
            }
        }

        @Override public void onSearchStopped() {
            Logger.e("onSearchStopped:");
            respone.onScanResult(null, !TextUtils.isEmpty(boundMac));
        }

        @Override public void onSearchCanceled() {
            Logger.e("onSearchCanceled:");
            respone.onScanResult(null, !TextUtils.isEmpty(boundMac));
        }
    };

    private void openNotify() {
        ClientManager.getClient().notify(boundMac, uuidService, uuidCharacter, new BleNotifyResponse() {

            @Override public void onNotify(UUID service, UUID character, byte[] value) {Logger.e("notify : " + Arrays.toString(value));}

            @Override
            public void onResponse(int code) {
                if (code == REQUEST_SUCCESS) {
                    respone.onConnectResult(true, boundMac);
                } else {
                    respone.onConnectResult(false, boundMac);
                    ClientManager.getClient().disconnect(boundMac);
                }
            }
        });
    }

    private boolean isValueOk() {
        if (respone == null || connectOpt == null || uuidService == null || uuidCharacter == null) {
            respone.onReInit();
            return false;
        }
        return true;
    }



}
