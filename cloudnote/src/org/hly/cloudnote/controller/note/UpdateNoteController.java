package org.hly.cloudnote.controller.note;

import javax.annotation.Resource;

import org.hly.cloudnote.service.NoteService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/note")
public class UpdateNoteController {
   @Resource
    private NoteService noteService;
   
@RequestMapping("/update.do")   
@ResponseBody
   public NoteResult update(String noteTitle,String noteBody,String noteId,String bookId,String userId){
	     NoteResult result=noteService.update(noteTitle, noteBody, noteId, userId, bookId);
	     return result;
   }
}
