package com.example.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.example.pojo.AccessToken;
import com.google.gson.JsonObject;



public class WeiXinUtil {
	
	private static final String TOKEN = "tudou";
	private static final String APPID="wx6c0f9b6a44271e19";
	private static final String APPSECRET="1dbd1f608b43d70f1d7711a29722c334";
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    
    /**
     * 获取token票据
     * @return
     */
    public static AccessToken getAccessToken(){
    	AccessToken token = new AccessToken();
    	String url = TOKEN_URL.replaceAll("APPID", APPID).replaceAll("APPSECRET", APPSECRET);
    	JsonObject obj = HttpUtil.doGet(url);
    	token.setAccess_token(obj.get("access_token").toString());
    	token.setExpires_in(Integer.parseInt(obj.get("expires_in").toString()));
    	Logger.log("获取TOKEN信息："+token);
    	return token;
    }
    
	
	/**
	 * 开发接口接入 服务器校验
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		String[] params = new String[]{TOKEN,timestamp,nonce};
		Arrays.sort(params);
		StringBuilder sb = new StringBuilder();
		for (String string : params) {
			sb.append(string);
		}
		if(signature.equals(getSHA(sb.toString())))
			return true;
		return false;
	}

    /** 
     * SHA、SHA1加密
     * @parameter：   str：待加密字符串
     * @return：  加密串
    **/
    private static String getSHA(String param) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            digest.update(param.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexStr = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    
	
}
