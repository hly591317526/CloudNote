package org.hly.cloudnote.controller.note;

import javax.annotation.Resource;

import org.hly.cloudnote.service.NoteService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/note")
@Controller
public class LikeNoteController {
	@Resource
	private NoteService noteService;

	@RequestMapping("/like.do")
	@ResponseBody
	public NoteResult execute1(String userId,String title,String content) {
		NoteResult result=noteService.likeNote(userId,title,content);
		return result;
	}
	
	@RequestMapping("/loadlike.do")
	@ResponseBody
	public NoteResult execute2(String userId){
		NoteResult result=noteService.loadLike(userId);
		return result;
	}
	
	@RequestMapping("/loadlikebody.do")
	@ResponseBody
	public NoteResult execute3(String likeId){
		NoteResult result=noteService.loadLikeNoteContent(likeId);
		return result;
	}
	
	@RequestMapping("/deletelikenote.do")
	@ResponseBody
	public NoteResult execute4(String likeId){
		NoteResult result=noteService.deleteLikeNote(likeId);
		return result;
	}
	
	
	
}
