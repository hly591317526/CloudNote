package org.hly.cloudnote.dao;

import java.util.List;

import org.hly.cloudnote.entity.Book;
import org.hly.cloudnote.entity.User;

public interface AssociationDao {
  public User findUserAndBooks(String userId);
  
  public User findUserAndBooks1(String userId);
//�����������ӳ��
   public List<Book> findAllbooks();
}
