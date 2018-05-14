package com.shengqi.shengqi.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shengqi.shengqi.R;
import com.shengqi.shengqi.activity.SensorListResultsActivity;
import com.shengqi.shengqi.log.L;
import com.shengqi.shengqi.model.DataInfo;
import com.shengqi.shengqi.model.EquipmentInfo;
import com.shengqi.shengqi.model.RegisterInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SensorListResultsAdapter extends BaseAdapter {
    private Context context; //运行上下文
    private LayoutInflater listContainer;     //视图容器
    private List<DataInfo> dataInfos;//设备列表
    private LruCache<String, BitmapDrawable> mImageCache;//图片缓存
    private ListView listview;
    /*
    * 构造函数
    * */
    public SensorListResultsAdapter(List<DataInfo> dataInfos, Context context){
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
        public ImageView re_image;
        public TextView re_name;
        public TextView re_data;
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
        ListItemView listItemView ;
        if(convertView ==null){
            listItemView=new ListItemView();
            //获取list_message_item布局文件的视图
            convertView = listContainer.inflate(R.layout.activity_sensorlist_item,null);
            //初始化item的各个组件
            listItemView.re_image=convertView.findViewById(R.id.sensorlist_image);
            listItemView.re_name=convertView.findViewById(R.id.sensorlist_name);
            listItemView.re_data=convertView.findViewById(R.id.sensorlist_data);
            convertView.setTag(listItemView);
        }else{
            listItemView=(ListItemView) convertView.getTag();
        }
        listItemView.re_image.setTag("http://122.14.216.11:8080/mobile_app"+dataInfos.get(position).getRegisterInfo().getImage());
        // 如果本地已有缓存，就从本地读取，否则从网络请求数据
        if (mImageCache.get("http://122.14.216.11:8080/mobile_app"+dataInfos.get(position).getRegisterInfo().getImage()) != null) {
            listItemView.re_image.setImageDrawable(mImageCache.get("http://122.14.216.11:8080/mobile_app"+dataInfos.get(position).getRegisterInfo().getImage()));
        } else {
            ImageTask it = new ImageTask();
            it.execute("http://122.14.216.11:8080/mobile_app"+dataInfos.get(position).getRegisterInfo().getImage());
        }

        listItemView.re_name.setText(""+dataInfos.get(position).getRegisterInfo().getDataName()+":");
        listItemView.re_data.setText(""+dataInfos.get(position).getData());
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
                L.e("缓存图片");
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
