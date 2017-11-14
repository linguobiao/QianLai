package com.qianlai.main;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lgb.xpro.helper.ToastHelper;
import com.lgb.xpro.helper.XDialogHelper;
import com.lgb.xpro.utils.DecimalUtil;
import com.qianlai.R;
import com.qianlai.adapter.PayAdapter;
import com.qianlai.database.model.Pay;
import com.qianlai.event.PayEvent;
import com.qianlai.global.Global;
import com.qianlai.global.NotifyManager;
import com.qianlai.util.AppUtil;
import com.qianlai.util.TimeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import app.framework.mvp.SimpleBaseActivity;
import butterknife.BindView;

public class MainActivity extends SimpleBaseActivity {

    @BindView(R.id.rv_pay) RecyclerView rv_pay;
    @BindView(R.id.tv_new_time) TextView tv_new_time;
    @BindView(R.id.tv_new_money) TextView tv_new_money;

    private PayAdapter payAdapter;
    private List<PayAdapter.Bean> payList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        rv_pay.setLayoutManager(new LinearLayoutManager(this));
        payAdapter = new PayAdapter(payList);
        rv_pay.setAdapter(payAdapter);

//        Pay pay = new Pay(System.currentTimeMillis() + 1000 * 60 * 60 * 96, Global.PAY_TYPE_WECHAT, 20.33);
//        pay.setPayText("测试数据库1");
//        pay.setContentDescription("测试数据库1--描述");
//        if (pay.save()) ToastHelper.getInstance().showToast("保存成功");
//        else ToastHelper.getInstance().showToast("保存失败");

        initList();
    }

    private void initList() {
        payList.clear();
        List<Pay> dbPayList = DataSupport.order("payTime desc").find(Pay.class);
        if (dbPayList != null) {
            long lastTime = -1;
            double moneyDay = 0;
            int size = dbPayList.size();
            for (int i = 0; i < size; i ++) {
                Pay pay = dbPayList.get(i);
                long time = pay.getPayTime();
                double money = pay.getPayMoney();
                if (lastTime == -1) {
                    moneyDay = money;
                    addDate(time, money);
                    addTime(time, money);
                    lastTime = time;
                    tv_new_money.setText(DecimalUtil.df_0_00().format(money));
                    tv_new_time.setText(TimeUtil.parseTime(time, "yyyy-MM-dd HH:mm:ss"));
                    if (i == size -1) {
                        addTotal(moneyDay);
                    }
                    continue;
                }

                if (TimeUtil.isSameDay(lastTime, time)) { //同一天
                    moneyDay = moneyDay + money;
                    addTime(time, money);
                } else {//不同一天
                    addTotal(moneyDay);
                    moneyDay = money;
                    addDate(time, money);
                    addTime(time, money);
                }
                if (i == size -1) {
                    addTotal(moneyDay);
                }
                lastTime = time;
            }
            payAdapter.notifyDataSetChanged();
        }
    }

    private void addDate(long time, double money) {
        payList.add(new PayAdapter.Bean(PayAdapter.Bean.TYPE_DATE).setDate(TimeUtil.parseTime(time, "yyyy-MM-dd")));
    }

    private void addTime(long time, double money) {
        payList.add(new PayAdapter.Bean(PayAdapter.Bean.TYPE_TIME).setTime(TimeUtil.parseTime(time, "HH:mm:ss")).setMoney(money));
    }

    private void addTotal(double money) {
        payList.add(new PayAdapter.Bean(PayAdapter.Bean.TYPE_TOTAL).setMoney(money));
    }

    @Subscribe
    public void onEventMainThread(PayEvent event) {
        initList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppUtil.isAccessibilitySettingsOn(this)) return;
        XDialogHelper.getInstance().showDialog(this, "提示", "辅助服务未开启，无法正常获取支付信息，请先开启", "去开启", new XDialogHelper.OnUiueDialogCommitListener() {
            @Override
            public void onCommit() {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                XDialogHelper.getInstance().dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
