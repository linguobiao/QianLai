package com.lgb.xpro.helper;

import com.lgb.xpro.logger.LogLevel;
import com.lgb.xpro.logger.Logger;
import com.lgb.xpro.logger.Settings;

/**
 * Created by LGB on 2017/11/11.
 */

public class LoggerHelper {

    public static void init(boolean isShowLog){
        Settings settings = Logger.init("xLog");
        settings.logLevel(isShowLog ? LogLevel.FULL : LogLevel.NONE);
    }
}
