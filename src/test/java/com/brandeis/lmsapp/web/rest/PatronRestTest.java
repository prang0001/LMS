package com.brandeis.lmsapp.web.rest;

import com.brandeis.lmsapp.LmsApp;
import com.brandeis.lmsapp.domain.Patron;
import com.brandeis.lmsapp.service.PatronQueryService;
import com.brandeis.lmsapp.service.PatronService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import java.net.URI;
import java.net.URISyntaxException;

import static com.brandeis.lmsapp.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { LmsApp.class })
@WebAppConfiguration
public class PatronRestTest {

    @Autowired
    private WebApplicationContext wac;

    private static final Integer DEFAULT_PATRON_ID = 2;
    private static final Integer UPDATED_PATRON_ID = 3;

    private static final String DEFAULT_PATRON_STATUS = "Active";
    private static final String UPDATED_PATRON_STATUS = "InActive";

    private static final String DEFAULT_FIRST_NAME = "Chris";
    private static final String UPDATED_FIRST_NAME = "Christopher";

    private static final String DEFAULT_MIDDLE_NAME = "Middle";
    private static final String UPDATED_MIDDLE_NAME = "MiddleName";

    private static final String DEFAULT_LAST_NAME = "Harris";
    private static final String UPDATED_LAST_NAME = "Harrison";

    private static final String DEFAULT_STREET_ADDRESS = "10 Main St, Marlborough MA 01752";
    private static final String UPDATED_STREET_ADDRESS = "1000 Main St, Marlborough MA 01752";

    private static final String DEFAULT_MAILING_ADDRESS = "10 Main St, Marlborough MA 01752";
    private static final String UPDATED_MAILING_ADDRESS = "1000 Main St, Marlborough MA 01752";

    private static final String DEFAULT_EMAIL = "c.h@harris.com";
    private static final String UPDATED_EMAIL = "chris.harris@harris.com";

    private static final String DEFAULT_PHONE_NUM_1 = "1111111111";
    private static final String UPDATED_PHONE_NUM_1 = "2222222222";

    private static final String DEFAULT_PHONE_NUM_2 = "2222222222";
    private static final String UPDATED_PHONE_NUM_2 = "3333333333";

    private static final String DEFAULT_PHONE_NUM_3 = "3333333333";
    private static final String UPDATED_PHONE_NUM_3 = "4444444444";

    private static final String DEFAULT_LOGIN = "chrisLogin1";
    private static final String UPDATED_LOGIN = "chrisLogin2";

    private Patron patron;

    @Autowired
    private EntityManager em;

    @Autowired
    PatronResource patronResource;

    @Autowired
    private PatronService patronService;

    @Autowired
    private PatronQueryService patronQueryService;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        final PatronResource patronResource = new PatronResource(patronService, patronQueryService);
    }

    @Before
    public void initTest() {
        patron = createEntity(em);
    }
    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patron createEntity(EntityManager em) {
        System.out.println("Creating Patron Entity for Testing...");
        Patron patron = new Patron()
           // .patronId(DEFAULT_PATRON_ID)
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
        System.out.print(patron);
        return patron;
    }

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    @Transactional
    @PostMapping("/api/createPatron")
    public void createNewPatron() throws URISyntaxException {

        ResponseEntity<Patron> p = patronResource.createPatron(patron);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
