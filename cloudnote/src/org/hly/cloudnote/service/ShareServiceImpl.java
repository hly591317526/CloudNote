package org.hly.cloudnote.service;

import java.util.List;

import javax.annotation.Resource;

import org.hly.cloudnote.dao.NoteDao;
import org.hly.cloudnote.dao.ShareDao;
import org.hly.cloudnote.entity.Note;
import org.hly.cloudnote.entity.Share;
import org.hly.cloudnote.util.NoteResult;
import org.hly.cloudnote.util.NoteUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("shareService")
@Transactional
public class ShareServiceImpl implements ShareService {
	@Resource
	private ShareDao shareDao;
	@Resource
	private NoteDao notedao;

	@Override
	public NoteResult add(String noteId) {
		NoteResult result = new NoteResult();
		Note note = notedao.findById(noteId);
		Share s = shareDao.findByNoteId(noteId);
		Share share = new Share();
		if (s == null) {
			// 没有分享过的笔记
			share.setCn_note_id(note.getCn_note_id());
			share.setCn_share_id(NoteUtil.createId());
			share.setCn_share_body(note.getCn_note_body());
			share.setCn_share_title(note.getCn_note_title());
			shareDao.add(share);
			result.setStatus(0);
			result.setMsg("分享成功！");
			return result;
		} else {
			// 被分享过的笔记
			share.setCn_share_body(note.getCn_note_body());
			share.setCn_share_title(note.getCn_note_title());
			shareDao.update(note.getCn_note_title(), note.getCn_note_body(),
					noteId);
			result.setStatus(0);
			result.setMsg("修改分享成功！");
			return result;
		}

	}

	@Override
	public NoteResult load(String search) {
		NoteResult result = new NoteResult();
		String title="%";
		//默认查询所有
		if(search!=null&&!"".equals(search)){
			title="%"+search+"%";
		}
		List<Share> lists = shareDao.load(title);
		result.setStatus(0);
		result.setMsg("查询分享笔记成功！");
		result.setData(lists);
		return result;
	}

	@Override
	public NoteResult loadShare(String shareId) {
       Share share=shareDao.findByShareId(shareId);
       NoteResult result=new NoteResult();
       result.setStatus(0);
       result.setMsg("查询分享笔记信息成功！");
       result.setData(share);
		return result;
	}
}
