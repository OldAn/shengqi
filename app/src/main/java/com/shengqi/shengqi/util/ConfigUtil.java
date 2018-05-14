package com.shengqi.shengqi.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.shengqi.shengqi.log.L;

/**
 * Created by Administrator on 2018/5/9.
 */

public class ConfigUtil {
    private static ConfigUtil configUtil;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private ConfigUtil(Context context){
        init(context);
    }
    public static ConfigUtil getConfigUtil(Context context){
        if(configUtil==null){
            configUtil=new ConfigUtil(context.getApplicationContext());
        }
        return configUtil;
    }
    private void init(Context context){
        sp=context.getSharedPreferences("com.shengqi.pref",Context.MODE_PRIVATE);
        editor=sp.edit();
    }
    public Boolean getLogined(){
        return sp.getBoolean("logined",false);
    }
    public void  setLogined(Boolean flag){
        L.e("登录状态————————————————————————————"+flag);
        editor.putBoolean("logined",flag);
        editor.commit();
    }
    public String getPhone(){
        return sp.getString("phone","");
    }
    public void  setPhone(String phone){
        editor.putString("phone",phone);
        editor.commit();
    }
    public String getPwd(){
        return sp.getString("pwd","");
    }
    public void  setPwd(String pwd){
        editor.putString("pwd",pwd);
        editor.commit();
    }
}
