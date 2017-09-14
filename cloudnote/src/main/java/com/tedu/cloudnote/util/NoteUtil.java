package com.tedu.cloudnote.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;


public class NoteUtil {

	/**
	 * ��������ַ������ܴ���(MD5���ܷ���)
	 * @param src �����ַ�
	 * @return  �����ַ�
	 * @throws Exception 
	 */
	public static String md5(String src) throws Exception{
		//���ַ�����Ϣ����MD5����
		MessageDigest md=MessageDigest.getInstance("MD5");
		byte[] output=md.digest(src.getBytes());
		
		//��MD5����������Base64ת����Ϊ�ַ���
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
		UUID uuid=UUID.randomUUID();   //���������һ��UUID�ַ���������д��
		return uuid.toString().replace("-", "");
	}
}
