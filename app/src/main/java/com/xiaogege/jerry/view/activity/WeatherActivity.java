package com.xiaogege.jerry.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaogege.jerry.R;
import com.xiaogege.jerry.model.xml.AQI;
import com.xiaogege.jerry.model.xml.Forecast;
import com.xiaogege.jerry.model.xml.Now;
import com.xiaogege.jerry.model.xml.Suggestion;
import com.xiaogege.jerry.presenter.WeatherActivityPresenterImpl;
import com.xiaogege.jerry.util.ToastUtils;
import com.xiaogege.jerry.view.ViewContract;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xiaogege.jerry.model.Constants.INTENT_PARAM_NAME;

public class WeatherActivity extends AppCompatActivity implements ViewContract.WeatherActivityView {

    @BindView (R.id.bing_pic_img)
    ImageView mBackgroundImage;

    @BindView (R.id.title_city)
    TextView mTitleCity;

    @BindView (R.id.title_time)
    TextView mUpdateTime;

    @BindView (R.id.degree_text)
    TextView mDegreeText;

    @BindView (R.id.weather_info_text)
    TextView mWeatherInfoText;

    @BindView (R.id.forecast_layout)
    LinearLayout mForecastLayout;

    @BindView (R.id.aqi_text)
    TextView mAqiText;

    @BindView (R.id.pm25_text)
    TextView mPm25Text;

    @BindView (R.id.comfort_text)
    TextView mComfortText;

    @BindView (R.id.car_wash_text)
    TextView mCarWashText;

    @BindView (R.id.sport_text)
    TextView mSportText;

    @BindView (R.id.travel_text)
    TextView mTravelText;

    @BindView (R.id.weather_layout)
    ScrollView mWeatherLayout;

    @BindView (R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView (R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView (R.id.nav_button)
    Button mNavButton;

    private WeatherActivityPresenterImpl mWeatherActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow ().getDecorView ();
            decorView.setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow ().setStatusBarColor (Color.TRANSPARENT);
        }

        setContentView (R.layout.activity_weather);

        ButterKnife.bind (this);
        mWeatherActivityPresenter = new WeatherActivityPresenterImpl (this);

        mWeatherActivityPresenter.checkStatus ();

        //拿到传来的城市代码
        String Location = (String) getIntent ().getSerializableExtra (INTENT_PARAM_NAME);
        mWeatherActivityPresenter.setLocation (Location);
        mWeatherActivityPresenter.attach (this);

        //监听刷新
        mSwipeRefreshLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh() {
                showProgress ();
                mWeatherActivityPresenter.refresh ();
            }
        });

        //监听按钮，打开Drawer
        mNavButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer (GravityCompat.START);
            }
        });

        //显示页面
        mWeatherActivityPresenter.queryAll ();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        mWeatherActivityPresenter.detach ();
    }

    /**
     *  提供一个启动自身的静态方法
     * @param context 发起启动的活动
     * @param cls 被启动的活动
     * @param s 传递的数据
     */
    public static void start(Context context, Class<? extends AppCompatActivity> cls, Serializable s){
        Intent intent=new Intent (context,cls);
        intent.putExtra (INTENT_PARAM_NAME,s);
        context.startActivity (intent);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.showToast (this, message);
    }

    @Override
    public void showProgress() {
        if(!mSwipeRefreshLayout.isRefreshing ()){
            mSwipeRefreshLayout.setRefreshing (true);
        }
    }

    @Override
    public void showError() {
    }

    @Override
    public void showNow(Now now) {
        stopRefresh ();
        String degreeText = now.getTemperature ()+"℃";
        mTitleCity.setText (now.getLocation ());
        mUpdateTime.setText (now.getTime ());
        mDegreeText.setText (degreeText);
        mWeatherInfoText.setText (now.getCondition ());
    }

    @Override
    public void showForecast(Forecast forecast) {
        stopRefresh ();
        mForecastLayout.removeAllViews ();
        for(Forecast.ForecastInfo forecastInfo:forecast.getForecastInfoList ()){
            View view = LayoutInflater.from (this).inflate (R.layout.forecast_item, mForecastLayout, false);
            TextView dateText = view.findViewById (R.id.date_text);
            TextView infoText = view.findViewById (R.id.info_text);
            TextView maxText = view.findViewById (R.id.max_text);
            TextView minText = view.findViewById (R.id.min_text);
            dateText.setText (forecastInfo.getTime ());
            infoText.setText (forecastInfo.getCondition ());
            maxText.setText (forecastInfo.getMaxTem ());
            minText.setText (forecastInfo.getMinTem ());
            mForecastLayout.addView (view);
        }
    }

    @Override
    public void showAQI(AQI aqi) {
        stopRefresh ();
        mAqiText.setText (aqi.getAqi ());
        mPm25Text.setText (aqi.getPm25 ());
    }

    @Override
    public void showSuggestion(Suggestion suggestion) {
        stopRefresh ();
        mComfortText.setText (suggestion.getComfort ());
        mCarWashText.setText (suggestion.getCarWash ());
        mSportText.setText (suggestion.getSport ());
        mTravelText.setText (suggestion.getTravel ());
    }

    @Override
    public void showImage(Bitmap image) {
        mBackgroundImage.setImageBitmap (image);
    }

    @Override
    public void showError(String message) {
        stopRefresh ();
        showToast (message);
    }

    public void queryFromDrawers(String weatherId){
        mWeatherActivityPresenter.setLocation (weatherId);
        mDrawerLayout.closeDrawers ();

        //刷新显示
        showProgress ();
        mWeatherActivityPresenter.refresh ();
    }

    private void stopRefresh(){
        if(mSwipeRefreshLayout.isRefreshing ()){
            mSwipeRefreshLayout.setRefreshing (false);
        }
    }
}
