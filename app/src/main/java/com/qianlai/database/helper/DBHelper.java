package com.qianlai.database.helper;

import android.text.TextUtils;

import com.qianlai.database.model.Pay;
import com.qianlai.event.PayEvent;
import com.qianlai.global.NotifyManager;
import com.qianlai.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by LGB on 2017/11/11.
 */

public class DBHelper {

    public static void save(int type, String content, String description){
        if (content == null) return;
        String money = StringUtil.parseMoney(content);
        if (TextUtils.isEmpty(money)) return;
        Pay pay = new Pay(System.currentTimeMillis(), type, Double.parseDouble(money));
        pay.setPayText(content);
        pay.setContentDescription(description);
        pay.save();
        EventBus.getDefault().post(new PayEvent());
        NotifyManager.getInstance().sendNotify(money,type);
    }

}
