package com.zhaoguan.nettest;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpApi {

    private static final String TAG = "HttpApi";

    private static HttpApi mInstance;

    private HttpService mHttpService;

    public static HttpApi getInstance(){
        if(mInstance == null){
            synchronized (HttpApi.class){
                if(mInstance == null){
                    mInstance = new HttpApi();
                }
            }
        }
        return mInstance;
    }

    public HttpApi() {
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://fanyi.youdao.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build();
        mHttpService = retrofit.create(HttpService.class);
    }

    private OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return chain.proceed(chain.request());
                    }
                })
                .addInterceptor(getHttpLoggingInterceptor())
                .build();
    }

    private HttpLoggingInterceptor getHttpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){

            @Override
            public void log(String message) {
                Log.i(TAG, String.format("%s", message));
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }

    public Call<ResponseBody> getInfo(){
        return mHttpService.getInfo();
    }

}
