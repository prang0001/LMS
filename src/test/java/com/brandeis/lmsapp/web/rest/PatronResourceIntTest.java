package com.brandeis.lmsapp.web.rest;

import com.brandeis.lmsapp.LmsApp;

import com.brandeis.lmsapp.domain.Patron;
import com.brandeis.lmsapp.repository.PatronRepository;
import com.brandeis.lmsapp.service.PatronQueryService;
import com.brandeis.lmsapp.service.PatronService;
import com.brandeis.lmsapp.web.rest.errors.ExceptionTranslator;
import org.hamcrest.*;
import org.hamcrest.Matchers.*;
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
 * Test class for the PatronResource REST controller.
 *
 * @see PatronResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LmsApp.class)
public class PatronResourceIntTest {

    private static final Integer DEFAULT_PATRON_ID = 1;
    private static final Integer UPDATED_PATRON_ID = 2;

    private static final String DEFAULT_PATRON_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PATRON_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MAILING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_MAILING_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUM_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUM_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUM_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUM_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUM_3 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUM_3 = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private PatronService patronService;

    @Autowired
    private PatronQueryService patronQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPatronMockMvc;

    private Patron patron;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatronResource patronResource = new PatronResource(patronService, patronQueryService);
        this.restPatronMockMvc = MockMvcBuilders.standaloneSetup(patronResource)
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
    public static Patron createEntity(EntityManager em) {
        Patron patron = new Patron()
            .patronStatus(DEFAULT_PATRON_STATUS)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .mailingAddress(DEFAULT_MAILING_ADDRESS)
            .email(DEFAULT_EMAIL)
            .phoneNum1(DEFAULT_PHONE_NUM_1)
            .phoneNum2(DEFAULT_PHONE_NUM_2)
            .phoneNum3(DEFAULT_PHONE_NUM_3)
            .login(DEFAULT_LOGIN);
        return patron;
    }

    @Before
    public void initTest() {
        System.out.println("Before Starting test--->Creating Patron entity");
        patron = createEntity(em);
        System.out.println("New Patron----->"+patron);
    }

    @Test
    @Transactional
    public void createPatron() throws Exception {
        System.out.println("Creating a Patron...");
        int databaseSizeBeforeCreate = patronRepository.findAll().size();

        // Create the Patron
        restPatronMockMvc.perform(post("/api/patrons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isCreated());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeCreate + 1);
        Patron testPatron = patronList.get(patronList.size() - 1);
        assertThat(testPatron.getPatronStatus()).isEqualTo(DEFAULT_PATRON_STATUS);
        assertThat(testPatron.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPatron.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testPatron.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPatron.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testPatron.getMailingAddress()).isEqualTo(DEFAULT_MAILING_ADDRESS);
        assertThat(testPatron.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPatron.getPhoneNum1()).isEqualTo(DEFAULT_PHONE_NUM_1);
        assertThat(testPatron.getPhoneNum2()).isEqualTo(DEFAULT_PHONE_NUM_2);
        assertThat(testPatron.getPhoneNum3()).isEqualTo(DEFAULT_PHONE_NUM_3);
        assertThat(testPatron.getLogin()).isEqualTo(DEFAULT_LOGIN);

        System.out.println("New Patron List:\n"+patronList);
    }

    @Test
    @Transactional
    public void createPatronWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patronRepository.findAll().size();

        // Create the Patron with an existing ID
        patron.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatronMockMvc.perform(post("/api/patrons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPatrons() throws Exception {
        System.out.println("Getting all Patrons...");
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        // Get all the patronList
        restPatronMockMvc.perform(get("/api/patrons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patron.getId().intValue())))
            //.andExpect(jsonPath("$.[*].patronId").value(hasItem(DEFAULT_PATRON_ID)))
            .andExpect(jsonPath("$.[*].patronStatus").value(hasItem(DEFAULT_PATRON_STATUS.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].mailingAddress").value(hasItem(DEFAULT_MAILING_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNum1").value(hasItem(DEFAULT_PHONE_NUM_1.toString())))
            .andExpect(jsonPath("$.[*].phoneNum2").value(hasItem(DEFAULT_PHONE_NUM_2.toString())))
            .andExpect(jsonPath("$.[*].phoneNum3").value(hasItem(DEFAULT_PHONE_NUM_3.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())));
    }

    @Test
    @Transactional
    public void getPatron() throws Exception {
        System.out.println("Getting a Patron...");
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        // Get the patron
        restPatronMockMvc.perform(get("/api/patrons/{id}", patron.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patron.getId().intValue()))
            //.andExpect(jsonPath("$.patronId").value(DEFAULT_PATRON_ID))
            .andExpect(jsonPath("$.patronStatus").value(DEFAULT_PATRON_STATUS.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.mailingAddress").value(DEFAULT_MAILING_ADDRESS.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNum1").value(DEFAULT_PHONE_NUM_1.toString()))
            .andExpect(jsonPath("$.phoneNum2").value(DEFAULT_PHONE_NUM_2.toString()))
            .andExpect(jsonPath("$.phoneNum3").value(DEFAULT_PHONE_NUM_3.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()));

        System.out.println("Got a Patron for ID: "+patron.getId());
    }

    @Test
    @Transactional
    public void getNonExistingPatron() throws Exception {
        System.out.println("Getting a Patron that does not exist...");
        // Get the patron
        restPatronMockMvc.perform(get("/api/patrons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatron() throws Exception {
        System.out.println("Updating a Patron...");
        // Initialize the database
        patronService.save(patron);

        int databaseSizeBeforeUpdate = patronRepository.findAll().size();

        // Update the patron
        Patron updatedPatron = patronRepository.findById(patron.getId()).get();
        // Disconnect from session so that the updates on updatedPatron are not directly saved in db
        em.detach(updatedPatron);
        updatedPatron
            .patronStatus(UPDATED_PATRON_STATUS)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .mailingAddress(UPDATED_MAILING_ADDRESS)
            .email(UPDATED_EMAIL)
            .phoneNum1(UPDATED_PHONE_NUM_1)
            .phoneNum2(UPDATED_PHONE_NUM_2)
            .phoneNum3(UPDATED_PHONE_NUM_3)
            .login(UPDATED_LOGIN);

        restPatronMockMvc.perform(put("/api/patrons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatron)))
            .andExpect(status().isOk());

        System.out.println("Updating Patron: "+updatedPatron);

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
        Patron testPatron = patronList.get(patronList.size() - 1);
        assertThat(testPatron.getPatronStatus()).isEqualTo(UPDATED_PATRON_STATUS);
        assertThat(testPatron.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPatron.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testPatron.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPatron.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testPatron.getMailingAddress()).isEqualTo(UPDATED_MAILING_ADDRESS);
        assertThat(testPatron.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPatron.getPhoneNum1()).isEqualTo(UPDATED_PHONE_NUM_1);
        assertThat(testPatron.getPhoneNum2()).isEqualTo(UPDATED_PHONE_NUM_2);
        assertThat(testPatron.getPhoneNum3()).isEqualTo(UPDATED_PHONE_NUM_3);
        assertThat(testPatron.getLogin()).isEqualTo(UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void updateNonExistingPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();

        // Create the Patron

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatronMockMvc.perform(put("/api/patrons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatron() throws Exception {
        System.out.println("Deleting Patron...");
        // Initialize the database
        patronService.save(patron);

        int databaseSizeBeforeDelete = patronRepository.findAll().size();

        // Get the patron
        restPatronMockMvc.perform(delete("/api/patrons/{id}", patron.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        System.out.println("Checking if 2 Patron ID's are the same...");
        TestUtil.equalsVerifier(Patron.class);
        Patron patron1 = new Patron();
        patron1.setId(1L);
        Patron patron2 = new Patron();
        patron2.setId(patron1.getId());
        assertThat(patron1).isEqualTo(patron2);
        patron2.setId(2L);
        assertThat(patron1).isNotEqualTo(patron2);
        patron1.setId(null);
        assertThat(patron1).isNotEqualTo(patron2);
    }
}
