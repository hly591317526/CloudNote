package org.hly.cloudnote.controller.notebook;

import javax.annotation.Resource;

import org.hly.cloudnote.service.BookService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/notebook")
public class DeleteBook {
	@Resource
	private BookService bookService;

	@ResponseBody
	@RequestMapping("/delete.do")
	public NoteResult execute(String bookId) {
		NoteResult noteResult = bookService.deleteBook(bookId);
		return noteResult;
	}

}
