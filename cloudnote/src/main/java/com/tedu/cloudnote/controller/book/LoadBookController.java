package com.tedu.cloudnote.controller.book;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.BookService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class LoadBookController {

	@Resource
	private BookService bookservice;
	
	@RequestMapping("/book/loadbooks.do")
	@ResponseBody
	public NoteResult execute(String userId){
		NoteResult result=bookservice.loadUserBooks(userId);
		return result;
	}
	
}
