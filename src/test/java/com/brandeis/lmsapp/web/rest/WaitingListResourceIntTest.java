package com.brandeis.lmsapp.web.rest;

import com.brandeis.lmsapp.LmsApp;

import com.brandeis.lmsapp.domain.WaitingList;
import com.brandeis.lmsapp.domain.LibraryResource;
import com.brandeis.lmsapp.domain.Patron;
import com.brandeis.lmsapp.repository.WaitingListRepository;
import com.brandeis.lmsapp.service.WaitingListService;
import com.brandeis.lmsapp.web.rest.errors.ExceptionTranslator;
import com.brandeis.lmsapp.service.dto.WaitingListCriteria;
import com.brandeis.lmsapp.service.WaitingListQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static com.brandeis.lmsapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WaitingListResource REST controller.
 *
 * @see WaitingListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LmsApp.class)
public class WaitingListResourceIntTest {

    private static final LocalDate DEFAULT_DATE_REQUEST = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REQUEST = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_REQUESTED = false;
    private static final Boolean UPDATED_REQUESTED = true;

    @Autowired
    private WaitingListRepository waitingListRepository;

    @Mock
    private WaitingListRepository waitingListRepositoryMock;
    

    @Mock
    private WaitingListService waitingListServiceMock;

    @Autowired
    private WaitingListService waitingListService;

    @Autowired
    private WaitingListQueryService waitingListQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWaitingListMockMvc;

    private WaitingList waitingList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WaitingListResource waitingListResource = new WaitingListResource(waitingListService, waitingListQueryService);
        this.restWaitingListMockMvc = MockMvcBuilders.standaloneSetup(waitingListResource)
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
    public static WaitingList createEntity(EntityManager em) {
        WaitingList waitingList = new WaitingList()
            .dateRequest(DEFAULT_DATE_REQUEST)
            .requested(DEFAULT_REQUESTED);
        return waitingList;
    }

    @Before
    public void initTest() {
        waitingList = createEntity(em);
    }

    @Test
    @Transactional
    public void createWaitingList() throws Exception {
        int databaseSizeBeforeCreate = waitingListRepository.findAll().size();

        // Create the WaitingList
        restWaitingListMockMvc.perform(post("/api/waiting-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(waitingList)))
            .andExpect(status().isCreated());

        // Validate the WaitingList in the database
        List<WaitingList> waitingListList = waitingListRepository.findAll();
        assertThat(waitingListList).hasSize(databaseSizeBeforeCreate + 1);
        WaitingList testWaitingList = waitingListList.get(waitingListList.size() - 1);
        assertThat(testWaitingList.getDateRequest()).isEqualTo(DEFAULT_DATE_REQUEST);
        assertThat(testWaitingList.isRequested()).isEqualTo(DEFAULT_REQUESTED);
    }

    @Test
    @Transactional
    public void createWaitingListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = waitingListRepository.findAll().size();

        // Create the WaitingList with an existing ID
        waitingList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWaitingListMockMvc.perform(post("/api/waiting-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(waitingList)))
            .andExpect(status().isBadRequest());

        // Validate the WaitingList in the database
        List<WaitingList> waitingListList = waitingListRepository.findAll();
        assertThat(waitingListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWaitingLists() throws Exception {
        // Initialize the database
        waitingListRepository.saveAndFlush(waitingList);

        // Get all the waitingListList
        restWaitingListMockMvc.perform(get("/api/waiting-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(waitingList.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateRequest").value(hasItem(DEFAULT_DATE_REQUEST.toString())))
            .andExpect(jsonPath("$.[*].requested").value(hasItem(DEFAULT_REQUESTED.booleanValue())));
    }
    
    public void getAllWaitingListsWithEagerRelationshipsIsEnabled() throws Exception {
        WaitingListResource waitingListResource = new WaitingListResource(waitingListServiceMock, waitingListQueryService);
        when(waitingListServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restWaitingListMockMvc = MockMvcBuilders.standaloneSetup(waitingListResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restWaitingListMockMvc.perform(get("/api/waiting-lists?eagerload=true"))
        .andExpect(status().isOk());

        verify(waitingListServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllWaitingListsWithEagerRelationshipsIsNotEnabled() throws Exception {
        WaitingListResource waitingListResource = new WaitingListResource(waitingListServiceMock, waitingListQueryService);
            when(waitingListServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restWaitingListMockMvc = MockMvcBuilders.standaloneSetup(waitingListResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restWaitingListMockMvc.perform(get("/api/waiting-lists?eagerload=true"))
        .andExpect(status().isOk());

            verify(waitingListServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getWaitingList() throws Exception {
        // Initialize the database
        waitingListRepository.saveAndFlush(waitingList);

        // Get the waitingList
        restWaitingListMockMvc.perform(get("/api/waiting-lists/{id}", waitingList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(waitingList.getId().intValue()))
            .andExpect(jsonPath("$.dateRequest").value(DEFAULT_DATE_REQUEST.toString()))
            .andExpect(jsonPath("$.requested").value(DEFAULT_REQUESTED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllWaitingListsByDateRequestIsEqualToSomething() throws Exception {
        // Initialize the database
        waitingListRepository.saveAndFlush(waitingList);

        // Get all the waitingListList where dateRequest equals to DEFAULT_DATE_REQUEST
        defaultWaitingListShouldBeFound("dateRequest.equals=" + DEFAULT_DATE_REQUEST);

        // Get all the waitingListList where dateRequest equals to UPDATED_DATE_REQUEST
        defaultWaitingListShouldNotBeFound("dateRequest.equals=" + UPDATED_DATE_REQUEST);
    }

    @Test
    @Transactional
    public void getAllWaitingListsByDateRequestIsInShouldWork() throws Exception {
        // Initialize the database
        waitingListRepository.saveAndFlush(waitingList);

        // Get all the waitingListList where dateRequest in DEFAULT_DATE_REQUEST or UPDATED_DATE_REQUEST
        defaultWaitingListShouldBeFound("dateRequest.in=" + DEFAULT_DATE_REQUEST + "," + UPDATED_DATE_REQUEST);

        // Get all the waitingListList where dateRequest equals to UPDATED_DATE_REQUEST
        defaultWaitingListShouldNotBeFound("dateRequest.in=" + UPDATED_DATE_REQUEST);
    }

    @Test
    @Transactional
    public void getAllWaitingListsByDateRequestIsNullOrNotNull() throws Exception {
        // Initialize the database
        waitingListRepository.saveAndFlush(waitingList);

        // Get all the waitingListList where dateRequest is not null
        defaultWaitingListShouldBeFound("dateRequest.specified=true");

        // Get all the waitingListList where dateRequest is null
        defaultWaitingListShouldNotBeFound("dateRequest.specified=false");
    }

    @Test
    @Transactional
    public void getAllWaitingListsByDateRequestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        waitingListRepository.saveAndFlush(waitingList);

        // Get all the waitingListList where dateRequest greater than or equals to DEFAULT_DATE_REQUEST
        defaultWaitingListShouldBeFound("dateRequest.greaterOrEqualThan=" + DEFAULT_DATE_REQUEST);

        // Get all the waitingListList where dateRequest greater than or equals to UPDATED_DATE_REQUEST
        defaultWaitingListShouldNotBeFound("dateRequest.greaterOrEqualThan=" + UPDATED_DATE_REQUEST);
    }

    @Test
    @Transactional
    public void getAllWaitingListsByDateRequestIsLessThanSomething() throws Exception {
        // Initialize the database
        waitingListRepository.saveAndFlush(waitingList);

        // Get all the waitingListList where dateRequest less than or equals to DEFAULT_DATE_REQUEST
        defaultWaitingListShouldNotBeFound("dateRequest.lessThan=" + DEFAULT_DATE_REQUEST);

        // Get all the waitingListList where dateRequest less than or equals to UPDATED_DATE_REQUEST
        defaultWaitingListShouldBeFound("dateRequest.lessThan=" + UPDATED_DATE_REQUEST);
    }


    @Test
    @Transactional
    public void getAllWaitingListsByRequestedIsEqualToSomething() throws Exception {
        // Initialize the database
        waitingListRepository.saveAndFlush(waitingList);

        // Get all the waitingListList where requested equals to DEFAULT_REQUESTED
        defaultWaitingListShouldBeFound("requested.equals=" + DEFAULT_REQUESTED);

        // Get all the waitingListList where requested equals to UPDATED_REQUESTED
        defaultWaitingListShouldNotBeFound("requested.equals=" + UPDATED_REQUESTED);
    }

    @Test
    @Transactional
    public void getAllWaitingListsByRequestedIsInShouldWork() throws Exception {
        // Initialize the database
        waitingListRepository.saveAndFlush(waitingList);

        // Get all the waitingListList where requested in DEFAULT_REQUESTED or UPDATED_REQUESTED
        defaultWaitingListShouldBeFound("requested.in=" + DEFAULT_REQUESTED + "," + UPDATED_REQUESTED);

        // Get all the waitingListList where requested equals to UPDATED_REQUESTED
        defaultWaitingListShouldNotBeFound("requested.in=" + UPDATED_REQUESTED);
    }

    @Test
    @Transactional
    public void getAllWaitingListsByRequestedIsNullOrNotNull() throws Exception {
        // Initialize the database
        waitingListRepository.saveAndFlush(waitingList);

        // Get all the waitingListList where requested is not null
        defaultWaitingListShouldBeFound("requested.specified=true");

        // Get all the waitingListList where requested is null
        defaultWaitingListShouldNotBeFound("requested.specified=false");
    }

    @Test
    @Transactional
    public void getAllWaitingListsByLibraryResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        LibraryResource libraryResource = LibraryResourceResourceIntTest.createEntity(em);
        em.persist(libraryResource);
        em.flush();
        waitingList.addLibraryResource(libraryResource);
        waitingListRepository.saveAndFlush(waitingList);
        Long libraryResourceId = libraryResource.getId();

        // Get all the waitingListList where libraryResource equals to libraryResourceId
        defaultWaitingListShouldBeFound("libraryResourceId.equals=" + libraryResourceId);

        // Get all the waitingListList where libraryResource equals to libraryResourceId + 1
        defaultWaitingListShouldNotBeFound("libraryResourceId.equals=" + (libraryResourceId + 1));
    }


    @Test
    @Transactional
    public void getAllWaitingListsByPatronIsEqualToSomething() throws Exception {
        // Initialize the database
        Patron patron = PatronResourceIntTest.createEntity(em);
        em.persist(patron);
        em.flush();
        waitingList.addPatron(patron);
        waitingListRepository.saveAndFlush(waitingList);
        Long patronId = patron.getId();

        // Get all the waitingListList where patron equals to patronId
        defaultWaitingListShouldBeFound("patronId.equals=" + patronId);

        // Get all the waitingListList where patron equals to patronId + 1
        defaultWaitingListShouldNotBeFound("patronId.equals=" + (patronId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultWaitingListShouldBeFound(String filter) throws Exception {
        restWaitingListMockMvc.perform(get("/api/waiting-lists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(waitingList.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateRequest").value(hasItem(DEFAULT_DATE_REQUEST.toString())))
            .andExpect(jsonPath("$.[*].requested").value(hasItem(DEFAULT_REQUESTED.booleanValue())));

        // Check, that the count call also returns 1
        restWaitingListMockMvc.perform(get("/api/waiting-lists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultWaitingListShouldNotBeFound(String filter) throws Exception {
        restWaitingListMockMvc.perform(get("/api/waiting-lists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWaitingListMockMvc.perform(get("/api/waiting-lists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingWaitingList() throws Exception {
        // Get the waitingList
        restWaitingListMockMvc.perform(get("/api/waiting-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWaitingList() throws Exception {
        // Initialize the database
        waitingListService.save(waitingList);

        int databaseSizeBeforeUpdate = waitingListRepository.findAll().size();

        // Update the waitingList
        WaitingList updatedWaitingList = waitingListRepository.findById(waitingList.getId()).get();
        // Disconnect from session so that the updates on updatedWaitingList are not directly saved in db
        em.detach(updatedWaitingList);
        updatedWaitingList
            .dateRequest(UPDATED_DATE_REQUEST)
            .requested(UPDATED_REQUESTED);

        restWaitingListMockMvc.perform(put("/api/waiting-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWaitingList)))
            .andExpect(status().isOk());

        // Validate the WaitingList in the database
        List<WaitingList> waitingListList = waitingListRepository.findAll();
        assertThat(waitingListList).hasSize(databaseSizeBeforeUpdate);
        WaitingList testWaitingList = waitingListList.get(waitingListList.size() - 1);
        assertThat(testWaitingList.getDateRequest()).isEqualTo(UPDATED_DATE_REQUEST);
        assertThat(testWaitingList.isRequested()).isEqualTo(UPDATED_REQUESTED);
    }

    @Test
    @Transactional
    public void updateNonExistingWaitingList() throws Exception {
        int databaseSizeBeforeUpdate = waitingListRepository.findAll().size();

        // Create the WaitingList

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWaitingListMockMvc.perform(put("/api/waiting-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(waitingList)))
            .andExpect(status().isBadRequest());

        // Validate the WaitingList in the database
        List<WaitingList> waitingListList = waitingListRepository.findAll();
        assertThat(waitingListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWaitingList() throws Exception {
        // Initialize the database
        waitingListService.save(waitingList);

        int databaseSizeBeforeDelete = waitingListRepository.findAll().size();

        // Get the waitingList
        restWaitingListMockMvc.perform(delete("/api/waiting-lists/{id}", waitingList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WaitingList> waitingListList = waitingListRepository.findAll();
        assertThat(waitingListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WaitingList.class);
        WaitingList waitingList1 = new WaitingList();
        waitingList1.setId(1L);
        WaitingList waitingList2 = new WaitingList();
        waitingList2.setId(waitingList1.getId());
        assertThat(waitingList1).isEqualTo(waitingList2);
        waitingList2.setId(2L);
        assertThat(waitingList1).isNotEqualTo(waitingList2);
        waitingList1.setId(null);
        assertThat(waitingList1).isNotEqualTo(waitingList2);
    }
}
