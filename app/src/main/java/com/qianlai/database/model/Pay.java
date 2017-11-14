package com.qianlai.database.model;

import org.litepal.crud.DataSupport;

/**
 * Created by LGB on 2017/11/11.
 */

public class Pay extends DataSupport {

    private long payTime;
    private int payType;
    private String payText;
    private String contentDescription;
    private double payMoney;

    public Pay(long payTime, int payType, double payMoney) {
        this.payTime = payTime;
        this.payType = payType;
        this.payMoney = payMoney;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayText() {
        return payText;
    }

    public void setPayText(String payText) {
        this.payText = payText;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

}
