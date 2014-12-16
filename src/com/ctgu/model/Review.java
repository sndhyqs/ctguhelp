package com.ctgu.model;

public class Review {
	private String id;
	private String discuss;
	private String user;
	private String time;
	private String content;
	private String user_review;


	public Review(String discuss, String content, String time, String user_review, String user, String id) {
		super();
		this.discuss = discuss;
		this.content = content;
		this.time = time;
		this.user_review = user_review;
		this.user = user;
		this.id = id;
	}

	public String getDiscuss() {
		return discuss;
	}

	public void setDiscuss(String discuss) {
		this.discuss = discuss;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUser_review() {
		return user_review;
	}

	public void setUser_review(String user_review) {
		this.user_review = user_review;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Review [discuss=" + discuss + ", content=" + content + ", time=" + time + ", user_review=" + user_review + ", user=" + user + ", id=" + id + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
