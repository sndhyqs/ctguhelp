package com.ctgu.model;

public class MyMessages {
	private String id;
	private String discuss;
	private String user;
	private String content;
	private String content_id;
	private String time;

	public MyMessages(String id, String discuss, String user, String content, String content_id, String time) {
		super();
		this.id = id;
		this.discuss = discuss;
		this.user = user;
		this.content = content;
		this.content_id = content_id;
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDiscuss() {
		return discuss;
	}

	public void setDiscuss(String discuss) {
		this.discuss = discuss;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent_id() {
		return content_id;
	}

	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "MyMessages [id=" + id + ", discuss=" + discuss + ", user=" + user + ", content=" + content + ", content_id=" + content_id + ", time=" + time + "]";
	}

}
