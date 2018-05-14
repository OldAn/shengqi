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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shengqi.shengqi.R;
import com.shengqi.shengqi.activity.ControlListResultsActivity;
import com.shengqi.shengqi.activity.DeviceListResultsActivity;
import com.shengqi.shengqi.activity.SensorListResultsActivity;
import com.shengqi.shengqi.log.L;
import com.shengqi.shengqi.model.EquipmentInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DeviceListResultsAdapter extends BaseAdapter {
    private Context context; //运行上下文
    private LayoutInflater listContainer;     //视图容器
    private List<EquipmentInfo> equipmentInfos;//设备列表
    private LruCache<String, BitmapDrawable> mImageCache;//图片缓存
    private ListView listview;

    /*
    * 构造函数
    * */
    public DeviceListResultsAdapter(List<EquipmentInfo> equipmentInfos, Context context){
       this.context=context;
       listContainer=LayoutInflater.from(context);
       this.equipmentInfos=equipmentInfos;

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
        public TextView eq_name, eq_state,eq_address;
        public Button eq_ssjc,eq_kgkz;
    }

    @Override
    public int getCount() {
        return equipmentInfos.size();
    }
    @Override
    public Object getItem(int position) {
        return equipmentInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return equipmentInfos.get(position).getEquipmentId();
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (listview == null) {
            listview = (ListView) parent;
        }
        ListItemView listItemView ;
        if(convertView ==null){
            listItemView=new ListItemView();
            //获取list_message_item布局文件的视图
            convertView = listContainer.inflate(R.layout.activity_devicelist_item,null);
            //初始化item的各个组件
            listItemView.eq_image=convertView.findViewById(R.id.devicelist_image);
            listItemView.eq_name=convertView.findViewById(R.id.devicelist_name);
            listItemView.eq_state=convertView.findViewById(R.id.devicelist_state);
            listItemView.eq_address=convertView.findViewById(R.id.devicelist_address);
            listItemView.eq_ssjc=convertView.findViewById(R.id.eq_ssjc);
            listItemView.eq_kgkz=convertView.findViewById(R.id.eq_kgkz);
            convertView.setTag(listItemView);
        }else{
            listItemView=(ListItemView) convertView.getTag();
        }

        listItemView.eq_image.setTag("http://122.14.216.11:8080/mobile_app"+equipmentInfos.get(position).getImage());
        // 如果本地已有缓存，就从本地读取，否则从网络请求数据
        if (mImageCache.get("http://122.14.216.11:8080/mobile_app"+equipmentInfos.get(position).getImage()) != null) {

            listItemView.eq_image.setImageDrawable(mImageCache.get("http://122.14.216.11:8080/mobile_app"+equipmentInfos.get(position).getImage()));
        } else {

            ImageTask it = new ImageTask();
            it.execute("http://122.14.216.11:8080/mobile_app"+equipmentInfos.get(position).getImage());
        }

        listItemView.eq_name.setText(""+equipmentInfos.get(position).getEquipmentName());
        if(equipmentInfos.get(position).getDtuInfo().getDtuState()==1){
            listItemView.eq_state.setText("在线");
            listItemView.eq_state.setTextColor(Color.parseColor("#ff009688"));
        }else {
            listItemView.eq_state.setText("离线");
            listItemView.eq_state.setTextColor(Color.parseColor("#F4511E"));
        }
        listItemView.eq_address.setText(equipmentInfos.get(position).getAddress());

        //设置按钮的监听，实现点击后跳转到详细内容的界面，并且传递数据
        listItemView.eq_ssjc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equipmentInfos.get(position).getDtuInfo().getDtuState()==0){
                    Toast.makeText(context, "当前设备已离线,不能监测数据", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("equipment", equipmentInfos.get(position));
                    intent.setClass(context,SensorListResultsActivity.class);
                    intent.putExtra("bundle", bundle);
                    context.startActivity(intent);
                }
            }
        });
        listItemView.eq_kgkz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equipmentInfos.get(position).getDtuInfo().getDtuState()==0){
                    Toast.makeText(context, "当前设备已离线,不能控制", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("equipment", equipmentInfos.get(position));
                    intent.setClass(context,ControlListResultsActivity.class);
                    intent.putExtra("bundle", bundle);
                    context.startActivity(intent);
                }
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

}
