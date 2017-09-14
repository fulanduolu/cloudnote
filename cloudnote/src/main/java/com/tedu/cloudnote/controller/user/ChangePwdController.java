package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class ChangePwdController {

	@Resource
	private UserService service;
	
	@ResponseBody
	@RequestMapping("/user/update.do")
	public NoteResult execute(String new_password,String userId){
		NoteResult result=service.updatePassword(new_password, userId);
		return result;
	}
}
