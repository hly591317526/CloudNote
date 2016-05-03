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
		result.setMsg("加载笔记本成功");
		result.setData(notes);
		return result;
	}

	@Override
	public NoteResult LoadNote(String noteId) {
		NoteResult result = new NoteResult();
		Note note = noteDao.findById(noteId);
		result.setStatus(0);
		result.setMsg("加载笔本内容成功");
		result.setData(note);
		return result;
	}

	@Override
	public NoteResult add(String noteTitle, String userId, String bookId) {
		NoteResult result = new NoteResult();
		Note b = noteDao.findByAll(noteTitle, userId, bookId);
		if (b == null) {
			// 可以创建新的笔记
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
			result.setMsg("创建笔记成功！");
			result.setData(note.getCn_note_id());
			return result;
		} else {
			// 名字被占用
			result.setStatus(1);
			result.setMsg("笔记重名");
			return result;
		}
	}

	@Override
	public NoteResult update(String noteTitle, String noteBody, String noteId,
			String userId, String bookId) {
		NoteResult result = new NoteResult();
		Note b = noteDao.findByAll(noteTitle, userId, bookId);
		// 改不改名字都可以
		if (b == null || b.getCn_note_id().equals(noteId)) {
			// 可以使用的笔记名字
			noteDao.update(noteTitle, noteBody, noteId,
					System.currentTimeMillis());
			result.setStatus(0);
			result.setMsg("更新成功！");
			return result;
		} else {
			// 名字被占用
			result.setStatus(1);
			result.setMsg("笔记重名");
			return result;
		}
	}

	@Override
	public NoteResult recycle(String noteId) {
		NoteResult result = new NoteResult();
		noteDao.recycle(noteId);
		result.setStatus(0);
		result.setMsg("删除成功!你可以在回收站看到最近删除笔记");
		return result;
	}

	@Override
	public NoteResult move(String userId, String noteTitle, String bookId,
			String noteId) {
		NoteResult result = new NoteResult();
		Note note = noteDao.findByAll(noteTitle, userId, bookId);
		if (note == null) {
			// 不存在重名现象
			noteDao.move(bookId, noteId);
			result.setMsg("转移成功");
			result.setStatus(0);
		} else {
			result.setStatus(1);
			result.setMsg("转移失败：目标笔记本有同名笔记");
		}
		return result;
	}

	// 动态查询sql语句，拼接
	@Override
	public NoteResult hightSearch(String title, String status, String begin,
			String end) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 如果标题不为空，就添加map参数
		if (title != null && !"".equals(title)) {
			// 模糊查询
			title = "%" + title + "%";
			params.put("title", title);
		}
		// 如果状态没有选择全部就添加map参数
		if (status != null && !"0".equals(status)) {
			params.put("status", status);
		}
		// 如果开始日期不为空，就添加map参数
		if (begin != null && !"".equals(begin)) {
			// 将字符串转换成日期 并且用Long并表示
			// 只支持yyyy-mm-dd
			Date begindate = java.sql.Date.valueOf(begin);
			params.put("beginDate", begindate.getTime());
		}
		// 如果结束日期不为空，就添加map参数
		if (end != null && !"".equals(end)) {
			// 将字符串转换成日期 并且用Long并表示
			Date enddate = java.sql.Date.valueOf(end);
			Calendar c = Calendar.getInstance();
			// 修改日期加一天
			c.setTime(enddate);
			c.add(Calendar.DATE, 1);
			params.put("endDate", c.getTimeInMillis());
		}
		// 调用dao
		List<Note> list = noteDao.hightSearch(params);
		NoteResult result = new NoteResult();
		result.setMsg("查询成功！");
		result.setData(list);
		result.setStatus(0);
		return result;
	}

	@Override
	public NoteResult loadTrash(String userId) {
		NoteResult result = new NoteResult();
		List<Note> notes = noteDao.loadTrash(userId);
		result.setStatus(0);
		result.setMsg("加载笔记本成功");
		result.setData(notes);
		return result;

	}

	@Override
	public NoteResult loadTrashContent(String noteId) {
		NoteResult result = new NoteResult();
		Note note = noteDao.findById(noteId);
		result.setStatus(0);
		result.setMsg("记载笔记内容成功");
		result.setData(note);
		return result;
	}

	@Override
	public NoteResult delete(String noteId) {
		NoteResult result = new NoteResult();
		noteDao.deleteNotes(new String[] { noteId });
		result.setStatus(0);
		result.setMsg("彻底删除笔记成功");
		return result;
	}

	@Override
	public NoteResult replay(String noteId) {
		NoteResult result = new NoteResult();
		noteDao.replay(noteId);
		result.setStatus(0);
		result.setMsg("恢复成功！");
		return result;
	}

	@Override
	public NoteResult likeNote(String userId, String title, String content) {
		NoteResult result = new NoteResult();
		LikeNote note = noteDao.findLikeNoteByTitle(title, userId);
		if (note != null) {
			result.setStatus(1);
			result.setMsg("您已经收藏过此笔记！");
		} else {
			String likeId = NoteUtil.createId();
			noteDao.noteLike(likeId, userId, title, content);
			result.setStatus(0);
			result.setMsg("收藏成功！");
		}
		return result;
	}

	@Override
	public NoteResult loadLike(String userId) {
		NoteResult result = new NoteResult();
		List<LikeNote> lites = noteDao.finLikeNoteByUserId(userId);
		result.setStatus(0);
		result.setMsg("载入收藏笔记成功！");
		result.setData(lites);
		return result;
	}

	@Override
	public NoteResult loadLikeNoteContent(String likeId) {
		NoteResult result = new NoteResult();
          LikeNote note=noteDao.findLikeNoteByLikeId(likeId);
		 result.setStatus(0);
		 result.setMsg("载入成功");
          result.setData(note);		
		return result;
	}

	@Override
	public NoteResult deleteLikeNote(String likeId) {
		NoteResult result = new NoteResult();
		noteDao.deleteLikeNote(likeId);
		result.setStatus(0);
		result.setMsg("删除搜藏笔记成功！");
		return result;
	}
}
