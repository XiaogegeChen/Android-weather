package com.xiaogege.jerry.model;

import com.xiaogege.jerry.model.gson.AQI;
import com.xiaogege.jerry.model.gson.City;
import com.xiaogege.jerry.model.gson.County;
import com.xiaogege.jerry.model.gson.Forecast;
import com.xiaogege.jerry.model.gson.Now;
import com.xiaogege.jerry.model.gson.Province;
import com.xiaogege.jerry.model.gson.Suggestion;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.xiaogege.jerry.model.Constants.LOCATION_HEAD_KEY;
import static com.xiaogege.jerry.model.Constants.OKHTTP_HEAD_NAME;
import static com.xiaogege.jerry.model.Constants.WEATHER_HEAD_KEY;

public class ApiService {

    public interface Api{

        /**
         *  请求省份数据
         * @return 所有省份的列表
         */
        @Headers ({OKHTTP_HEAD_NAME+":"+LOCATION_HEAD_KEY})
        @GET("api/china")
        Observable<List<Province>> queryProvince();

        /**
         *  请求城市数据
         * @param provinceId 待查询的省代号
         * @return 该省的城市列表
         */
        @Headers ({OKHTTP_HEAD_NAME+":"+LOCATION_HEAD_KEY})
        @GET("api/china/{provinceId}")
        Observable<List<City>> queryCity(@Path ("provinceId") String provinceId);

        /**
         *  请求县数据
         * @param provinceId 待查询的省份代号
         * @param cityId 带查询的城市代号
         * @return 该市的县列表
         */
        @Headers ({OKHTTP_HEAD_NAME+":"+LOCATION_HEAD_KEY})
        @GET("api/china/{provinceId}/{cityId}")
        Observable<List<County>> queryCounty(@Path ("provinceId") String provinceId, @Path ("cityId") String cityId);

        /**
         * 请求每日一图的url地址
         * @return 每日一图的url地址
         */
        @Headers ({OKHTTP_HEAD_NAME+":"+LOCATION_HEAD_KEY})
        @GET("api/bing_pic")
        Observable<String> queryImage();

        /**
         * 请求未来七天的天气数据
         * @param userKey 和风天气平台申请的key
         * @param location 待查询的城市代码
         * @return 未来七天天气状况的实例
         */
        @Headers ({OKHTTP_HEAD_NAME+":"+WEATHER_HEAD_KEY})
        @GET("s6/weather/forecast")
        Observable<Forecast> queryForecast(@Query ("key") String userKey, @Query ("location") String location);

        /**
         * 请求pm 2.5和aqi 指数
         * @para userKey 和风天气平台申请的key
         * @param location 查询的城市代码
         * @return 当天的请求pm 2.5和aqi 指数的JavaBean
         */
        @Headers ({OKHTTP_HEAD_NAME+":"+WEATHER_HEAD_KEY})
        @GET("s6/air")
        Observable<AQI> queryAQI(@Query ("key") String userKey, @Query ("location") String location);

        /**
         * 请求刷新时间和地理位置
         * @param userKey 和风天气平台申请的key
         * @param location 查询的城市代码
         * @return 刷新时间和地理位置的JavaBean
         */
        @Headers ({OKHTTP_HEAD_NAME+":"+WEATHER_HEAD_KEY})
        @GET("/s6/weather/now")
        Observable<Now> queryNow(@Query ("key") String userKey, @Query ("location") String location);

        /**
         * 请求生活建议
         * @param userKey 和风天气平台申请的key
         * @param location 查询的城市代码
         * @return 生活建议的javabean
         */
        @Headers ({OKHTTP_HEAD_NAME+":"+WEATHER_HEAD_KEY})
        @GET("/s6/weather/lifestyle")
        Observable<Suggestion> querySuggestion(@Query ("key") String userKey, @Query ("location") String location);
    }
}
