package com.brandeis.lmsapp.service.dto;

import java.util.List;

public class SearchResponseContainer {

private String statusCode;
	
	private String statusMessage;
	
	private List<SearchResults> searchResults;
	
	public List<SearchResults> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<SearchResults> searchResults) {
		this.searchResults = searchResults;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
