package com.brandeis.lmsapp.web.rest;

import com.brandeis.lmsapp.LmsApp;

import com.brandeis.lmsapp.domain.ResourceStatus;
import com.brandeis.lmsapp.domain.LibraryResource;
import com.brandeis.lmsapp.repository.ResourceStatusRepository;
import com.brandeis.lmsapp.service.ResourceStatusService;
import com.brandeis.lmsapp.web.rest.errors.ExceptionTranslator;
import com.brandeis.lmsapp.service.dto.ResourceStatusCriteria;
import com.brandeis.lmsapp.service.ResourceStatusQueryService;

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

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static com.brandeis.lmsapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test class for the ResourceStatusResource REST controller.
 *
 * @see ResourceStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LmsApp.class)
public class ByStatusRestTest {

    private static final String DEFAULT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBBBBBBB";

    @Autowired
    private ResourceStatusRepository resourceStatusRepository;

    @Autowired
    private ResourceStatusService resourceStatusService;

    @Autowired
    private ResourceStatusQueryService resourceStatusQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResourceStatusMockMvc;

    private ResourceStatus resourceStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResourceStatusResource resourceStatusResource = new ResourceStatusResource(resourceStatusService, resourceStatusQueryService);
        this.restResourceStatusMockMvc = MockMvcBuilders.standaloneSetup(resourceStatusResource)
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
    public static ResourceStatus createEntity(EntityManager em) {
        ResourceStatus resourceStatus = new ResourceStatus()
            .statusName(DEFAULT_STATUS_NAME);
        return resourceStatus;
    }

    @Before
    public void initTest() {
        resourceStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createResourceStatus() throws Exception {
        int databaseSizeBeforeCreate = resourceStatusRepository.findAll().size();
        System.out.println("-------------------------We are in CreateResourceStatus test-------------------------");
        System.out.println("current items in database" + databaseSizeBeforeCreate);

        // Create the ResourceStatus
        restResourceStatusMockMvc.perform(post("/api/resource-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceStatus)))
            .andExpect(status().isCreated());
        System.out.println("Added a new resourceStatus: " + resourceStatus);

        // Validate the ResourceStatus in the database
        List<ResourceStatus> resourceStatusList = resourceStatusRepository.findAll();
        assertThat(resourceStatusList).hasSize(databaseSizeBeforeCreate + 1);
        System.out.println("current items in database after adding resource" + databaseSizeBeforeCreate+1);
        String actual = resourceStatusList.toString();
        String expected = "[ResourceStatus{id=1, statusName='Available'}, ResourceStatus{id=2, statusName='Rented'}, " +
            "ResourceStatus{id=3, statusName='Reserved'}, ResourceStatus{id=4, statusName='Lost'}, ResourceStatus{id=7, statusName='AAAAAAAAAA'}]";
        System.out.println("Actual items in DB: " + resourceStatusList);
        System.out.println("Expected items in DB: " + expected);
        assertThat(actual, is(equalTo(expected)));
        System.out.println("Validated that actual items equal expected items");
        ResourceStatus testResourceStatus = resourceStatusList.get(resourceStatusList.size() - 1);
        System.out.println("Validating by getStatusName for the new ID = added new status");
        assertThat(testResourceStatus.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        System.out.println(testResourceStatus + testResourceStatus.getStatusName() );
        System.out.println("Validated that getStatusName works");
        System.out.println("-------------------------Completed CreateResourceStatus test-------------------------");
    }

    @Test
    @Transactional
    public void createResourceStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceStatusRepository.findAll().size();
        System.out.println("--------------------We are in CreateResourceStatusWithExistingID test----------------------");

        // Create the ResourceStatus with an existing ID
        resourceStatus.setId(1L);
        System.out.println("Creating a new status with existing ID of 1");
        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceStatusMockMvc.perform(post("/api/resource-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceStatus)))
            .andExpect(status().isBadRequest());
        System.out.println("Expecting an error message");
        // Validate the ResourceStatus in the database
        List<ResourceStatus> resourceStatusList = resourceStatusRepository.findAll();
        assertThat(resourceStatusList).hasSize(databaseSizeBeforeCreate);
        System.out.println("making sure that status with existing id wasn't added by validating the size of table didnt change: ");
        System.out.println("--------------------Completed CreateResourceStatusWithExistingID test----------------------");
    }

    @Test
    @Transactional
    public void checkStatusNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceStatusRepository.findAll().size();
        System.out.println("-------------------------We are in CCheckStatusNameIsRequired test-------------------------");
        // set the field null
        resourceStatus.setStatusName(null);
        System.out.println("Trying to create a null status");

        // Create the ResourceStatus, which fails.

        restResourceStatusMockMvc.perform(post("/api/resource-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceStatus)))
            .andExpect(status().isBadRequest());
        System.out.println("Expecting an error message");

        List<ResourceStatus> resourceStatusList = resourceStatusRepository.findAll();
        assertThat(resourceStatusList).hasSize(databaseSizeBeforeTest);
        System.out.println("making sure that null status wasn't added by validating the size of table didnt change: ");
        System.out.println("-------------------------Completed CCheckStatusNameIsRequired test-------------------------");

    }

    @Test
    @Transactional
    public void getAllResourceStatusesByStatusNameIsEqualToSomething() throws Exception {
        // Initialize the database
        System.out.println("------------We are in GetAllResourceStatusesByStatusNameIsEqualToSomething test------------");
        resourceStatusRepository.saveAndFlush(resourceStatus);
        System.out.println("Added new resource status");

        // Get all the resourceStatusList where statusName equals to DEFAULT_STATUS_NAME
        //added the test data as well
        defaultResourceStatusShouldBeFound("statusName.equals=" + DEFAULT_STATUS_NAME);
        System.out.println("validated that the new status could be found");

        // Get all the resourceStatusList where statusName equals to UPDATED_STATUS_NAME
        defaultResourceStatusShouldNotBeFound("statusName.equals=" + UPDATED_STATUS_NAME);
        System.out.println("validated that non existent status couldn't be found");
        System.out.println("------------Completed GetAllResourceStatusesByStatusNameIsEqualToSomething test------------");
    }

    @Test
    @Transactional
    public void getAllResourceStatusesByStatusNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        System.out.println("--------We are in GetAllResourceStatusesByStatusNameIsNullOrNotNull test---------");
        resourceStatusRepository.saveAndFlush(resourceStatus);
        System.out.println("Added new resource status");

        // Get all the resourceStatusList where statusName is not null
        defaultResourceStatusShouldBeFound1("statusName.specified=true");
        System.out.println("validated that the new status could be found");

        // Get all the resourceStatusList where statusName is null
        defaultResourceStatusShouldNotBeFound("statusName.specified=false");
        System.out.println("validated that null status couldn't be found");
        System.out.println("--------Completed GetAllResourceStatusesByStatusNameIsNullOrNotNull test---------");
    }

    @Test
    @Transactional
    public void getAllResourceStatusesByLibraryResourceIsEqualToSomething() throws Exception {

        System.out.println("---------We are in GetAllResourceStatusesByLibraryResourceIsEqualToSomething test---------");
        // Initialize the database
        LibraryResource libraryResource = LibraryResourceResourceIntTest.createEntity(em);
        em.persist(libraryResource);
        em.flush();
        resourceStatus.addLibraryResource(libraryResource);
        resourceStatusRepository.saveAndFlush(resourceStatus);
        Long libraryResourceId = libraryResource.getId();
        System.out.println("added a library resource with a new resource status");
        System.out.println("resource status is: " + resourceStatus);
        System.out.println("library resource is: " + libraryResource + " it's ID: "+ libraryResourceId );

        // Get all the resourceStatusList where libraryResource equals to libraryResourceId
        defaultResourceStatusShouldBeFound("libraryResourceId.equals=" + libraryResourceId);
        System.out.println("validated that the status could be found based on libraryResourceID");

        // Get all the resourceStatusList where libraryResource equals to libraryResourceId + 1
        defaultResourceStatusShouldNotBeFound("libraryResourceId.equals=" + (libraryResourceId + 1));
        System.out.println("validated that the status could not be found based on libraryResourceID + 1");
        System.out.println("---------Completed GetAllResourceStatusesByLibraryResourceIsEqualToSomething test---------");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultResourceStatusShouldBeFound(String filter) throws Exception {
        restResourceStatusMockMvc.perform(get("/api/resource-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME.toString())));

        // Check, that the count call also returns 1
        // made it 5 from 1 due to test data
        restResourceStatusMockMvc.perform(get("/api/resource-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultResourceStatusShouldBeFound1(String filter) throws Exception {
        restResourceStatusMockMvc.perform(get("/api/resource-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME.toString())));

        // Check, that the count call also returns 1
        // made it 5 from 1 due to test data
        restResourceStatusMockMvc.perform(get("/api/resource-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("5"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultResourceStatusShouldNotBeFound(String filter) throws Exception {
        restResourceStatusMockMvc.perform(get("/api/resource-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResourceStatusMockMvc.perform(get("/api/resource-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void updateResourceStatus() throws Exception {
        System.out.println("-------------------------We are in UpdateResourceStatus Test-------------------------");
        // Initialize the database
        resourceStatusService.save(resourceStatus);
        System.out.println("Saved the new test case");
        int databaseSizeBeforeUpdate = resourceStatusRepository.findAll().size();
        System.out.println("Checked size of statuses: " + databaseSizeBeforeUpdate);

        // Update the resourceStatus
        ResourceStatus updatedResourceStatus = resourceStatusRepository.findById(resourceStatus.getId()).get();
        // Disconnect from session so that the updates on updatedResourceStatus are not directly saved in db
        em.detach(updatedResourceStatus);
        updatedResourceStatus
            .statusName(UPDATED_STATUS_NAME);
        System.out.println("Updated Status name to a new value" + UPDATED_STATUS_NAME );

        restResourceStatusMockMvc.perform(put("/api/resource-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResourceStatus)))
            .andExpect(status().isOk());
        System.out.println("Expect put performs ok");
        // Validate the ResourceStatus in the database
        List<ResourceStatus> resourceStatusList = resourceStatusRepository.findAll();
        assertThat(resourceStatusList).hasSize(databaseSizeBeforeUpdate);
        System.out.println("current items in database after updating resource" + databaseSizeBeforeUpdate);
        String actual = resourceStatusList.toString();
        String expected = "[ResourceStatus{id=1, statusName='Available'}, ResourceStatus{id=2, statusName='Rented'}, " +
            "ResourceStatus{id=3, statusName='Reserved'}, ResourceStatus{id=4, statusName='Lost'}, ResourceStatus{id=6, statusName='BBBBBBBBBB'}]";
        System.out.println("Actual items in DB: " + resourceStatusList);
        System.out.println("Expected items in DB: " + expected);
        assertThat(actual, is(equalTo(expected)));
        System.out.println("Validated that actual items equal expected items");
        ResourceStatus testResourceStatus = resourceStatusList.get(resourceStatusList.size() - 1);
        assertThat(testResourceStatus.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        System.out.println("Checking the new status name to be the updated status name: " + UPDATED_STATUS_NAME );
        System.out.println("-------------------------Completed UpdateResourceStatus Test-------------------------");
    }

    @Test
    @Transactional
    public void deleteResourceStatus() throws Exception {
        System.out.println("-------------------------We are in DeleteResourceStatus Test-------------------------");
        // Initialize the database
        resourceStatusService.save(resourceStatus);
        System.out.println("Save the new status");

        int databaseSizeBeforeDelete = resourceStatusRepository.findAll().size();
        System.out.println("Get the statuses count: " + databaseSizeBeforeDelete );

        // Get the resourceStatus
        restResourceStatusMockMvc.perform(delete("/api/resource-statuses/{id}", resourceStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
        System.out.println("Perform delete method ");

        // Validate the database is empty
        List<ResourceStatus> resourceStatusList = resourceStatusRepository.findAll();
        assertThat(resourceStatusList).hasSize(databaseSizeBeforeDelete - 1);
        System.out.println("validate that the status was deleted by ensuring the count of statuses = count before delete -1 ");
        System.out.println("-------------------------Completed DeleteResourceStatus Test-------------------------");
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        System.out.println("-------------------------Testing Begins----------------------");
        System.out.println("-------------------------We are in EqualsVerifier Test-------------------------");
        TestUtil.equalsVerifier(ResourceStatus.class);
        ResourceStatus resourceStatus1 = new ResourceStatus();
        resourceStatus1.setId(1L);
        System.out.println("Set resourceStatus 1 ID to 1L");
        ResourceStatus resourceStatus2 = new ResourceStatus();
        resourceStatus2.setId(resourceStatus1.getId());
        System.out.println("Set resourceStatus 2 ID to 1L");
        assertThat(resourceStatus1).isEqualTo(resourceStatus2);
        System.out.println("Test for if resourceStatus1 = resourceStatus2");
        resourceStatus2.setId(2L);
        System.out.println("Set resourceStatus 2 ID to 2L");
        assertThat(resourceStatus1).isNotEqualTo(resourceStatus2);
        System.out.println("Test for if resourceStatus1 <> resourceStatus2");
        resourceStatus1.setId(null);
        System.out.println("Set resourceStatus 1 ID to null");
        assertThat(resourceStatus1).isNotEqualTo(resourceStatus2);
        System.out.println("Test for if resourceStatus1 <> resourceStatus2");
        System.out.println("-------------------------Completed EqualsVerifier Test-------------------------");
    }
}
