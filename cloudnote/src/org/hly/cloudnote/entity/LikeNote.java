package org.hly.cloudnote.entity;

public class LikeNote {

	private String cn_like_id;
	private String cn_note_title;
	private String cn_note_body;
	private String cn_user_id;

	public String getCn_like_id() {
		return cn_like_id;
	}

	public void setCn_like_id(String cn_like_id) {
		this.cn_like_id = cn_like_id;
	}

	public String getCn_note_title() {
		return cn_note_title;
	}

	public void setCn_note_title(String cn_note_title) {
		this.cn_note_title = cn_note_title;
	}

	public String getCn_note_body() {
		return cn_note_body;
	}

	public void setCn_note_body(String cn_note_body) {
		this.cn_note_body = cn_note_body;
	}

	public String getCn_user_id() {
		return cn_user_id;
	}

	public void setCn_user_id(String cn_user_id) {
		this.cn_user_id = cn_user_id;
	}

}
