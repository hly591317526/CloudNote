package org.hly.cloudnote.util;

import java.io.Serializable;
//{"status":0,"msg":"xxx","data":xxx}
public class NoteResult implements Serializable{
	private int status;//״̬,0��ȷ;��������
	private String msg;//��Ϣ
	private Object data;//����ȥ������
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
