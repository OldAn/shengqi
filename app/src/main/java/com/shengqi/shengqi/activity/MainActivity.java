package com.shengqi.shengqi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shengqi.shengqi.R;
import com.shengqi.shengqi.log.L;
import com.shengqi.shengqi.model.DataInfo;
import com.shengqi.shengqi.util.ConfigUtil;
import com.shengqi.shengqi.util.UrlOperation;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private  ImageView more,btn_zxjc;
    private Button exitL;
    public ConfigUtil configUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configUtil=ConfigUtil.getConfigUtil(this);

        more=findViewById(R.id.button_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_zxjc=findViewById(R.id.btn_zxjc);
        btn_zxjc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,DeviceListResultsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        exitL=findViewById(R.id.ExitLogon);
        exitL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configUtil.setLogined(false);
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,LoginActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
                Toast.makeText(MainActivity.this,"退出登录",Toast.LENGTH_LONG).show();
            }
        });
    }












    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;


    @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
                 if (keyCode == KeyEvent.KEYCODE_BACK
                         && event.getAction() == KeyEvent.ACTION_DOWN) {
                         if ((System.currentTimeMillis() - exitTime) > 2000) {
                                 //弹出提示，可以有多种方式
                                 Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                                 exitTime = System.currentTimeMillis();
                             } else {
                                 finish();
                             }
                         return true;
                     }

                 return super.onKeyDown(keyCode, event);
             }




}
