package com.brandeis.lmsapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandeis.lmsapp.domain.LibraryResource;
import com.brandeis.lmsapp.repository.SearchRepository;
import com.brandeis.lmsapp.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	SearchRepository repo;
	
	@Override
	public Optional<LibraryResource> searchByID(Long id) {
		return repo.findById(id);
	}

	@Override
	public Optional<LibraryResource> searchByResourceTitle(String title) {
		// TODO Auto-generated method stub
		return repo.searchByResourceTitle(title);
	}

	//searchByCallNumber
	@Override
	public Optional<LibraryResource> searchByCallNumber(Long id) {
		// TODO Auto-generated method stub
		return repo.searchByCallNumber(id);
	}

	@Override
	public Optional<List<LibraryResource>> searchByAuthor(String author) {
		// TODO Auto-generated method stub
		return repo.searchByAuthor(author);
	}
	
	@Override
	public Optional<List<LibraryResource>> searchByDescription(String description) {
		// TODO Auto-generated method stub
		return repo.searchByDescription(description);
	}
	
	@Override
	public Optional<List<LibraryResource>> searchBySubject(String subject) {
		// TODO Auto-generated method stub
		return repo.searchBySubject(subject);
	}
	
	
	

}
