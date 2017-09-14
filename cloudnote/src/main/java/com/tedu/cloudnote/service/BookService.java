package com.tedu.cloudnote.service;

import com.tedu.cloudnote.util.NoteResult;

public interface BookService {

	public NoteResult loadUserBooks(String userId);
	
	public NoteResult addBook(String userId,String name);
	
	public NoteResult updateName(String bookId,String bookName);
}
