package com.gang.happylife.api;

import android.util.Log;

import com.gang.happylife.App;
import com.gang.happylife.utils.NetWorkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/6/5.
 */

public class Api {
    public static String BASE_URL = "http://japi.juhe.cn/joke/";
    public static String RANDOM_BASE_URL = "http://v.juhe.cn/joke/";
    public static String BASE_WHEATHER = "http://op.juhe.cn/onebox/weather/";
    public static String KEY = "fcc736b44a9f3ed587971eb62276ff0b";
    public static String WKEY = "656da567f62a550f02a9b56843c0a75d";

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create(gson);
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private Retrofit retrofit;
    private ApiService apiService;
    private static OkHttpClient okHttpClient;
    private static Api INSTANCE;


    /**
     * 初始化okhttp
     */
    public static void initOkhttp() {
        if (okHttpClient == null) {
            Interceptor mInterceptor = ((chain -> chain.proceed(chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build())));
            //setLevel 改变日志级别
            //共包含四个级别：NONE、BASIC、HEADER、BODY
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            File cacheFile = new File(App.getAppContext().getCacheDir(), "cache");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(7676, TimeUnit.MILLISECONDS)
                    .connectTimeout(7676, TimeUnit.MILLISECONDS)
                    .addInterceptor(mInterceptor)
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new HttpCacheInterceptor())
                    .cache(cache)
                    .build();

        }
    }

    /**
     * 获取最新文本笑话
     *
     * @return
     */
    public ApiService getTextJokeApi() {
        initOkhttp();
        if (apiService == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    /**
     * 获取全部按时间文本笑话
     *
     * @return
     */
    public void getTimeTextJokeApi() {
        initOkhttp();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    /**
     * 获取最新图片笑话
     *
     * @return
     */
    public void getImageJokeApi() {
        initOkhttp();
        retrofit = new Retrofit.Builder()
                .baseUrl(RANDOM_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    /**
     * 获取按时间排序的图片笑话
     *
     * @return
     */
    public void getTimeImageJokeApi() {
        initOkhttp();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    /**
     * 获取天气数据
     *
     * @return
     */
    public void getWeatherApi() {
        initOkhttp();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_WHEATHER)
                .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    //获取单例
    public static Api getInstance() {
        if(INSTANCE == null){
            synchronized (Api.class){
                INSTANCE = new Api();
            }
        }
        return INSTANCE;
    }

    static class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetConnected(App.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetConnected(App.getAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}
