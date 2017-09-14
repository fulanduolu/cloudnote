package com.tedu.cloudnote.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.cloudnote.dao.UserDAO;
import com.tedu.cloudnote.entity.User;
import com.tedu.cloudnote.util.NoteException;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

//ɨ��
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	//ע��
	@Resource
	private UserDAO dao;
	
	public NoteResult checkLogin(String name, String password){
		User user=dao.findByName(name);
		NoteResult nr=new NoteResult();
		//�ж��û���
		if(user==null){
			//û�ҵ�,�˺Ŵ���
			nr.setStatus(1);
			nr.setMsg("�û���������");
			nr.setData(null);
			return nr;
		}
		
		//�ж�����
		//���û���������ļ���
		try{
			String md5_pwd=NoteUtil.md5(password);
			if(!user.getCn_user_password().equals(md5_pwd)){
				//�������
				nr.setStatus(2);
				nr.setMsg("�������");
				nr.setData(null);
				return nr;
			}
		}catch(Exception e){
			throw new NoteException("��������쳣",e);
		}
		//��½�ɹ�
		nr.setStatus(0);
		nr.setMsg("��½�ɹ�");
		
		user.setCn_user_password("");   //���ε����벻����
		nr.setData(user);    //����user��Ϣ
		return nr;
	}

	@Transactional
	public NoteResult addUser(String name, String nick, String password) {
		
		try{
			//����Ƿ�����
			User has_user=dao.findByName(name);
			NoteResult result=new NoteResult();
			//ִ���û�ע��
			if(has_user!=null){
				result.setStatus(1);
				result.setMsg("�û�����ռ��");
				return result;	
			}
			User user=new User();
			user.setCn_user_name(name);      //�����û���
			String md5_password=NoteUtil.md5(password);    
			user.setCn_user_password(md5_password);   //���ü�������
			user.setCn_user_nick(nick);        //�����ǳ�
			user.setCn_user_id(NoteUtil.createId());    //�����û�ID  (ʹ��UUID����������)
			dao.save(user);
			
			//�������ؽ��
			result.setStatus(0);
			result.setMsg("ע��ɹ�");
			return result;
		}catch(Exception e){
			throw new NoteException("�û�ע���쳣",e);		
		}
	}

	public NoteResult checkPwd(String last_password, String userId) {
		String password=dao.findByUserId(userId);
		NoteResult result=new NoteResult();
		System.out.println(last_password+" "+userId);
		try {
			System.out.println(last_password);
			String old_password=NoteUtil.md5(last_password);
			if(old_password.equals(password)){
				//����������ͬ
				result.setStatus(0);
				result.setMsg("ԭʼ������ȷ");
				result.setData(null);
			}else{
				result.setStatus(1);
				result.setMsg("ԭʼ�������");
				result.setData(null);
			}
		} catch (Exception e) {
			throw new NoteException("��������쳣",e);
		}
		
		return result;
	}

	public NoteResult updatePassword(String new_password, String userId) {
		try {
			String newPassword=NoteUtil.md5(new_password);
			User user=new User();
			user.setCn_user_id(userId);
			user.setCn_user_password(newPassword);
			dao.updatePwd(user);    //�����޸�����
			NoteResult result=new NoteResult();
			result.setStatus(0);
			result.setMsg("�޸�����ɹ�");
			result.setData(null);
			return result;
		} catch (Exception e) {
			throw new NoteException("��������쳣", e);
		}
	}

}
