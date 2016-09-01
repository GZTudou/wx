package com.example.util;

import com.example.pojo.BaseMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TestUtil {
	
	public static void main(String[] args) {
		Gson gson = new Gson();
		String str = gson.toJson(new BaseMessage());
		JsonObject object = new JsonParser().parse(str).getAsJsonObject();  
		System.out.println(object);
		
		WeiXinUtil.getAccessToken();
	}
	
}
