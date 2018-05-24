package com.ko30.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密算法工具类
 * @author DengHuaqing
 * 2017-04-14
 *
 */
public class EncryptionUtil {
	
	private static final String encryModel="MD5";  
	
	/**
	 * 对注册用户及登录用户的密码加密
	 * @param password
	 * @return
	 */
	 public static String md5Password(String password) {  
		  
	        try {  
	            // 得到一个信息摘要器  
	            MessageDigest digest = MessageDigest.getInstance("md5");  
	            byte[] result = digest.digest(password.getBytes());  
	            StringBuffer buffer = new StringBuffer();  
	            // 把没一个byte 做一个与运算 0xff;  
	            for (byte b : result) {  
	                // 与运算  
	                int number = b & 0xff;// 加盐  
	                String str = Integer.toHexString(number);  
	                if (str.length() == 1) {  
	                    buffer.append("0");  
	                }  
	                buffer.append(str);  
	            }  
	  
	            // 标准的md5加密后的结果  
	            return buffer.toString();  
	        } catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();  
	            return "";  
	        }  
	  
	    } 
	 
	 /**
	  * 32位md5加密
	  * @param str
	  * @return
	  */
	 public  static String md5(String str) {  
	        return encrypt(encryModel, str);  
	    }  
	    public static String encrypt(String algorithm, String str) {  
	        try {  
	            MessageDigest md = MessageDigest.getInstance(algorithm);  
	            md.update(str.getBytes());  
	            StringBuffer sb = new StringBuffer();  
	            byte[] bytes = md.digest();  
	            for (int i = 0; i < bytes.length; i++) {  
	                int b = bytes[i] & 0xFF;  
	                if (b < 0x10) {  
	                    sb.append('0');  
	                }  
	                sb.append(Integer.toHexString(b));  
	            }  
	            return sb.toString();  
	        } catch (Exception e) {  
	            return "";  
	        }  
	    }  

}
