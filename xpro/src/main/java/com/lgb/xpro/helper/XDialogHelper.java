package com.lgb.xpro.helper;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgb.xpro.R;

/**
 * Created by LGB on 2017/5/8.
 */

public class XDialogHelper {

    private Activity appActivity;
    private static XDialogHelper instance;
    private XDialogHelper(){}

    public static synchronized XDialogHelper getInstance() {
        if (instance == null) {
            instance = new XDialogHelper();
        }
        return instance;
    }

    // 标题
    private TextView tv_dialog_title;
    private RelativeLayout ly_dialog_title;
    private View view_dialog_title_line, view_dialog_content_line;
    // 内容
    private TextView tv_dialog_content;
    private LinearLayout ly_dialog_content;
    // 按钮
    private TextView tv_dialog_ok;
    private TextView tv_dialog_cancel;
    private RelativeLayout ly_dialog_ok;
    private RelativeLayout ly_dialog_cancel;
    private ImageView iv_dialog_close;
    private OnUiueDialogCommitListener commitListener;
    private OnUiueDialogCloseListener closeListener;

    public Dialog alertDialog;
    private void initAlertDialog(Activity activity) {
        this.appActivity = activity;
        alertDialog = new Dialog(appActivity, R.style._xDialog);
        View view = appActivity.getLayoutInflater().inflate(R.layout._dialog_def, null);
        //标题
        tv_dialog_title = (TextView) view.findViewById(R.id.tv_dialog_title);
        ly_dialog_title = (RelativeLayout) view.findViewById(R.id.ly_dialog_title);
        view_dialog_title_line = view.findViewById(R.id.view_dialog_title_line);
        view_dialog_content_line = view.findViewById(R.id.view_dialog_content_line);
        //内容
        tv_dialog_content = (TextView) view.findViewById(R.id.tv_dialog_content);
        ly_dialog_content = (LinearLayout) view.findViewById(R.id.ly_dialog_content);
        //按钮
        tv_dialog_ok = (TextView) view.findViewById(R.id.tv_dialog_ok);
        ly_dialog_ok = (RelativeLayout) view.findViewById(R.id.ly_dialog_ok);
        tv_dialog_cancel = (TextView) view.findViewById(R.id.tv_dialog_cancel);
        ly_dialog_cancel = (RelativeLayout) view.findViewById(R.id.ly_dialog_cancel);
        iv_dialog_close = (ImageView) view.findViewById(R.id.iv_dialog_close);
        ly_dialog_ok.setOnClickListener(alertDialogClickListener);
        ly_dialog_cancel.setOnClickListener(alertDialogClickListener);
        iv_dialog_close.setOnClickListener(alertDialogClickListener);

        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(view);
        alertDialog.setCancelable(false);
    }

    /**
     * 设置弹出框的信息，默认样式，只有一个文字按钮
     * @param title 标题
     * @param contentMsg 文字内容
     * @param view 自定义view
     * @param btnMsgOk 按钮文字
     * @param commitListener 确定按钮点击监听
     * @param closeListener 取消按钮点击监听
     */
    private void initDefStyle(String title, String contentMsg, View view, String btnMsgOk, OnUiueDialogCommitListener commitListener, OnUiueDialogCloseListener closeListener){
        if(null == alertDialog) return;
        //标题，标题为空时，隐藏标题和两条线，变成内容+确定按钮样式
        tv_dialog_title.setText(title);
        if (TextUtils.isEmpty(title)) {
            ly_dialog_title.setVisibility(View.GONE);
            view_dialog_title_line.setVisibility(View.GONE);
            view_dialog_content_line.setVisibility(View.GONE);
        } else {
            ly_dialog_title.setVisibility(View.VISIBLE);
            view_dialog_title_line.setVisibility(View.VISIBLE);
            view_dialog_content_line.setVisibility(View.VISIBLE);
        }
        //内容，当有自定义view时，将view添加进来并隐藏默认内容，没有时则还原回默认样式
        tv_dialog_content.setText(contentMsg);
        ly_dialog_content.removeAllViews();    //移除ly_content下所有的view
        if (view != null) {                 //有自定义的view，添加到ly_content
            ly_dialog_content.addView(view);
        } else {                            //没有自定义的view，把ly_content下所有默认的view加回来
            ly_dialog_content.addView(tv_dialog_content);     //添加回原来的TextView
        }
        //按钮
        tv_dialog_ok.setText(btnMsgOk);
        this.commitListener = commitListener;
        this.closeListener = closeListener;
        if (this.closeListener == null) {
            alertDialog.setCancelable(true);
        } else {
            alertDialog.setCancelable(false);
        }
        show();
    }

    /**
     * 设置弹出框的信息，默认样式，只有一个文字按钮
     * @param title 标题
     * @param contentMsg 文字内容
     * @param view 自定义view
     * @param btnMsgOk 按钮文字
     * @param commitListener 确定按钮点击监听
     * @param closeListener 取消按钮点击监听
     */
    private void setMsgToAlertDialog(Activity activity,String title, String contentMsg, View view, String btnMsgOk, OnUiueDialogCommitListener commitListener, OnUiueDialogCloseListener closeListener){
        this.initAlertDialog(activity);
        initDefStyle(title, contentMsg, view, btnMsgOk, commitListener, closeListener);
    }

    /**
     * 设置弹出框的信息，取消按钮文字、图片， 确定按钮文字、图片
     * @param title 标题
     * @param contentMsg 文字内容
     * @param view 自定义view
     * @param btnMsgCancel 取消按钮 文字
     * @param btnImgCancel 取消按钮 图片
     * @param btnMsgOk 确定按钮 文字
     * @param btnImgOk 确定按钮 图片
     * @param commitListener 确定按钮点击监听
     * @param closeListener 取消按钮点击监听
     */
    private void setMsgToAlertDialog(Activity activity,String title, String contentMsg, View view, String btnMsgCancel, Drawable btnImgCancel, String btnMsgOk, Drawable btnImgOk, OnUiueDialogCommitListener commitListener, OnUiueDialogCloseListener closeListener){
        this.initAlertDialog(activity);
        if(null == alertDialog) return;
        //按钮
        tv_dialog_cancel.setText(btnMsgCancel);
        tv_dialog_cancel.setCompoundDrawablesWithIntrinsicBounds(btnImgCancel, null, null, null);
        tv_dialog_ok.setCompoundDrawablesWithIntrinsicBounds(btnImgOk, null, null, null);
        if (TextUtils.isEmpty(btnMsgCancel) && btnImgCancel == null) {
            ly_dialog_cancel.setVisibility(View.GONE);
        } else {
            ly_dialog_cancel.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(btnMsgOk) && btnImgOk == null) {
            ly_dialog_ok.setVisibility(View.GONE);
        } else {
            ly_dialog_ok.setVisibility(View.VISIBLE);
        }
        initDefStyle(title, contentMsg, view, btnMsgOk, commitListener, closeListener);
    }

    /**
     * 弹出框事件监听
     */
    private View.OnClickListener alertDialogClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.ly_dialog_ok){
                if (commitListener != null) commitListener.onCommit();
                else alertDialog.dismiss();
            } else if(v.getId() == R.id.iv_dialog_close || v.getId() == R.id.ly_dialog_cancel){
                if (closeListener != null) closeListener.onClose();
                else alertDialog.dismiss();
            }
        }
    };

    //---------------------------------- 默认样式 start------------------------------------------------
    /**
     * 默认样式
     */
    public void showDialog(Activity activity,String title,String contentMsg,String btnMsg){
        setMsgToAlertDialog(activity , title, contentMsg, null, btnMsg, null, null);
    }

    /**
     * 默认样式
     * 自定义确定
     */
    public void showDialog(Activity activity,String title,String contentMsg,String btnMsg, OnUiueDialogCommitListener commitListener){
        setMsgToAlertDialog(activity,title, contentMsg, null, btnMsg, commitListener, null);
    }

    /**
     * 默认样式
     * 自定义取消
     */
    public void showDialog(Activity activity,String title,String contentMsg,String btnMsg, OnUiueDialogCloseListener closeListener){
        setMsgToAlertDialog(activity,title, contentMsg, null, btnMsg, null, closeListener);
    }

    /**
     * 默认样式
     * 自定义确定、取消
     */
    public void showDialog(Activity activity,String title,String contentMsg,String btnMsg, OnUiueDialogCommitListener commitListener, OnUiueDialogCloseListener closeListener){
        setMsgToAlertDialog(activity,title, contentMsg, null, btnMsg, commitListener, closeListener);
    }

    /**
     * 默认样式，两个按钮
     */
    public void showDialog(Activity activity,String title, String contentMsg, String btnMsgCancel, Drawable btnImgCancel, String btnMsgOk, Drawable btnImgOk){
        setMsgToAlertDialog(activity,title, contentMsg, null, btnMsgCancel, btnImgCancel, btnMsgOk, btnImgOk, null, null);
    }

    /**
     * 默认样式，两个按钮
     * 自定义确定
     */
    public void showDialog(Activity activity,String title, String contentMsg, String btnMsgCancel, Drawable btnImgCancel, String btnMsgOk, Drawable btnImgOk, OnUiueDialogCommitListener commitListener){
        setMsgToAlertDialog(activity,title, contentMsg, null, btnMsgCancel, btnImgCancel, btnMsgOk, btnImgOk, commitListener, null);
    }

    /**
     * 默认样式，两个按钮
     * 自定义取消点击事件
     */
    public void showDialog(Activity activity,String title, String contentMsg, String btnMsgCancel, Drawable btnImgCancel, String btnMsgOk, Drawable btnImgOk, OnUiueDialogCloseListener closeListener){
        setMsgToAlertDialog(activity,title, contentMsg, null, btnMsgCancel, btnImgCancel, btnMsgOk, btnImgOk, null, closeListener);
    }

    /**
     * 默认样式，两个按钮
     * 自定义确定、取消
     */
    public void showDialog(Activity activity,String title, String contentMsg, String btnMsgCancel, Drawable btnImgCancel, String btnMsgOk, Drawable btnImgOk, OnUiueDialogCommitListener commitListener, OnUiueDialogCloseListener closeListener){
        setMsgToAlertDialog(activity,title, contentMsg, null, btnMsgCancel, btnImgCancel, btnMsgOk, btnImgOk, commitListener, closeListener);
    }
    //---------------------------------- 默认样式 end------------------------------------------------

    //---------------------------------- 自定义view样式 start----------------------------------------
    /**
     * 自定义view样式
     */
    public void showDialog(Activity activity,String title, View view, String btnMsg){
        setMsgToAlertDialog(activity,title, null, view, btnMsg, null, null);
    }

    /**
     * 自定义view样式
     * 自定义确定
     */
    public void showDialog(Activity activity,String title, View view, String btnMsg, OnUiueDialogCommitListener commitListener){
        setMsgToAlertDialog(activity,title, null, view, btnMsg, commitListener, null);
    }

    /**
     * 自定义view样式
     * 自定义取消
     */
    public void showDialog(Activity activity,String title, View view, String btnMsg, OnUiueDialogCloseListener closeListener){
        setMsgToAlertDialog(activity,title, null, view, btnMsg, null, closeListener);
    }

    /**
     * 自定义view样式
     * 自定义确定、取消
     */
    public void showDialog(Activity activity,String title, View view, String btnMsg, OnUiueDialogCommitListener commitListener, OnUiueDialogCloseListener closeListener){
        setMsgToAlertDialog(activity,title, null, view,  btnMsg, commitListener, closeListener);
    }

    /**
     * 自定义view样式，两个按钮
     */
    public void showDialog(Activity activity,String title, View view, String btnMsgCancel, Drawable btnImgCancel, String btnMsgOk, Drawable btnImgOk){
        setMsgToAlertDialog(activity,title, null, view, btnMsgCancel, btnImgCancel, btnMsgOk, btnImgOk, null, null);
    }

    /**
     * 自定义view样式，两个按钮
     * 自定义确定
     */
    public void showDialog(Activity activity,String title, View view, String btnMsgCancel, Drawable btnImgCancel, String btnMsgOk, Drawable btnImgOk, OnUiueDialogCommitListener commitListener){
        setMsgToAlertDialog(activity,title, null, view, btnMsgCancel, btnImgCancel, btnMsgOk, btnImgOk, commitListener, null);
    }

    /**
     * 自定义view样式，两个按钮
     * 自定义取消
     */
    public void showDialog(Activity activity,String title, View view, String btnMsgCancel, Drawable btnImgCancel, String btnMsgOk, Drawable btnImgOk, OnUiueDialogCloseListener closeListener){
        setMsgToAlertDialog(activity,title, null, view, btnMsgCancel, btnImgCancel, btnMsgOk, btnImgOk, null, closeListener);
    }

    /**
     * 自定义view样式，两个按钮
     * 自定义确定、取消
     */
    public void showDialog(Activity activity,String title, View view, String btnMsgCancel, Drawable btnImgCancel, String btnMsgOk, Drawable btnImgOk, OnUiueDialogCommitListener commitListener, OnUiueDialogCloseListener closeListener){
        setMsgToAlertDialog(activity,title, null, view, btnMsgCancel, btnImgCancel, btnMsgOk, btnImgOk, commitListener, closeListener);
    }
    //---------------------------------- 自定义view样式 end----------------------------------------

    public void hide() {
        if (alertDialog != null) alertDialog.hide();
    }

    public void show() {if(null != alertDialog) alertDialog.show();}

    public void dismiss() {
        if (alertDialog != null) alertDialog.dismiss();
    }

    public void setCancelable(boolean cancelable) {if (alertDialog != null) alertDialog.setCancelable(cancelable);}

    public interface OnUiueDialogCloseListener {void onClose();}

    public interface OnUiueDialogCommitListener {void onCommit();}

}
