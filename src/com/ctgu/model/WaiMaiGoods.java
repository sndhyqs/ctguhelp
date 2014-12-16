package com.ctgu.model;

/**
 * 外卖商品的原型包含 单价、图片地址、交易量、id、类型id、商品名称;
 * 
 * @author Administrator
 * 
 */
public class WaiMaiGoods {
	private String price;
	private String picUrl;
	private String tradeNumber;
	private int id;
	private int typeId;
	private String name;

	public WaiMaiGoods(String price, String picUrl, String tradeNumber, int id, int typeId, String name) {
		super();
		this.price = price;
		this.picUrl = picUrl;
		this.tradeNumber = tradeNumber;
		this.id = id;
		this.typeId = typeId;
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getTradeNumber() {
		return tradeNumber;
	}

	public void setTradeNumber(String tradeNumber) {
		this.tradeNumber = tradeNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "WaiMaiGoods [price=" + price + ", picUrl=" + picUrl + ", tradeNumber=" + tradeNumber + ", id=" + id + ", typeId=" + typeId + ", name=" + name + "]";
	}

}
