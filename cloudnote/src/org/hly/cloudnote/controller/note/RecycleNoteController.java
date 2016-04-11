package org.hly.cloudnote.controller.note;

import javax.annotation.Resource;

import org.hly.cloudnote.service.NoteService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/note")
@Controller
public class RecycleNoteController {
	@Resource
	private NoteService noteService;

	@RequestMapping("/recycle.do")
	@ResponseBody
	public NoteResult execute1(String noteId) {
		NoteResult result = noteService.recycle(noteId);
		return result;
	}
}
