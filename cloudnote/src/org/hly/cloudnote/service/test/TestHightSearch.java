package org.hly.cloudnote.service.test;


import java.util.List;

import org.hly.cloudnote.entity.Note;
import org.hly.cloudnote.service.NoteService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestHightSearch {

	public static void main(String[] args) {
		String[] confs = { "spring-bean.xml", "spring-mybatis.xml" };
		ApplicationContext ac = new ClassPathXmlApplicationContext(confs);
		NoteService service=ac.getBean("noteService",NoteService.class);
		NoteResult result=service.hightSearch(null, "0","","");
		@SuppressWarnings("unchecked")
		List<Note> list=(List<Note>)result.getData();
		for (Note note : list) {
			System.out.println(note.getCn_note_title());
		}
		System.out.println("ËÑË÷¼ÇÂ¼Êý£º"+list.size());
	}
}
