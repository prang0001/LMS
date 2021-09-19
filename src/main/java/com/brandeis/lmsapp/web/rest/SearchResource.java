package com.brandeis.lmsapp.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandeis.lmsapp.domain.LibraryResource;
import com.brandeis.lmsapp.domain.SearchCriteria;
import com.brandeis.lmsapp.service.SearchService;
import com.brandeis.lmsapp.service.dto.SearchResponseContainer;
import com.brandeis.lmsapp.service.dto.SearchResults;
import com.codahale.metrics.annotation.Timed;

@RestController
@RequestMapping("/api")
public class SearchResource {
	
    private final Logger log = LoggerFactory.getLogger(LibraryResourceResource.class);

    //@Autowired
    private SearchService service;

   
    public SearchResource(SearchService service) {
    	this.service = service;
    }
	
	@PostMapping("/search")
    @Timed
    public ResponseEntity<List<SearchResults>> getLibraryResource(@RequestBody SearchCriteria searchCriteria) {
		long id = 1;
		
		List<SearchResults> results = new ArrayList<SearchResults>();
		log.debug("Search criteria type" + searchCriteria.getSearchType());
		log.debug("Search criteria author" + searchCriteria.getAuthor());
		log.debug("Search criteria resource id" +searchCriteria.getId() );
		log.debug("Search criteria title" +searchCriteria.getTitle() );
		log.debug("Search criteria call number" +searchCriteria.getCallNumber() );
		log.debug("Search criteria description" + searchCriteria.getDescription());
		log.debug("Search criteria subject"+ searchCriteria.getSubject());
		
		try {
			if(searchCriteria.getSearchType().equalsIgnoreCase("Resource ID")) {
				log.debug("REST request to get LibraryResource by id : {}", searchCriteria.getId());
		        Optional<LibraryResource> libraryResource = service.searchByID(searchCriteria.getId());
		        List<SearchResults> searchResultList = new ArrayList<SearchResults>();
		        SearchResponseContainer resContainer = new SearchResponseContainer();
		        if(libraryResource!=null && libraryResource.isPresent()) {
		        	SearchResults res = new SearchResults();
		        	res.setId(libraryResource.get().getId());
		        	res.setTitle(libraryResource.get().getResourceTitle());
		        	res.setResourceDescription(libraryResource.get().getResourceDescription());
		        	res.setAuthor(libraryResource.get().getAuthor().getAuthorName());
		        	//res.setResourceType(libraryResource.get().getResourceType());
		        	//res.setCallNumber(libraryResource.get().getCallNumber());
		        	results.add(res);
		        	resContainer.setStatusCode("SUCCESS");
					resContainer.setStatusMessage("Results obtained are below");
					searchResultList.add(res);
					resContainer.setSearchResults(searchResultList);
					return new ResponseEntity(resContainer, HttpStatus.OK);

		        }
		        
			}
			
			else if(searchCriteria.getSearchType().equalsIgnoreCase("title")) {
				log.debug("REST request to get LibraryResource by title: {}", searchCriteria.getTitle());
		        Optional<LibraryResource> libraryResource = service.searchByResourceTitle(searchCriteria.getTitle());
		        SearchResponseContainer resContainer = new SearchResponseContainer();
		        List<SearchResults> searchResultList = new ArrayList<SearchResults>();
		        if(libraryResource!=null && libraryResource.isPresent()) {
		        	SearchResults res = new SearchResults();
		        	res.setId(libraryResource.get().getId());
		        	res.setTitle(libraryResource.get().getResourceTitle());
		        	res.setResourceDescription(libraryResource.get().getResourceDescription());
		        	res.setAuthor(libraryResource.get().getAuthor().getAuthorName());
		        	//res.setResourceType(libraryResource.get().getResourceType());
		        	//res.setCallNumber(libraryResource.get().getCallNumber());
		        	resContainer.setStatusCode("SUCCESS");
					resContainer.setStatusMessage("Results obtained are below");
					searchResultList.add(res);
					resContainer.setSearchResults(searchResultList);
					return new ResponseEntity(resContainer, HttpStatus.OK);

		        }
			}
			
			else if(searchCriteria.getSearchType().equalsIgnoreCase("call Number")) {
				log.debug("REST request to get LibraryResource by call number: {}", searchCriteria.getCallNumber());
		        Optional<LibraryResource> libraryResource = service.
		        		searchByCallNumber(searchCriteria.getCallNumber());
				SearchResponseContainer resContainer = new SearchResponseContainer();
		        List<SearchResults> searchResultList = new ArrayList<SearchResults>();

		        if(libraryResource!=null && libraryResource.isPresent()) {
		        	SearchResults res = new SearchResults();
		        	res.setId(libraryResource.get().getId());
		        	res.setTitle(libraryResource.get().getResourceTitle());
		        	res.setResourceDescription(libraryResource.get().getResourceDescription());
		        	res.setAuthor(libraryResource.get().getAuthor().getAuthorName());
		        	//res.setResourceType(libraryResource.get().getResourceType());
		        	//res.setCallNumber(libraryResource.get().getCallNumber());
		        	resContainer.setStatusCode("SUCCESS");
					resContainer.setStatusMessage("Results obtained are below");
					searchResultList.add(res);
					resContainer.setSearchResults(searchResultList);
					return new ResponseEntity(resContainer, HttpStatus.OK);

		        }
			}
			
			else if(searchCriteria.getSearchType().equalsIgnoreCase("author")) {
				SearchResponseContainer resContainer = new SearchResponseContainer();
				
				log.debug("REST request to get LibraryResource by author : {}", searchCriteria.getAuthor());
		        Optional<List<LibraryResource>> libraryResources = service.
		        		searchByAuthor(searchCriteria.getAuthor());
		        List<SearchResults> searchResultList = new ArrayList<SearchResults>();
		        if(libraryResources!=null && libraryResources.isPresent()) {
		        	for(LibraryResource resource : libraryResources.get()) {
		        		SearchResults results1 = new SearchResults();
		        		results1.setId(resource.getId());
			        	results1.setTitle(resource.getResourceTitle());
			        	results1.setResourceDescription(resource.getResourceDescription());
			        	results1.setAuthor(resource.getAuthor().getAuthorName());
			        	//results1.setResourceType(resource.getResourceType());
			        	//results1.setCallNumber(resource.getCallNumber());
			        	searchResultList.add(results1);
		        	}
		        	resContainer.setStatusCode("SUCCESS");
					resContainer.setStatusMessage("Results obtained are below");
					resContainer.setSearchResults(searchResultList);
		        }
		        else {
		        	resContainer.setStatusCode("SUCCESS_NO_RECORDS");
					resContainer.setStatusMessage("No Results obtained");
		        }
		        
		        return new ResponseEntity(resContainer, HttpStatus.OK);
			}
			else if(searchCriteria.getSearchType().equalsIgnoreCase("description")) {
				SearchResponseContainer resContainer = new SearchResponseContainer();
				log.debug("REST request to get LibraryResource by description : {}", searchCriteria.getDescription());
				Optional<List<LibraryResource>> libraryResources = service.
		        		searchByDescription(searchCriteria.getDescription());
				List<SearchResults> searchResultList = new ArrayList<SearchResults>();
		        if(libraryResources!=null && libraryResources.isPresent()) {
		        	log.debug("resource present " + libraryResources.isPresent())  ;
		        	for(LibraryResource resource : libraryResources.get()) {
		        		log.debug("resource  " + resource.getResourceTitle())  ;
		        		SearchResults results1 = new SearchResults();
		        		results1.setId(resource.getId());
			        	results1.setTitle(resource.getResourceTitle());
			        	results1.setResourceDescription(resource.getResourceDescription());
			        	results1.setAuthor(resource.getAuthor().getAuthorName());
			        	//results1.setResourceType(resource.getResourceType());
			        	//results1.setCallNumber(resource.getCallNumber());
			        	searchResultList.add(results1);
		        	}
		        	resContainer.setStatusCode("SUCCESS");
					resContainer.setStatusMessage("Results obtained are below");
					resContainer.setSearchResults(searchResultList);
		        }
		        else {
		        	resContainer.setStatusCode("SUCCESS_NO_RECORDS");
					resContainer.setStatusMessage("No Results obtained");
		        }
		        return new ResponseEntity(resContainer, HttpStatus.OK);
			}
			else if ( searchCriteria.getSearchType().equalsIgnoreCase("subject")) {
				SearchResponseContainer resContainer = new SearchResponseContainer();
				log.debug("REST request to get LibraryResource by subejct : {}", searchCriteria.getSubject());
				Optional<List<LibraryResource>> libraryResources = service.
		        		searchBySubject(searchCriteria.getSubject());
				List<SearchResults> searchResultList = new ArrayList<SearchResults>();
		        if(libraryResources!=null && libraryResources.isPresent()) {
		        	for(LibraryResource resource : libraryResources.get()) {
		        		SearchResults results1 = new SearchResults();
		        		results1.setId(resource.getId());
			        	results1.setTitle(resource.getResourceTitle());
			        	results1.setResourceDescription(resource.getResourceDescription());
			        	results1.setAuthor(resource.getAuthor().getAuthorName());
			        	//results1.setResourceType(resource.getResourceType());
			        	//results1.setCallNumber(resource.getCallNumber());
			        	searchResultList.add(results1);
		        	}
		        	resContainer.setStatusCode("SUCCESS");
					resContainer.setStatusMessage("Results obtained are below");
					resContainer.setSearchResults(searchResultList);
		        }
		        else {
		        	resContainer.setStatusCode("SUCCESS_NO_RECORDS");
					resContainer.setStatusMessage("No Results obtained");
		        }
		        return new ResponseEntity(resContainer, HttpStatus.OK);
			}
			return new ResponseEntity(null, HttpStatus.OK);

		}catch(Exception e) {
			log.debug("exception" + e);
			SearchResponseContainer resContainer = new SearchResponseContainer();
			resContainer.setStatusCode("FAILED");
			resContainer.setStatusMessage("Something bad happened . please check server logs");

			return new ResponseEntity(resContainer,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

		
        
    }

}
