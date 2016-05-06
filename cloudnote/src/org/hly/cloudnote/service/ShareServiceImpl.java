package org.hly.cloudnote.service;

import java.util.List;

import javax.annotation.Resource;

import org.hly.cloudnote.dao.NoteDao;
import org.hly.cloudnote.dao.ShareDao;
import org.hly.cloudnote.entity.Note;
import org.hly.cloudnote.entity.Share;
import org.hly.cloudnote.entity.User;
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
		User user = notedao.findUserNameByUserId(note.getCn_user_id());
		String userName = user.getCn_user_name();
		if (s == null) {
			// 没有分享过的笔记
			share.setCn_note_id(note.getCn_note_id());
			share.setCn_share_id(NoteUtil.createId());
			share.setCn_share_body(note.getCn_note_body());
			share.setCn_share_title(note.getCn_note_title() + "----来自"
					+ userName + "的分享。");
			share.setCn_user_name(userName);
			shareDao.add(share);
			result.setStatus(0);
			result.setMsg("分享成功！");
			return result;
		} else {
			// 被分享过的笔记
			share.setCn_share_body(note.getCn_note_body());
			share.setCn_share_title(note.getCn_note_title() + "----来自"
					+ userName + "的分享。");
			shareDao.update(note.getCn_note_title() + "----来自" + userName
					+ "的分享。", note.getCn_note_body(), noteId);
			result.setStatus(0);
			result.setMsg("修改分享成功！");
			return result;
		}

	}

	@Override
	public NoteResult load(String search) {
		NoteResult result = new NoteResult();
		String title = "%";
		// 默认查询所有
		if (search != null && !"".equals(search)) {
			title = "%" + search + "%";
		}
		List<Share> lists = shareDao.load(title);
		result.setStatus(0);
		result.setMsg("查询分享笔记成功！");
		result.setData(lists);
		return result;
	}

	@Override
	public NoteResult loadShare(String shareId) {
		Share share = shareDao.findByShareId(shareId);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("查询分享笔记信息成功！");
		result.setData(share);
		return result;
	}

	@Override
	public NoteResult up(String userId, String shareId) {
		NoteResult result = new NoteResult();
		Share share = shareDao.findByShareId(shareId);
		Integer sum=share.getCn_like_sum();
		String allUser = share.getCn_user_like();
		if (allUser != null) {
			String[] users = allUser.split("%");
			for (String user : users) {
				if (user.equals(userId)) {
					result.setStatus(1);
					result.setMsg("您对此笔记已经点过赞了");
					return result;
				}
			}
			allUser += userId;
			allUser += "%";
			sum+=1;
			shareDao.setUserLike(shareId, allUser,sum);
			result.setStatus(0);
			result.setMsg("点赞成功！");
			result.setData(sum);
			return result;
		}
		allUser = userId + "%";
		sum+=1;
		shareDao.setUserLike(shareId, allUser,sum);
		result.setStatus(0);
		result.setMsg("点赞成功！");
		result.setData(sum);
		return result;
	}

	@Override
	public NoteResult down(String userId, String shareId) {
		NoteResult result = new NoteResult();
		Share share = shareDao.findByShareId(shareId);
		Integer sum=share.getCn_like_sum();
		String allUser = share.getCn_user_like();
		String newAllUser="";
		if(allUser!=null){
			result.setStatus(0);
			result.setMsg("你还未对此笔记点过赞，所以不能取消赞！");
			String[] users = allUser.split("%");
      for (String user : users) {
    		if (user.equals(userId)) {
    			sum-=1;
    			result.setMsg("取消点赞成功！");
    			continue;
    		}
    	       newAllUser+=user;
    	       newAllUser+="%";
         	}			
		shareDao.setUserLike(shareId, newAllUser,sum);
	         result.setData(sum);
			return result;
		}else{
			result.setStatus(1);
			result.setMsg("你还未对此笔记点过赞，所以不能取消赞！");
	         result.setData(sum);
			return result;
		}
	}
}
