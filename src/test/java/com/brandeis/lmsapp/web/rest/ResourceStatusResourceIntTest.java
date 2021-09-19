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
import java.util.List;


import static com.brandeis.lmsapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ResourceStatusResource REST controller.
 *
 * @see ResourceStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LmsApp.class)
public class ResourceStatusResourceIntTest {

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

        // Create the ResourceStatus
        restResourceStatusMockMvc.perform(post("/api/resource-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceStatus)))
            .andExpect(status().isCreated());

        // Validate the ResourceStatus in the database
        List<ResourceStatus> resourceStatusList = resourceStatusRepository.findAll();
        assertThat(resourceStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ResourceStatus testResourceStatus = resourceStatusList.get(resourceStatusList.size() - 1);
        assertThat(testResourceStatus.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
    }

    @Test
    @Transactional
    public void createResourceStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceStatusRepository.findAll().size();

        // Create the ResourceStatus with an existing ID
        resourceStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceStatusMockMvc.perform(post("/api/resource-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceStatus)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceStatus in the database
        List<ResourceStatus> resourceStatusList = resourceStatusRepository.findAll();
        assertThat(resourceStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceStatusRepository.findAll().size();
        // set the field null
        resourceStatus.setStatusName(null);

        // Create the ResourceStatus, which fails.

        restResourceStatusMockMvc.perform(post("/api/resource-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceStatus)))
            .andExpect(status().isBadRequest());

        List<ResourceStatus> resourceStatusList = resourceStatusRepository.findAll();
        assertThat(resourceStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResourceStatuses() throws Exception {
        // Initialize the database
        resourceStatusRepository.saveAndFlush(resourceStatus);

        // Get all the resourceStatusList
        restResourceStatusMockMvc.perform(get("/api/resource-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getResourceStatus() throws Exception {
        // Initialize the database
        resourceStatusRepository.saveAndFlush(resourceStatus);

        // Get the resourceStatus
        restResourceStatusMockMvc.perform(get("/api/resource-statuses/{id}", resourceStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resourceStatus.getId().intValue()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllResourceStatusesByStatusNameIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceStatusRepository.saveAndFlush(resourceStatus);

        // Get all the resourceStatusList where statusName equals to DEFAULT_STATUS_NAME
        //added the test data as well
        defaultResourceStatusShouldBeFound("statusName.equals=" + DEFAULT_STATUS_NAME);

        // Get all the resourceStatusList where statusName equals to UPDATED_STATUS_NAME
        defaultResourceStatusShouldNotBeFound("statusName.equals=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    public void getAllResourceStatusesByStatusNameIsInShouldWork() throws Exception {
        // Initialize the database
        resourceStatusRepository.saveAndFlush(resourceStatus);

        // Get all the resourceStatusList where statusName in DEFAULT_STATUS_NAME or UPDATED_STATUS_NAME
        //added the test data as well
        defaultResourceStatusShouldBeFound("statusName.in=" + DEFAULT_STATUS_NAME + "," +
            UPDATED_STATUS_NAME);

        // Get all the resourceStatusList where statusName equals to UPDATED_STATUS_NAME
        defaultResourceStatusShouldNotBeFound("statusName.in=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    public void getAllResourceStatusesByStatusNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceStatusRepository.saveAndFlush(resourceStatus);

        // Get all the resourceStatusList where statusName is not null
        defaultResourceStatusShouldBeFound("statusName.specified=true");

        // Get all the resourceStatusList where statusName is null
        defaultResourceStatusShouldNotBeFound("statusName.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourceStatusesByLibraryResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        LibraryResource libraryResource = LibraryResourceResourceIntTest.createEntity(em);
        em.persist(libraryResource);
        em.flush();
        resourceStatus.addLibraryResource(libraryResource);
        resourceStatusRepository.saveAndFlush(resourceStatus);
        Long libraryResourceId = libraryResource.getId();

        // Get all the resourceStatusList where libraryResource equals to libraryResourceId
        defaultResourceStatusShouldBeFound("libraryResourceId.equals=" + libraryResourceId);

        // Get all the resourceStatusList where libraryResource equals to libraryResourceId + 1
        defaultResourceStatusShouldNotBeFound("libraryResourceId.equals=" + (libraryResourceId + 1));
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
    public void getNonExistingResourceStatus() throws Exception {
        // Get the resourceStatus
        restResourceStatusMockMvc.perform(get("/api/resource-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResourceStatus() throws Exception {
        // Initialize the database
        resourceStatusService.save(resourceStatus);

        int databaseSizeBeforeUpdate = resourceStatusRepository.findAll().size();

        // Update the resourceStatus
        ResourceStatus updatedResourceStatus = resourceStatusRepository.findById(resourceStatus.getId()).get();
        // Disconnect from session so that the updates on updatedResourceStatus are not directly saved in db
        em.detach(updatedResourceStatus);
        updatedResourceStatus
            .statusName(UPDATED_STATUS_NAME);

        restResourceStatusMockMvc.perform(put("/api/resource-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResourceStatus)))
            .andExpect(status().isOk());

        // Validate the ResourceStatus in the database
        List<ResourceStatus> resourceStatusList = resourceStatusRepository.findAll();
        assertThat(resourceStatusList).hasSize(databaseSizeBeforeUpdate);
        ResourceStatus testResourceStatus = resourceStatusList.get(resourceStatusList.size() - 1);
        assertThat(testResourceStatus.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingResourceStatus() throws Exception {
        int databaseSizeBeforeUpdate = resourceStatusRepository.findAll().size();

        // Create the ResourceStatus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceStatusMockMvc.perform(put("/api/resource-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceStatus)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceStatus in the database
        List<ResourceStatus> resourceStatusList = resourceStatusRepository.findAll();
        assertThat(resourceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResourceStatus() throws Exception {
        // Initialize the database
        resourceStatusService.save(resourceStatus);

        int databaseSizeBeforeDelete = resourceStatusRepository.findAll().size();

        // Get the resourceStatus
        restResourceStatusMockMvc.perform(delete("/api/resource-statuses/{id}", resourceStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResourceStatus> resourceStatusList = resourceStatusRepository.findAll();
        assertThat(resourceStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceStatus.class);
        ResourceStatus resourceStatus1 = new ResourceStatus();
        resourceStatus1.setId(1L);
        ResourceStatus resourceStatus2 = new ResourceStatus();
        resourceStatus2.setId(resourceStatus1.getId());
        assertThat(resourceStatus1).isEqualTo(resourceStatus2);
        resourceStatus2.setId(2L);
        assertThat(resourceStatus1).isNotEqualTo(resourceStatus2);
        resourceStatus1.setId(null);
        assertThat(resourceStatus1).isNotEqualTo(resourceStatus2);
    }
}
