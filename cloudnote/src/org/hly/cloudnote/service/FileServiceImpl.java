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
		result.setMsg("查询成功！");
		result.setData(courses);
		return result;
	}

	@Override
	public NoteResult loadFiles(String courseId) {
		NoteResult result = new NoteResult();
		List<File> files = fileDao.findFilesByCourseId(courseId);
		result.setStatus(0);
		result.setMsg("查询成功！");
		result.setData(files);
		return result;
	}

	@Override
	public NoteResult fileUp(HttpServletRequest request) throws Exception {
		NoteResult result = new NoteResult();
		request.setCharacterEncoding("utf-8");

		// 1.创建文件上传工厂类
		DiskFileItemFactory fac = new DiskFileItemFactory();
		// 2.创建文件上传核心类
		ServletFileUpload upload = new ServletFileUpload(fac);
		String courseId = "";
		String userName = "";
		String fileName = "";
		String fileid = NoteUtil.createId();
		// 判断：表单是否为文件上传表单
		if (upload.isMultipartContent(request)) {
			// 3.把请求转换为FileItem的集合
			List<FileItem> items = upload.parseRequest(request);
			// 遍历FileItem
			for (FileItem item : items) {
				// 获取元素名称
				if (item.isFormField()) {
					String fieldName = item.getFieldName();
					// 获取值
					String string = item.getString("utf-8");
					String[] infos = string.split("%");
					courseId = infos[0];
					userName = infos[1];
				} else {
					// 处理文件上传
					String name = item.getName();
					fileName = name;
					// 获取上传的目录路径
					String basePath = "C:/softWare/apache-tomcat-7.0.54/webapps/cloudnote/upload";
					java.io.File file = new java.io.File(basePath, name);
					item.write(file);
					item.delete();
				}
			}
		} else {
			System.out.println("不处理");
		}
      fileDao.save(fileid,userName,courseId,fileName);
		result.setStatus(0);
		result.setMsg("上传成功！");
		return result;
	}

	@Override
	public void downFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		// 获取文件的Id
		String fileId = request.getParameter("fileId");
        //找到文件名字
		File file=fileDao.findByFileId(fileId);
		String fileName=file.getCn_file_name();
		// 先获取上传路径目录
		String basePath = "C:/softWare/apache-tomcat-7.0.54/webapps/cloudnote/upload";
		// 获取一个文件流
		InputStream in = new FileInputStream(new 	java.io.File(basePath, fileName));
		// 设置下载的响应头
        //如果文件名是中文，则需要进行URL编码
		fileName=URLEncoder.encode(fileName, "utf-8");
		response.setHeader("content-disposition","attachement;fileName="+fileName);
		// 获取respond字节流
		OutputStream out = response.getOutputStream();
		byte[] b = new byte[10 * 1024];
		int len = -1;
		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}
		// 关闭
		out.close();
		in.close();
	}

	@Override
	public NoteResult search(String search) {
		NoteResult result=new NoteResult();
		//默认查询全部
		String title="%";
		if(search!=null&&!"".equals(search)){
			title="%"+search+"%";
		}
		List<Share> lists = fileDao.search(title);
		result.setStatus(0);
		result.setMsg("查询文件成功！");
		result.setData(lists);
		return result;
	}

}
