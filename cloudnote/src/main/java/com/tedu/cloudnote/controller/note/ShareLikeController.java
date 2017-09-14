package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class ShareLikeController {

	
	@Resource
	private NoteService service;
	
	@RequestMapping("/note/share_like.do")
	@ResponseBody
	public NoteResult execute(String userId,String shareId){
		NoteResult result=service.shareLike(userId, shareId);
		return result;
	}
}
