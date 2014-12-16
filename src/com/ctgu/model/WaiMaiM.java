package com.ctgu.model;

/**
 * 外卖商家属性
 * 包含 商家名称、评分等级 、商家id、商家图标、交易量；
 */
public class WaiMaiM {
	private String name;
	private String dengji;
	private String id;
	private String tubiao;
	private String tradeNumber;
	
	public WaiMaiM(String name, String dengji, String id, String tubiao, String tradeNumber) {
		super();
		this.name = name;
		this.dengji = dengji;
		this.id = id;
		this.tubiao = tubiao;
		this.tradeNumber = tradeNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDengji() {
		return dengji;
	}

	public void setDengji(String dengji) {
		this.dengji = dengji;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTubiao() {
		return tubiao;
	}

	public void setTubiao(String tubiao) {
		this.tubiao = tubiao;
	}

	public String getTradeNumber() {
		return tradeNumber;
	}

	public void setTradeNumber(String tradeNumber) {
		this.tradeNumber = tradeNumber;
	}

	@Override
	public String toString() {
		return "WaiMaiM [name=" + name + ", dengji=" + dengji + ", id=" + id + ", tubiao=" + tubiao + ", tradeNumber=" + tradeNumber + "]";
	}
	

}
