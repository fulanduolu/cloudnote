package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class AddNoteController {

	@Resource
	private NoteService service;
	
	@ResponseBody
	@RequestMapping("/note/add")
	public NoteResult execute(String bookId,String userId,String bookName){
		System.out.println(bookName);
		NoteResult result=service.addNote(userId, bookId, bookName);
		return result;
	}
}
