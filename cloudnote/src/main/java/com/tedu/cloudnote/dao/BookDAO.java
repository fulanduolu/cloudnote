package com.tedu.cloudnote.dao;

import java.util.List;

import com.tedu.cloudnote.entity.Book;

public interface BookDAO {

	public List<Book> findByUserId(String userId);
	
	public void save(Book book);
	
	public void update(Book book);
	
}
