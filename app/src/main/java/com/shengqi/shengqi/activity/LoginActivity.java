package com.shengqi.shengqi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shengqi.shengqi.R;
import com.shengqi.shengqi.log.L;
import com.shengqi.shengqi.util.ConfigUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends TitleActivity  {
    private EditText phone,pwd;
    private CheckBox rtp;
    private TextView login;
    public ConfigUtil configUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("圣启科技");
        showBackwardView(R.string.text_back,false);
        showForwardView(R.string.text_forward,false);
        configUtil=ConfigUtil.getConfigUtil(this);

        if(configUtil.getLogined()){
            L.e("查看登录状态——————————"+configUtil.getLogined());
            Intent intent=new Intent();
            intent.setClass(LoginActivity.this,MainActivity.class);
            LoginActivity.this.startActivity(intent);
            finish();
        }
        phone=findViewById(R.id.et_phone);
        pwd=findViewById(R.id.et_pwd);
        rtp=findViewById(R.id.chb_rtp);
        login=findViewById(R.id.btn_login);
        phone.setText(configUtil.getPhone());
        pwd.setText(configUtil.getPwd());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ph=phone.getText().toString();
                final String pw=pwd.getText().toString();
                if(TextUtils.isEmpty(ph)){
                    Toast.makeText(LoginActivity.this,"手机号不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(pw)){
                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

                OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
                FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                formBody.add("ph",ph);//传递键值对参数
                formBody.add("pw",pw);//传递键值对参数
                final Request request = new Request.Builder()//创建Request 对象。
                        .url("http://122.14.216.11:8080/mobile_app/login/applogin")
                        .post(formBody.build())//传递请求体
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        L.e("onFailure:"+e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = response.body().string();
                            L.e(result);
                            if(result.equals("200")){
                                configUtil.setLogined(true);
                                configUtil.setPhone(ph);
                                configUtil.setPwd(pw);
                                Intent intent=new Intent();
                                intent.setClass(LoginActivity.this,MainActivity.class);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            }else{
                                L.e("用户名密码不正确");
                            }
                        }else{
                            L.e("请求失败");
                        }
                    }
                });

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
