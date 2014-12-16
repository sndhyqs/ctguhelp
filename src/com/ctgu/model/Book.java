package com.ctgu.model;
public class Book {
	private String author, bookTitle, publisher, publicationYear, reserved, available, url,callNumber;

	public Book(String author, String bookTitle, String publisher, String publicationYear, String reserved, String available, String url, String callNumber) {
		super();
		this.author = author;
		this.bookTitle = bookTitle;
		this.publisher = publisher;
		this.publicationYear = publicationYear;
		this.reserved = reserved;
		this.available = available;
		this.url = url;
		this.callNumber = callNumber;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	@Override
	public String toString() {
		return "Book [author=" + author + ", bookTitle=" + bookTitle + ", publisher=" + publisher + ", publicationYear=" + publicationYear + ", reserved=" + reserved + ", available=" + available
				+ ", url=" + url + ", callNumber=" + callNumber + "]";
	}

	

}
