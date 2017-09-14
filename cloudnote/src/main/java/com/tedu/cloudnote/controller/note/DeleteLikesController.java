package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class DeleteLikesController {
	
	@Resource(name="noteService")
	private NoteService service;
	
	@ResponseBody
	@RequestMapping("/note/delete_likes.do")
	public NoteResult execute(String noteId,String userId){
		NoteResult result=service.DeleteLikes(userId, noteId);
		return result;
	}
}
