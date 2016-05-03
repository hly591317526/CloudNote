package org.hly.cloudnote.entity;

public class File {

	private String cn_file_id;
	private String cn_user_name;
	private String cn_course_id;
	private String cn_file_name;
	public String getCn_file_name() {
		return cn_file_name;
	}
	public void setCn_file_name(String cn_file_name) {
		this.cn_file_name = cn_file_name;
	}
	public String getCn_file_id() {
		return cn_file_id;
	}
	public void setCn_file_id(String cn_file_id) {
		this.cn_file_id = cn_file_id;
	}
	public String getCn_user_name() {
		return cn_user_name;
	}
	public void setCn_user_name(String cn_user_id) {
		this.cn_user_name = cn_user_id;
	}
	public String getCn_course_id() {
		return cn_course_id;
	}
	public void setCn_course_id(String cn_course_id) {
		this.cn_course_id = cn_course_id;
	}
	
	
}
