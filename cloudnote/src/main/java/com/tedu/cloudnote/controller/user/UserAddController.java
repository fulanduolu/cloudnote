package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;
@Controller   //…®√Ë
@RequestMapping("/user")
public class UserAddController {
	@Resource   //◊¢»ÎDI
	private UserService service;
	@RequestMapping("/add.do")
	@ResponseBody
	public NoteResult execute(String name,String nick,String password){
		NoteResult result=service.addUser(name, nick, password);
		return result;
	}
	
}
