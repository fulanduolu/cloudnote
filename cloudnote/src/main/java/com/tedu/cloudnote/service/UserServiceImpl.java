package com.tedu.cloudnote.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.cloudnote.dao.UserDAO;
import com.tedu.cloudnote.entity.User;
import com.tedu.cloudnote.util.NoteException;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

//扫描
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	//注入
	@Resource
	private UserDAO dao;
	
	public NoteResult checkLogin(String name, String password){
		User user=dao.findByName(name);
		NoteResult nr=new NoteResult();
		//判断用户名
		if(user==null){
			//没找到,账号错误
			nr.setStatus(1);
			nr.setMsg("用户名不存在");
			nr.setData(null);
			return nr;
		}
		
		//判断密码
		//将用户输入的明文加密
		try{
			String md5_pwd=NoteUtil.md5(password);
			if(!user.getCn_user_password().equals(md5_pwd)){
				//密码错误
				nr.setStatus(2);
				nr.setMsg("密码错误");
				nr.setData(null);
				return nr;
			}
		}catch(Exception e){
			throw new NoteException("密码加密异常",e);
		}
		//登陆成功
		nr.setStatus(0);
		nr.setMsg("登陆成功");
		
		user.setCn_user_password("");   //屏蔽掉密码不返回
		nr.setData(user);    //返回user信息
		return nr;
	}

	@Transactional
	public NoteResult addUser(String name, String nick, String password) {
		
		try{
			//检测是否重名
			User has_user=dao.findByName(name);
			NoteResult result=new NoteResult();
			//执行用户注册
			if(has_user!=null){
				result.setStatus(1);
				result.setMsg("用户名被占用");
				return result;	
			}
			User user=new User();
			user.setCn_user_name(name);      //设置用户名
			String md5_password=NoteUtil.md5(password);    
			user.setCn_user_password(md5_password);   //设置加密密码
			user.setCn_user_nick(nick);        //设置昵称
			user.setCn_user_id(NoteUtil.createId());    //设置用户ID  (使用UUID来产生主键)
			dao.save(user);
			
			//创建返回结果
			result.setStatus(0);
			result.setMsg("注册成功");
			return result;
		}catch(Exception e){
			throw new NoteException("用户注册异常",e);		
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
				//两个密码相同
				result.setStatus(0);
				result.setMsg("原始密码正确");
				result.setData(null);
			}else{
				result.setStatus(1);
				result.setMsg("原始密码错误");
				result.setData(null);
			}
		} catch (Exception e) {
			throw new NoteException("检查密码异常",e);
		}
		
		return result;
	}

	public NoteResult updatePassword(String new_password, String userId) {
		try {
			String newPassword=NoteUtil.md5(new_password);
			User user=new User();
			user.setCn_user_id(userId);
			user.setCn_user_password(newPassword);
			dao.updatePwd(user);    //进行修改密码
			NoteResult result=new NoteResult();
			result.setStatus(0);
			result.setMsg("修改密码成功");
			result.setData(null);
			return result;
		} catch (Exception e) {
			throw new NoteException("密码加密异常", e);
		}
	}

}
