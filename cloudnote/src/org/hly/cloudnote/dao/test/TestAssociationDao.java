package org.hly.cloudnote.dao.test;


import java.util.List;

import org.hly.cloudnote.dao.AssociationDao;
import org.hly.cloudnote.entity.Book;
import org.hly.cloudnote.entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAssociationDao {
	
	public static void main(String[] args) {
		String conf = "spring-mybatis.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
      AssociationDao dao=ac.getBean("associationDao",AssociationDao.class);
      List<Book> list=dao.findAllbooks();
        for (Book book : list) {
			System.out.println(book.getCn_notebook_id()+" "+book.getCn_notebook_name()+" "+book.getUser().getCn_user_name());
		}
	}
	}
    
	
