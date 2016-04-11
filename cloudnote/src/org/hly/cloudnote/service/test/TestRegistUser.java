package org.hly.cloudnote.service.test;

import org.hly.cloudnote.service.UserService;
import org.hly.cloudnote.util.NoteResult;
import org.junit.Assert;
import org.junit.Test;

public class TestRegistUser extends ServiceTest {

	@Test
	public void test1() throws Exception {
		UserService service =super.getContext().getBean("userService", UserService.class);
           NoteResult result=service.registUser("demo", "123456", "demo");
           Assert.assertEquals(1, result.getStatus());
	}
	
	@Test
	public void test2() throws Exception {
		UserService service =super.getContext().getBean("userService", UserService.class);
           NoteResult result=service.registUser("helingyun", "123456", "hly");
           Assert.assertEquals(0, result.getStatus());
	}
	
	
}
