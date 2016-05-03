package org.hly.cloudnote.dao;

import java.util.List;

import org.hly.cloudnote.entity.Course;
import org.hly.cloudnote.entity.File;
import org.hly.cloudnote.entity.Share;

public interface FileDao {

	public List<Course> findAllCourse();

	public List<File> findFilesByCourseId(String courseId);

	public void save(String fileid, String userName, String courseId,
			String fileName);

	public File findByFileId(String fileId);

	public List<Share> search(String search);
}
