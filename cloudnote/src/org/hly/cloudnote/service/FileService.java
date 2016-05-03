package org.hly.cloudnote.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hly.cloudnote.util.NoteResult;

public interface FileService {

	public NoteResult loadCourse();

	public NoteResult loadFiles(String CourseId);

	public NoteResult fileUp(HttpServletRequest request) throws Exception;

	public void downFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public NoteResult search(String search);
}
