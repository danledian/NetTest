package com.zhaoguan.nettest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HttpService {


    @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
    Call<ResponseBody> getInfo();
}
