package org.hly.cloudnote.service;

import org.hly.cloudnote.util.NoteResult;

public interface BookService {
	public NoteResult loadUserBooks(String userId);

	public NoteResult addBook(String userId,String bookName);

	public NoteResult deleteBook(String bookId);
	
}
