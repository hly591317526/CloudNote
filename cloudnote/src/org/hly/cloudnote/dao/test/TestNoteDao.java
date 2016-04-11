package org.hly.cloudnote.dao.test;

import org.hly.cloudnote.dao.NoteDao;
import org.hly.cloudnote.entity.Note;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestNoteDao {
@Test
	public void test(){
	String conf = "spring-mybatis.xml";
	ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
	NoteDao dao=ac.getBean("noteDao",NoteDao.class);
	String [] ids={"019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0","01da5d69-89d5-4140-9585-b559a97f9cb0","046b0110-67f9-48c3-bef3-b0b23bda9d4e"};
     int i=dao.deleteNotes(ids);
     System.out.println(i);
}
}
