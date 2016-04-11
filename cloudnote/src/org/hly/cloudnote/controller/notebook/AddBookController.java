package org.hly.cloudnote.controller.notebook;

import javax.annotation.Resource;

import org.hly.cloudnote.service.BookService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/notebook")
public class AddBookController {
	@Resource
	private BookService bookService;

	@RequestMapping("/add.do")
	@ResponseBody
	public NoteResult execute1(String bookName, String userId) {
		NoteResult result = bookService.addBook(userId, bookName);
		return result;
	}	
}
