package com.xiaogege.jerry.util.manager;

import com.xiaogege.jerry.util.convert.MyGsonConverterFactory;
import com.xiaogege.jerry.model.ApiService;
import com.xiaogege.jerry.util.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.xiaogege.jerry.model.Constants.LOCATION_BASE_URL;
import static com.xiaogege.jerry.model.Constants.LOCATION_HEAD_KEY;
import static com.xiaogege.jerry.model.Constants.OKHTTP_CONNECT_TIME_OUT;
import static com.xiaogege.jerry.model.Constants.OKHTTP_HEAD_NAME;
import static com.xiaogege.jerry.model.Constants.WEATHER_BASE_URL;
import static com.xiaogege.jerry.model.Constants.WEATHER_HEAD_KEY;

/**
 * Retrofit的单例
 */
public class RetrofitManager {
    private static final String TAG =" RetrofitManager";
    private static RetrofitManager mSingleTon;
    private Retrofit mRetrofit;

    private RetrofitManager(){

        //通过okhttp拦截器动态更改baseurl
        OkHttpClient client = new OkHttpClient.Builder ()
                .connectTimeout (OKHTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor (new Interceptor () {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request ();
                        Request.Builder builder = request.newBuilder();
                        List<String> headList = request.headers (OKHTTP_HEAD_NAME);
                        HttpUrl oldHttpUrl = request.url();
                        if(headList.size () > 0){
                            builder.removeHeader (OKHTTP_HEAD_NAME);
                            String head = headList.get (0);
                            HttpUrl newBaseUrl;
                            if(LOCATION_HEAD_KEY.equals (head)){
                                newBaseUrl = HttpUrl.parse(LOCATION_BASE_URL);
                            }else if(WEATHER_HEAD_KEY.equals (head)){
                                newBaseUrl = HttpUrl.parse (WEATHER_BASE_URL);
                            }else{
                                newBaseUrl = oldHttpUrl;
                            }
                            HttpUrl newUrl = oldHttpUrl
                                    .newBuilder()
                                    .scheme(newBaseUrl.scheme())
                                    .host(newBaseUrl.host())
                                    .port(newBaseUrl.port())
                                    .build();
                            LogUtils.d (TAG, "newUrl: "+newUrl.toString ());
                            return chain.proceed (builder.url (newUrl).build ());
                        }else{
                            LogUtils.d (TAG, "oldUrl: "+request.url ().toString ());
                            return chain.proceed (request);
                        }
                    }
                })
                .build ();

        mRetrofit=new Retrofit.Builder ()
                .client (client)
                .baseUrl (LOCATION_BASE_URL)
//                .addConverterFactory (GsonConverterFactory.create ())
                .addConverterFactory (MyGsonConverterFactory.create ())
                .addCallAdapterFactory (RxJava2CallAdapterFactory.create ())
                .build ();
    }

    public static RetrofitManager getInstance(){
        if(mSingleTon==null){
            synchronized (RetrofitManager.class){
                if(mSingleTon==null){
                    mSingleTon=new RetrofitManager ();
                }
            }
        }
        return mSingleTon;
    }

    public ApiService.Api create(){
        return mRetrofit.create (ApiService.Api.class);
    }
}
