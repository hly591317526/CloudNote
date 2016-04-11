package org.hly.cloudnote.service;

import org.hly.cloudnote.util.NoteResult;

public interface NoteService {
	public NoteResult LoadBookNotes(String bookId);

	public NoteResult LoadNote(String noteId);

	public NoteResult add(String noteTitle, String userId, String bookId);

	public NoteResult update(String noteTitle, String noteBody, String noteId,
			String userId, String bookId);
	
	public NoteResult recycle(String noteId);
	
	public NoteResult  move(String userId,String noteTitle,String bookId,String noteId);
	
	public NoteResult hightSearch(String title,String status,String begin,String end);
}
