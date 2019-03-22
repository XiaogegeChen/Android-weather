package com.xiaogege.jerry.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.xiaogege.jerry.model.gson.Now;
import com.xiaogege.jerry.model.xml.AQI;
import com.xiaogege.jerry.model.xml.Forecast;
import com.xiaogege.jerry.model.xml.Suggestion;
import com.xiaogege.jerry.util.LogUtils;
import com.xiaogege.jerry.util.XmlIOUtils;
import com.xiaogege.jerry.util.helper.QueryDataHelper;
import com.xiaogege.jerry.util.manager.RetrofitManager;
import com.xiaogege.jerry.view.ViewContract;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.xiaogege.jerry.model.Constants.AQI_ERROR_MESSAGE;
import static com.xiaogege.jerry.model.Constants.FORECAST_ERROR_MESSAGE;
import static com.xiaogege.jerry.model.Constants.IMAGE_ERROR_MESSAGE;
import static com.xiaogege.jerry.model.Constants.IS_JUMP;
import static com.xiaogege.jerry.model.Constants.IS_JUMP_VALUE;
import static com.xiaogege.jerry.model.Constants.NOW_ERROR_MESSAGE;
import static com.xiaogege.jerry.model.Constants.SUGGESTION_ERROR_MESSAGE;
import static com.xiaogege.jerry.model.Constants.WEATHER_STATUE_SUCCESS;
import static com.xiaogege.jerry.model.Constants.WEATHER_USER_KEY;

public class WeatherActivityPresenterImpl implements PresenterContract.WeatherActivityPresenter {

    private static final String TAG = "WeatherActivityPresenter";

    private static final String NOW_JSON_KEY = "now_json_key";
    private static final String FORECAST_JSON_KEY = "forecast_json_key";
    private static final String AQI_JSON_KEY = "aqi_json_key";
    private static final String SUGGESTION_JSON_KEY = "suggestion_json_key";
    private static final String IMAGE_URL_KEY = "image_url_key";

    /**
     * 当前页面的城市代码
     */
    private String mLocation;

    private Context mContext;

    private ViewContract.WeatherActivityView mWeatherActivityView;

    public WeatherActivityPresenterImpl(Context context) {
        mContext = context;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    @Override
    public void attach(ViewContract.WeatherActivityView weatherActivityView) {
        mWeatherActivityView = weatherActivityView;
    }

    @Override
    public void detach() {
        mWeatherActivityView = null;
    }


    @Override
    public void queryAll() {
        queryNow (QueryDataHelper.Type.NORMAL);
        queryAQI (QueryDataHelper.Type.NORMAL);
        queryForecast (QueryDataHelper.Type.NORMAL);
        querySuggestion (QueryDataHelper.Type.NORMAL);
        queryImage(QueryDataHelper.Type.NORMAL);
    }

    @Override
    public void refresh() {
        queryNow (QueryDataHelper.Type.REFRESH);
        queryAQI (QueryDataHelper.Type.REFRESH);
        queryForecast (QueryDataHelper.Type.REFRESH);
        querySuggestion (QueryDataHelper.Type.REFRESH);
        queryImage(QueryDataHelper.Type.REFRESH);
    }

    @Override
    public void checkStatus(){
        String isJump = XmlIOUtils.xmlGet (IS_JUMP, mContext);
        if(isJump == null){
            XmlIOUtils.xmlPut (IS_JUMP, IS_JUMP_VALUE, mContext);
        }
    }

    @SuppressLint("CheckResult")
    private void queryNow(QueryDataHelper.Type type){
        new QueryNowHelper().queryData (type)
                .subscribe (new Consumer<com.xiaogege.jerry.model.xml.Now> () {
                    @Override
                    public void accept(com.xiaogege.jerry.model.xml.Now now) throws Exception {
                        mWeatherActivityView.showNow (now);
                    }
                }, new Consumer<Throwable> () {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace ();
                        mWeatherActivityView.showError (NOW_ERROR_MESSAGE);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void queryForecast(QueryDataHelper.Type type){
        new QueryForecastHelper ().queryData (type)
                .subscribe (new Consumer<Forecast> () {
                    @Override
                    public void accept(Forecast forecast) throws Exception {
                        mWeatherActivityView.showForecast (forecast);
                    }
                }, new Consumer<Throwable> () {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace ();
                        mWeatherActivityView.showError (FORECAST_ERROR_MESSAGE);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void queryAQI(QueryDataHelper.Type type){
        new QueryAQIHelper ().queryData (type)
                .subscribe (new Consumer<AQI> () {
                    @Override
                    public void accept(AQI aqi) throws Exception {
                        mWeatherActivityView.showAQI (aqi);
                    }
                }, new Consumer<Throwable> () {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace ();
                        mWeatherActivityView.showError (AQI_ERROR_MESSAGE);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void querySuggestion(QueryDataHelper.Type type){
        new QuerySuggestionHelper ().queryData (type)
                .subscribe (new Consumer<Suggestion> () {
                    @Override
                    public void accept(Suggestion suggestion) throws Exception {
                        mWeatherActivityView.showSuggestion (suggestion);
                    }
                }, new Consumer<Throwable> () {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace ();
                        mWeatherActivityView.showError (SUGGESTION_ERROR_MESSAGE);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void queryImage(QueryDataHelper.Type type){
        new QueryImageHelper ().queryData (type)
                .subscribe (new Consumer<String> () {
                    @Override
                    public void accept(final String s) throws Exception {
                        Observable.create (new ObservableOnSubscribe<Bitmap> () {
                            @Override
                            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                                Bitmap bitmap = getBitmap (s);
                                if(bitmap != null){
                                    emitter.onNext (getBitmap (s));
                                }
                                emitter.onComplete ();
                            }
                        })
                        .subscribeOn (Schedulers.io ())
                        .observeOn (AndroidSchedulers.mainThread ())
                        .subscribe (new Consumer<Bitmap> () {
                            @Override
                            public void accept(Bitmap bitmap) throws Exception {
                                mWeatherActivityView.showImage (bitmap);
                            }
                        }, new Consumer<Throwable> () {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace ();
                                mWeatherActivityView.showError (IMAGE_ERROR_MESSAGE);
                            }
                        });
                    }
                }, new Consumer<Throwable> () {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace ();
                        mWeatherActivityView.showError (IMAGE_ERROR_MESSAGE);
                    }
                });
    }

    /**
     * 根据url请求网路拿到bitmap
     * @param url 图片的url地址
     * @return bitmap
     */
    public Bitmap getBitmap(String url) {
        HttpURLConnection connection = null;
        InputStream stream = null;
        Bitmap bitmap=null;
        try {
            connection = (HttpURLConnection) new URL (url).openConnection ();
            connection.setRequestMethod ("GET");
            stream = connection.getInputStream ();
            bitmap = BitmapFactory.decodeStream (stream);
        } catch (IOException e) {
            e.printStackTrace ();
        }finally {
            if(connection != null){
                connection.disconnect ();
            }
            if(stream != null){
                try {
                    stream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }
        return bitmap;
    }

    /**
     * 请求Now位置的数据
     */
    class QueryNowHelper extends QueryDataHelper<com.xiaogege.jerry.model.xml.Now>{
        @Override
        public Observable<com.xiaogege.jerry.model.xml.Now> queryFromNet() {
            return RetrofitManager.getInstance ()
                    .create ()
                    .queryNow (WEATHER_USER_KEY, mLocation)
                    .filter (new Predicate<Now> () {
                        @Override
                        public boolean test(Now now) throws Exception {
                            return now != null
                                    && now.getHeWeather6 () != null
                                    && now.getHeWeather6 ().size () > 0
                                    && WEATHER_STATUE_SUCCESS.equals (now.getHeWeather6 ().get (0).getStatus ());
                        }
                    })
                    .flatMap (new Function<Now, ObservableSource<com.xiaogege.jerry.model.xml.Now>> () {
                        @Override
                        public ObservableSource<com.xiaogege.jerry.model.xml.Now> apply(Now now) throws Exception {
                            //拿到Now界面需要的数据
                            String time = now.getHeWeather6 ().get (0).getUpdate ().getLoc ().split (" ")[1];
                            String location = now.getHeWeather6 ().get (0).getBasic ().getLocation ();
                            String temperature = now.getHeWeather6 ().get (0).getNow ().getTmp ();
                            String condition = now.getHeWeather6 ().get (0).getNow ().getCond_txt ();
                            com.xiaogege.jerry.model.xml.Now xmlNow = new com.xiaogege.jerry.model.xml.Now (time, location, temperature, condition);
                            //写入sharePreference
                            String nowJson = new Gson ().toJson (xmlNow);
                            XmlIOUtils.xmlPut (NOW_JSON_KEY, nowJson, mContext);
                            return Observable.just (xmlNow);
                        }
                    });
        }

        @Override
        public Observable<com.xiaogege.jerry.model.xml.Now> queryFromLocal() {
            //从sharePreference拿数据
            final String nowJson = XmlIOUtils.xmlGet (NOW_JSON_KEY, mContext);
            return Observable.create (new ObservableOnSubscribe<com.xiaogege.jerry.model.xml.Now> () {
                @Override
                public void subscribe(ObservableEmitter<com.xiaogege.jerry.model.xml.Now> emitter) throws Exception {
                    if(nowJson != null){
                        com.xiaogege.jerry.model.xml.Now now = new Gson ().fromJson (nowJson, com.xiaogege.jerry.model.xml.Now.class);
                        emitter.onNext (now);
                    }
                    emitter.onComplete ();
                }
            });
        }
    }

    /**
     * 请求 forecast位置的数据
     */
    class QueryForecastHelper extends QueryDataHelper<Forecast>{
        @Override
        public Observable<Forecast> queryFromNet() {
            return RetrofitManager.getInstance ()
                    .create ()
                    .queryForecast (WEATHER_USER_KEY, mLocation)
                    .filter (new Predicate<com.xiaogege.jerry.model.gson.Forecast> () {
                        @Override
                        public boolean test(com.xiaogege.jerry.model.gson.Forecast forecast) throws Exception {
                            return forecast != null
                                    && forecast.getHeWeather6 () != null
                                    && forecast.getHeWeather6 ().size () > 0
                                    && WEATHER_STATUE_SUCCESS.equals (forecast.getHeWeather6 ().get (0).getStatus ())
                                    && forecast.getHeWeather6 ().get (0).getDaily_forecast () != null
                                    && forecast.getHeWeather6 ().get (0).getDaily_forecast ().size () > 0;
                        }
                    })
                    .flatMap (new Function<com.xiaogege.jerry.model.gson.Forecast, ObservableSource<Forecast>> () {
                        @Override
                        public ObservableSource<Forecast> apply(com.xiaogege.jerry.model.gson.Forecast forecast) throws Exception {
                            //拿到数据
                            List<Forecast.ForecastInfo> forecastInfoList = new ArrayList<> ();
                            List<com.xiaogege.jerry.model.gson.Forecast.HeWeather6Bean.DailyForecastBean> forecastBeanList = forecast.getHeWeather6 ().get (0).getDaily_forecast ();
                            for(com.xiaogege.jerry.model.gson.Forecast.HeWeather6Bean.DailyForecastBean forecastBean:forecastBeanList){
                                Forecast.ForecastInfo forecastInfo = new Forecast.ForecastInfo (forecastBean.getDate (), forecastBean.getCond_txt_n (), forecastBean.getTmp_max (), forecastBean.getTmp_min ());
                                forecastInfoList.add (forecastInfo);
                            }
                            Forecast xmlForecast = new Forecast (forecastInfoList);
                            //写入sharePreference
                            String forecastJson = new Gson ().toJson (xmlForecast);
                            XmlIOUtils.xmlPut (FORECAST_JSON_KEY, forecastJson, mContext);
                            return Observable.just (xmlForecast);
                        }
                    });
        }

        @Override
        public Observable<Forecast> queryFromLocal() {
            //从sharePreference拿数据
            final String forecastJson = XmlIOUtils.xmlGet (FORECAST_JSON_KEY, mContext);
            return Observable.create (new ObservableOnSubscribe<Forecast> () {
                @Override
                public void subscribe(ObservableEmitter<Forecast> emitter) throws Exception {
                    if(forecastJson != null){
                        Forecast forecast = new Gson ().fromJson (forecastJson, Forecast.class);
                        emitter.onNext (forecast);
                    }
                    emitter.onComplete ();
                }
            });
        }
    }

    /**
     * 请求AQI数据
     */
    class QueryAQIHelper extends QueryDataHelper<AQI>{

        @Override
        public Observable<AQI> queryFromNet() {
            return RetrofitManager.getInstance ()
                    .create ()
                    .queryAQI (WEATHER_USER_KEY, mLocation)
                    .filter (new Predicate<com.xiaogege.jerry.model.gson.AQI> () {
                        @Override
                        public boolean test(com.xiaogege.jerry.model.gson.AQI aqi) throws Exception {
                            return aqi != null
                                    && aqi.getHeWeather6 () != null
                                    && aqi.getHeWeather6 ().size () > 0
                                    && WEATHER_STATUE_SUCCESS.equals (aqi.getHeWeather6 ().get (0).getStatus ())
                                    && aqi.getHeWeather6 ().get (0).getAir_now_station () != null
                                    && aqi.getHeWeather6 ().get (0).getAir_now_station ().size () > 0;
                        }
                    })
                    .flatMap (new Function<com.xiaogege.jerry.model.gson.AQI, ObservableSource<AQI>> () {
                        @Override
                        public ObservableSource<AQI> apply(com.xiaogege.jerry.model.gson.AQI jsonAQI) throws Exception {
                            //拿到数据
                            String aqi = jsonAQI.getHeWeather6 ().get (0).getAir_now_station ().get (0).getAqi ();
                            String pm25 = jsonAQI.getHeWeather6 ().get (0).getAir_now_station ().get (0).getPm25 ();
                            AQI xmlAQI = new AQI (aqi,pm25);
                            //写入sharePreference
                            String aqiJson = new Gson ().toJson (xmlAQI);
                            XmlIOUtils.xmlPut (AQI_JSON_KEY, aqiJson, mContext);
                            return Observable.just (xmlAQI);
                        }
                    })
                    ;
        }

        @Override
        public Observable<AQI> queryFromLocal() {
            final String aqiJson = XmlIOUtils.xmlGet (AQI_JSON_KEY, mContext);
            return Observable.create (new ObservableOnSubscribe<AQI> () {
                @Override
                public void subscribe(ObservableEmitter<AQI> emitter) throws Exception {
                    if(aqiJson != null){
                        AQI aqi = new Gson ().fromJson (aqiJson, AQI.class);
                        emitter.onNext (aqi);
                    }
                    emitter.onComplete ();
                }
            });
        }
    }

    /**
     * 请求suggestion数据
     */
    class QuerySuggestionHelper extends QueryDataHelper<Suggestion>{

        @Override
        public Observable<Suggestion> queryFromNet() {
            return RetrofitManager.getInstance ()
                    .create ()
                    .querySuggestion (WEATHER_USER_KEY, mLocation)
                    .filter (new Predicate<com.xiaogege.jerry.model.gson.Suggestion> () {
                        @Override
                        public boolean test(com.xiaogege.jerry.model.gson.Suggestion suggestion) throws Exception {
                            return suggestion != null
                                    && suggestion.getHeWeather6 () != null
                                    && suggestion.getHeWeather6 ().size () > 0
                                    && WEATHER_STATUE_SUCCESS.equals (suggestion.getHeWeather6 ().get (0).getStatus ())
                                    && suggestion.getHeWeather6 ().get (0).getLifestyle () != null
                                    && suggestion.getHeWeather6 ().get (0).getLifestyle ().size () > 0;
                        }
                    })
                    .flatMap (new Function<com.xiaogege.jerry.model.gson.Suggestion, ObservableSource<Suggestion>> () {
                        @Override
                        public ObservableSource<Suggestion> apply(com.xiaogege.jerry.model.gson.Suggestion suggestion) throws Exception {
                            //拿到数据
                            String comfort = suggestion.getHeWeather6 ().get (0).getLifestyle ().get (0).getTxt ();
                            String sport = suggestion.getHeWeather6 ().get (0).getLifestyle ().get (3).getTxt ();
                            String carWash = suggestion.getHeWeather6 ().get (0).getLifestyle ().get (6).getTxt ();
                            String travel = suggestion.getHeWeather6 ().get (0).getLifestyle ().get (4).getTxt ();
                            Suggestion xmlSuggestion = new Suggestion (comfort, carWash, sport, travel);
                            //写入
                            String suggestionJson = new Gson ().toJson (xmlSuggestion);
                            XmlIOUtils.xmlPut (SUGGESTION_JSON_KEY, suggestionJson, mContext);
                            return Observable.just (xmlSuggestion);
                        }
                    })
                    ;
        }

        @Override
        public Observable<Suggestion> queryFromLocal() {
            final String suggestionJson = XmlIOUtils.xmlGet (SUGGESTION_JSON_KEY, mContext);
            return Observable.create (new ObservableOnSubscribe<Suggestion> () {
                @Override
                public void subscribe(ObservableEmitter<Suggestion> emitter) throws Exception {
                    if(suggestionJson != null){
                        Suggestion suggestion = new Gson ().fromJson (suggestionJson, Suggestion.class);
                        emitter.onNext (suggestion);
                    }
                    emitter.onComplete ();
                }
            });
        }
    }

    /**
     * 请求imageUrl数据
     */
    class QueryImageHelper extends QueryDataHelper<String>{

        @Override
        public Observable<String> queryFromNet() {
            return RetrofitManager.getInstance ()
                    .create ()
                    .queryImage ()
                    .filter (new Predicate<String> () {
                        @Override
                        public boolean test(String s) throws Exception {
                            LogUtils.d (TAG, "queryFromNet: "+s);
                            return s != null;
                        }
                    });
        }

        @Override
        public Observable<String> queryFromLocal() {
            return Observable.create (new ObservableOnSubscribe<String> () {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    String imageUrl = XmlIOUtils.xmlGet (IMAGE_URL_KEY, mContext);
                    LogUtils.d (TAG, "queryFromLocal: "+imageUrl);
                    if(imageUrl != null){
                        emitter.onNext (imageUrl);
                    }
                    emitter.onComplete ();
                }
            });
        }
    }
}
