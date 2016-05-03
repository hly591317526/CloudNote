package org.hly.cloudnote.controller.file;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hly.cloudnote.service.FileService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/file")
public class FileController {

	@Resource
	private FileService fileService;

	@ResponseBody
	@RequestMapping("/loadcourse.do")
	public NoteResult execute() {
		NoteResult result = fileService.loadCourse();
		return result;
	}

	@RequestMapping("/loadfiles.do")
	@ResponseBody
	public NoteResult execute1(String courseId) {
		NoteResult result = fileService.loadFiles(courseId);
		return result;
	}

	@RequestMapping("/fileup.do")
	public void execute2(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		NoteResult result = fileService.fileUp(request);
		response.sendRedirect("/cloudnote/activity_detail.html");
	}

	@RequestMapping("/downfile.do")
	public void execute3(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		fileService.downFile(request, response);
	}

	@RequestMapping("/search.do")
	@ResponseBody
	public NoteResult execute4(String search){
            NoteResult result=fileService.search(search);
		return result;
	}

}
