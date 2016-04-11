package org.hly.cloudnote.controller.note;

import javax.annotation.Resource;

import org.hly.cloudnote.service.NoteService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/note")
@Controller
public class AddNoteController {
	@Resource
	private NoteService noteService;

	@RequestMapping("/add.do")
	@ResponseBody
	public NoteResult execute1(String noteTitle,String userId,String bookId) {
		NoteResult result = noteService.add(noteTitle,userId,bookId);
		return result;
	}
}
