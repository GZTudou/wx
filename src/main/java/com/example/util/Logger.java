package com.example.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger {
	
	private static final Log log = LogFactory.getLog(Logger.class);
	
	public static void log(String msg){
		log.info(msg);
	}
	
}
