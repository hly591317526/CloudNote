package org.hly.cloudnote.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hly.cloudnote.dao.FileDao;
import org.hly.cloudnote.dao.UserDao;
import org.hly.cloudnote.entity.Course;
import org.hly.cloudnote.entity.File;
import org.hly.cloudnote.entity.Share;
import org.hly.cloudnote.util.NoteResult;
import org.hly.cloudnote.util.NoteUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("fileService")
@Transactional
public class FileServiceImpl implements FileService {

	@Resource
	private FileDao fileDao;

	@Override
	public NoteResult loadCourse() {
		NoteResult result = new NoteResult();
		List<Course> courses = fileDao.findAllCourse();
		result.setStatus(0);
		result.setMsg("��ѯ�ɹ���");
		result.setData(courses);
		return result;
	}

	@Override
	public NoteResult loadFiles(String courseId) {
		NoteResult result = new NoteResult();
		List<File> files = fileDao.findFilesByCourseId(courseId);
		result.setStatus(0);
		result.setMsg("��ѯ�ɹ���");
		result.setData(files);
		return result;
	}

	@Override
	public NoteResult fileUp(HttpServletRequest request) throws Exception {
		NoteResult result = new NoteResult();
		request.setCharacterEncoding("utf-8");

		// 1.�����ļ��ϴ�������
		DiskFileItemFactory fac = new DiskFileItemFactory();
		// 2.�����ļ��ϴ�������
		ServletFileUpload upload = new ServletFileUpload(fac);
		String courseId = "";
		String userName = "";
		String fileName = "";
		String fileid = NoteUtil.createId();
		// �жϣ����Ƿ�Ϊ�ļ��ϴ���
		if (upload.isMultipartContent(request)) {
			// 3.������ת��ΪFileItem�ļ���
			List<FileItem> items = upload.parseRequest(request);
			// ����FileItem
			for (FileItem item : items) {
				// ��ȡԪ������
				if (item.isFormField()) {
					String fieldName = item.getFieldName();
					// ��ȡֵ
					String string = item.getString("utf-8");
					String[] infos = string.split("%");
					courseId = infos[0];
					userName = infos[1];
				} else {
					// �����ļ��ϴ�
					String name = item.getName();
					fileName = name;
					// ��ȡ�ϴ���Ŀ¼·��
					String basePath = "C:/softWare/apache-tomcat-7.0.54/webapps/cloudnote/upload";
					java.io.File file = new java.io.File(basePath, name);
					item.write(file);
					item.delete();
				}
			}
		} else {
			System.out.println("������");
		}
      fileDao.save(fileid,userName,courseId,fileName);
		result.setStatus(0);
		result.setMsg("�ϴ��ɹ���");
		return result;
	}

	@Override
	public void downFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		// ��ȡ�ļ���Id
		String fileId = request.getParameter("fileId");
        //�ҵ��ļ�����
		File file=fileDao.findByFileId(fileId);
		String fileName=file.getCn_file_name();
		// �Ȼ�ȡ�ϴ�·��Ŀ¼
		String basePath = "C:/softWare/apache-tomcat-7.0.54/webapps/cloudnote/upload";
		// ��ȡһ���ļ���
		InputStream in = new FileInputStream(new 	java.io.File(basePath, fileName));
		// �������ص���Ӧͷ
        //����ļ��������ģ�����Ҫ����URL����
		fileName=URLEncoder.encode(fileName, "utf-8");
		response.setHeader("content-disposition","attachement;fileName="+fileName);
		// ��ȡrespond�ֽ���
		OutputStream out = response.getOutputStream();
		byte[] b = new byte[10 * 1024];
		int len = -1;
		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}
		// �ر�
		out.close();
		in.close();
	}

	@Override
	public NoteResult search(String search) {
		NoteResult result=new NoteResult();
		//Ĭ�ϲ�ѯȫ��
		String title="%";
		if(search!=null&&!"".equals(search)){
			title="%"+search+"%";
		}
		List<Share> lists = fileDao.search(title);
		result.setStatus(0);
		result.setMsg("��ѯ�ļ��ɹ���");
		result.setData(lists);
		return result;
	}

}
