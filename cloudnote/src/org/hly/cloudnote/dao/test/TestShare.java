package org.hly.cloudnote.dao.test;


import org.hly.cloudnote.dao.ShareDao;
import org.hly.cloudnote.entity.Share;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestShare {
	@Test
	public void test1() {
		String conf = "spring-mybatis.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
           ShareDao dao= ac.getBean("shareDao",ShareDao.class);
           System.out.println(dao.load("%±Ê¼Ç2%"));
           
	}

}