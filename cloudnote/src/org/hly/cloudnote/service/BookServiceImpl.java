package org.hly.cloudnote.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.hly.cloudnote.dao.BookDao;
import org.hly.cloudnote.entity.Book;
import org.hly.cloudnote.util.NoteResult;
import org.hly.cloudnote.util.NoteUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("bookService")
@Transactional
public class BookServiceImpl implements  BookService {
	@Resource
	private BookDao bookDao;

	@Override
	public NoteResult loadUserBooks(String userId) {
		NoteResult result = new NoteResult();
		List<Book> books = bookDao.findByUserId(userId);
		result.setStatus(0);
		result.setMsg("�����ڱʼǱ��ɹ�");
		result.setData(books);
		return result;
	}

	@Override
	public NoteResult addBook(String userId, String bookName) {
		NoteResult result = new NoteResult();
		Book b = bookDao.findByBookName(bookName, userId);
		if (b == null) {
			//���Դ����µıʼǱ�
			Book book = new Book();
			book.setCn_user_id(userId);
			book.setCn_notebook_name(bookName);
			book.setCn_notebook_id(NoteUtil.createId());
			book.setCn_notebook_desc("");
			book.setCn_notebook_type_id("5");
			Timestamp time = new Timestamp(System.currentTimeMillis());
			book.setCn_notebook_createtime(time);
			bookDao.save(book);
			result.setStatus(0);
			result.setMsg("�����ʼǱ��ɹ���");
			result.setData(book.getCn_notebook_id());
			return result;
		} else {
			//���ֱ�ռ��
			result.setStatus(1);
			result.setMsg("�ʼǱ�����");
			return result;
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	public NoteResult deleteBook(String bookId) {
      NoteResult result=new NoteResult();
		bookDao.delete(bookId);
		bookDao.deleteNotes(bookId);
		result.setStatus(0);
		result.setMsg("ɾ���ɹ���");
		return result;
	}

}
