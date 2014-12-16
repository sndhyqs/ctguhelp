package com.ctgu.model;

import com.ctgu.base.BaseModel;


public class Notice extends BaseModel {
	
	
	
	private String id;
	private String message;
	
	public Notice () {}
	
	public String getId () {
		return this.id;
	}
	
	public void setId (String id) {
		this.id = id;
	}
	
	public String getMessage () {
		return this.message;
	}
	
	public void setMessage (String message) {
		this.message = message;
	}
}