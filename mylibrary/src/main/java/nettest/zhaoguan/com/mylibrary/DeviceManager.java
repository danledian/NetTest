package nettest.zhaoguan.com.mylibrary;

import android.content.Context;
import android.util.Log;

import com.lt.volley.http.Volley;

public class DeviceManager {


    public static void test(Context context){
        Log.i("DeviceManager", "test");

        Volley.newRequestQueue(context);
    }
}
