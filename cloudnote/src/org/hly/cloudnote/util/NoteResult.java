package org.hly.cloudnote.util;

import java.io.Serializable;
//{"status":0,"msg":"xxx","data":xxx}
public class NoteResult implements Serializable{
	private int status;//状态,0正确;其他错误
	private String msg;//消息
	private Object data;//传出去的数据
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
