package org.hly.cloudnote.service.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class ServiceTest {

	public ApplicationContext getContext() {
		String[] confs = { "spring-bean.xml", "spring-mybatis.xml" };
		ApplicationContext ac = new ClassPathXmlApplicationContext(confs);
		return ac;
	}
}
