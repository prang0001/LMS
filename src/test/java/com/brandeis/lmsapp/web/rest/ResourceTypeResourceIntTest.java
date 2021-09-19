package com.brandeis.lmsapp.web.rest;

import com.brandeis.lmsapp.LmsApp;

import com.brandeis.lmsapp.domain.ResourceType;
import com.brandeis.lmsapp.domain.LibraryResource;
import com.brandeis.lmsapp.repository.ResourceTypeRepository;
import com.brandeis.lmsapp.service.ResourceTypeService;
import com.brandeis.lmsapp.web.rest.errors.ExceptionTranslator;
import com.brandeis.lmsapp.service.dto.ResourceTypeCriteria;
import com.brandeis.lmsapp.service.ResourceTypeQueryService;

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
 * Test class for the ResourceTypeResource REST controller.
 *
 * @see ResourceTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LmsApp.class)
public class ResourceTypeResourceIntTest {

    private static final String DEFAULT_RESOURCE_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_TYPE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_RENTAL_DURATION = 1;
    private static final Integer UPDATED_RENTAL_DURATION = 2;

    private static final Double DEFAULT_OVERDUE_CHARGE = 1D;
    private static final Double UPDATED_OVERDUE_CHARGE = 2D;

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;
    
    @Autowired
    private ResourceTypeService resourceTypeService;

    @Autowired
    private ResourceTypeQueryService resourceTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResourceTypeMockMvc;

    private ResourceType resourceType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResourceTypeResource resourceTypeResource = new ResourceTypeResource(resourceTypeService, resourceTypeQueryService);
        this.restResourceTypeMockMvc = MockMvcBuilders.standaloneSetup(resourceTypeResource)
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
    public static ResourceType createEntity(EntityManager em) {
        ResourceType resourceType = new ResourceType()
            .resourceTypeName(DEFAULT_RESOURCE_TYPE_NAME)
            .rentalDuration(DEFAULT_RENTAL_DURATION)
            .overdueCharge(DEFAULT_OVERDUE_CHARGE);
        return resourceType;
    }

    @Before
    public void initTest() {
        resourceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createResourceType() throws Exception {
        int databaseSizeBeforeCreate = resourceTypeRepository.findAll().size();

        // Create the ResourceType
        restResourceTypeMockMvc.perform(post("/api/resource-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceType)))
            .andExpect(status().isCreated());

        // Validate the ResourceType in the database
        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ResourceType testResourceType = resourceTypeList.get(resourceTypeList.size() - 1);
        assertThat(testResourceType.getResourceTypeName()).isEqualTo(DEFAULT_RESOURCE_TYPE_NAME);
        assertThat(testResourceType.getRentalDuration()).isEqualTo(DEFAULT_RENTAL_DURATION);
        assertThat(testResourceType.getOverdueCharge()).isEqualTo(DEFAULT_OVERDUE_CHARGE);
    }

    @Test
    @Transactional
    public void createResourceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceTypeRepository.findAll().size();

        // Create the ResourceType with an existing ID
        resourceType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceTypeMockMvc.perform(post("/api/resource-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceType)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceType in the database
        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkResourceTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceTypeRepository.findAll().size();
        // set the field null
        resourceType.setResourceTypeName(null);

        // Create the ResourceType, which fails.

        restResourceTypeMockMvc.perform(post("/api/resource-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceType)))
            .andExpect(status().isBadRequest());

        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRentalDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceTypeRepository.findAll().size();
        // set the field null
        resourceType.setRentalDuration(null);

        // Create the ResourceType, which fails.

        restResourceTypeMockMvc.perform(post("/api/resource-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceType)))
            .andExpect(status().isBadRequest());

        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOverdueChargeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceTypeRepository.findAll().size();
        // set the field null
        resourceType.setOverdueCharge(null);

        // Create the ResourceType, which fails.

        restResourceTypeMockMvc.perform(post("/api/resource-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceType)))
            .andExpect(status().isBadRequest());

        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResourceTypes() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList
        restResourceTypeMockMvc.perform(get("/api/resource-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceTypeName").value(hasItem(DEFAULT_RESOURCE_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].rentalDuration").value(hasItem(DEFAULT_RENTAL_DURATION)))
            .andExpect(jsonPath("$.[*].overdueCharge").value(hasItem(DEFAULT_OVERDUE_CHARGE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getResourceType() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get the resourceType
        restResourceTypeMockMvc.perform(get("/api/resource-types/{id}", resourceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resourceType.getId().intValue()))
            .andExpect(jsonPath("$.resourceTypeName").value(DEFAULT_RESOURCE_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.rentalDuration").value(DEFAULT_RENTAL_DURATION))
            .andExpect(jsonPath("$.overdueCharge").value(DEFAULT_OVERDUE_CHARGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllResourceTypesByResourceTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList where resourceTypeName equals to DEFAULT_RESOURCE_TYPE_NAME
        defaultResourceTypeShouldBeFound("resourceTypeName.equals=" + DEFAULT_RESOURCE_TYPE_NAME);

        // Get all the resourceTypeList where resourceTypeName equals to UPDATED_RESOURCE_TYPE_NAME
        defaultResourceTypeShouldNotBeFound("resourceTypeName.equals=" + UPDATED_RESOURCE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllResourceTypesByResourceTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList where resourceTypeName in DEFAULT_RESOURCE_TYPE_NAME or UPDATED_RESOURCE_TYPE_NAME
        defaultResourceTypeShouldBeFound("resourceTypeName.in=" + DEFAULT_RESOURCE_TYPE_NAME + "," + UPDATED_RESOURCE_TYPE_NAME);

        // Get all the resourceTypeList where resourceTypeName equals to UPDATED_RESOURCE_TYPE_NAME
        defaultResourceTypeShouldNotBeFound("resourceTypeName.in=" + UPDATED_RESOURCE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllResourceTypesByResourceTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList where resourceTypeName is not null
        defaultResourceTypeShouldBeFound("resourceTypeName.specified=true");

        // Get all the resourceTypeList where resourceTypeName is null
        defaultResourceTypeShouldNotBeFound("resourceTypeName.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourceTypesByRentalDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList where rentalDuration equals to DEFAULT_RENTAL_DURATION
        defaultResourceTypeShouldBeFound("rentalDuration.equals=" + DEFAULT_RENTAL_DURATION);

        // Get all the resourceTypeList where rentalDuration equals to UPDATED_RENTAL_DURATION
        defaultResourceTypeShouldNotBeFound("rentalDuration.equals=" + UPDATED_RENTAL_DURATION);
    }

    @Test
    @Transactional
    public void getAllResourceTypesByRentalDurationIsInShouldWork() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList where rentalDuration in DEFAULT_RENTAL_DURATION or UPDATED_RENTAL_DURATION
        defaultResourceTypeShouldBeFound("rentalDuration.in=" + DEFAULT_RENTAL_DURATION + "," + UPDATED_RENTAL_DURATION);

        // Get all the resourceTypeList where rentalDuration equals to UPDATED_RENTAL_DURATION
        defaultResourceTypeShouldNotBeFound("rentalDuration.in=" + UPDATED_RENTAL_DURATION);
    }

    @Test
    @Transactional
    public void getAllResourceTypesByRentalDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList where rentalDuration is not null
        defaultResourceTypeShouldBeFound("rentalDuration.specified=true");

        // Get all the resourceTypeList where rentalDuration is null
        defaultResourceTypeShouldNotBeFound("rentalDuration.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourceTypesByRentalDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList where rentalDuration greater than or equals to DEFAULT_RENTAL_DURATION
        defaultResourceTypeShouldBeFound("rentalDuration.greaterOrEqualThan=" + DEFAULT_RENTAL_DURATION);

        // Get all the resourceTypeList where rentalDuration greater than or equals to UPDATED_RENTAL_DURATION
        defaultResourceTypeShouldNotBeFound("rentalDuration.greaterOrEqualThan=" + UPDATED_RENTAL_DURATION);
    }

    @Test
    @Transactional
    public void getAllResourceTypesByRentalDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList where rentalDuration less than or equals to DEFAULT_RENTAL_DURATION
        defaultResourceTypeShouldNotBeFound("rentalDuration.lessThan=" + DEFAULT_RENTAL_DURATION);

        // Get all the resourceTypeList where rentalDuration less than or equals to UPDATED_RENTAL_DURATION
        defaultResourceTypeShouldBeFound("rentalDuration.lessThan=" + UPDATED_RENTAL_DURATION);
    }


    @Test
    @Transactional
    public void getAllResourceTypesByOverdueChargeIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList where overdueCharge equals to DEFAULT_OVERDUE_CHARGE
        defaultResourceTypeShouldBeFound("overdueCharge.equals=" + DEFAULT_OVERDUE_CHARGE);

        // Get all the resourceTypeList where overdueCharge equals to UPDATED_OVERDUE_CHARGE
        defaultResourceTypeShouldNotBeFound("overdueCharge.equals=" + UPDATED_OVERDUE_CHARGE);
    }

    @Test
    @Transactional
    public void getAllResourceTypesByOverdueChargeIsInShouldWork() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList where overdueCharge in DEFAULT_OVERDUE_CHARGE or UPDATED_OVERDUE_CHARGE
        defaultResourceTypeShouldBeFound("overdueCharge.in=" + DEFAULT_OVERDUE_CHARGE + "," + UPDATED_OVERDUE_CHARGE);

        // Get all the resourceTypeList where overdueCharge equals to UPDATED_OVERDUE_CHARGE
        defaultResourceTypeShouldNotBeFound("overdueCharge.in=" + UPDATED_OVERDUE_CHARGE);
    }

    @Test
    @Transactional
    public void getAllResourceTypesByOverdueChargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList where overdueCharge is not null
        defaultResourceTypeShouldBeFound("overdueCharge.specified=true");

        // Get all the resourceTypeList where overdueCharge is null
        defaultResourceTypeShouldNotBeFound("overdueCharge.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourceTypesByLibraryResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        LibraryResource libraryResource = LibraryResourceResourceIntTest.createEntity(em);
        em.persist(libraryResource);
        em.flush();
        resourceType.addLibraryResource(libraryResource);
        resourceTypeRepository.saveAndFlush(resourceType);
        Long libraryResourceId = libraryResource.getId();

        // Get all the resourceTypeList where libraryResource equals to libraryResourceId
        defaultResourceTypeShouldBeFound("libraryResourceId.equals=" + libraryResourceId);

        // Get all the resourceTypeList where libraryResource equals to libraryResourceId + 1
        defaultResourceTypeShouldNotBeFound("libraryResourceId.equals=" + (libraryResourceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultResourceTypeShouldBeFound(String filter) throws Exception {
        restResourceTypeMockMvc.perform(get("/api/resource-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceTypeName").value(hasItem(DEFAULT_RESOURCE_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].rentalDuration").value(hasItem(DEFAULT_RENTAL_DURATION)))
            .andExpect(jsonPath("$.[*].overdueCharge").value(hasItem(DEFAULT_OVERDUE_CHARGE.doubleValue())));

        // Check, that the count call also returns 1
        restResourceTypeMockMvc.perform(get("/api/resource-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultResourceTypeShouldNotBeFound(String filter) throws Exception {
        restResourceTypeMockMvc.perform(get("/api/resource-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResourceTypeMockMvc.perform(get("/api/resource-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingResourceType() throws Exception {
        // Get the resourceType
        restResourceTypeMockMvc.perform(get("/api/resource-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResourceType() throws Exception {
        // Initialize the database
        resourceTypeService.save(resourceType);

        int databaseSizeBeforeUpdate = resourceTypeRepository.findAll().size();

        // Update the resourceType
        ResourceType updatedResourceType = resourceTypeRepository.findById(resourceType.getId()).get();
        // Disconnect from session so that the updates on updatedResourceType are not directly saved in db
        em.detach(updatedResourceType);
        updatedResourceType
            .resourceTypeName(UPDATED_RESOURCE_TYPE_NAME)
            .rentalDuration(UPDATED_RENTAL_DURATION)
            .overdueCharge(UPDATED_OVERDUE_CHARGE);

        restResourceTypeMockMvc.perform(put("/api/resource-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResourceType)))
            .andExpect(status().isOk());

        // Validate the ResourceType in the database
        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeUpdate);
        ResourceType testResourceType = resourceTypeList.get(resourceTypeList.size() - 1);
        assertThat(testResourceType.getResourceTypeName()).isEqualTo(UPDATED_RESOURCE_TYPE_NAME);
        assertThat(testResourceType.getRentalDuration()).isEqualTo(UPDATED_RENTAL_DURATION);
        assertThat(testResourceType.getOverdueCharge()).isEqualTo(UPDATED_OVERDUE_CHARGE);
    }

    @Test
    @Transactional
    public void updateNonExistingResourceType() throws Exception {
        int databaseSizeBeforeUpdate = resourceTypeRepository.findAll().size();

        // Create the ResourceType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceTypeMockMvc.perform(put("/api/resource-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceType)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceType in the database
        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResourceType() throws Exception {
        // Initialize the database
        resourceTypeService.save(resourceType);

        int databaseSizeBeforeDelete = resourceTypeRepository.findAll().size();

        // Get the resourceType
        restResourceTypeMockMvc.perform(delete("/api/resource-types/{id}", resourceType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceType.class);
        ResourceType resourceType1 = new ResourceType();
        resourceType1.setId(1L);
        ResourceType resourceType2 = new ResourceType();
        resourceType2.setId(resourceType1.getId());
        assertThat(resourceType1).isEqualTo(resourceType2);
        resourceType2.setId(2L);
        assertThat(resourceType1).isNotEqualTo(resourceType2);
        resourceType1.setId(null);
        assertThat(resourceType1).isNotEqualTo(resourceType2);
    }
}
