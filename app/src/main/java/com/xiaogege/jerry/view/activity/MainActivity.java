package com.xiaogege.jerry.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaogege.jerry.R;
import com.xiaogege.jerry.util.XmlIOUtils;

import static com.xiaogege.jerry.model.Constants.SELECTED_LOCATION_KEY;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        if(Build.VERSION.SDK_INT>=21){
            getWindow ().setStatusBarColor (Color.parseColor ("#008577"));
        }

        setContentView (R.layout.activity_main);

        //拿到保存的城市代码,并传递给weatherActivity
        String selectedLocation = XmlIOUtils.xmlGet (SELECTED_LOCATION_KEY, this);
        if(selectedLocation != null){
            WeatherActivity.start (this, WeatherActivity.class, selectedLocation);
            finish ();
        }
    }
}
