package com.qianlai.util;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.lgb.xpro.utils.AppUtils;
import com.lgb.xpro.utils.FileHelper;
import com.qianlai.App;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LGB on 2017/11/6.
 */

public class StringUtil {

    public static String parseMoney(String str) {
        String money;
        // 需要取整数和小数的字符串
        // 控制正则表达式的匹配行为的参数(小数)
        Pattern p = Pattern.compile("(\\d+\\.\\d+)");
        //Matcher类的构造方法也是私有的,不能随意创建,只能通过Pattern.matcher(CharSequence input)方法得到该类的实例.
        Matcher m = p.matcher(str);
        //m.find用来判断该字符串中是否含有与"(\\d+\\.\\d+)"相匹配的子串
        if (m.find()) {
            //如果有相匹配的,则判断是否为null操作
            //group()中的参数：0表示匹配整个正则，1表示匹配第一个括号的正则,2表示匹配第二个正则,在这只有一个括号,即1和0是一样的
            money = m.group(1) == null ? "" : m.group(1);
        } else {
            //如果匹配不到小数，就进行整数匹配
            p = Pattern.compile("(\\d+)");
            m = p.matcher(str);
            if (m.find()) {
                //如果有整数相匹配
                money = m.group(1) == null ? "" : m.group(1);
            } else {
                //如果没有小数和整数相匹配,即字符串中没有整数和小数，就设为空
                money = "";
            }
        }
        Log.e("test", "金额：" + money);
        System.out.println(money);
        return money;
    }

    public static String getContent(AccessibilityEvent event) {
        List<CharSequence> textList = event.getText();
        if (textList != null && textList.size() > 0) {
            String text = textList.get(0).toString();
            return text;
        }
        return null;
    }

    public static void saveLog(String content, String des) {
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
}
