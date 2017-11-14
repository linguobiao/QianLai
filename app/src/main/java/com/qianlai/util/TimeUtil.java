package com.qianlai.util;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by lw
 */
public class TimeUtil {
    /**
     * 获取当前时间
     *
     * @param format "yyyy-MM-dd HH:mm:ss"
     * @return 当前时间
     */
    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间为本月的第几周
     *
     * @return WeekOfMonth
     */
    public static int getWeekOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        return week - 1;
    }

    /**
     * 获取当前时间为本周的第几天
     *
     * @return DayOfWeek
     */
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
        } else {
            day = day - 1;
        }
        return day;
    }

    /**
     * 获取当前时间的年份
     *
     * @return 年份
     */
    public static int getYear() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间的月份
     *
     * @return 月份
     */
    public static int getMonth() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 获取当前时间是哪天
     *
     * @return 哪天
     */
    public static int getDay() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 时间比较大小
     *
     * @param date1 date1
     * @param date2 date2
     * @param format "yyyy-MM-dd HH:mm:ss"
     * @return 1:date1大于date2；
     * -1:date1小于date2
     */
    public static int compareDate(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间加减
     *
     * @param day       如"2015-09-22"
     * @param dayAddNum 加减值
     * @return 结果
     */
    public static String timeAddSubtract(String day, int dayAddNum) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date newDate = new Date(simpleDateFormat.parse(day).getTime() + dayAddNum * 24 * 60 * 60 * 1000);
            return simpleDateFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 毫秒格式化
     * 使用unixTimestamp2BeijingTime方法
     *
     * @param millisecond 如"1449455517602"
     * @param format      如"yyyy-MM-dd HH:mm:ss"
     * @return 格式化结果
     */
    @Deprecated
    public static String millisecond2String(Object millisecond, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(millisecond);
    }

    /**
     * 时间戳转北京时间
     *
     * @param millisecond 如"1449455517602"
     * @param format      如"yyyy-MM-dd HH:mm:ss"
     * @return 格式化结果
     */
    public static String unixTimestamp2BeijingTime(Object millisecond, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(millisecond);
    }

    /**
     * 北京时间转时间戳
     * 注意第一个参数和第二个参数格式必须一样
     *
     * @param beijingTime 如"2016-6-26 20:35:9"
     * @param format      如"yyyy-MM-dd HH:mm:ss"
     * @return 时间戳
     */
    public static long beijingTime2UnixTimestamp(String beijingTime, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        long unixTimestamp = 0;
        try {
            Date date = simpleDateFormat.parse(beijingTime);
            unixTimestamp = date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return unixTimestamp;
    }

    private static boolean check_continue = false;
    public static void readSeconds(final Handler handler, final int seconds){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int time = seconds;
                while (isCheck_continue()){
                    if (time <= 60 && time > 0){
                        time --;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }finally {
                            handler.sendEmptyMessage(time);
                        }
                    }
                }
            }
        }).start();
    }

    public static void readSeconds(final TextView textView, final int seconds){
        textView.setClickable(false);
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int time = msg.what;
                textView.setText("重新获取" + "(" + time + "S)");
                if (time == 0){
                    textView.setClickable(true);
                    textView.setText("短信获取");
                }
            }
        };
        TimeUtil.setCheck_continue(true);
        TimeUtil.readSeconds(handler, 60);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int time = seconds;
                while (isCheck_continue()){
                    if (time <= 60 && time > 0){
                        time --;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }finally {
                            handler.sendEmptyMessage(time);
                        }
                    }
                }
            }
        }).start();
    }

    public static boolean isCheck_continue() {
        return check_continue;
    }

    public static void setCheck_continue(boolean check_continue2) {
        check_continue = check_continue2;
    }

    public static String parseTime(long time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(time);
    }

    public static boolean isSameDay(long time1, long time2) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTimeInMillis(time1);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTimeInMillis(time2);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                .get(Calendar.DAY_OF_MONTH);
    }
}
