package org.hly.cloudnote.controller.user;

import javax.annotation.Resource;

import org.hly.cloudnote.service.UserService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("/user")
@Controller
public class UserChangePwd {
@Resource
  private UserService userService;
  
  @RequestMapping("/changepsw.do")
	@ResponseBody
	public NoteResult execute(String userId,String lastpwd,String newpwd) throws Exception{
	     NoteResult result=userService.changePwd(userId,lastpwd,newpwd);
	     return result;
  }
	
}
