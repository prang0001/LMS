package com.brandeis.lmsapp.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchCriteria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "SearchCriteria [searchType=" + searchType + ", id=" + id + ", title=" + title + ", author=" + author
				+ "]";
	}

	private String searchType; 
	
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	@JsonProperty
	private Long id ;
	
	private String title;
	
	private Long callNumber;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String description;
	
	private String subject;
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(Long callNumber) {
		this.callNumber = callNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@JsonProperty(value="author")
	private String author;

	public SearchCriteria(String searchType, Long id, String title, String author) {
		super();
		this.searchType = searchType;
		this.id = id;
		this.title = title;
		this.author = author;
	}
	
	public SearchCriteria() {
		
	}
}
