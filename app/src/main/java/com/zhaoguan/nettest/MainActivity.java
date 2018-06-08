package com.zhaoguan.nettest;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView mTvMsg;


    OkHttpClient okHttpClient;


    public void onClickViewed(View view) {
        Log.i(TAG, "Click");
//        useRetrofit();

        String url = Environment.getExternalStorageDirectory() + File.separator + "test.txt";
        if(!new File(url).exists()){
            Toast.makeText(this, "Not find", Toast.LENGTH_SHORT).show();
            return;
        }


        startActivity(getTextFileIntent(url, false));
    }

    private void useRetrofit(){
        Call<ResponseBody> call = HttpApi.getInstance().getInfo();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                String message = response.message();
                Log.i(TAG, "code:" + code + ",message:" +message);
                try {
                    String content = response.body().string();
                    Log.i(TAG, "Msg:" + content);
                    mTvMsg.setText("Msg:" + content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, t.getMessage());

                mTvMsg.setText("msg:" + t.getMessage());
            }
        });

    }


    //Android获取一个用于打开文本文件的intent
    public Intent getTextFileIntent(String param, boolean paramBoolean) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {

            Uri fileUri = FileProvider.getUriForFile(MainActivity.this, "com.zhaoguan.nettest.fileprovider", new File(param));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

//            Uri fileUri = Uri.fromFile(new File(param));
            intent.setDataAndType(fileUri, "text/plain");
        }
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvMsg = (TextView)findViewById(R.id.tv_msg);


        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
