package com.shengqi.shengqi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.shengqi.shengqi.log.L;
import com.shengqi.shengqi.model.DataInfo;

import java.io.IOException;
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

/**
 * Created by Administrator on 2018/5/4.
 */

public class ControlListResultsAdapter extends BaseAdapter {

    private Context context; //运行上下文
    private LayoutInflater listContainer;     //视图容器
    private List<DataInfo> dataInfos;//设备列表
    private LruCache<String, BitmapDrawable> mImageCache;//图片缓存
    private ListView listview;
    protected static boolean flag=false;

    //获取okhttp全局对象
    public OkHttpClient client=new OkHttpClient();
    //构造request
    Request.Builder builder=new Request.Builder();


    /*
    * 构造函数
    * */
    public ControlListResultsAdapter(List<DataInfo> dataInfos, Context context){
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
        public ImageView cl_image;
        public ImageView cl_con;
        public TextView cl_name;
        public TextView cl_data;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (listview == null) {
            listview = (ListView) parent;
        }
        ListItemView listItemView ;
        if(convertView ==null){
            listItemView=new ListItemView();
            //获取list_message_item布局文件的视图
            convertView = listContainer.inflate(R.layout.activity_controllist_item,null);
            //初始化item的各个组件
            listItemView.cl_image=convertView.findViewById(R.id.controllist_image);
            listItemView.cl_name=convertView.findViewById(R.id.controllist_name);
            listItemView.cl_data=convertView.findViewById(R.id.controllist_data);
            listItemView.cl_con=convertView.findViewById(R.id.controllist_con);
            convertView.setTag(listItemView);
        }else{
            listItemView=(ListItemView) convertView.getTag();
        }
        L.e("regid:"+dataInfos.get(position).getRegisterId()+"imageurl:"+dataInfos.get(position).getRegisterInfo().getImage());
        listItemView.cl_image.setTag("http://122.14.216.11:8080/mobile_app"+dataInfos.get(position).getRegisterInfo().getImage());
        if (mImageCache.get("http://122.14.216.11:8080/mobile_app"+dataInfos.get(position).getRegisterInfo().getImage()) != null) {
            L.e("引用内存图片");
            listItemView.cl_image.setImageDrawable(mImageCache.get("http://122.14.216.11:8080/mobile_app"+dataInfos.get(position).getRegisterInfo().getImage()));
        } else {
            L.e("获取网络图片");
            ImageTask it = new ImageTask();
            it.execute("http://122.14.216.11:8080/mobile_app"+dataInfos.get(position).getRegisterInfo().getImage());
        }
        listItemView.cl_name.setText(""+dataInfos.get(position).getRegisterInfo().getDataName()+":");
        if(dataInfos.get(position).getData().equals("1")){
            listItemView.cl_data.setText("开");
            listItemView.cl_con.setImageResource(R.mipmap.open);
        }else{
            listItemView.cl_data.setText("关");
            listItemView.cl_con.setImageResource(R.mipmap.shut);
        }

        listItemView.cl_con.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(dataInfos.get(position).getData().equals("1")){
                    String urlall="http://122.14.216.11:8080/mobile_app/dataInfo/manualSendOut?registerId="+dataInfos.get(position).getRegisterId()+"&sign=0";
                    final Request request=builder.get().url(urlall).build();
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
                                L.e("请求失败:");
                            }
                        }
                    });
                    if(flag){
                        dataInfos.get(position).setData("0");
                        dateChanged(dataInfos);
                    }

                }else{

                    String urlall="http://122.14.216.11:8080/mobile_app/dataInfo/manualSendOut?registerId="+dataInfos.get(position).getRegisterId()+"&sign=1";
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
                        }
                    });
                    if(flag){
                        dataInfos.get(position).setData("1");
                        dateChanged(dataInfos);
                    }
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
                L.e("保存图片至内存");
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
        L.e("填充新数据");
        if (dataInfos != null) {
            this.dataInfos = dataInfos;
        } else {
            this.dataInfos = new ArrayList<>();
        }
    }
    public void dateChanged(List<DataInfo> dataInfos) {
        L.e("执行更改方法");
        this.setKeywords(dataInfos);
        this.notifyDataSetChanged();
    }

}
