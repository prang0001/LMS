package com.brandeis.lmsapp.service.dto;

/*
 * this class represents the 
 */
public class SearchResults {

	private long id ;
	
	private String author;
	
	private String title;
	

	private String resourceDescription;
	
	private String resourceType;
	
	private Integer callNumber;
	
	
	public Integer getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(Integer callNumber) {
		this.callNumber = callNumber;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResourceDescription() {
		return resourceDescription;
	}

	public void setResourceDescription(String resourceDescription) {
		this.resourceDescription = resourceDescription;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	
	
	
}
