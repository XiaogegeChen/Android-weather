package com.xiaogege.jerry.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast sToast;

    public static void showToast(Context context, String message){
        if(sToast == null){
            sToast = Toast.makeText (context, message, Toast.LENGTH_SHORT);
        }else{
            sToast.setText (message);
        }
        sToast.show ();
    }
}
