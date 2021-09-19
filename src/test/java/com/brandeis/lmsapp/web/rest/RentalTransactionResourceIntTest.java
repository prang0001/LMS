package com.brandeis.lmsapp.web.rest;

import com.brandeis.lmsapp.LmsApp;

import com.brandeis.lmsapp.domain.RentalTransaction;
import com.brandeis.lmsapp.domain.LibraryResource;
import com.brandeis.lmsapp.domain.Patron;
import com.brandeis.lmsapp.repository.RentalTransactionRepository;
import com.brandeis.lmsapp.service.RentalTransactionService;
import com.brandeis.lmsapp.web.rest.errors.ExceptionTranslator;
import com.brandeis.lmsapp.service.dto.RentalTransactionCriteria;
import com.brandeis.lmsapp.service.RentalTransactionQueryService;

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
 * Test class for the RentalTransactionResource REST controller.
 *
 * @see RentalTransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LmsApp.class)
public class RentalTransactionResourceIntTest {

    private static final Integer DEFAULT_RENTAL_PERIOD = 1;
    private static final Integer UPDATED_RENTAL_PERIOD = 2;

    private static final LocalDate DEFAULT_RENTAL_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RENTAL_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RENTAL_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RENTAL_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_EXTEND_RENTAL = false;
    private static final Boolean UPDATED_EXTEND_RENTAL = true;

    private static final LocalDate DEFAULT_RENTAL_FINAL_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RENTAL_FINAL_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_OVERDUE = false;
    private static final Boolean UPDATED_OVERDUE = true;

    private static final Integer DEFAULT_DAYS_OVERDUE = 1;
    private static final Integer UPDATED_DAYS_OVERDUE = 2;

    private static final Double DEFAULT_FINES_OVERDUE = 1D;
    private static final Double UPDATED_FINES_OVERDUE = 2D;

    @Autowired
    private RentalTransactionRepository rentalTransactionRepository;

    @Mock
    private RentalTransactionRepository rentalTransactionRepositoryMock;
    

    @Mock
    private RentalTransactionService rentalTransactionServiceMock;

    @Autowired
    private RentalTransactionService rentalTransactionService;

    @Autowired
    private RentalTransactionQueryService rentalTransactionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRentalTransactionMockMvc;

    private RentalTransaction rentalTransaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RentalTransactionResource rentalTransactionResource = new RentalTransactionResource(rentalTransactionService, rentalTransactionQueryService);
        this.restRentalTransactionMockMvc = MockMvcBuilders.standaloneSetup(rentalTransactionResource)
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
    public static RentalTransaction createEntity(EntityManager em) {
        RentalTransaction rentalTransaction = new RentalTransaction()
            .rentalPeriod(DEFAULT_RENTAL_PERIOD)
            .rentalStartDate(DEFAULT_RENTAL_START_DATE)
            .rentalDueDate(DEFAULT_RENTAL_DUE_DATE)
            .extendRental(DEFAULT_EXTEND_RENTAL)
            .rentalFinalDueDate(DEFAULT_RENTAL_FINAL_DUE_DATE)
            .overdue(DEFAULT_OVERDUE)
            .daysOverdue(DEFAULT_DAYS_OVERDUE)
            .finesOverdue(DEFAULT_FINES_OVERDUE);
        return rentalTransaction;
    }

    @Before
    public void initTest() {
        rentalTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createRentalTransaction() throws Exception {
        int databaseSizeBeforeCreate = rentalTransactionRepository.findAll().size();

        // Create the RentalTransaction
        restRentalTransactionMockMvc.perform(post("/api/rental-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalTransaction)))
            .andExpect(status().isCreated());

        // Validate the RentalTransaction in the database
        List<RentalTransaction> rentalTransactionList = rentalTransactionRepository.findAll();
        assertThat(rentalTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        RentalTransaction testRentalTransaction = rentalTransactionList.get(rentalTransactionList.size() - 1);
        assertThat(testRentalTransaction.getRentalPeriod()).isEqualTo(DEFAULT_RENTAL_PERIOD);
        assertThat(testRentalTransaction.getRentalStartDate()).isEqualTo(DEFAULT_RENTAL_START_DATE);
        assertThat(testRentalTransaction.getRentalDueDate()).isEqualTo(DEFAULT_RENTAL_DUE_DATE);
        assertThat(testRentalTransaction.isExtendRental()).isEqualTo(DEFAULT_EXTEND_RENTAL);
        assertThat(testRentalTransaction.getRentalFinalDueDate()).isEqualTo(DEFAULT_RENTAL_FINAL_DUE_DATE);
        assertThat(testRentalTransaction.isOverdue()).isEqualTo(DEFAULT_OVERDUE);
        assertThat(testRentalTransaction.getDaysOverdue()).isEqualTo(DEFAULT_DAYS_OVERDUE);
        assertThat(testRentalTransaction.getFinesOverdue()).isEqualTo(DEFAULT_FINES_OVERDUE);
    }

    @Test
    @Transactional
    public void createRentalTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rentalTransactionRepository.findAll().size();

        // Create the RentalTransaction with an existing ID
        rentalTransaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentalTransactionMockMvc.perform(post("/api/rental-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the RentalTransaction in the database
        List<RentalTransaction> rentalTransactionList = rentalTransactionRepository.findAll();
        assertThat(rentalTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRentalTransactions() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList
        restRentalTransactionMockMvc.perform(get("/api/rental-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rentalTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].rentalPeriod").value(hasItem(DEFAULT_RENTAL_PERIOD)))
            .andExpect(jsonPath("$.[*].rentalStartDate").value(hasItem(DEFAULT_RENTAL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].rentalDueDate").value(hasItem(DEFAULT_RENTAL_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].extendRental").value(hasItem(DEFAULT_EXTEND_RENTAL.booleanValue())))
            .andExpect(jsonPath("$.[*].rentalFinalDueDate").value(hasItem(DEFAULT_RENTAL_FINAL_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].overdue").value(hasItem(DEFAULT_OVERDUE.booleanValue())))
            .andExpect(jsonPath("$.[*].daysOverdue").value(hasItem(DEFAULT_DAYS_OVERDUE)))
            .andExpect(jsonPath("$.[*].finesOverdue").value(hasItem(DEFAULT_FINES_OVERDUE.doubleValue())));
    }
    
    public void getAllRentalTransactionsWithEagerRelationshipsIsEnabled() throws Exception {
        RentalTransactionResource rentalTransactionResource = new RentalTransactionResource(rentalTransactionServiceMock, rentalTransactionQueryService);
        when(rentalTransactionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRentalTransactionMockMvc = MockMvcBuilders.standaloneSetup(rentalTransactionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRentalTransactionMockMvc.perform(get("/api/rental-transactions?eagerload=true"))
        .andExpect(status().isOk());

        verify(rentalTransactionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllRentalTransactionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        RentalTransactionResource rentalTransactionResource = new RentalTransactionResource(rentalTransactionServiceMock, rentalTransactionQueryService);
            when(rentalTransactionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRentalTransactionMockMvc = MockMvcBuilders.standaloneSetup(rentalTransactionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRentalTransactionMockMvc.perform(get("/api/rental-transactions?eagerload=true"))
        .andExpect(status().isOk());

            verify(rentalTransactionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRentalTransaction() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get the rentalTransaction
        restRentalTransactionMockMvc.perform(get("/api/rental-transactions/{id}", rentalTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rentalTransaction.getId().intValue()))
            .andExpect(jsonPath("$.rentalPeriod").value(DEFAULT_RENTAL_PERIOD))
            .andExpect(jsonPath("$.rentalStartDate").value(DEFAULT_RENTAL_START_DATE.toString()))
            .andExpect(jsonPath("$.rentalDueDate").value(DEFAULT_RENTAL_DUE_DATE.toString()))
            .andExpect(jsonPath("$.extendRental").value(DEFAULT_EXTEND_RENTAL.booleanValue()))
            .andExpect(jsonPath("$.rentalFinalDueDate").value(DEFAULT_RENTAL_FINAL_DUE_DATE.toString()))
            .andExpect(jsonPath("$.overdue").value(DEFAULT_OVERDUE.booleanValue()))
            .andExpect(jsonPath("$.daysOverdue").value(DEFAULT_DAYS_OVERDUE))
            .andExpect(jsonPath("$.finesOverdue").value(DEFAULT_FINES_OVERDUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalPeriod equals to DEFAULT_RENTAL_PERIOD
        defaultRentalTransactionShouldBeFound("rentalPeriod.equals=" + DEFAULT_RENTAL_PERIOD);

        // Get all the rentalTransactionList where rentalPeriod equals to UPDATED_RENTAL_PERIOD
        defaultRentalTransactionShouldNotBeFound("rentalPeriod.equals=" + UPDATED_RENTAL_PERIOD);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalPeriod in DEFAULT_RENTAL_PERIOD or UPDATED_RENTAL_PERIOD
        defaultRentalTransactionShouldBeFound("rentalPeriod.in=" + DEFAULT_RENTAL_PERIOD + "," + UPDATED_RENTAL_PERIOD);

        // Get all the rentalTransactionList where rentalPeriod equals to UPDATED_RENTAL_PERIOD
        defaultRentalTransactionShouldNotBeFound("rentalPeriod.in=" + UPDATED_RENTAL_PERIOD);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalPeriod is not null
        defaultRentalTransactionShouldBeFound("rentalPeriod.specified=true");

        // Get all the rentalTransactionList where rentalPeriod is null
        defaultRentalTransactionShouldNotBeFound("rentalPeriod.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalPeriodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalPeriod greater than or equals to DEFAULT_RENTAL_PERIOD
        defaultRentalTransactionShouldBeFound("rentalPeriod.greaterOrEqualThan=" + DEFAULT_RENTAL_PERIOD);

        // Get all the rentalTransactionList where rentalPeriod greater than or equals to UPDATED_RENTAL_PERIOD
        defaultRentalTransactionShouldNotBeFound("rentalPeriod.greaterOrEqualThan=" + UPDATED_RENTAL_PERIOD);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalPeriodIsLessThanSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalPeriod less than or equals to DEFAULT_RENTAL_PERIOD
        defaultRentalTransactionShouldNotBeFound("rentalPeriod.lessThan=" + DEFAULT_RENTAL_PERIOD);

        // Get all the rentalTransactionList where rentalPeriod less than or equals to UPDATED_RENTAL_PERIOD
        defaultRentalTransactionShouldBeFound("rentalPeriod.lessThan=" + UPDATED_RENTAL_PERIOD);
    }


    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalStartDate equals to DEFAULT_RENTAL_START_DATE
        defaultRentalTransactionShouldBeFound("rentalStartDate.equals=" + DEFAULT_RENTAL_START_DATE);

        // Get all the rentalTransactionList where rentalStartDate equals to UPDATED_RENTAL_START_DATE
        defaultRentalTransactionShouldNotBeFound("rentalStartDate.equals=" + UPDATED_RENTAL_START_DATE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalStartDate in DEFAULT_RENTAL_START_DATE or UPDATED_RENTAL_START_DATE
        defaultRentalTransactionShouldBeFound("rentalStartDate.in=" + DEFAULT_RENTAL_START_DATE + "," + UPDATED_RENTAL_START_DATE);

        // Get all the rentalTransactionList where rentalStartDate equals to UPDATED_RENTAL_START_DATE
        defaultRentalTransactionShouldNotBeFound("rentalStartDate.in=" + UPDATED_RENTAL_START_DATE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalStartDate is not null
        defaultRentalTransactionShouldBeFound("rentalStartDate.specified=true");

        // Get all the rentalTransactionList where rentalStartDate is null
        defaultRentalTransactionShouldNotBeFound("rentalStartDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalStartDate greater than or equals to DEFAULT_RENTAL_START_DATE
        defaultRentalTransactionShouldBeFound("rentalStartDate.greaterOrEqualThan=" + DEFAULT_RENTAL_START_DATE);

        // Get all the rentalTransactionList where rentalStartDate greater than or equals to UPDATED_RENTAL_START_DATE
        defaultRentalTransactionShouldNotBeFound("rentalStartDate.greaterOrEqualThan=" + UPDATED_RENTAL_START_DATE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalStartDate less than or equals to DEFAULT_RENTAL_START_DATE
        defaultRentalTransactionShouldNotBeFound("rentalStartDate.lessThan=" + DEFAULT_RENTAL_START_DATE);

        // Get all the rentalTransactionList where rentalStartDate less than or equals to UPDATED_RENTAL_START_DATE
        defaultRentalTransactionShouldBeFound("rentalStartDate.lessThan=" + UPDATED_RENTAL_START_DATE);
    }


    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalDueDate equals to DEFAULT_RENTAL_DUE_DATE
        defaultRentalTransactionShouldBeFound("rentalDueDate.equals=" + DEFAULT_RENTAL_DUE_DATE);

        // Get all the rentalTransactionList where rentalDueDate equals to UPDATED_RENTAL_DUE_DATE
        defaultRentalTransactionShouldNotBeFound("rentalDueDate.equals=" + UPDATED_RENTAL_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalDueDate in DEFAULT_RENTAL_DUE_DATE or UPDATED_RENTAL_DUE_DATE
        defaultRentalTransactionShouldBeFound("rentalDueDate.in=" + DEFAULT_RENTAL_DUE_DATE + "," + UPDATED_RENTAL_DUE_DATE);

        // Get all the rentalTransactionList where rentalDueDate equals to UPDATED_RENTAL_DUE_DATE
        defaultRentalTransactionShouldNotBeFound("rentalDueDate.in=" + UPDATED_RENTAL_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalDueDate is not null
        defaultRentalTransactionShouldBeFound("rentalDueDate.specified=true");

        // Get all the rentalTransactionList where rentalDueDate is null
        defaultRentalTransactionShouldNotBeFound("rentalDueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalDueDate greater than or equals to DEFAULT_RENTAL_DUE_DATE
        defaultRentalTransactionShouldBeFound("rentalDueDate.greaterOrEqualThan=" + DEFAULT_RENTAL_DUE_DATE);

        // Get all the rentalTransactionList where rentalDueDate greater than or equals to UPDATED_RENTAL_DUE_DATE
        defaultRentalTransactionShouldNotBeFound("rentalDueDate.greaterOrEqualThan=" + UPDATED_RENTAL_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalDueDate less than or equals to DEFAULT_RENTAL_DUE_DATE
        defaultRentalTransactionShouldNotBeFound("rentalDueDate.lessThan=" + DEFAULT_RENTAL_DUE_DATE);

        // Get all the rentalTransactionList where rentalDueDate less than or equals to UPDATED_RENTAL_DUE_DATE
        defaultRentalTransactionShouldBeFound("rentalDueDate.lessThan=" + UPDATED_RENTAL_DUE_DATE);
    }


    @Test
    @Transactional
    public void getAllRentalTransactionsByExtendRentalIsEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where extendRental equals to DEFAULT_EXTEND_RENTAL
        defaultRentalTransactionShouldBeFound("extendRental.equals=" + DEFAULT_EXTEND_RENTAL);

        // Get all the rentalTransactionList where extendRental equals to UPDATED_EXTEND_RENTAL
        defaultRentalTransactionShouldNotBeFound("extendRental.equals=" + UPDATED_EXTEND_RENTAL);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByExtendRentalIsInShouldWork() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where extendRental in DEFAULT_EXTEND_RENTAL or UPDATED_EXTEND_RENTAL
        defaultRentalTransactionShouldBeFound("extendRental.in=" + DEFAULT_EXTEND_RENTAL + "," + UPDATED_EXTEND_RENTAL);

        // Get all the rentalTransactionList where extendRental equals to UPDATED_EXTEND_RENTAL
        defaultRentalTransactionShouldNotBeFound("extendRental.in=" + UPDATED_EXTEND_RENTAL);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByExtendRentalIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where extendRental is not null
        defaultRentalTransactionShouldBeFound("extendRental.specified=true");

        // Get all the rentalTransactionList where extendRental is null
        defaultRentalTransactionShouldNotBeFound("extendRental.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalFinalDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalFinalDueDate equals to DEFAULT_RENTAL_FINAL_DUE_DATE
        defaultRentalTransactionShouldBeFound("rentalFinalDueDate.equals=" + DEFAULT_RENTAL_FINAL_DUE_DATE);

        // Get all the rentalTransactionList where rentalFinalDueDate equals to UPDATED_RENTAL_FINAL_DUE_DATE
        defaultRentalTransactionShouldNotBeFound("rentalFinalDueDate.equals=" + UPDATED_RENTAL_FINAL_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalFinalDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalFinalDueDate in DEFAULT_RENTAL_FINAL_DUE_DATE or UPDATED_RENTAL_FINAL_DUE_DATE
        defaultRentalTransactionShouldBeFound("rentalFinalDueDate.in=" + DEFAULT_RENTAL_FINAL_DUE_DATE + "," + UPDATED_RENTAL_FINAL_DUE_DATE);

        // Get all the rentalTransactionList where rentalFinalDueDate equals to UPDATED_RENTAL_FINAL_DUE_DATE
        defaultRentalTransactionShouldNotBeFound("rentalFinalDueDate.in=" + UPDATED_RENTAL_FINAL_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalFinalDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalFinalDueDate is not null
        defaultRentalTransactionShouldBeFound("rentalFinalDueDate.specified=true");

        // Get all the rentalTransactionList where rentalFinalDueDate is null
        defaultRentalTransactionShouldNotBeFound("rentalFinalDueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalFinalDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalFinalDueDate greater than or equals to DEFAULT_RENTAL_FINAL_DUE_DATE
        defaultRentalTransactionShouldBeFound("rentalFinalDueDate.greaterOrEqualThan=" + DEFAULT_RENTAL_FINAL_DUE_DATE);

        // Get all the rentalTransactionList where rentalFinalDueDate greater than or equals to UPDATED_RENTAL_FINAL_DUE_DATE
        defaultRentalTransactionShouldNotBeFound("rentalFinalDueDate.greaterOrEqualThan=" + UPDATED_RENTAL_FINAL_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByRentalFinalDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where rentalFinalDueDate less than or equals to DEFAULT_RENTAL_FINAL_DUE_DATE
        defaultRentalTransactionShouldNotBeFound("rentalFinalDueDate.lessThan=" + DEFAULT_RENTAL_FINAL_DUE_DATE);

        // Get all the rentalTransactionList where rentalFinalDueDate less than or equals to UPDATED_RENTAL_FINAL_DUE_DATE
        defaultRentalTransactionShouldBeFound("rentalFinalDueDate.lessThan=" + UPDATED_RENTAL_FINAL_DUE_DATE);
    }


    @Test
    @Transactional
    public void getAllRentalTransactionsByOverdueIsEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where overdue equals to DEFAULT_OVERDUE
        defaultRentalTransactionShouldBeFound("overdue.equals=" + DEFAULT_OVERDUE);

        // Get all the rentalTransactionList where overdue equals to UPDATED_OVERDUE
        defaultRentalTransactionShouldNotBeFound("overdue.equals=" + UPDATED_OVERDUE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByOverdueIsInShouldWork() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where overdue in DEFAULT_OVERDUE or UPDATED_OVERDUE
        defaultRentalTransactionShouldBeFound("overdue.in=" + DEFAULT_OVERDUE + "," + UPDATED_OVERDUE);

        // Get all the rentalTransactionList where overdue equals to UPDATED_OVERDUE
        defaultRentalTransactionShouldNotBeFound("overdue.in=" + UPDATED_OVERDUE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByOverdueIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where overdue is not null
        defaultRentalTransactionShouldBeFound("overdue.specified=true");

        // Get all the rentalTransactionList where overdue is null
        defaultRentalTransactionShouldNotBeFound("overdue.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByDaysOverdueIsEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where daysOverdue equals to DEFAULT_DAYS_OVERDUE
        defaultRentalTransactionShouldBeFound("daysOverdue.equals=" + DEFAULT_DAYS_OVERDUE);

        // Get all the rentalTransactionList where daysOverdue equals to UPDATED_DAYS_OVERDUE
        defaultRentalTransactionShouldNotBeFound("daysOverdue.equals=" + UPDATED_DAYS_OVERDUE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByDaysOverdueIsInShouldWork() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where daysOverdue in DEFAULT_DAYS_OVERDUE or UPDATED_DAYS_OVERDUE
        defaultRentalTransactionShouldBeFound("daysOverdue.in=" + DEFAULT_DAYS_OVERDUE + "," + UPDATED_DAYS_OVERDUE);

        // Get all the rentalTransactionList where daysOverdue equals to UPDATED_DAYS_OVERDUE
        defaultRentalTransactionShouldNotBeFound("daysOverdue.in=" + UPDATED_DAYS_OVERDUE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByDaysOverdueIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where daysOverdue is not null
        defaultRentalTransactionShouldBeFound("daysOverdue.specified=true");

        // Get all the rentalTransactionList where daysOverdue is null
        defaultRentalTransactionShouldNotBeFound("daysOverdue.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByDaysOverdueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where daysOverdue greater than or equals to DEFAULT_DAYS_OVERDUE
        defaultRentalTransactionShouldBeFound("daysOverdue.greaterOrEqualThan=" + DEFAULT_DAYS_OVERDUE);

        // Get all the rentalTransactionList where daysOverdue greater than or equals to UPDATED_DAYS_OVERDUE
        defaultRentalTransactionShouldNotBeFound("daysOverdue.greaterOrEqualThan=" + UPDATED_DAYS_OVERDUE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByDaysOverdueIsLessThanSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where daysOverdue less than or equals to DEFAULT_DAYS_OVERDUE
        defaultRentalTransactionShouldNotBeFound("daysOverdue.lessThan=" + DEFAULT_DAYS_OVERDUE);

        // Get all the rentalTransactionList where daysOverdue less than or equals to UPDATED_DAYS_OVERDUE
        defaultRentalTransactionShouldBeFound("daysOverdue.lessThan=" + UPDATED_DAYS_OVERDUE);
    }


    @Test
    @Transactional
    public void getAllRentalTransactionsByFinesOverdueIsEqualToSomething() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where finesOverdue equals to DEFAULT_FINES_OVERDUE
        defaultRentalTransactionShouldBeFound("finesOverdue.equals=" + DEFAULT_FINES_OVERDUE);

        // Get all the rentalTransactionList where finesOverdue equals to UPDATED_FINES_OVERDUE
        defaultRentalTransactionShouldNotBeFound("finesOverdue.equals=" + UPDATED_FINES_OVERDUE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByFinesOverdueIsInShouldWork() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where finesOverdue in DEFAULT_FINES_OVERDUE or UPDATED_FINES_OVERDUE
        defaultRentalTransactionShouldBeFound("finesOverdue.in=" + DEFAULT_FINES_OVERDUE + "," + UPDATED_FINES_OVERDUE);

        // Get all the rentalTransactionList where finesOverdue equals to UPDATED_FINES_OVERDUE
        defaultRentalTransactionShouldNotBeFound("finesOverdue.in=" + UPDATED_FINES_OVERDUE);
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByFinesOverdueIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentalTransactionRepository.saveAndFlush(rentalTransaction);

        // Get all the rentalTransactionList where finesOverdue is not null
        defaultRentalTransactionShouldBeFound("finesOverdue.specified=true");

        // Get all the rentalTransactionList where finesOverdue is null
        defaultRentalTransactionShouldNotBeFound("finesOverdue.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentalTransactionsByLibraryResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        LibraryResource libraryResource = LibraryResourceResourceIntTest.createEntity(em);
        em.persist(libraryResource);
        em.flush();
        rentalTransaction.addLibraryResource(libraryResource);
        rentalTransactionRepository.saveAndFlush(rentalTransaction);
        Long libraryResourceId = libraryResource.getId();

        // Get all the rentalTransactionList where libraryResource equals to libraryResourceId
        defaultRentalTransactionShouldBeFound("libraryResourceId.equals=" + libraryResourceId);

        // Get all the rentalTransactionList where libraryResource equals to libraryResourceId + 1
        defaultRentalTransactionShouldNotBeFound("libraryResourceId.equals=" + (libraryResourceId + 1));
    }


    @Test
    @Transactional
    public void getAllRentalTransactionsByPatronIsEqualToSomething() throws Exception {
        // Initialize the database
        Patron patron = PatronResourceIntTest.createEntity(em);
        em.persist(patron);
        em.flush();
        rentalTransaction.addPatron(patron);
        rentalTransactionRepository.saveAndFlush(rentalTransaction);
        Long patronId = patron.getId();

        // Get all the rentalTransactionList where patron equals to patronId
        defaultRentalTransactionShouldBeFound("patronId.equals=" + patronId);

        // Get all the rentalTransactionList where patron equals to patronId + 1
        defaultRentalTransactionShouldNotBeFound("patronId.equals=" + (patronId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRentalTransactionShouldBeFound(String filter) throws Exception {
        restRentalTransactionMockMvc.perform(get("/api/rental-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rentalTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].rentalPeriod").value(hasItem(DEFAULT_RENTAL_PERIOD)))
            .andExpect(jsonPath("$.[*].rentalStartDate").value(hasItem(DEFAULT_RENTAL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].rentalDueDate").value(hasItem(DEFAULT_RENTAL_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].extendRental").value(hasItem(DEFAULT_EXTEND_RENTAL.booleanValue())))
            .andExpect(jsonPath("$.[*].rentalFinalDueDate").value(hasItem(DEFAULT_RENTAL_FINAL_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].overdue").value(hasItem(DEFAULT_OVERDUE.booleanValue())))
            .andExpect(jsonPath("$.[*].daysOverdue").value(hasItem(DEFAULT_DAYS_OVERDUE)))
            .andExpect(jsonPath("$.[*].finesOverdue").value(hasItem(DEFAULT_FINES_OVERDUE.doubleValue())));

        // Check, that the count call also returns 1
        restRentalTransactionMockMvc.perform(get("/api/rental-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRentalTransactionShouldNotBeFound(String filter) throws Exception {
        restRentalTransactionMockMvc.perform(get("/api/rental-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRentalTransactionMockMvc.perform(get("/api/rental-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRentalTransaction() throws Exception {
        // Get the rentalTransaction
        restRentalTransactionMockMvc.perform(get("/api/rental-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRentalTransaction() throws Exception {
        // Initialize the database
        rentalTransactionService.save(rentalTransaction);

        int databaseSizeBeforeUpdate = rentalTransactionRepository.findAll().size();

        // Update the rentalTransaction
        RentalTransaction updatedRentalTransaction = rentalTransactionRepository.findById(rentalTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedRentalTransaction are not directly saved in db
        em.detach(updatedRentalTransaction);
        updatedRentalTransaction
            .rentalPeriod(UPDATED_RENTAL_PERIOD)
            .rentalStartDate(UPDATED_RENTAL_START_DATE)
            .rentalDueDate(UPDATED_RENTAL_DUE_DATE)
            .extendRental(UPDATED_EXTEND_RENTAL)
            .rentalFinalDueDate(UPDATED_RENTAL_FINAL_DUE_DATE)
            .overdue(UPDATED_OVERDUE)
            .daysOverdue(UPDATED_DAYS_OVERDUE)
            .finesOverdue(UPDATED_FINES_OVERDUE);

        restRentalTransactionMockMvc.perform(put("/api/rental-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRentalTransaction)))
            .andExpect(status().isOk());

        // Validate the RentalTransaction in the database
        List<RentalTransaction> rentalTransactionList = rentalTransactionRepository.findAll();
        assertThat(rentalTransactionList).hasSize(databaseSizeBeforeUpdate);
        RentalTransaction testRentalTransaction = rentalTransactionList.get(rentalTransactionList.size() - 1);
        assertThat(testRentalTransaction.getRentalPeriod()).isEqualTo(UPDATED_RENTAL_PERIOD);
        assertThat(testRentalTransaction.getRentalStartDate()).isEqualTo(UPDATED_RENTAL_START_DATE);
        assertThat(testRentalTransaction.getRentalDueDate()).isEqualTo(UPDATED_RENTAL_DUE_DATE);
        assertThat(testRentalTransaction.isExtendRental()).isEqualTo(UPDATED_EXTEND_RENTAL);
        assertThat(testRentalTransaction.getRentalFinalDueDate()).isEqualTo(UPDATED_RENTAL_FINAL_DUE_DATE);
        assertThat(testRentalTransaction.isOverdue()).isEqualTo(UPDATED_OVERDUE);
        assertThat(testRentalTransaction.getDaysOverdue()).isEqualTo(UPDATED_DAYS_OVERDUE);
        assertThat(testRentalTransaction.getFinesOverdue()).isEqualTo(UPDATED_FINES_OVERDUE);
    }

    @Test
    @Transactional
    public void updateNonExistingRentalTransaction() throws Exception {
        int databaseSizeBeforeUpdate = rentalTransactionRepository.findAll().size();

        // Create the RentalTransaction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentalTransactionMockMvc.perform(put("/api/rental-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the RentalTransaction in the database
        List<RentalTransaction> rentalTransactionList = rentalTransactionRepository.findAll();
        assertThat(rentalTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRentalTransaction() throws Exception {
        // Initialize the database
        rentalTransactionService.save(rentalTransaction);

        int databaseSizeBeforeDelete = rentalTransactionRepository.findAll().size();

        // Get the rentalTransaction
        restRentalTransactionMockMvc.perform(delete("/api/rental-transactions/{id}", rentalTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RentalTransaction> rentalTransactionList = rentalTransactionRepository.findAll();
        assertThat(rentalTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentalTransaction.class);
        RentalTransaction rentalTransaction1 = new RentalTransaction();
        rentalTransaction1.setId(1L);
        RentalTransaction rentalTransaction2 = new RentalTransaction();
        rentalTransaction2.setId(rentalTransaction1.getId());
        assertThat(rentalTransaction1).isEqualTo(rentalTransaction2);
        rentalTransaction2.setId(2L);
        assertThat(rentalTransaction1).isNotEqualTo(rentalTransaction2);
        rentalTransaction1.setId(null);
        assertThat(rentalTransaction1).isNotEqualTo(rentalTransaction2);
    }
}
