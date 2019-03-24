package com.xiaogege.jerry.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.xiaogege.jerry.model.Constants.XML_DEFAULT_VALUE;

public class XmlIOUtils {

    private XmlIOUtils(){
    }

    public static void xmlPut(String key, String value, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences (context).edit ();
        editor.putString (key, value);
        editor.apply ();
    }

    public static String xmlGet(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (context);
        return preferences.getString (key, XML_DEFAULT_VALUE);
    }
}
