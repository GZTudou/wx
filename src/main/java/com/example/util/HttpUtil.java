/**
 * This file created at 2016年6月27日.
 *
 * Copyright (c) 2002-2016 Bingosoft, Inc. All rights reserved.
 */
package com.example.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpUtil {

	/**
	 * POST 提交
	 * @author Tudou
	 * @param url
	 * @param map
	 * @return
	 */
	public static JsonObject doPost(String url, Map<String, String> map) {
		JsonObject obj = null;
		HttpPost httpPost = new HttpPost(url);// 创建HttpPost对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

		Set<Entry<String, String>> entrys = map.entrySet();
		Iterator<Entry<String, String>> it = entrys.iterator();
		while (it.hasNext()) {
			Entry<String, String> e = it.next();
			params.add(new BasicNameValuePair(e.getKey(), e.getValue()));
		}
		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			response = httpclient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = response.getEntity();
				String result = EntityUtils.toString(httpEntity);// 取出应答字符串
				obj = new JsonParser().parse(result).getAsJsonObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(response);
		}
		return obj;
	}
	
	/**
	 * get 提交
	 * @author Tudou
	 * @param url 
	 * @return
	 */
	public static JsonObject doGet(String url) {
		JsonObject obj = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = response.getEntity();
				String result = EntityUtils.toString(httpEntity);// 取出应答字符串
				obj = new JsonParser().parse(result).getAsJsonObject();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(response);
		}
		return obj;
	}

	/**
	 * 释放资源
	 * 
	 * @param response
	 */
	public static void close(CloseableHttpResponse response) {
		try {
			if (response != null)
				response.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
