package com.example.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class WXUtil {
	
	private static final String TOKEN = "tudou";
	
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
		System.out.println(getSHA(sb.toString())+":"+sb.toString());
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
        return null;
    }
    
	
}
