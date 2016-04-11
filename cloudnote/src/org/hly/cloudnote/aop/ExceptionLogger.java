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
// 扫描，将组件放到spring容器
@Aspect
public class ExceptionLogger {

	@AfterThrowing(throwing = "e1", pointcut = "within(org.hly.cloudnote.controller..*)")
	public void log(Exception e1) {
		// 将异常信息写入文件
		try {
			FileWriter fw = new FileWriter(
					"C:/Users/冬冬侠/Documents/workSpaces/MyeclipseWorkSpaces/cloudnote/error.txt",
					true);
			PrintWriter pw = new PrintWriter(fw);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
			Date date=new Date();
			//利用pw打印一个头部
			pw.println();
			pw.println("----------------------------------------------------");
			pw.println("*错误时间："+sdf.format(date));
			pw.println("*错误原因："+e1.toString());
			pw.println("----------------------------------------------------");
			pw.println();
			e1.printStackTrace(pw);
			pw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("记录异常信息失败！");
		}

	}
}
