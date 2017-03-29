package com.jfz.currency.dao.http;


import com.google.gson.JsonParser;
import com.jfz.currency.util.JsonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ping.chen on 2017/3/27.
 */
public class HttpTools {
    private HttpResponse response;
    private CloseableHttpClient client;
    public <T> T post(String url, String body, Class<T> clazz) {
         client =  HttpClients.createDefault();
        HttpPost method = new HttpPost(url);
        //请求超时
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
        try {
            method.setConfig(requestConfig);
            method.setEntity(new ByteArrayEntity(body.getBytes()));
            response = client.execute(method);
            //返回值
            HttpEntity entity = response.getEntity();
            return JsonUtil.toObj(EntityUtils.toString(entity), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            method = null;
            client.getConnectionManager().shutdown();
            client = null;
        }
    }

    public <T> T get(String url,String host, Class<T> clazz) {
        client =  HttpClients.createDefault();
        HttpGet method = new HttpGet(url);
        //请求超时
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
        try {
            method.setHeader("Host", host);
            method.setConfig(requestConfig);
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            return JsonUtil.toObj(EntityUtils.toString(entity), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            method = null;
            client.getConnectionManager().shutdown();
            client = null;
        }
    }
}
