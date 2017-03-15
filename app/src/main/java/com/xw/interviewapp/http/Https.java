package com.xw.interviewapp.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xw.interviewapp.bean.HomeRecyclerBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * <br/>
 * 作者：XW <br/>
 * 邮箱：xw_appdev@163.com <br/>
 * 时间：2017-03-16 00:27
 */

public class Https {
    
    private static Https sHttps;
    
    private OkHttpClient mOkHttpClient;
    
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    
    private Https() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient = builder.build();
    }
    
    public static Https instance() {
        if (sHttps == null) {
            sHttps = new Https();
        }
        return sHttps;
    }
    
    private Headers setHeaders(Map<String, String> headersParams) {
        Headers headers;
        Headers.Builder headersBuilder = new Headers.Builder();
        if (headersParams != null) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                headersBuilder.add(key, headersParams.get(key));
            }
        }
        headers = headersBuilder.build();
        return headers;
    }
    
    private RequestBody setRequestBody(Map<String, String> bodyParams) {
        RequestBody body;
        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        if (bodyParams != null) {
            Iterator<String> it = bodyParams.keySet().iterator();
            String key;
            while (it.hasNext()) {
                key = it.next();
                formEncodingBuilder.add(key, bodyParams.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;
    }
    
    private RequestBody setFileRequestBody(Map<String, String> bodyParams, Map<String, String> fileParams) {
        RequestBody body;
        MultipartBody.Builder multipartBody = new MultipartBody.Builder();
        multipartBody.setType(MultipartBody.FORM);
        RequestBody fileBody;
        if (bodyParams != null) {
            Iterator<String> it = bodyParams.keySet().iterator();
            String key;
            while (it.hasNext()) {
                key = it.next();
                multipartBody.addFormDataPart(key, bodyParams.get(key));
            }
        }
        if (fileParams != null) {
            Iterator<String> it = fileParams.keySet().iterator();
            String key;
            int i = 0;
            while (it.hasNext()) {
                key = it.next();
                i++;
                multipartBody.addFormDataPart(key, fileParams.get(key));
                fileBody = RequestBody.create(
                        MEDIA_TYPE_PNG, new File(fileParams.get(key)));
                multipartBody.addFormDataPart(key, i + ".png", fileBody);
            }
        }
        body = multipartBody.build();
        return body;
    }
    
    private String setUrlParams( Map<String, String> mapParams){
        String strParams = "";
        if(mapParams != null){
            Iterator<String> iterator = mapParams.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                strParams += "&"+ key + "=" + mapParams.get(key);
            }
        }
        
        return strParams;
    }
    
    public void getBeanExecute(String url, Map<String, String> headersParams,
                               Map<String, String> params, Object object,
                               final Handler handler, Class<?> cls) {
        Request.Builder builder = new Request.Builder();
        builder.url(url + setUrlParams(params));
        builder.headers(setHeaders(headersParams));
        if (object != null) {
            builder.tag(object);
        }
        final Request request = builder.build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message m = Message.obtain(handler, 404);
                m.sendToTarget();
            }
    
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result;
                Message m = Message.obtain(handler);
                if (response.code() == 200) {
                    result = response.body().string();
                    List<HomeRecyclerBean> jsonBean = new ArrayList<HomeRecyclerBean>();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
//                        jsonBean.setError(jsonObject.getBoolean("error"));
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HomeRecyclerBean jb = new HomeRecyclerBean();
                            JSONObject json = jsonArray.getJSONObject(i);
                            String name = json.getString("type");
                            String url = json.getString("url");
                            Log.d("tag", "name--" + name + ", url--" + url);
                            jb.setName(name);
                            jb.setUrl(url);
                            jsonBean.add(jb);
                        }
                        m.what = 200;
                        m.obj = jsonBean;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    m.what = response.code();
                }
                m.sendToTarget();
            }
        });
    }
    
}

























