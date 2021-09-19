package com.brandeis.lmsapp.web.rest;

import static com.brandeis.lmsapp.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.brandeis.lmsapp.LmsApp;
import com.brandeis.lmsapp.domain.SearchCriteria;
import com.brandeis.lmsapp.service.SearchService;
import com.brandeis.lmsapp.web.rest.errors.ExceptionTranslator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LmsApp.class)
public class SearchResourceIntTest {

     private MockMvc restSearchMockMVC;
     
     @Autowired
     private MappingJackson2HttpMessageConverter jacksonMessageConverter;

     @Autowired
     private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

     @Autowired
     private ExceptionTranslator exceptionTranslator;
     
     @Autowired
     private SearchService service;
     
 
	 @Before
	    public void setup() {
	        MockitoAnnotations.initMocks(this);
	        final SearchResource searchResource = new SearchResource(service);
	        this.restSearchMockMVC = MockMvcBuilders.standaloneSetup(searchResource)
        		.setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter).build();
	 }
	 
	 @Test
	 @Transactional
	 
	 public void searchByAuthor() throws Exception {
		 SearchCriteria criteria = new SearchCriteria();
		 criteria.setAuthor("Ray");
		 criteria.setSearchType("author");
		 restSearchMockMVC.perform(post("/api/search")
				 .contentType(TestUtil.APPLICATION_JSON_UTF8)
		 		 .content(TestUtil.convertObjectToJsonBytes(criteria)))
		 		.andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
         .andExpect(jsonPath("$.searchResults").isNotEmpty());
		 
	 }
	 
	 @Test
	 @Transactional
	 
	 public void searchByAuthorNotExists() throws Exception {
		 SearchCriteria criteria = new SearchCriteria();
		 criteria.setAuthor("rasssy");
		 criteria.setSearchType("author");
		 restSearchMockMVC.perform(post("/api/search")
				 .contentType(TestUtil.APPLICATION_JSON_UTF8)
		 		 .content(TestUtil.convertObjectToJsonBytes(criteria)))
		 		.andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
         .andExpect(jsonPath("$.searchResults").doesNotExist());
		 
	 }
	 
	 //search By Id 
	 @Test
	 @Transactional
	 public void searchById() throws Exception{
		 SearchCriteria criteria = new SearchCriteria();
		 criteria.setId(1L);
		 criteria.setSearchType("Resource ID");
		 restSearchMockMVC.perform(post("/api/search")
				 .contentType(TestUtil.APPLICATION_JSON_UTF8)
		 		 .content(TestUtil.convertObjectToJsonBytes(criteria)))
		 		.andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
         .andExpect(jsonPath("$.searchResults[0].title").value("Fahrenheit 451"));
	 }
	 
	//search By Call Number 
		 @Test
		 @Transactional
		 public void searchByCallNumber() throws Exception{
			 SearchCriteria criteria = new SearchCriteria();
			 criteria.setCallNumber(111L);
			 criteria.setSearchType("call Number");
			 restSearchMockMVC.perform(post("/api/search")
					 .contentType(TestUtil.APPLICATION_JSON_UTF8)
			 		 .content(TestUtil.convertObjectToJsonBytes(criteria)))
			 		.andExpect(status().isOk())
	         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	         .andExpect(jsonPath("$.searchResults[0].author").value("Joseph Jacobs"));
		 }
		 
		//search By Title
		 @Test
		 @Transactional
		 public void searchByTitle() throws Exception{
			 SearchCriteria criteria = new SearchCriteria();
			 criteria.setTitle("Inferno");
			 criteria.setSearchType("title");
			 restSearchMockMVC.perform(post("/api/search")
					 .contentType(TestUtil.APPLICATION_JSON_UTF8)
			 		 .content(TestUtil.convertObjectToJsonBytes(criteria)))
			 		.andExpect(status().isOk())
	         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	         .andExpect(jsonPath("$.searchResults[0].id").value(2));
		 }
	 
	 
	 
}
