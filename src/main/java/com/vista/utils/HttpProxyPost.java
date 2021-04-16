package com.vista.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class HttpProxyPost {	
	
	private final static Logger logger = LoggerFactory.getLogger(HttpProxyPost.class);
	
	@Value("${spring.fund.proxyIp}")
	private String proxyIp;
	@Value("${spring.fund.proxyPort}")
	private String proxyPort;
	
	
	
//	final static PropertiesConfiguration config = PropsUtils.INSTANCE.getConfig();
	/**
	 * http post请求
	 * 
	 * @param postUrl
	 * @param json
	 * @param isHttps
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public  JSONObject requestWithPost(String postUrl, JSONObject json,boolean isHttps,boolean proxy) {
		
		HttpResponse rsp = null;
		CloseableHttpClient client = null;
		JSONObject rspJson =null;
		try {
			client = SSLClient.getHttpsClient(isHttps);
		} catch (Exception e1) {
			logger.error("获取httpclient 异常",e1);
		}
		 HttpPost post= new HttpPost(postUrl);
		 logger.info("目标请求地址："+postUrl);
		if(proxy){
//			String proxyIp =config.getString("proxyIp");
			int port = Integer.parseInt(proxyPort);
			logger.info("使用代理模式发送请求："+proxyIp+","+port);
			HttpHost proxyHost = new HttpHost(proxyIp,port);
			
			RequestConfig config = RequestConfig.custom().setProxy(proxyHost).build();
			post.setConfig(config);
		}
		StringEntity params = null;
		String respAllStr = "";
		try {
			params = new StringEntity(json.toString(), "UTF-8");
			params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
			params.setContentType("application/json");
			post.addHeader("content-type", "application/json");
			post.setEntity(params);

			rsp = client.execute(post);
			HttpEntity entity = rsp.getEntity();

			String rspString = EntityUtils.toString(entity, "UTF-8");
			rspJson = JSONObject.parseObject(rspString);
			logger.info("请求返回报文："+rspJson);
		} catch (Exception e) {
			logger.error("外调异常", e);

		} finally {
			client.getConnectionManager().shutdown();
		}
		return rspJson;
	}


}
