package com.lgb.xpro.utils;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.widget.DrawerLayout;
import android.view.WindowManager;

/**
 * 本类包含改变Activiy外观的方法
 */

public class ActivityUIChangeUitl {
    /**
     *  用于设置手机状态栏透明
     *  使用要点：
     *  此方法只能用于包含toolbar样式的界面
     *  此方法要在Activity的onCreate()中使用
     *  因为版本兼容需添加values-19文件，详情看代码结构
     *  添加上面文件后，在toolar中添加android:paddingTop="@dimen/toolbar_padding_top"属性
     *  DrawerLayout 中要添加android:fitsSystemWindows="true"属性
     * @param activity      activity
     * @param mDrawerLayout 带侧栏的父视图
     */
    public static void setPhoneBarHyaline(Activity activity, DrawerLayout mDrawerLayout){
        if (VERSION.SDK_INT >=VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            if(VERSION.SDK_INT < VERSION_CODES.LOLLIPOP){
                //将侧边栏顶部延伸至status bar
                mDrawerLayout.setFitsSystemWindows(true);
                //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
                mDrawerLayout.setClipToPadding(false);
            }
        }
    }

    /**
     *  用于设置手机状态栏透明
     *  使用方法同上，没有DrawerLayout，所以没有对DrawerLayout的处理
     * @param activity      activity
     */
    public static void setPhoneBarHyaline(Activity activity){
        if (VERSION.SDK_INT >=VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    /**
     * 设置添加屏幕的背景透明度(分享菜单出来时用到)
     * 0.0f为全黑，1.0f为全透
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity activity,float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }
}
