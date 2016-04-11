package org.hly.cloudnote.controller.notebook;

import javax.annotation.Resource;

import org.hly.cloudnote.service.BookService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/notebook")
public class LoadBooksController {
	@Resource
	private BookService bookService;

	@RequestMapping("/loadbooks.do")
	@ResponseBody
	public NoteResult execute(String userId) {
		NoteResult result = bookService.loadUserBooks(userId);
		return result;
	}

}
