package com.xiaogege.jerry.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xiaogege.jerry.R;
import com.xiaogege.jerry.util.XmlIOUtils;

import static com.xiaogege.jerry.model.Constants.IS_JUMP;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        if(Build.VERSION.SDK_INT>=21){
            getWindow ().setStatusBarColor (Color.parseColor ("#008577"));
        }

        setContentView (R.layout.activity_main);

        String isJump = XmlIOUtils.xmlGet (IS_JUMP, this);
        if(isJump != null){
            WeatherActivity.start (this, WeatherActivity.class, null);
            finish ();
        }
    }
}
