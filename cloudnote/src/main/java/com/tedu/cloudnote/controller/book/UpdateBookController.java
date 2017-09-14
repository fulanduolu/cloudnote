package com.tedu.cloudnote.controller.book;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.BookService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class UpdateBookController {

	@Resource
	private BookService service;
	
	@RequestMapping("/book/updateName.do")
	@ResponseBody
	public NoteResult execute(String bookId,String bookName){
		NoteResult result=service.updateName(bookId, bookName);
		return result;
	}
}
