package org.hly.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.hly.cloudnote.service.UserService;
import org.hly.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/user")
public class UserLoginController {
	@Resource
	private UserService userService;

	
	@RequestMapping("/login.do")        // ��Ӧ/user/login.do����
	@ResponseBody                             	// ��NoteResult����json���
	public NoteResult execute(String name, String password) throws Exception {
		NoteResult result = userService.checkLogin(name, password);
		return result;
	}

	
	
	
	
}
