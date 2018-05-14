package com.shengqi.shengqi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shengqi.shengqi.R;
import com.shengqi.shengqi.adapter.ControlListResultsAdapter;
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
import java.util.concurrent.atomic.LongAdder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/4.
 */

public class ControlListResultsActivity extends TitleActivity {
    private LinearLayout linear_error,linear_load;
    private ListView listView;
    private ControlListResultsAdapter adapter;
    private Context context=this;
    List<DataInfo> dataInfos;
    EquipmentInfo equipmentInfo;
    private Button button_forward;

    Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter=new ControlListResultsAdapter(dataInfos,ControlListResultsActivity.this);
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
    String urlall="http://122.14.216.11:8080/mobile_app/dataInfo/getthecontrollist";
    private Gson gson=new Gson();
    private Handler handler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controllist);
        setTitle("开关控制");
        showBackwardView(R.string.text_back,true);
        showForwardView(R.string.text_forward,true);




        Bundle bundle=(Bundle) getIntent().getExtras().get("bundle");
        equipmentInfo=(EquipmentInfo) bundle.getSerializable("equipment");
        LinkedHashMap<String,String> map=new LinkedHashMap<String,String>();
        map.put("equipmentid",""+equipmentInfo.getEquipmentId());
        urlall= UrlOperation.attachHttpGetParams(urlall,map);
        listView=findViewById(R.id.control_listView);
        listView.setVisibility(View.VISIBLE);
        linear_load=findViewById(R.id.controllist_load);
        linear_load.setVisibility(View.GONE);
        linear_error=findViewById(R.id.controllist_error);
        linear_error.setVisibility(View.GONE);
        button_forward=findViewById(R.id.button_forward);
        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("equipment", equipmentInfo);
                intent.setClass(context,PatternListResultsActivity.class);
                intent.putExtra("bundle", bundle);
                context.startActivity(intent);
            }
        });

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
        L.e(urlall);
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
                    L.e("请求失败");
                    mHandler.obtainMessage(400).sendToTarget();
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
