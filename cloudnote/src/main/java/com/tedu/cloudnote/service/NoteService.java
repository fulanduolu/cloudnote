package com.tedu.cloudnote.service;

import com.tedu.cloudnote.util.NoteResult;

public interface NoteService {

	public NoteResult loadBookNotes(String bookId);
	
	public NoteResult loadNote(String noteId);
	
	public NoteResult updateNote(String noteId,String title,String body);
	
	public NoteResult addNote(String userId,String bookId,String bookName);
	
	public NoteResult deleteNote(String noteId);
	
	public NoteResult moveNote(String noteId,String bookId);
	
	public NoteResult shareNote(String noteId);
	
	public NoteResult showTrash(String userId);
	
	public NoteResult searchShareNote(String noteTitle,int page);
	
	public NoteResult loadShareNote(String shareId);
	
	public NoteResult shareLike(String userId,String shareId);
	
	public NoteResult showLikes(String userId);
	
	public NoteResult DeleteLikes(String userId,String noteId);
}
