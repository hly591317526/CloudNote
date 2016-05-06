package org.hly.cloudnote.entity;

import java.io.Serializable;
import java.util.List;
/**
 * 规则:类型对应;名称一致;实现序列化
 * 类型建议采用封装类型
 */
public class User implements Serializable{
	//追加关联的Book属性
	private List<Book> books;
	private String cn_user_id;
	private String cn_user_name;
	private String cn_user_password;
	private String cn_user_token;
	private String cn_user_nick;
	private String cn_user_friend;

	public String getCn_user_friend() {
		return cn_user_friend;
	}
	public void setCn_user_friend(String cn_user_friend) {
		this.cn_user_friend = cn_user_friend;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public String getCn_user_id() {
		return cn_user_id;
	}
	public void setCn_user_id(String cnUserId) {
		cn_user_id = cnUserId;
	}
	public String getCn_user_name() {
		return cn_user_name;
	}
	public void setCn_user_name(String cnUserName) {
		cn_user_name = cnUserName;
	}
	public String getCn_user_password() {
		return cn_user_password;
	}
	public void setCn_user_password(String cnUserPassword) {
		cn_user_password = cnUserPassword;
	}
	public String getCn_user_token() {
		return cn_user_token;
	}
	public void setCn_user_token(String cnUserToken) {
		cn_user_token = cnUserToken;
	}
	public String getCn_user_nick() {
		return cn_user_nick;
	}
	public void setCn_user_nick(String cnUserNick) {
		cn_user_nick = cnUserNick;
	}
	
}
