package com.shengqi.shengqi.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shengqi.shengqi.R;
import com.shengqi.shengqi.activity.ControlListResultsActivity;
import com.shengqi.shengqi.activity.PatternListResultsActivity;
import com.shengqi.shengqi.activity.SensorListResultsActivity;
import com.shengqi.shengqi.log.L;
import com.shengqi.shengqi.model.DataInfo;
import com.shengqi.shengqi.model.EquipmentInfo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PatternListResultsAdapter extends BaseAdapter {
    private Context context; //运行上下文
    private LayoutInflater listContainer;     //视图容器
    private List<DataInfo> dataInfos;//数据列表
    private LruCache<String, BitmapDrawable> mImageCache;//图片缓存
    private ListView listview;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    protected static boolean flag=false;
    String urlall="";

    //获取okhttp全局对象
    public OkHttpClient client=new OkHttpClient();
    //构造request
    Request.Builder builder=new Request.Builder();

    private Gson gson=new Gson();

    /*
    * 构造函数
    * */
    public PatternListResultsAdapter(List<DataInfo> dataInfos, Context context){
       this.context=context;
       listContainer=LayoutInflater.from(context);
       this.dataInfos=dataInfos;

        int maxCache = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxCache / 8;
        mImageCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return value.getBitmap().getByteCount();
            }
        };

    }

    //自定义控件集合
    public class ListItemView {
        public ImageView eq_image;
        public TextView eq_name,pattern;
        public Spinner spinner;
    }

    @Override
    public int getCount() {
        return dataInfos.size();
    }
    @Override
    public Object getItem(int position) {
        return dataInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataInfos.get(position).getRegisterId();
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (listview == null) {
            listview = (ListView) parent;
        }
        final ListItemView listItemView ;
        if(convertView ==null){
            listItemView=new ListItemView();
            //获取list_message_item布局文件的视图
            convertView = listContainer.inflate(R.layout.activity_patternlist_item,null);
            //初始化item的各个组件
            listItemView.eq_image=convertView.findViewById(R.id.devicelist_image);
            listItemView.eq_name=convertView.findViewById(R.id.devicelist_name);
            listItemView.pattern=convertView.findViewById(R.id.pattern);
            listItemView.spinner=convertView.findViewById(R.id.spinner);
            convertView.setTag(listItemView);
        }else{
            listItemView=(ListItemView) convertView.getTag();
        }

        listItemView.eq_image.setTag("http://122.14.216.11:8080/mobile_app"+dataInfos.get(position).getRegisterInfo().getImage());
        // 如果本地已有缓存，就从本地读取，否则从网络请求数据
        if (mImageCache.get("http://122.14.216.11:8080/mobile_app"+dataInfos.get(position).getRegisterInfo().getImage()) != null) {
            listItemView.eq_image.setImageDrawable(mImageCache.get("http://122.14.216.11:8080/mobile_app"+dataInfos.get(position).getRegisterInfo().getImage()));
        } else {
            ImageTask it = new ImageTask();
            it.execute("http://122.14.216.11:8080/mobile_app"+dataInfos.get(position).getRegisterInfo().getImage());
        }

        listItemView.eq_name.setText(""+dataInfos.get(position).getRegisterInfo().getDataName());
        if(dataInfos.get(position).getRegisterInfo().getFunctionCodeId()==1){
            data_list =new ArrayList<>();
            data_list.add("自动控制");
            data_list.add("手动控制");
            urlall="http://122.14.216.11:8080/mobile_app/dataInfo/manualSendOut";
        }else{
            //数据
            data_list = new ArrayList<>();
            data_list.add("未设置");
            data_list.add("手动控制");
            data_list.add("参数控制");
            data_list.add("时间控制");
            urlall="http://122.14.216.11:8080/mobile_app/dataInfo/manualSendOutsix";
        }

        listItemView.pattern.setText(data_list.get(Integer.valueOf(dataInfos.get(position).getData())));



        //适配器
        arr_adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        listItemView.spinner.setAdapter(arr_adapter);
        //设置默认选中
        listItemView.spinner.setSelection(Integer.valueOf(dataInfos.get(position).getData()), true);
        //设置一下tag试试
        listItemView.spinner.setTag(position);
        //给Spinner添加事件监听
        listItemView.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //当选中某一个数据项时触发该方法
            /*
             * parent接收的是被选择的数据项所属的 Spinner对象，
             * view参数接收的是显示被选择的数据项的TextView对象
             * position接收的是被选择的数据项在适配器中的位置
             * id被选择的数据项的行号
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position1, long id) {
                //System.out.println(spinner==parent);//true
                //System.out.println(view);
                //String data = adapter.getItem(position);//从适配器中获取被选择的数据项
                //String data = list.get(position);//从集合中获取被选择的数据项
                L.e(""+position);
                String data = (String)listItemView.spinner.getItemAtPosition(position1);//从spinner中获取被选择的数据
                if(!data.equals(listItemView.pattern.getText())){

                    urlall+="?registerId="+dataInfos.get(position).getRegisterId();
                    urlall+="&sign="+position1;
                    L.e(urlall);
                    Request request=builder.get().url(urlall).build();
                    Call call=client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            L.e("onFailure:"+e.getMessage());
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String result = response.body().string();
                                if(result.equals("200")){
                                    flag=true;
                                }
                            }else{
                                L.e("请求失败");
                            }
                            //无论任何情况都要关闭该对象
                            response.close();
                        }
                    });
                    if(flag){
                        dataInfos.get(position).setData("1");
                        dateChanged(dataInfos);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



        return convertView;
    }

    class ImageTask extends AsyncTask<String, Void, BitmapDrawable> {

        private String imageUrl;

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            imageUrl = params[0];
            Bitmap bitmap = downloadImage();
            BitmapDrawable db = new BitmapDrawable(listview.getResources(),
                    bitmap);
            // 如果本地还没缓存该图片，就缓存
            if (mImageCache.get(imageUrl) == null) {
                mImageCache.put(imageUrl, db);
            }
            return db;
        }

        @Override
        protected void onPostExecute(BitmapDrawable result) {
            // 通过Tag找到我们需要的ImageView，如果该ImageView所在的item已被移出页面，就会直接返回null
            ImageView iv =  listview.findViewWithTag(imageUrl);
            if (iv != null && result != null) {
                iv.setImageDrawable(result);
            }
        }

        /**
         * 根据url从网络上下载图片
         *
         * @return
         */
        private Bitmap downloadImage() {
            L.e("开始下载图片"+imageUrl);
            HttpURLConnection con = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(20 * 1000);
                con.setReadTimeout(20 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return bitmap;
        }

    }
    public void setKeywords(List<DataInfo> dataInfos) {
        if (dataInfos != null) {
            this.dataInfos = dataInfos;
        } else {
            this.dataInfos = new ArrayList<>();
        }
    }
    public void dateChanged(List<DataInfo> dataInfos) {

        L.e("更改数据");
        this.setKeywords(dataInfos);
        this.notifyDataSetChanged();
    }

}
