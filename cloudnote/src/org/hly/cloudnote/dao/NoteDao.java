package org.hly.cloudnote.dao;

import java.util.List;
import java.util.Map;

import org.hly.cloudnote.entity.LikeNote;
import org.hly.cloudnote.entity.Note;
import org.hly.cloudnote.entity.User;

public interface NoteDao {
	public int deleteNotes(String[] ids);
	
	public void dynamicUpdate(Note note);
	
	public List<Note> findByBookId(String bookId);
	
	public Note findById(String noteId);
	
	public Note findByAll(String noteTitle,String userId,String bookId);
	
	public void add(Note note);

	public void update(String noteTitle,String noteBody,String noteId,Long lastModify);
	
	public void recycle(String noteId);
	
	public void move(String bookId,String noteId);
	
	public List<Note> hightSearch(Map<String, Object> params);

	public List<Note> loadTrash(String userId);

	public User findUserNameByUserId(String cn_user_id);

	public void replay(String noteId);

	public void noteLike(String likeId, String userId, String title,
			String content);

	public LikeNote findLikeNoteByTitle(String title,String userId);

	public List<LikeNote> finLikeNoteByUserId(String userId);

	public LikeNote findLikeNoteByLikeId(String likeId);

	public void deleteLikeNote(String likeId);
}
