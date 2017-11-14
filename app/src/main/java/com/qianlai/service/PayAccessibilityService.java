package com.qianlai.service;

import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lgb.xpro.utils.AppUtils;
import com.lgb.xpro.utils.FileHelper;
import com.qianlai.App;
import com.qianlai.bean.PayBean;
import com.qianlai.database.model.Pay;
import com.qianlai.event.PayEvent;
import com.qianlai.global.Global;
import com.qianlai.global.NotifyManager;
import com.qianlai.util.KeyUtil;
import com.qianlai.util.StringUtil;
import com.qianlai.util.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import app.framework.util.SpManager;

public class PayAccessibilityService extends BaseAccessibilityService {

    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {
        int eventType = event.getEventType();
        String packageName = event.getPackageName().toString();

        if ("com.tencent.mm".equals(packageName)) {
            Log.e("------微信---------", "" + event.toString());

            String content = getContent(event);
            CharSequence charTemp = event.getContentDescription();
            String contentDescription = (charTemp == null) ? "" : charTemp.toString();
            if (content != null && content.startsWith("微信支付")) {
                save(Global.PAY_TYPE_WECHAT, content, contentDescription);
                log(content, contentDescription);
            }
//            if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
//                save(Global.PAY_TYPE_WECHAT, "微信支付: 二维码收款到账0.01元", contentDescription);
//                saveContent(KEY_WX_NOTIFY, content, contentDescription);
//            }
        }

    }

    private void save(int type, String content, String description){
        if (content == null) return;
        String money = StringUtil.parseMoney(content);
        if (TextUtils.isEmpty(money)) return;
        Pay pay = new Pay(System.currentTimeMillis(), type, Double.parseDouble(money));
        pay.setPayText(content);
        pay.setContentDescription(description);
        pay.save();
        EventBus.getDefault().post(new PayEvent());
        NotifyManager.getInstance().sendNotify(money);
    }

    private void log(String content, String des) {
        File file = FileHelper.newFile("payLog.txt");
        if (file != null) {
            FileOutputStream fos = null;
            OutputStreamWriter osw = null;
            BufferedWriter bw = null;
            try {
                fos = new FileOutputStream(file, true);
                osw = new OutputStreamWriter(fos);
                bw = new BufferedWriter(osw);
                StringBuffer value = new StringBuffer();
                String time = TimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss");
                value.append(AppUtils.getVersionName(App.getInstance())).append("--").append(time).append("--text:").append(content).append(", des:").append(des).append("\r\n");
                bw.write(value.toString());
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                FileHelper.flush(bw);
                FileHelper.close(bw);
                FileHelper.close(osw);
                FileHelper.close(fos);
            }
        }
    }

    private void saveContent(String SpKey, String content,String contentDescription) {
        List<PayBean> list = SpManager.getInstance().fromJsonList(KeyUtil.SP_WECHAT_PAY, SpKey, PayBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(new PayBean(TimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"), content, contentDescription));
        SpManager.getInstance().writeObject(KeyUtil.SP_WECHAT_PAY, SpKey, list);
    }

    private String getContent(AccessibilityEvent event) {
        List<CharSequence> textList = event.getText();
        if (textList != null && textList.size() > 0) {
            String text = textList.get(0).toString();
            return text;
        }
        return null;
    }
}
