package org.hly.cloudnote.controller.note;

import javax.annotation.Resource;

import org.hly.cloudnote.service.NoteService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/note")
@Controller
public class MoveNoteController {
	@Resource
	private NoteService noteService;

	@RequestMapping("/move.do")
	@ResponseBody
	public NoteResult execute(String userId,String noteTitle,String bookId,String noteId) {
		NoteResult result= noteService.move(userId, noteTitle, bookId, noteId);
	         return result;
	}
}
