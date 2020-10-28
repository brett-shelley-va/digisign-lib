package com.digisign.libservice;

import java.time.LocalDateTime;
import java.util.List;

public class Book {

	private String id;
	private String title;
	private List<String> authors;
	private String publisher;
	private String year;
	private String description;
	private LocalDateTime lastModifiedDateTime;
	
	public Book() {
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public List<String> getAuthors() {
		return authors;
	}


	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDateTime getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}


	public void setLastModifiedDate(LocalDateTime lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}

}
