package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

//É¨Ãè
@RequestMapping("/user")
@Controller
public class UserLoginController {

	@Resource   //×¢Èë
	private UserService service;
	
	@ResponseBody   //JSONÊä³ö
	@RequestMapping("/login.do")
	public NoteResult execute(String name,String password){	
		NoteResult result=service.checkLogin(name, password);
		return result;
	}
	
}
