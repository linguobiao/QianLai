package com.lgb.xpro.helper;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by LW on 2017/3/31.
 */

public class ToastHelper {

    private static Context context;

    private static ToastHelper instance;

    private ToastHelper(){}

    public static synchronized ToastHelper getInstance() {
        if(null == context) context = ContextHelper.getInstance().getApplicationContext();
        if (instance == null) {
            instance = new ToastHelper();
        }
        return instance;
    }

    public void showToast(String toastMsg){
        if(TextUtils.isEmpty(toastMsg)) return;
        Toast.makeText(context,toastMsg,Toast.LENGTH_LONG).show();
    }

    public void showToastByShort(String toastMsg){
        if(TextUtils.isEmpty(toastMsg)) return;
        Toast.makeText(context,toastMsg,Toast.LENGTH_SHORT).show();
    }
}
