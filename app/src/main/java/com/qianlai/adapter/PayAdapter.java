package com.qianlai.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lgb.xpro.utils.DecimalUtil;
import com.qianlai.R;

import java.util.List;
/**
 * Created by LGB on 2017/7/24.
 */

public class PayAdapter extends BaseMultiItemQuickAdapter<PayAdapter.Bean, BaseViewHolder> {
    public PayAdapter(List<PayAdapter.Bean> list) {
        super(list);
        addItemType(Bean.TYPE_DATE, R.layout.item_rec_pay_date);
        addItemType(Bean.TYPE_TIME, R.layout.item_rec_pay_time);
        addItemType(Bean.TYPE_TOTAL, R.layout.item_rec_pay_total);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayAdapter.Bean item) {
        switch (helper.getItemViewType()) {
            case Bean.TYPE_DATE:
                helper.setText(R.id.tv_date, item.getDate());
                break;
            case Bean.TYPE_TIME:
                helper.setText(R.id.tv_time, item.getTime());
                helper.setText(R.id.tv_money, "收款：" + DecimalUtil.df_0_00().format(item.getMoney()) + "元");
                break;
            case Bean.TYPE_TOTAL:
                helper.setText(R.id.tv_money, "合计：" + DecimalUtil.df_0_00().format(item.getMoney()) + "元");
                break;
            default:break;
        }
    }


    public static class Bean implements MultiItemEntity {

        public static final int TYPE_DATE = 1;
        public static final int TYPE_TIME = 2;
        public static final int TYPE_TOTAL = 3;

        private int type;
        private String date;
        private String time;
        private double money;

        public Bean(int type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public Bean setDate(String date) {
            this.date = date;
            return this;
        }

        public String getTime() {
            return time;
        }

        public Bean setTime(String time) {
            this.time = time;
            return this;
        }

        public double getMoney() {
            return money;
        }

        public Bean setMoney(double money) {
            this.money = money;
            return this;
        }

        @Override
        public int getItemType() {
            return type;
        }
    }

}
