package com.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import com.tedu.cloudnote.entity.Note;

public interface NoteDAO {

	public List<Map> findByBookId(String bookId);
	
	public Note findById(String noteId); 
	
	public int updateNote(Note note);
	
	public void save(Note note);
	
	public int updateStatus(String noteId);
	
	public int updateBookId(Note note);
	
	public int updateType(String noteId);
	
	public List<Note> findTrashById(String userId);
	
	public Note findByIdAndTypeId(Note note);
	
	public void insertLike(Note note);
	
	public List<Note> findByLike(String userId);
	
	public void deleteById(Note note);
}
