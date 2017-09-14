package com.tedu.cloudnote.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;


public class NoteUtil {

	/**
	 * 将传入的字符串加密处理(MD5加密方法)
	 * @param src 明文字符
	 * @return  密文字符
	 * @throws Exception 
	 */
	public static String md5(String src) throws Exception{
		//将字符串信息采用MD5处理
		MessageDigest md=MessageDigest.getInstance("MD5");
		byte[] output=md.digest(src.getBytes());
		
		//将MD5处理结果利用Base64转换成为字符串
		String s=Base64.encodeBase64String(output);
		return s;
	}
	
	public static void main(String[] args) throws Exception{
//		System.out.println(md5("123456"));
//		System.out.println(md5("12366788sadasd9052342375asdasdasdasdasdas"));
		System.out.println(createId());
		System.out.println(createId());
	}
	
	public static String createId(){
		UUID uuid=UUID.randomUUID();   //随机的生成一个UUID字符串来将其写出
		return uuid.toString().replace("-", "");
	}
}
