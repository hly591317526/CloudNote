package org.hly.cloudnote.dao;

import java.util.List;
import java.util.Map;

import org.hly.cloudnote.entity.Note;

public interface NoteDao {
	public int deleteNotes(String[] ids);
	
	public void dynamicUpdate(Note note);
	
	public List<Note> findByBookId(String bookId);
	
	public Note findById(String noteId);
	
	public Note findByAll(String noteTitle,String userId,String bookId);
	
	public void add(Note note);

	public void update(String noteTitle,String noteBody,String noteId,Long lastModify);
	
	public void recycle(String noteId);
	
	public void move(String bookId,String noteId);
	
	public List<Note> hightSearch(Map<String, Object> params);
}
