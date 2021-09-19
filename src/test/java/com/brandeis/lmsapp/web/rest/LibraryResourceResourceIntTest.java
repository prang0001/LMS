package com.brandeis.lmsapp.web.rest;

import com.brandeis.lmsapp.LmsApp;

import com.brandeis.lmsapp.domain.LibraryResource;
import com.brandeis.lmsapp.domain.Author;
import com.brandeis.lmsapp.domain.Subject;
import com.brandeis.lmsapp.domain.ResourceStatus;
import com.brandeis.lmsapp.domain.ResourceType;
import com.brandeis.lmsapp.domain.RentalTransaction;
import com.brandeis.lmsapp.domain.WaitingList;
import com.brandeis.lmsapp.repository.LibraryResourceRepository;
import com.brandeis.lmsapp.service.LibraryResourceService;
import com.brandeis.lmsapp.web.rest.errors.ExceptionTranslator;
import com.brandeis.lmsapp.service.dto.LibraryResourceCriteria;
import com.brandeis.lmsapp.service.LibraryResourceQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.brandeis.lmsapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LibraryResourceResource REST controller.
 *
 * @see LibraryResourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LmsApp.class)
public class LibraryResourceResourceIntTest {

    private static final String DEFAULT_RESOURCE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CALL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CALL_NUMBER = "BBBBBBBBBB";

    @Autowired
    private LibraryResourceRepository libraryResourceRepository;

    @Mock
    private LibraryResourceRepository libraryResourceRepositoryMock;
    

    @Mock
    private LibraryResourceService libraryResourceServiceMock;

    @Autowired
    private LibraryResourceService libraryResourceService;

    @Autowired
    private LibraryResourceQueryService libraryResourceQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLibraryResourceMockMvc;

    private LibraryResource libraryResource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LibraryResourceResource libraryResourceResource = new LibraryResourceResource(libraryResourceService, libraryResourceQueryService);
        this.restLibraryResourceMockMvc = MockMvcBuilders.standaloneSetup(libraryResourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibraryResource createEntity(EntityManager em) {
        System.out.println("Creating Library Resource for Testing...");
        LibraryResource libraryResource = new LibraryResource()
            .resourceTitle(DEFAULT_RESOURCE_TITLE)
            .resourceDescription(DEFAULT_RESOURCE_DESCRIPTION)
            .callNumber(DEFAULT_CALL_NUMBER);
        return libraryResource;
    }

    @Before
    public void initTest() {
        libraryResource = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibraryResource() throws Exception {
        System.out.println("Creating Test Library Resource...");
        int databaseSizeBeforeCreate = libraryResourceRepository.findAll().size();
        System.out.println("Database Size:");
        System.out.println(databaseSizeBeforeCreate);

        // Create the LibraryResource
        restLibraryResourceMockMvc.perform(post("/api/library-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryResource)))
            .andExpect(status().isCreated());

        // Validate the LibraryResource in the database
        List<LibraryResource> libraryResourceList = libraryResourceRepository.findAll();
        assertThat(libraryResourceList).hasSize(databaseSizeBeforeCreate + 1);
        LibraryResource testLibraryResource = libraryResourceList.get(libraryResourceList.size() - 1);
        assertThat(testLibraryResource.getResourceTitle()).isEqualTo(DEFAULT_RESOURCE_TITLE);
        assertThat(testLibraryResource.getResourceDescription()).isEqualTo(DEFAULT_RESOURCE_DESCRIPTION);
        assertThat(testLibraryResource.getCallNumber()).isEqualTo(DEFAULT_CALL_NUMBER);

        int databaseSizeAfterCreate = libraryResourceRepository.findAll().size();
        System.out.println("Database Size after create:");
        System.out.println(databaseSizeAfterCreate);

        System.out.println("Current Resource List:");
        System.out.print(libraryResourceList);

    }

    @Test
    @Transactional
    public void createLibraryResourceWithExistingId() throws Exception {
        System.out.println("Test Creation of a resource using an ID that already exists.");
        int databaseSizeBeforeCreate = libraryResourceRepository.findAll().size();
        System.out.println("Database Size before attempt to create:");
        System.out.println(databaseSizeBeforeCreate);

        // Create the LibraryResource with an existing ID
        libraryResource.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibraryResourceMockMvc.perform(post("/api/library-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryResource)))
            .andExpect(status().isBadRequest());

        // Validate the LibraryResource in the database
        List<LibraryResource> libraryResourceList = libraryResourceRepository.findAll();
        assertThat(libraryResourceList).hasSize(databaseSizeBeforeCreate);

        int databaseSizeAfterCreate = libraryResourceRepository.findAll().size();
        System.out.println("Database Size after attempt to create:");
        System.out.println(databaseSizeAfterCreate);
    }

    @Test
    @Transactional
    public void checkResourceTitleIsRequired() throws Exception {
        System.out.println("Test Creation of a resource without a title - Expected to not create a resource.");
        int databaseSizeBeforeTest = libraryResourceRepository.findAll().size();
        System.out.println("Database Size before attempt to create:");
        System.out.println(databaseSizeBeforeTest);

        // set the field null
        libraryResource.setResourceTitle(null);

        // Create the LibraryResource, which fails.

        restLibraryResourceMockMvc.perform(post("/api/library-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryResource)))
            .andExpect(status().isBadRequest());

        List<LibraryResource> libraryResourceList = libraryResourceRepository.findAll();
        assertThat(libraryResourceList).hasSize(databaseSizeBeforeTest);

        int databaseSizeAfterTest = libraryResourceRepository.findAll().size();
        System.out.println("Database Size after attempt to create:");
        System.out.println(databaseSizeAfterTest);
    }

    @Test
    @Transactional
    public void checkCallNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = libraryResourceRepository.findAll().size();
        // set the field null
        libraryResource.setCallNumber(null);

        // Create the LibraryResource, which fails.

        restLibraryResourceMockMvc.perform(post("/api/library-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryResource)))
            .andExpect(status().isBadRequest());

        List<LibraryResource> libraryResourceList = libraryResourceRepository.findAll();
        assertThat(libraryResourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibraryResources() throws Exception {
        // Initialize the database
        libraryResourceRepository.saveAndFlush(libraryResource);

        // Get all the libraryResourceList
        restLibraryResourceMockMvc.perform(get("/api/library-resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libraryResource.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceTitle").value(hasItem(DEFAULT_RESOURCE_TITLE.toString())))
            .andExpect(jsonPath("$.[*].resourceDescription").value(hasItem(DEFAULT_RESOURCE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].callNumber").value(hasItem(DEFAULT_CALL_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getLibraryResource() throws Exception {
        // Initialize the database
        libraryResourceRepository.saveAndFlush(libraryResource);

        // Get the libraryResource
        restLibraryResourceMockMvc.perform(get("/api/library-resources/{id}", libraryResource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libraryResource.getId().intValue()))
            .andExpect(jsonPath("$.resourceTitle").value(DEFAULT_RESOURCE_TITLE.toString()))
            .andExpect(jsonPath("$.resourceDescription").value(DEFAULT_RESOURCE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.callNumber").value(DEFAULT_CALL_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getAllLibraryResourcesByResourceTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        libraryResourceRepository.saveAndFlush(libraryResource);

        // Get all the libraryResourceList where resourceTitle equals to DEFAULT_RESOURCE_TITLE
        defaultLibraryResourceShouldBeFound("resourceTitle.equals=" + DEFAULT_RESOURCE_TITLE);

        // Get all the libraryResourceList where resourceTitle equals to UPDATED_RESOURCE_TITLE
        defaultLibraryResourceShouldNotBeFound("resourceTitle.equals=" + UPDATED_RESOURCE_TITLE);
    }

    @Test
    @Transactional
    public void getAllLibraryResourcesByResourceTitleIsInShouldWork() throws Exception {
        // Initialize the database
        libraryResourceRepository.saveAndFlush(libraryResource);

        // Get all the libraryResourceList where resourceTitle in DEFAULT_RESOURCE_TITLE or UPDATED_RESOURCE_TITLE
        defaultLibraryResourceShouldBeFound("resourceTitle.in=" + DEFAULT_RESOURCE_TITLE + "," + UPDATED_RESOURCE_TITLE);

        // Get all the libraryResourceList where resourceTitle equals to UPDATED_RESOURCE_TITLE
        defaultLibraryResourceShouldNotBeFound("resourceTitle.in=" + UPDATED_RESOURCE_TITLE);
    }

    @Test
    @Transactional
    public void getAllLibraryResourcesByResourceTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        libraryResourceRepository.saveAndFlush(libraryResource);

        // Get all the libraryResourceList where resourceTitle is not null
        defaultLibraryResourceShouldBeFound("resourceTitle.specified=true");

        // Get all the libraryResourceList where resourceTitle is null
        defaultLibraryResourceShouldNotBeFound("resourceTitle.specified=false");
    }

    @Test
    @Transactional
    public void getAllLibraryResourcesByResourceDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        libraryResourceRepository.saveAndFlush(libraryResource);

        // Get all the libraryResourceList where resourceDescription equals to DEFAULT_RESOURCE_DESCRIPTION
        defaultLibraryResourceShouldBeFound("resourceDescription.equals=" + DEFAULT_RESOURCE_DESCRIPTION);

        // Get all the libraryResourceList where resourceDescription equals to UPDATED_RESOURCE_DESCRIPTION
        defaultLibraryResourceShouldNotBeFound("resourceDescription.equals=" + UPDATED_RESOURCE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLibraryResourcesByResourceDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        libraryResourceRepository.saveAndFlush(libraryResource);

        // Get all the libraryResourceList where resourceDescription in DEFAULT_RESOURCE_DESCRIPTION or UPDATED_RESOURCE_DESCRIPTION
        defaultLibraryResourceShouldBeFound("resourceDescription.in=" + DEFAULT_RESOURCE_DESCRIPTION + "," + UPDATED_RESOURCE_DESCRIPTION);

        // Get all the libraryResourceList where resourceDescription equals to UPDATED_RESOURCE_DESCRIPTION
        defaultLibraryResourceShouldNotBeFound("resourceDescription.in=" + UPDATED_RESOURCE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLibraryResourcesByResourceDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        libraryResourceRepository.saveAndFlush(libraryResource);

        // Get all the libraryResourceList where resourceDescription is not null
        defaultLibraryResourceShouldBeFound("resourceDescription.specified=true");

        // Get all the libraryResourceList where resourceDescription is null
        defaultLibraryResourceShouldNotBeFound("resourceDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllLibraryResourcesByCallNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        libraryResourceRepository.saveAndFlush(libraryResource);

        // Get all the libraryResourceList where callNumber equals to DEFAULT_CALL_NUMBER
        defaultLibraryResourceShouldBeFound("callNumber.equals=" + DEFAULT_CALL_NUMBER);

        // Get all the libraryResourceList where callNumber equals to UPDATED_CALL_NUMBER
        defaultLibraryResourceShouldNotBeFound("callNumber.equals=" + UPDATED_CALL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLibraryResourcesByCallNumberIsInShouldWork() throws Exception {
        // Initialize the database
        libraryResourceRepository.saveAndFlush(libraryResource);

        // Get all the libraryResourceList where callNumber in DEFAULT_CALL_NUMBER or UPDATED_CALL_NUMBER
        defaultLibraryResourceShouldBeFound("callNumber.in=" + DEFAULT_CALL_NUMBER + "," + UPDATED_CALL_NUMBER);

        // Get all the libraryResourceList where callNumber equals to UPDATED_CALL_NUMBER
        defaultLibraryResourceShouldNotBeFound("callNumber.in=" + UPDATED_CALL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLibraryResourcesByCallNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        libraryResourceRepository.saveAndFlush(libraryResource);

        // Get all the libraryResourceList where callNumber is not null
        defaultLibraryResourceShouldBeFound("callNumber.specified=true");

        // Get all the libraryResourceList where callNumber is null
        defaultLibraryResourceShouldNotBeFound("callNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllLibraryResourcesByAuthorIsEqualToSomething() throws Exception {
        // Initialize the database
        Author author = AuthorResourceIntTest.createEntity(em);
        em.persist(author);
        em.flush();
        libraryResource.setAuthor(author);
        libraryResourceRepository.saveAndFlush(libraryResource);
        Long authorId = author.getId();

        // Get all the libraryResourceList where author equals to authorId
        defaultLibraryResourceShouldBeFound("authorId.equals=" + authorId);

        // Get all the libraryResourceList where author equals to authorId + 1
        defaultLibraryResourceShouldNotBeFound("authorId.equals=" + (authorId + 1));
    }


    @Test
    @Transactional
    public void getAllLibraryResourcesBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        Subject subject = SubjectResourceIntTest.createEntity(em);
        em.persist(subject);
        em.flush();
        libraryResource.setSubject(subject);
        libraryResourceRepository.saveAndFlush(libraryResource);
        Long subjectId = subject.getId();

        // Get all the libraryResourceList where subject equals to subjectId
        defaultLibraryResourceShouldBeFound("subjectId.equals=" + subjectId);

        // Get all the libraryResourceList where subject equals to subjectId + 1
        defaultLibraryResourceShouldNotBeFound("subjectId.equals=" + (subjectId + 1));
    }


    @Test
    @Transactional
    public void getAllLibraryResourcesByResourceStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ResourceStatus resourceStatus = ResourceStatusResourceIntTest.createEntity(em);
        em.persist(resourceStatus);
        em.flush();
        libraryResource.setResourceStatus(resourceStatus);
        libraryResourceRepository.saveAndFlush(libraryResource);
        Long resourceStatusId = resourceStatus.getId();

        // Get all the libraryResourceList where resourceStatus equals to resourceStatusId
        defaultLibraryResourceShouldBeFound("resourceStatusId.equals=" + resourceStatusId);

        // Get all the libraryResourceList where resourceStatus equals to resourceStatusId + 1
        defaultLibraryResourceShouldNotBeFound("resourceStatusId.equals=" + (resourceStatusId + 1));
    }


    @Test
    @Transactional
    public void getAllLibraryResourcesByResourceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ResourceType resourceType = ResourceTypeResourceIntTest.createEntity(em);
        em.persist(resourceType);
        em.flush();
        libraryResource.setResourceType(resourceType);
        libraryResourceRepository.saveAndFlush(libraryResource);
        Long resourceTypeId = resourceType.getId();

        // Get all the libraryResourceList where resourceType equals to resourceTypeId
        defaultLibraryResourceShouldBeFound("resourceTypeId.equals=" + resourceTypeId);

        // Get all the libraryResourceList where resourceType equals to resourceTypeId + 1
        defaultLibraryResourceShouldNotBeFound("resourceTypeId.equals=" + (resourceTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllLibraryResourcesByRentalTransactionIsEqualToSomething() throws Exception {
        // Initialize the database
        RentalTransaction rentalTransaction = RentalTransactionResourceIntTest.createEntity(em);
        em.persist(rentalTransaction);
        em.flush();
        libraryResource.addRentalTransaction(rentalTransaction);
        libraryResourceRepository.saveAndFlush(libraryResource);
        Long rentalTransactionId = rentalTransaction.getId();

        // Get all the libraryResourceList where rentalTransaction equals to rentalTransactionId
        defaultLibraryResourceShouldBeFound("rentalTransactionId.equals=" + rentalTransactionId);

        // Get all the libraryResourceList where rentalTransaction equals to rentalTransactionId + 1
        defaultLibraryResourceShouldNotBeFound("rentalTransactionId.equals=" + (rentalTransactionId + 1));
    }


    @Test
    @Transactional
    public void getAllLibraryResourcesByWaitingListIsEqualToSomething() throws Exception {
        // Initialize the database
        WaitingList waitingList = WaitingListResourceIntTest.createEntity(em);
        em.persist(waitingList);
        em.flush();
        libraryResource.addWaitingList(waitingList);
        libraryResourceRepository.saveAndFlush(libraryResource);
        Long waitingListId = waitingList.getId();

        // Get all the libraryResourceList where waitingList equals to waitingListId
        defaultLibraryResourceShouldBeFound("waitingListId.equals=" + waitingListId);

        // Get all the libraryResourceList where waitingList equals to waitingListId + 1
        defaultLibraryResourceShouldNotBeFound("waitingListId.equals=" + (waitingListId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLibraryResourceShouldBeFound(String filter) throws Exception {
        restLibraryResourceMockMvc.perform(get("/api/library-resources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libraryResource.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceTitle").value(hasItem(DEFAULT_RESOURCE_TITLE.toString())))
            .andExpect(jsonPath("$.[*].resourceDescription").value(hasItem(DEFAULT_RESOURCE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].callNumber").value(hasItem(DEFAULT_CALL_NUMBER.toString())));

        // Check, that the count call also returns 1
        restLibraryResourceMockMvc.perform(get("/api/library-resources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLibraryResourceShouldNotBeFound(String filter) throws Exception {
        restLibraryResourceMockMvc.perform(get("/api/library-resources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLibraryResourceMockMvc.perform(get("/api/library-resources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLibraryResource() throws Exception {
        // Get the libraryResource
        restLibraryResourceMockMvc.perform(get("/api/library-resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibraryResource() throws Exception {
        // Initialize the database
        libraryResourceService.save(libraryResource);
        System.out.println("Updating Library Resource");

        int databaseSizeBeforeUpdate = libraryResourceRepository.findAll().size();

        System.out.println("Database Size:");
        System.out.println(databaseSizeBeforeUpdate);

        // Update the libraryResource
        LibraryResource updatedLibraryResource = libraryResourceRepository.findById(libraryResource.getId()).get();
        // Disconnect from session so that the updates on updatedLibraryResource are not directly saved in db
        System.out.println("Resource Title Before Update: " + updatedLibraryResource.getResourceTitle());
        em.detach(updatedLibraryResource);
        updatedLibraryResource
            .resourceTitle(UPDATED_RESOURCE_TITLE)
            .resourceDescription(UPDATED_RESOURCE_DESCRIPTION)
            .callNumber(UPDATED_CALL_NUMBER);

        restLibraryResourceMockMvc.perform(put("/api/library-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLibraryResource)))
            .andExpect(status().isOk());

        // Validate the LibraryResource in the database
        List<LibraryResource> libraryResourceList = libraryResourceRepository.findAll();
        assertThat(libraryResourceList).hasSize(databaseSizeBeforeUpdate);
        LibraryResource testLibraryResource = libraryResourceList.get(libraryResourceList.size() - 1);
        assertThat(testLibraryResource.getResourceTitle()).isEqualTo(UPDATED_RESOURCE_TITLE);
        assertThat(testLibraryResource.getResourceDescription()).isEqualTo(UPDATED_RESOURCE_DESCRIPTION);
        assertThat(testLibraryResource.getCallNumber()).isEqualTo(UPDATED_CALL_NUMBER);

        System.out.println("Resource Title After Update: " + testLibraryResource.getResourceTitle());

        int databaseSizeAfterUpdate = libraryResourceRepository.findAll().size();

        System.out.println("Database Size After Update:");
        System.out.println(databaseSizeAfterUpdate);

    }

    @Test
    @Transactional
    public void updateNonExistingLibraryResource() throws Exception {
        int databaseSizeBeforeUpdate = libraryResourceRepository.findAll().size();

        // Create the LibraryResource

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLibraryResourceMockMvc.perform(put("/api/library-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryResource)))
            .andExpect(status().isBadRequest());

        // Validate the LibraryResource in the database
        List<LibraryResource> libraryResourceList = libraryResourceRepository.findAll();
        assertThat(libraryResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLibraryResource() throws Exception {

        System.out.println("Testing Destruction of Library Resource");
        // Initialize the database
        libraryResourceService.save(libraryResource);

        int databaseSizeBeforeDelete = libraryResourceRepository.findAll().size();
        System.out.println("Database Size before delete:");
        System.out.println(databaseSizeBeforeDelete);

        // Get the libraryResource
        restLibraryResourceMockMvc.perform(delete("/api/library-resources/{id}", libraryResource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibraryResource> libraryResourceList = libraryResourceRepository.findAll();
        assertThat(libraryResourceList).hasSize(databaseSizeBeforeDelete - 1);

        int databaseSizeAfterDelete = libraryResourceRepository.findAll().size();
        System.out.println("Database Size after delete:");
        System.out.println(databaseSizeAfterDelete);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibraryResource.class);
        LibraryResource libraryResource1 = new LibraryResource();
        libraryResource1.setId(1L);
        LibraryResource libraryResource2 = new LibraryResource();
        libraryResource2.setId(libraryResource1.getId());
        assertThat(libraryResource1).isEqualTo(libraryResource2);
        libraryResource2.setId(2L);
        assertThat(libraryResource1).isNotEqualTo(libraryResource2);
        libraryResource1.setId(null);
        assertThat(libraryResource1).isNotEqualTo(libraryResource2);
    }
}
