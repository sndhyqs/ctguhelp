/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.ctgu.model;


public class Lost_Find_Message implements Comparable {
    private int id;
    private String keyword;
    private String time;
    private String title;
    private Lost_Find_Message message;
    
    public Lost_Find_Message() {
		super();
	}

	public Lost_Find_Message(int id, String keyword, String time, String title) {
		super();
		this.id = id;
		this.keyword = keyword;
		this.time = time;
		this.title = title;
		
	}

	public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
    	this.keyword = keyword;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
    	this. time = time;
    }
    
    @Override
	public String toString() {
		return "Lost_Find_Message [id=" + id + ", keyword=" + keyword
				+ ", time=" + time + ", title=" + title + ", message="
				+ message + "]";
	}

	@Override
    public int compareTo(Object another) {
    	message = (Lost_Find_Message)another;
        if(this.id > message.id) {
            return -1;
        }
        return 1;
    }

	

	
}
