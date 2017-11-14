package com.lgb.xpro.utils;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.lgb.xpro.helper.ToastHelper;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;

/**
 * Created by LGB on 2017/7/26.
 */

public class FileHelper {

    /**
     * 文件根目录
     */
    public static final String PATH_ROOT = new StringBuffer()
            .append(Environment.getExternalStorageDirectory().getAbsolutePath())
            .append("/QianLai/").toString();

    /**
     * 文件夹目录
     */
    public static final String PATH_FILE = new StringBuffer()
            .append(PATH_ROOT)
            .append("log/").toString();

    //内存地址
    public static String root = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static boolean isSDCardOk() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)&&getAvailableStorage()>1000000) {
            ToastHelper.getInstance().showToast("SD卡不可用");
            return false;
        }
        return true;
    }

    /** 获取SD可用容量 */
    private static long getAvailableStorage() {

        StatFs statFs = new StatFs(root);
        long blockSize = statFs.getBlockSize();
        long availableBlocks = statFs.getAvailableBlocks();
        long availableSize = blockSize * availableBlocks;
        // Formatter.formatFileSize(context, availableSize);
        return availableSize;
    }

    public static File newFile(String fileName) {
        if (!isSDCardOk()) return null;
        setMkdir(PATH_FILE);
        File file = new File(PATH_FILE, fileName);
        return file;
    }

    /**
     * 创建目录
     * @param path
     */
    public static void setMkdir(String path) {
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
            Log.e("file", "目录不存在  创建目录    ");
        }else{
            Log.e("file", "目录存在");
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if(!file.exists()) return;
        file.delete();
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void flush(Flushable flushable) {
        if (flushable != null) {
            try {
                flushable.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
