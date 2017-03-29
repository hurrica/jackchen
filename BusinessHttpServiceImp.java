package com.yunva.channel.admin.business.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 服务请求发送处理
 * ping.chen
 * 2016.9.26
 * */

@Service
public class BusinessHttpServiceImp implements BusinessHttpService {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public <T> T post(String url, String json, Class<T> clazz) {
		HttpResponse response;
		CloseableHttpClient httpClient =  HttpClients.createDefault();
		HttpPost method = new HttpPost(url);
		//请求超时
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
    	try {
    		method.setConfig(requestConfig);
    		method.setEntity(new ByteArrayEntity(json.getBytes()));
			// 设置编码
			response = httpClient.execute(method);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}else{
				//返回参数
				return JSON.parseObject(response.getEntity().getContent(), clazz);
			}
			
		} catch (Exception e) {
			// 发生网络异常
			log.error("BusinessHttpServiceImp.post::" + e);
			e.printStackTrace();
			return null;
		} finally {
			method = null;
			httpClient.getConnectionManager().shutdown();
			httpClient = null;
		}
	}
	public String post(String url, String json) {
		HttpResponse response;
		CloseableHttpClient httpClient =  HttpClients.createDefault();
		HttpPost method = new HttpPost(url);
		//请求超时
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
    	try {
    		method.setConfig(requestConfig);
    		method.addHeader("Content-Type", "application/json; charset=utf-8");
    		method.addHeader("Accept", "application/json; charset=utf-8");
    		method.setEntity(new ByteArrayEntity(json.getBytes()));
			// 设置编码
			response = httpClient.execute(method);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}else{
				//返回参数
				byte [] texts = new byte[2048];
				response.getEntity().getContent().read(texts);
				
				return new String(texts);
			}
			
		} catch (Exception e) {
			// 发生网络异常
			log.error("BusinessHttpServiceImp.post::" + e);
			e.printStackTrace();
			return null;
		} finally {
			method = null;
			httpClient.getConnectionManager().shutdown();
			httpClient = null;
		}
	}
}
