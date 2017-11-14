package com.lgb.xpro.utils;

import android.content.Context;
import android.util.Log;

/** 
 * 根据资源的名字获取其ID值 
 * @author lingb 
 * 
 */  
public class MResource {
    /**
     * 通过type获取相应的资源
     * @param context
     *              上下文
     * @param idType
     *              支持 layout 、id、string、drawable、array、dimen
     * @param name
     *              资源ID名称
     * @return
     */
    private static int getIdByName(Context context, String idType, String name) {
        return context.getResources().getIdentifier(name,idType, context.getPackageName());
    }

    public static int getLayout(Context context, String name) {
        return MResource.getIdByName(context, "layout", name);
    }

    public static int getId(Context context, String name) {
        return MResource.getIdByName(context, "id", name);
    }

    public static int getString(Context context, String name) {
        return MResource.getIdByName(context, "string", name);
    }

    public static int getDrawable(Context context, String name) {
        return MResource.getIdByName(context, "drawable", name);
    }

    public static int getArray(Context context, String name) {
        return MResource.getIdByName(context, "array", name);
    }

    public static int getDimen(Context context, String name) {
        return MResource.getIdByName(context, "dimen", name);
    }
}  
