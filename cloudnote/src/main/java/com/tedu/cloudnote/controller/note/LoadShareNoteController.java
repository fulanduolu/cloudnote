package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class LoadShareNoteController {

	@Resource
	private NoteService service;
	
	@ResponseBody
	@RequestMapping("/note/load_share")
	public NoteResult execute(String shareId){
		NoteResult result=service.loadShareNote(shareId);
		return result;
	}
}
