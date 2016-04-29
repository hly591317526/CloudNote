package org.hly.cloudnote.dao;

import java.util.List;

import org.hly.cloudnote.entity.Book;

public interface BookDao {
  public List<Book> findByUserId(String userId);
  
  public void save(Book book);
  
  public Book findByBookName(String bookName,String userId);

	public void delete(String bookId);

	public void deleteNotes(String bookId);
}
