package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class SearchShareNoteController {

	@Resource
	private NoteService service;
	
	
	@ResponseBody
	@RequestMapping("/note/search_share.do")
	public NoteResult execute(String noteTitle,int page){
		NoteResult result=service.searchShareNote(noteTitle,page);
		return result;
	}
}
