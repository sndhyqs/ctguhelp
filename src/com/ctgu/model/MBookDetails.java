package com.ctgu.model;

public class MBookDetails {
	private String place;
	private String status;
	private String indexNumber;

	public MBookDetails(String place, String status, String indexNumber) {
		super();
		this.place = place;
		this.status = status;
		this.indexNumber = indexNumber;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(String indexNumber) {
		this.indexNumber = indexNumber;
	}

	@Override
	public String toString() {
		return "MBookDetails [place=" + place + ", status=" + status + ", indexNumber=" + indexNumber + "]";
	}

}
