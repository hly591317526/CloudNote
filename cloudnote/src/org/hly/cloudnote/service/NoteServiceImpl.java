package org.hly.cloudnote.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hly.cloudnote.dao.NoteDao;
import org.hly.cloudnote.entity.LikeNote;
import org.hly.cloudnote.entity.Note;
import org.hly.cloudnote.util.NoteResult;
import org.hly.cloudnote.util.NoteUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("noteService")
@Transactional
public class NoteServiceImpl implements NoteService {
	@Resource
	private NoteDao noteDao;

	@Override
	public NoteResult LoadBookNotes(String bookId) {
		NoteResult result = new NoteResult();
		List<Note> notes = noteDao.findByBookId(bookId);
		result.setStatus(0);
		result.setMsg("���رʼǱ��ɹ�");
		result.setData(notes);
		return result;
	}

	@Override
	public NoteResult LoadNote(String noteId) {
		NoteResult result = new NoteResult();
		Note note = noteDao.findById(noteId);
		result.setStatus(0);
		result.setMsg("���رʱ����ݳɹ�");
		result.setData(note);
		return result;
	}

	@Override
	public NoteResult add(String noteTitle, String userId, String bookId) {
		NoteResult result = new NoteResult();
		Note b = noteDao.findByAll(noteTitle, userId, bookId);
		if (b == null) {
			// ���Դ����µıʼ�
			Note note = new Note();
			note.setCn_note_body("");
			note.setCn_note_create_time(System.currentTimeMillis());
			note.setCn_note_id(NoteUtil.createId());
			note.setCn_note_last_modify_time(System.currentTimeMillis());
			note.setCn_note_status_id("1");
			note.setCn_note_title(noteTitle);
			note.setCn_note_type_id("1");
			note.setCn_notebook_id(bookId);
			note.setCn_user_id(userId);
			noteDao.add(note);
			result.setStatus(0);
			result.setMsg("�����ʼǳɹ���");
			result.setData(note.getCn_note_id());
			return result;
		} else {
			// ���ֱ�ռ��
			result.setStatus(1);
			result.setMsg("�ʼ�����");
			return result;
		}
	}

	@Override
	public NoteResult update(String noteTitle, String noteBody, String noteId,
			String userId, String bookId) {
		NoteResult result = new NoteResult();
		Note b = noteDao.findByAll(noteTitle, userId, bookId);
		// �Ĳ������ֶ�����
		if (b == null || b.getCn_note_id().equals(noteId)) {
			// ����ʹ�õıʼ�����
			noteDao.update(noteTitle, noteBody, noteId,
					System.currentTimeMillis());
			result.setStatus(0);
			result.setMsg("���³ɹ���");
			return result;
		} else {
			// ���ֱ�ռ��
			result.setStatus(1);
			result.setMsg("�ʼ�����");
			return result;
		}
	}

	@Override
	public NoteResult recycle(String noteId) {
		NoteResult result = new NoteResult();
		noteDao.recycle(noteId);
		result.setStatus(0);
		result.setMsg("ɾ���ɹ�!������ڻ���վ�������ɾ���ʼ�");
		return result;
	}

	@Override
	public NoteResult move(String userId, String noteTitle, String bookId,
			String noteId) {
		NoteResult result = new NoteResult();
		Note note = noteDao.findByAll(noteTitle, userId, bookId);
		if (note == null) {
			// ��������������
			noteDao.move(bookId, noteId);
			result.setMsg("ת�Ƴɹ�");
			result.setStatus(0);
		} else {
			result.setStatus(1);
			result.setMsg("ת��ʧ�ܣ�Ŀ��ʼǱ���ͬ���ʼ�");
		}
		return result;
	}

	// ��̬��ѯsql��䣬ƴ��
	@Override
	public NoteResult hightSearch(String title, String status, String begin,
			String end) {
		Map<String, Object> params = new HashMap<String, Object>();
		// ������ⲻΪ�գ������map����
		if (title != null && !"".equals(title)) {
			// ģ����ѯ
			title = "%" + title + "%";
			params.put("title", title);
		}
		// ���״̬û��ѡ��ȫ�������map����
		if (status != null && !"0".equals(status)) {
			params.put("status", status);
		}
		// �����ʼ���ڲ�Ϊ�գ������map����
		if (begin != null && !"".equals(begin)) {
			// ���ַ���ת�������� ������Long����ʾ
			// ֻ֧��yyyy-mm-dd
			Date begindate = java.sql.Date.valueOf(begin);
			params.put("beginDate", begindate.getTime());
		}
		// ����������ڲ�Ϊ�գ������map����
		if (end != null && !"".equals(end)) {
			// ���ַ���ת�������� ������Long����ʾ
			Date enddate = java.sql.Date.valueOf(end);
			Calendar c = Calendar.getInstance();
			// �޸����ڼ�һ��
			c.setTime(enddate);
			c.add(Calendar.DATE, 1);
			params.put("endDate", c.getTimeInMillis());
		}
		// ����dao
		List<Note> list = noteDao.hightSearch(params);
		NoteResult result = new NoteResult();
		result.setMsg("��ѯ�ɹ���");
		result.setData(list);
		result.setStatus(0);
		return result;
	}

	@Override
	public NoteResult loadTrash(String userId) {
		NoteResult result = new NoteResult();
		List<Note> notes = noteDao.loadTrash(userId);
		result.setStatus(0);
		result.setMsg("���رʼǱ��ɹ�");
		result.setData(notes);
		return result;

	}

	@Override
	public NoteResult loadTrashContent(String noteId) {
		NoteResult result = new NoteResult();
		Note note = noteDao.findById(noteId);
		result.setStatus(0);
		result.setMsg("���رʼ����ݳɹ�");
		result.setData(note);
		return result;
	}

	@Override
	public NoteResult delete(String noteId) {
		NoteResult result = new NoteResult();
		noteDao.deleteNotes(new String[] { noteId });
		result.setStatus(0);
		result.setMsg("����ɾ���ʼǳɹ�");
		return result;
	}

	@Override
	public NoteResult replay(String noteId) {
		NoteResult result = new NoteResult();
		noteDao.replay(noteId);
		result.setStatus(0);
		result.setMsg("�ָ��ɹ���");
		return result;
	}

	@Override
	public NoteResult likeNote(String userId, String title, String content) {
		NoteResult result = new NoteResult();
		LikeNote note = noteDao.findLikeNoteByTitle(title, userId);
		if (note != null) {
			result.setStatus(1);
			result.setMsg("���Ѿ��ղع��˱ʼǣ�");
		} else {
			String likeId = NoteUtil.createId();
			noteDao.noteLike(likeId, userId, title, content);
			result.setStatus(0);
			result.setMsg("�ղسɹ���");
		}
		return result;
	}

	@Override
	public NoteResult loadLike(String userId) {
		NoteResult result = new NoteResult();
		List<LikeNote> lites = noteDao.finLikeNoteByUserId(userId);
		result.setStatus(0);
		result.setMsg("�����ղرʼǳɹ���");
		result.setData(lites);
		return result;
	}

	@Override
	public NoteResult loadLikeNoteContent(String likeId) {
		NoteResult result = new NoteResult();
          LikeNote note=noteDao.findLikeNoteByLikeId(likeId);
		 result.setStatus(0);
		 result.setMsg("����ɹ�");
          result.setData(note);		
		return result;
	}

	@Override
	public NoteResult deleteLikeNote(String likeId) {
		NoteResult result = new NoteResult();
		noteDao.deleteLikeNote(likeId);
		result.setStatus(0);
		result.setMsg("ɾ���Ѳرʼǳɹ���");
		return result;
	}
}
