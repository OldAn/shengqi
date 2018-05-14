package com.shengqi.shengqi.util;

import android.util.Log;

import com.shengqi.shengqi.log.L;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2018/4/16.
 */

public class UrlOperation {
    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     * @param url
     * @param params
     * @return
     */
    public static String attachHttpGetParams(String url, LinkedHashMap<String,String> params){
        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for (int i=0;i<params.size();i++ ) {
            String value=null;
            try {
                value= URLEncoder.encode(values.next(),"utf-8");
            }catch (Exception e){
                e.printStackTrace();
            }

            stringBuffer.append(keys.next()+"="+value);
            if (i!=params.size()-1) {
                stringBuffer.append("&");
            }
            L.e("stringBuffer:"+stringBuffer.toString());
        }

        return url + stringBuffer.toString();
    }
}
