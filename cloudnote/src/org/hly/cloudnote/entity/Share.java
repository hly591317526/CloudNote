package org.hly.cloudnote.entity;

import java.io.Serializable;

public class Share implements Serializable {
	private String cn_share_id;
	private String cn_share_title;
	private String cn_share_body;
	private String cn_note_id;
	private Integer cn_like_sum;
	private String cn_user_name;
	public String getCn_user_name() {
		return cn_user_name;
	}

	public void setCn_user_name(String cn_user_name) {
		this.cn_user_name = cn_user_name;
	}

	public Integer getCn_like_sum() {
		return cn_like_sum;
	}

	public void setCn_like_sum(Integer cn_like_sum) {
		this.cn_like_sum = cn_like_sum;
	}

	public String getCn_user_like() {
		return cn_user_like;
	}

	public void setCn_user_like(String cn_user_like) {
		this.cn_user_like = cn_user_like;
	}

	private String cn_user_like;

	@Override
	public String toString() {
		return "Share [cn_share_id=" + cn_share_id + ", cn_share_title="
				+ cn_share_title + ", cn_share_body=" + cn_share_body
				+ ", cn_note_id=" + cn_note_id + "]";
	}

	public String getCn_share_id() {
		return cn_share_id;
	}

	public void setCn_share_id(String cn_share_id) {
		this.cn_share_id = cn_share_id;
	}

	public String getCn_share_title() {
		return cn_share_title;
	}

	public void setCn_share_title(String cn_share_title) {
		this.cn_share_title = cn_share_title;
	}

	public String getCn_share_body() {
		return cn_share_body;
	}

	public void setCn_share_body(String cn_share_body) {
		this.cn_share_body = cn_share_body;
	}

	public String getCn_note_id() {
		return cn_note_id;
	}

	public void setCn_note_id(String cn_note_id) {
		this.cn_note_id = cn_note_id;
	}

}
