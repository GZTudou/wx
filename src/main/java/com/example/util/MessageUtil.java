package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.example.pojo.TextMessage;
import com.thoughtworks.xstream.XStream;

/**
 * 处理消息工具类
 * @author Tudou
 *
 */
public class MessageUtil {

	private static XStream xstream = new XStream();
	
	/**
	 * 将微信服务器发来的XML格式的数据 存储到map集合中
	 * @param request
	 * @return
	 */
	public static Map<String,String> parseXML(HttpServletRequest request){
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = null;
        try {
	        inputStream = request.getInputStream();
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(inputStream); 
	        Element root = document.getRootElement(); // 得到XML根元素
	        List<Element> elementList = root.elements();// 得到根元素的所有子节点
	        for (Element e : elementList)	 // 遍历所有子节点
	            map.put(e.getName(), e.getText());
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
	        close(inputStream);
		}
		return map;
		
	}
	
	/**
	 * 将文本消息内容转换为XML格式
	 * @param textMessage
	 * @return
	 */
	public static String messageToXML(TextMessage textMessage){
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	
	/**
	 * 释放资源
	 * @param inputStream
	 */
	private static void close(InputStream inputStream){
		try {
				if(inputStream!=null)
					inputStream.close();
				inputStream = null; 
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
	}
	
}
