package org.hly.cloudnote.dao.test;

import java.util.List;

import org.hly.cloudnote.dao.BookDao;
import org.hly.cloudnote.entity.Book;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFindByUserId {
	@Test
	public void test1() {
		String conf = "spring-mybatis.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
           BookDao dao= ac.getBean("bookDao",BookDao.class);
           List<Book> list=dao.findByUserId("39295a3d-cc9b-42b4-b206-a2e7fab7e77c");
           System.out.println(list);
	}

	@Test
	public void test2() {
		String conf = "spring-mybatis.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
           BookDao dao= ac.getBean("bookDao",BookDao.class);
          Book book=dao.findByBookName("wsf1","39295a3d-cc9b-42b4-b206-a2e7fab7e77c");
           System.out.println(book);
	}
}
