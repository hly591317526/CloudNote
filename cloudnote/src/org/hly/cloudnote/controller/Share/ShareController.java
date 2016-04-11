package org.hly.cloudnote.controller.Share;

import javax.annotation.Resource;

import org.hly.cloudnote.service.ShareService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/share")
public class ShareController {
     @Resource
	private ShareService shareService;
     
     @RequestMapping("/add.do")
     @ResponseBody
     public NoteResult execute(String noteId){
    	  NoteResult result=shareService.add(noteId);
           return result;    	 
     }
     
     @RequestMapping("/load.do")
     @ResponseBody
     public NoteResult execute1(String search){
    	   NoteResult result=shareService.load(search);
    	   return result;
     }
     
     @RequestMapping("/loadShare.do")
     @ResponseBody
     public NoteResult execute3(String shareId){
    	   NoteResult result=shareService.loadShare(shareId);
    	   return result;
     }
     
}
