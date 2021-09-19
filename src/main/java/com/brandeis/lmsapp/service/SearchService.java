package com.brandeis.lmsapp.service;

import java.util.List;
import java.util.Optional;

import com.brandeis.lmsapp.domain.LibraryResource;

public interface SearchService {
	
	
	public Optional<LibraryResource> searchByID(Long id);
	
	
	public Optional<LibraryResource> searchByResourceTitle(String title);
	
	
	public Optional<LibraryResource> searchByCallNumber(Long id);
	
	public Optional<List<LibraryResource>> searchByAuthor(String author);


	Optional<List<LibraryResource>> searchByDescription(String description);


	Optional<List<LibraryResource>> searchBySubject(String subject);

	

}
