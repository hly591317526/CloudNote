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
			// û�з�����ıʼ�
			share.setCn_note_id(note.getCn_note_id());
			share.setCn_share_id(NoteUtil.createId());
			share.setCn_share_body(note.getCn_note_body());
			share.setCn_share_title(note.getCn_note_title() + "----����"
					+ userName + "�ķ���");
			share.setCn_user_name(userName);
			shareDao.add(share);
			result.setStatus(0);
			result.setMsg("����ɹ���");
			return result;
		} else {
			// ��������ıʼ�
			share.setCn_share_body(note.getCn_note_body());
			share.setCn_share_title(note.getCn_note_title() + "----����"
					+ userName + "�ķ���");
			shareDao.update(note.getCn_note_title() + "----����" + userName
					+ "�ķ���", note.getCn_note_body(), noteId);
			result.setStatus(0);
			result.setMsg("�޸ķ���ɹ���");
			return result;
		}

	}

	@Override
	public NoteResult load(String search) {
		NoteResult result = new NoteResult();
		String title = "%";
		// Ĭ�ϲ�ѯ����
		if (search != null && !"".equals(search)) {
			title = "%" + search + "%";
		}
		List<Share> lists = shareDao.load(title);
		result.setStatus(0);
		result.setMsg("��ѯ����ʼǳɹ���");
		result.setData(lists);
		return result;
	}

	@Override
	public NoteResult loadShare(String shareId) {
		Share share = shareDao.findByShareId(shareId);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("��ѯ����ʼ���Ϣ�ɹ���");
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
					result.setMsg("���Դ˱ʼ��Ѿ��������");
					return result;
				}
			}
			allUser += userId;
			allUser += "%";
			sum+=1;
			shareDao.setUserLike(shareId, allUser,sum);
			result.setStatus(0);
			result.setMsg("���޳ɹ���");
			result.setData(sum);
			return result;
		}
		allUser = userId + "%";
		sum+=1;
		shareDao.setUserLike(shareId, allUser,sum);
		result.setStatus(0);
		result.setMsg("���޳ɹ���");
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
			result.setMsg("�㻹δ�Դ˱ʼǵ���ޣ����Բ���ȡ���ޣ�");
			String[] users = allUser.split("%");
      for (String user : users) {
    		if (user.equals(userId)) {
    			sum-=1;
    			result.setMsg("ȡ�����޳ɹ���");
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
			result.setMsg("�㻹δ�Դ˱ʼǵ���ޣ����Բ���ȡ���ޣ�");
	         result.setData(sum);
			return result;
		}
	}
}
