package com.shengqi.shengqi.log;

import android.nfc.Tag;
import android.util.Log;

/**
 * Created by Administrator on 2018/4/16.
 */

public class L {
    private  static  final String TAG="Shengqi_okhttp";
    private static  boolean debug=true;
    public  static  void e(String msg){
        if(debug)
            Log.e(TAG,msg);
    }
    public  static  void i(String msg){
        if(debug)
            Log.i(TAG,msg);
    }
}
