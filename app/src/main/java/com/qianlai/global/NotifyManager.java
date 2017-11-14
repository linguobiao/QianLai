package com.qianlai.global;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.lgb.xpro.utils.AppUtils;
import com.qianlai.App;
import com.qianlai.R;
import com.qianlai.main.MainActivity;

/**
 * Created by LGB on 2017/7/27.
 */

public class NotifyManager {

    private static NotifyManager instance;
    private NotificationManager mNotificationManager;
    private boolean isBackground = false;

    private NotifyManager(){}

    public static synchronized NotifyManager getInstance() {
        if (instance == null) {
            instance = new NotifyManager();
        }
        return instance;
    }

    public void clean() {
        if (mNotificationManager != null) {
            mNotificationManager.cancelAll();
            mNotificationManager = null;
        }
    }

    public void sendNotify(String value) {
//        if (!isBackground) return;
        String content = "";
        content = App.getInstance().getString(R.string.app_name) + "：微信收款到账" + value + "元";
        if (mNotificationManager == null) mNotificationManager = (NotificationManager) App.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(App.getInstance())
                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)//使用默认的震动和声音
                .setContentTitle(content)
//                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(getClickIntent()); //设置通知栏点击意图

        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(100, notification);

    }

    private PendingIntent getClickIntent(){
        Intent intent = new Intent(App.getInstance(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(App.getInstance(), 0, intent, 0);
        return pendingIntent;
    }

    public void initNotify(Application application) {
        AppUtils.initActivityLifecycleCallbacks(application, new AppUtils.OnAppStateListener() {
            @Override public void onBackgroundState() {isBackground = true;}

            @Override public void onForegroundState() {isBackground = false;}
        });
    }

}
