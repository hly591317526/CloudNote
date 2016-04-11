package org.hly.cloudnote.dao.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.hly.cloudnote.dao.UserDao;
import org.hly.cloudnote.entity.User;

public class TestFindByName {
	@Test
	public void test1(){
		String conf = "spring-mybatis.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		UserDao dao = ac.getBean("userDao",UserDao.class);
		User user = dao.findByName("demo");
		//采用断言,比对实际结果和预期结果
		Assert.assertNotNull(user);
	}
	
	@Test
	public void test2(){
		String conf = "spring-mybatis.xml";
		ApplicationContext ac = 
			new ClassPathXmlApplicationContext(conf);
		UserDao dao = ac.getBean(
			"userDao",UserDao.class);
		User user = dao.findByName("demo1");
		//采用断言,比对实际结果和预期结果
		Assert.assertNull(user);
	}
}



