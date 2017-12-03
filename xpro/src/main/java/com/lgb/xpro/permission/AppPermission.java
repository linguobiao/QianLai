package com.lgb.xpro.permission;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.Request;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LW on 2017/8/4.
 *
 *   权限调用方式，暂时只提供Activity，后续可以扩展
     AppPermission.build(context,new AppPermissionListener() {
        @Override
        public void success() {
            // 权限申请成功或者默认已经拥有需要的权限，都会都到这个逻辑，进行具体业务逻辑处理
            // 权限申请失败逻辑，底层已经处理好，不需要关心。 如果申请失败，不能进入下一步业务逻辑
        }
    },AppPermissionGroup.CAMERA);
 *   一般建议在 lunch 方法调用
 */

public class AppPermission {

    private Activity activity;
    private String [] permissions;
    private AppPermissionListener listener;

    public static void build(final Activity activity, AppPermissionListener listener, String[]... permissions){
        if(null == permissions || permissions.length == 0) return;
        List<String> requirePermissionsList = new ArrayList<>();
        for(String[] strings : permissions){
            for(String permission : strings){
                requirePermissionsList.add(permission);
            }
        }
        String [] requirePermissions = new String[requirePermissionsList.size()];
        for(int i=0;i<requirePermissionsList.size();i++){
            requirePermissions[i] = requirePermissionsList.get(i);
        }

        build(activity,listener,requirePermissions);
    }

    public static void build(final Activity activity, AppPermissionListener listener, String... permissions){
        Assert.assertNotNull(activity);
        Assert.assertNotNull(listener);
        Assert.assertNotNull(permissions);

        new AppPermission.Builder()
                .with(activity)
                .permissions(permissions)
                .rationale(new AppRationaleListener(){
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(activity, rationale).show();
                    }
                })
                .callback(listener).build();
    }

    private AppPermission(Builder builder){
        this.activity = builder.activity;
        this.permissions = builder.permissions;
        this.listener = builder.listener;

        runtimePermissionCheck(builder);
    }

    private void runtimePermissionCheck(Builder builder){
        // 判断是否有权限
        if(!AndPermission.hasPermission(builder.activity, permissions)) {
            // 申请权限
            Request request = AndPermission.with(builder.activity);
            request.requestCode(builder.requestCode);
            request.permission(builder.permissions);
            if(null != builder.rationaleListener) request.rationale(builder.rationaleListener);
            request.callback(appPermissionListener);
            request.start();
        } else {
            if(null != listener) listener.success();
        }
    }

    private static final int REQUEST_CODE = 111;
    private static final int REQUEST_CODE_SETTING = 112;
    private PermissionListener appPermissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantedPermissions) {
            // 权限申请成功回调。
            if(requestCode == REQUEST_CODE) {
                if(!AndPermission.hasPermission(activity, grantedPermissions)) {
                    // 使用AndPermission提供的默认设置dialog，用户点击确定后会打开App的设置页面让用户授权。
                    AndPermission.defaultSettingDialog(activity,REQUEST_CODE_SETTING).show();
                } else {
                    if(null != listener) listener.success();
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            // 权限申请失败回调。
            if(requestCode == REQUEST_CODE) {
                if(!AndPermission.hasPermission(activity, deniedPermissions)) {
                    AndPermission.defaultSettingDialog(activity,REQUEST_CODE_SETTING).show();
                } else {
                    if(null != listener) listener.success();
                }
            }
        }
    };

    public static class Builder{
        private Activity activity;
        private int requestCode = REQUEST_CODE;
        private String [] permissions;
        private AppPermissionListener listener = null;
        private AppRationaleListener rationaleListener = null;

        public Builder with(@NonNull Activity activity){
            this.activity = activity;
            return this;
        }
        public Builder permissions(@NonNull String... permissions){
            this.permissions = permissions;
            return this;
        }
        public Builder callback(@NonNull AppPermissionListener listener){
            this.listener = listener;
            return this;
        }
        public Builder rationale(AppRationaleListener rationaleListener){
            this.rationaleListener = rationaleListener;
            return this;
        }
        public AppPermission build(){
            return new AppPermission(this);
        }
    }
}
