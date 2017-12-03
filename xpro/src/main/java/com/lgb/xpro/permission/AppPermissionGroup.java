package com.lgb.xpro.permission;

import android.Manifest;
import android.os.Build;

import com.yanzhenjie.permission.Permission;

/**
 * Created by LW on 2017/8/9.
 *
 * 对Android O运行时权限策略变化,比如申请相机权限，相关相机权限组的都会被申请
 */

public class AppPermissionGroup {

    /**
     *  读写日历
     */
    public static final String[] CALENDAR = Permission.CALENDAR;

    /**
     *  相机权限组
     */
    public static final String[] CAMERA  = Permission.CAMERA;

    /**
     *  读写联系人
     */
    public static final String[] CONTACTS  = Permission.CONTACTS;

    /**
     *  读位置信息
     */
    public static final String[] LOCATION  = Permission.LOCATION;

    /**
     *  使用麦克风
     */
    public static final String[] MICROPHONE  = Permission.MICROPHONE;

    /**
     *  读电话状态、打电话、读写电话记录
     */
    public static final String[] PHONE  = Build.VERSION.SDK_INT < Build.VERSION_CODES.O ? new String[]{ Manifest.permission.CALL_PHONE } : Permission.PHONE;

    /**
     *  传感器
     */
    public static final String[] SENSORS  = Permission.SENSORS;

    /**
     *  读写短信、收发短信
     */
    public static final String[] SMS  = Build.VERSION.SDK_INT < Build.VERSION_CODES.O ? new String[]{ Manifest.permission.SEND_SMS } : Permission.SMS;

    /**
     *  读写存储卡
     */
    public static final String[] STORAGE  = Build.VERSION.SDK_INT < Build.VERSION_CODES.O ? new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE } : Permission.STORAGE;

}
