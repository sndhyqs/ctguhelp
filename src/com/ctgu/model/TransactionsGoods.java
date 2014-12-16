package com.ctgu.model;

/**
 * 二手交易模型包含 id、名称、描述、时间、手机、图片
 * 
 * @author Administrator
 * 
 */
public class TransactionsGoods {

	private int id;
	private String name;
	private String describe;
	private String time;
	private String picUrl;
	private String tel;
	private String read;
	private String discuss;
	
	public TransactionsGoods(int id, String name, String describe, String time, String picUrl, String tel, String read, String discuss) {
		super();
		this.id = id;
		this.name = name;
		this.describe = describe;
		this.time = time;
		this.picUrl = picUrl;
		this.tel = tel;
		this.read = read;
		this.discuss = discuss;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getDiscuss() {
		return discuss;
	}
	public void setDiscuss(String discuss) {
		this.discuss = discuss;
	}
	@Override
	public String toString() {
		return "TransactionsGoods [id=" + id + ", name=" + name + ", describe=" + describe + ", time=" + time + ", picUrl=" + picUrl + ", tel=" + tel + ", read=" + read + ", discuss=" + discuss + "]";
	}
	
}
