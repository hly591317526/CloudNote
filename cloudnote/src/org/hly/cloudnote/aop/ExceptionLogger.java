package org.hly.cloudnote.aop;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
// ɨ�裬������ŵ�spring����
@Aspect
public class ExceptionLogger {
	@AfterThrowing(throwing = "e1", pointcut = "within(org.hly.cloudnote.controller..*)")
	public void log(Exception e1) {
		// ���쳣��Ϣд���ļ�
		try {
			FileWriter fw = new FileWriter(
					"C:/Users/������/Documents/GitHub/CloudNote/cloudnote/error.txt",
					true);
			PrintWriter pw = new PrintWriter(fw);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
			Date date=new Date();
			//����pw��ӡһ��ͷ��
			pw.println();
			pw.println("----------------------------------------------------");
			pw.println("*����ʱ�䣺"+sdf.format(date));
			pw.println("*����ԭ��"+e1.toString());
			pw.println("----------------------------------------------------");
			pw.println();
			e1.printStackTrace(pw);
			pw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("��¼�쳣��Ϣʧ�ܣ�");
		}

	}
}
