package org.hly.cloudnote.controller.user;

import javax.annotation.Resource;

import org.hly.cloudnote.service.UserService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserRegistController {
   @Resource
	 private  UserService userService;
	
   @RequestMapping("/regist.do")
   @ResponseBody
   public NoteResult execute(String name,String password,String nick) throws Exception{
	   NoteResult result=userService.registUser(name, password, nick);
	   return result;
	   
   }
}
