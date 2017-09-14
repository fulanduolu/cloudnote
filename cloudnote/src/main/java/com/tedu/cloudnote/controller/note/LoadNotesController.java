package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;
@Controller
public class LoadNotesController {
	
	@Resource
	private NoteService service;
	
	@ResponseBody
	@RequestMapping("/note/loadnotes.do")
	public NoteResult execute(String bookId){
		NoteResult result=service.loadBookNotes(bookId);
		return result;
	}
	
}
