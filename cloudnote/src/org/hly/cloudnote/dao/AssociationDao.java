package org.hly.cloudnote.dao;

import java.util.List;

import org.hly.cloudnote.entity.Book;
import org.hly.cloudnote.entity.User;

public interface AssociationDao {
  public User findUserAndBooks(String userId);
  
  public User findUserAndBooks1(String userId);
//单个对象关联映射
   public List<Book> findAllbooks();
}
