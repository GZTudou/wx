package com.example.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.pojo.MessageType;
import com.example.pojo.TextMessage;
import com.example.util.MessageUtil;

public class CoreService {
	/**
	 * 处理消息核心类
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		Map<String,String> map = MessageUtil.parseXML(request);
		String content = "未知的消息类型！";
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(map.get("ToUserName"));
		textMessage.setToUserName(map.get("FromUserName"));
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageType.TEXT);
		if(map.get("MsgType").equals(MessageType.TEXT)){
			content = "您发送的消息是文本消息，内容为："+map.get("Content");
		}
		textMessage.setContent(content);
		String xml = MessageUtil.messageToXML(textMessage);
		return xml;
	}
	
}
