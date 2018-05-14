package com.shengqi.shengqi.activity;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shengqi.shengqi.R;
import com.shengqi.shengqi.adapter.SensorListResultsAdapter;
import com.shengqi.shengqi.log.L;
import com.shengqi.shengqi.model.DataInfo;
import com.shengqi.shengqi.model.EquipmentInfo;
import com.shengqi.shengqi.util.UrlOperation;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SensorListResultsActivity extends TitleActivity {

    private LinearLayout linear_error,linear_load;
    private ListView listView;
    private SensorListResultsAdapter adapter;
    private Context context=this;
    List<DataInfo> dataInfos;
    EquipmentInfo equipmentInfo;

    Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter=new SensorListResultsAdapter(dataInfos,SensorListResultsActivity.this);
                    listView.setAdapter(adapter); // 重新设置ListView的数据适配器
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }
                    });
                    listView.setVisibility(View.VISIBLE);
                    linear_load.setVisibility(View.GONE);
                    linear_error.setVisibility(View.GONE);
                    break;

                case 1:
                    adapter.dateChanged(dataInfos);
                    break;
                case 400:
                    listView.setVisibility(View.GONE);
                    linear_load.setVisibility(View.GONE);
                    linear_error.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }

    };




    //获取okhttp全局对象
    public OkHttpClient client=new OkHttpClient();
    //构造request
    Request.Builder builder=new Request.Builder();
    String urlall="http://122.14.216.11:8080/mobile_app/dataInfo/getdataInfoautomatic";
    private Gson gson=new Gson();
    private Handler handler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensorlist);
        setTitle("数据监测");
        showBackwardView(R.string.text_back,true);
        showForwardView(R.string.text_forward,false);



        Bundle bundle=(Bundle) getIntent().getExtras().get("bundle");
        equipmentInfo=(EquipmentInfo) bundle.getSerializable("equipment");
        LinkedHashMap<String,String> map=new LinkedHashMap<String,String>();
        map.put("equipmentid",""+equipmentInfo.getEquipmentId());
        urlall= UrlOperation.attachHttpGetParams(urlall,map);
        listView=findViewById(R.id.sensor_listView);
        listView.setVisibility(View.VISIBLE);
        linear_load=findViewById(R.id.sensorlist_load);
        linear_load.setVisibility(View.GONE);
        linear_error=findViewById(R.id.sensorlist_error);
        linear_error.setVisibility(View.GONE);
        initData();


        TimerTask task = new TimerTask(){
            public void run(){
                updateData();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,10000,10000);


    }

    private void initData(){
        Request request=builder.get().url(urlall).build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("onFailure:"+e.getMessage());
                e.printStackTrace();
                mHandler.obtainMessage(400).sendToTarget();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Type type = new TypeToken<List<DataInfo>>() {}.getType();
                    dataInfos = gson.fromJson(result, type);
                    mHandler.obtainMessage(0).sendToTarget();

                }else{
                    mHandler.obtainMessage(400).sendToTarget();
                    L.e("请求失败");
                }
                //无论任何情况都要关闭该对象
                response.close();
            }
        });
    }

    private void updateData(){
        Request request=builder.get().url(urlall).build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("onFailure:"+e.getMessage());
                e.printStackTrace();
                mHandler.obtainMessage(400).sendToTarget();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Type type = new TypeToken<List<DataInfo>>() {}.getType();
                    dataInfos = gson.fromJson(result, type);
                    mHandler.obtainMessage(1).sendToTarget();

                }else{
                    L.e("请求失败");
                    mHandler.obtainMessage(400).sendToTarget();
                }
                //无论任何情况都要关闭该对象
                response.close();
            }
        });
    }

}



