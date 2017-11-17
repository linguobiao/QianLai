package com.qianlai.service;

import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;

import com.lgb.xpro.logger.Logger;
import com.qianlai.database.helper.DBHelper;
import com.qianlai.global.Global;
import com.qianlai.util.StringUtil;

public class PayAccessibilityService extends BaseAccessibilityService {

    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();

        if ("com.tencent.mm".equals(packageName)) {
            String content = StringUtil.getContent(event);
            CharSequence charTemp = event.getContentDescription();
            String contentDescription = (charTemp == null) ? "" : charTemp.toString();
            if (!TextUtils.isEmpty(content) && content.startsWith("微信支付")) {
                DBHelper.save(Global.PAY_TYPE_WECHAT, content, contentDescription);
                StringUtil.saveLog(content, contentDescription);
            }
//            if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
//                save(Global.PAY_TYPE_WECHAT, "微信支付: 二维码收款到账0.01元", contentDescription);
//                saveContent(KEY_WX_NOTIFY, content, contentDescription);
//            }
        }
        else if("com.eg.android.AlipayGphone".equals(packageName)) {
            String content = StringUtil.getContent(event);
            CharSequence charTemp = event.getContentDescription();
            String contentDescription = (charTemp == null) ? "" : charTemp.toString();
            Logger.e("------支付宝-------" + content + ",  " + contentDescription);
            if (!TextUtils.isEmpty(content) && content.contains("向你付款")) {
                DBHelper.save(Global.PAY_TYPE_ALIPAY, content, contentDescription);
                StringUtil.saveLog(content, contentDescription);
            }
        }
    }
}
