package com.qianlai.bean;

/**
 * Created by LGB on 2017/10/30.
 */

public class PayBean {

//    EventType: TYPE_NOTIFICATION_STATE_CHANGED; EventTime: 101913344; PackageName: com.tencent.mm; MovementGranularity: 0; Action: 0 [ ClassName: android.app.Notification; Text: [微信支付: 二维码收款到账0.01元]; ContentDescription: null; ItemCount: -1; CurrentItemIndex: -1; IsEnabled: false; IsPassword: false; IsChecked: false; IsFullScreen: false; Scrollable: false; BeforeText: null; FromIndex: -1; ToIndex: -1; ScrollX: -1; ScrollY: -1; MaxScrollX: -1; MaxScrollY: -1; AddedCount: -1; RemovedCount: -1; ParcelableData: Notification(pri=1 contentView=null vibrate=[] sound=null tick defaults=0x0 flags=0x101 color=0x00000000 category=msg vis=PRIVATE) ]; recordCount: 0

    //EventType: TYPE_WINDOW_CONTENT_CHANGED; EventTime: 66576707; PackageName: com.eg.android.AlipayGphone; MovementGranularity: 0; Action: 0 [ ClassName: android.widget.TableLayout; Text: []; ContentDescription: 支付助手: ￥0.01 二维码收款到账通知; ItemCount: -1; CurrentItemIndex: -1; IsEnabled: true; IsPassword: false; IsChecked: false; IsFullScreen: false; Scrollable: false; BeforeText: null; FromIndex: -1; ToIndex: -1; ScrollX: -1; ScrollY: -1; MaxScrollX: -1; MaxScrollY: -1; AddedCount: -1; RemovedCount: -1; ParcelableData: null ]; recordCount: 1

    public PayBean(String payTime, String payText, String contentDescription) {
        this.payTime = payTime;
        this.payText = payText;
        this.contentDescription = contentDescription;
        this.payMoney = payMoney;
    }

    private String payTime;
    private String payText;
    private String contentDescription;
    private String payMoney;

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
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

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }
}
