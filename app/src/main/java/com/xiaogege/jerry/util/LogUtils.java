package com.xiaogege.jerry.util;

import android.util.Log;

import com.xiaogege.jerry.AppConfig;

public class LogUtils {

    public static void d(String tag,String message){
        if(AppConfig.LOG){
            Log.d (tag,message);
        }
    }
}
