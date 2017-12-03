package com.qianlai.ble;

import com.inuker.bluetooth.library.connect.options.BleConnectOptions;

import java.util.UUID;

/**
 * Created by LGB on 2017/11/20.
 */

public class BleHelper {

    //        Service: 00001800-0000-1000-8000-00805f9b34fb
//        Service: 00001801-0000-1000-8000-00805f9b34fb
//            Character: BleGattCharacter{uuid=00002a05-0000-1000-8000-00805f9b34fb, property=32, permissions=0,
//            BleGattDescriptor{mUuid=00002902-0000-1000-8000-00805f9b34fb, mPermissions=0, mValue=null}]}
//        Service: 0000ffe0-0000-1000-8000-00805f9b34fb
//            Character: BleGattCharacter{uuid=0000ffe1-0000-1000-8000-00805f9b34fb, property=22, permissions=0,
//            BleGattDescriptor{mUuid=00002902-0000-1000-8000-00805f9b34fb, mPermissions=0, mValue=null},
//            BleGattDescriptor{mUuid=00002901-0000-1000-8000-00805f9b34fb, mPermissions=0, mValue=null}]}

    public static final UUID UUID_SERVICE_1800 = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_SERVICE_1801 = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_SERVICE_ffe0 = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_CHARACTER_2a05 = UUID.fromString("00002a05-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_CHARACTER_ffe1 = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_DESCRIPTOR_2901 = UUID.fromString("00002901-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_DESCRIPTOR_2902 = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    public static final BleConnectOptions options = new BleConnectOptions.Builder()
            .setConnectRetry(0)
            .setConnectTimeout(8000)
            .setServiceDiscoverRetry(0)
            .setServiceDiscoverTimeout(5000)
            .build();

    public static final BleConnectOptions options_back = new BleConnectOptions.Builder()
            .setConnectRetry(6 * 5)
            .setConnectTimeout(10 * 1000)
            .setServiceDiscoverRetry(0)
            .setServiceDiscoverTimeout(5000)
            .build();

}
