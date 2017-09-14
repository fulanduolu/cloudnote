package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

//ɨ��
@RequestMapping("/user")
@Controller
public class UserLoginController {

	@Resource   //ע��
	private UserService service;
	
	@ResponseBody   //JSON���
	@RequestMapping("/login.do")
	public NoteResult execute(String name,String password){	
		NoteResult result=service.checkLogin(name, password);
		return result;
	}
	
}
