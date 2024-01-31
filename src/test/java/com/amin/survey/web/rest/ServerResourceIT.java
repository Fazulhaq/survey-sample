package com.amin.survey.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amin.survey.IntegrationTest;
import com.amin.survey.domain.Form;
import com.amin.survey.domain.Server;
import com.amin.survey.repository.ServerRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ServerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServerResourceIT {

    private static final String DEFAULT_QUESTION_1 = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_1 = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_2 = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_2 = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_3 = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_3 = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_4 = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_4 = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_5 = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_5 = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_6 = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_6 = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_7 = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_7 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/servers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServerMockMvc;

    private Server server;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Server createEntity(EntityManager em) {
        Server server = new Server()
            .question1(DEFAULT_QUESTION_1)
            .question2(DEFAULT_QUESTION_2)
            .question3(DEFAULT_QUESTION_3)
            .question4(DEFAULT_QUESTION_4)
            .question5(DEFAULT_QUESTION_5)
            .question6(DEFAULT_QUESTION_6)
            .question7(DEFAULT_QUESTION_7);
        // Add required entity
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            form = FormResourceIT.createEntity(em);
            em.persist(form);
            em.flush();
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        server.setForm(form);
        return server;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Server createUpdatedEntity(EntityManager em) {
        Server server = new Server()
            .question1(UPDATED_QUESTION_1)
            .question2(UPDATED_QUESTION_2)
            .question3(UPDATED_QUESTION_3)
            .question4(UPDATED_QUESTION_4)
            .question5(UPDATED_QUESTION_5)
            .question6(UPDATED_QUESTION_6)
            .question7(UPDATED_QUESTION_7);
        // Add required entity
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            form = FormResourceIT.createUpdatedEntity(em);
            em.persist(form);
            em.flush();
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        server.setForm(form);
        return server;
    }

    @BeforeEach
    public void initTest() {
        server = createEntity(em);
    }

    @Test
    @Transactional
    void createServer() throws Exception {
        int databaseSizeBeforeCreate = serverRepository.findAll().size();
        // Create the Server
        restServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(server)))
            .andExpect(status().isCreated());

        // Validate the Server in the database
        List<Server> serverList = serverRepository.findAll();
        assertThat(serverList).hasSize(databaseSizeBeforeCreate + 1);
        Server testServer = serverList.get(serverList.size() - 1);
        assertThat(testServer.getQuestion1()).isEqualTo(DEFAULT_QUESTION_1);
        assertThat(testServer.getQuestion2()).isEqualTo(DEFAULT_QUESTION_2);
        assertThat(testServer.getQuestion3()).isEqualTo(DEFAULT_QUESTION_3);
        assertThat(testServer.getQuestion4()).isEqualTo(DEFAULT_QUESTION_4);
        assertThat(testServer.getQuestion5()).isEqualTo(DEFAULT_QUESTION_5);
        assertThat(testServer.getQuestion6()).isEqualTo(DEFAULT_QUESTION_6);
        assertThat(testServer.getQuestion7()).isEqualTo(DEFAULT_QUESTION_7);
    }

    @Test
    @Transactional
    void createServerWithExistingId() throws Exception {
        // Create the Server with an existing ID
        server.setId(1L);

        int databaseSizeBeforeCreate = serverRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(server)))
            .andExpect(status().isBadRequest());

        // Validate the Server in the database
        List<Server> serverList = serverRepository.findAll();
        assertThat(serverList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServers() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList
        restServerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(server.getId().intValue())))
            .andExpect(jsonPath("$.[*].question1").value(hasItem(DEFAULT_QUESTION_1)))
            .andExpect(jsonPath("$.[*].question2").value(hasItem(DEFAULT_QUESTION_2)))
            .andExpect(jsonPath("$.[*].question3").value(hasItem(DEFAULT_QUESTION_3)))
            .andExpect(jsonPath("$.[*].question4").value(hasItem(DEFAULT_QUESTION_4)))
            .andExpect(jsonPath("$.[*].question5").value(hasItem(DEFAULT_QUESTION_5)))
            .andExpect(jsonPath("$.[*].question6").value(hasItem(DEFAULT_QUESTION_6)))
            .andExpect(jsonPath("$.[*].question7").value(hasItem(DEFAULT_QUESTION_7)));
    }

    @Test
    @Transactional
    void getServer() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get the server
        restServerMockMvc
            .perform(get(ENTITY_API_URL_ID, server.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(server.getId().intValue()))
            .andExpect(jsonPath("$.question1").value(DEFAULT_QUESTION_1))
            .andExpect(jsonPath("$.question2").value(DEFAULT_QUESTION_2))
            .andExpect(jsonPath("$.question3").value(DEFAULT_QUESTION_3))
            .andExpect(jsonPath("$.question4").value(DEFAULT_QUESTION_4))
            .andExpect(jsonPath("$.question5").value(DEFAULT_QUESTION_5))
            .andExpect(jsonPath("$.question6").value(DEFAULT_QUESTION_6))
            .andExpect(jsonPath("$.question7").value(DEFAULT_QUESTION_7));
    }

    @Test
    @Transactional
    void getServersByIdFiltering() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        Long id = server.getId();

        defaultServerShouldBeFound("id.equals=" + id);
        defaultServerShouldNotBeFound("id.notEquals=" + id);

        defaultServerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServerShouldNotBeFound("id.greaterThan=" + id);

        defaultServerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllServersByQuestion1IsEqualToSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question1 equals to DEFAULT_QUESTION_1
        defaultServerShouldBeFound("question1.equals=" + DEFAULT_QUESTION_1);

        // Get all the serverList where question1 equals to UPDATED_QUESTION_1
        defaultServerShouldNotBeFound("question1.equals=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllServersByQuestion1IsInShouldWork() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question1 in DEFAULT_QUESTION_1 or UPDATED_QUESTION_1
        defaultServerShouldBeFound("question1.in=" + DEFAULT_QUESTION_1 + "," + UPDATED_QUESTION_1);

        // Get all the serverList where question1 equals to UPDATED_QUESTION_1
        defaultServerShouldNotBeFound("question1.in=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllServersByQuestion1IsNullOrNotNull() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question1 is not null
        defaultServerShouldBeFound("question1.specified=true");

        // Get all the serverList where question1 is null
        defaultServerShouldNotBeFound("question1.specified=false");
    }

    @Test
    @Transactional
    void getAllServersByQuestion1ContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question1 contains DEFAULT_QUESTION_1
        defaultServerShouldBeFound("question1.contains=" + DEFAULT_QUESTION_1);

        // Get all the serverList where question1 contains UPDATED_QUESTION_1
        defaultServerShouldNotBeFound("question1.contains=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllServersByQuestion1NotContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question1 does not contain DEFAULT_QUESTION_1
        defaultServerShouldNotBeFound("question1.doesNotContain=" + DEFAULT_QUESTION_1);

        // Get all the serverList where question1 does not contain UPDATED_QUESTION_1
        defaultServerShouldBeFound("question1.doesNotContain=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllServersByQuestion2IsEqualToSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question2 equals to DEFAULT_QUESTION_2
        defaultServerShouldBeFound("question2.equals=" + DEFAULT_QUESTION_2);

        // Get all the serverList where question2 equals to UPDATED_QUESTION_2
        defaultServerShouldNotBeFound("question2.equals=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllServersByQuestion2IsInShouldWork() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question2 in DEFAULT_QUESTION_2 or UPDATED_QUESTION_2
        defaultServerShouldBeFound("question2.in=" + DEFAULT_QUESTION_2 + "," + UPDATED_QUESTION_2);

        // Get all the serverList where question2 equals to UPDATED_QUESTION_2
        defaultServerShouldNotBeFound("question2.in=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllServersByQuestion2IsNullOrNotNull() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question2 is not null
        defaultServerShouldBeFound("question2.specified=true");

        // Get all the serverList where question2 is null
        defaultServerShouldNotBeFound("question2.specified=false");
    }

    @Test
    @Transactional
    void getAllServersByQuestion2ContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question2 contains DEFAULT_QUESTION_2
        defaultServerShouldBeFound("question2.contains=" + DEFAULT_QUESTION_2);

        // Get all the serverList where question2 contains UPDATED_QUESTION_2
        defaultServerShouldNotBeFound("question2.contains=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllServersByQuestion2NotContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question2 does not contain DEFAULT_QUESTION_2
        defaultServerShouldNotBeFound("question2.doesNotContain=" + DEFAULT_QUESTION_2);

        // Get all the serverList where question2 does not contain UPDATED_QUESTION_2
        defaultServerShouldBeFound("question2.doesNotContain=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllServersByQuestion3IsEqualToSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question3 equals to DEFAULT_QUESTION_3
        defaultServerShouldBeFound("question3.equals=" + DEFAULT_QUESTION_3);

        // Get all the serverList where question3 equals to UPDATED_QUESTION_3
        defaultServerShouldNotBeFound("question3.equals=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllServersByQuestion3IsInShouldWork() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question3 in DEFAULT_QUESTION_3 or UPDATED_QUESTION_3
        defaultServerShouldBeFound("question3.in=" + DEFAULT_QUESTION_3 + "," + UPDATED_QUESTION_3);

        // Get all the serverList where question3 equals to UPDATED_QUESTION_3
        defaultServerShouldNotBeFound("question3.in=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllServersByQuestion3IsNullOrNotNull() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question3 is not null
        defaultServerShouldBeFound("question3.specified=true");

        // Get all the serverList where question3 is null
        defaultServerShouldNotBeFound("question3.specified=false");
    }

    @Test
    @Transactional
    void getAllServersByQuestion3ContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question3 contains DEFAULT_QUESTION_3
        defaultServerShouldBeFound("question3.contains=" + DEFAULT_QUESTION_3);

        // Get all the serverList where question3 contains UPDATED_QUESTION_3
        defaultServerShouldNotBeFound("question3.contains=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllServersByQuestion3NotContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question3 does not contain DEFAULT_QUESTION_3
        defaultServerShouldNotBeFound("question3.doesNotContain=" + DEFAULT_QUESTION_3);

        // Get all the serverList where question3 does not contain UPDATED_QUESTION_3
        defaultServerShouldBeFound("question3.doesNotContain=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllServersByQuestion4IsEqualToSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question4 equals to DEFAULT_QUESTION_4
        defaultServerShouldBeFound("question4.equals=" + DEFAULT_QUESTION_4);

        // Get all the serverList where question4 equals to UPDATED_QUESTION_4
        defaultServerShouldNotBeFound("question4.equals=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllServersByQuestion4IsInShouldWork() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question4 in DEFAULT_QUESTION_4 or UPDATED_QUESTION_4
        defaultServerShouldBeFound("question4.in=" + DEFAULT_QUESTION_4 + "," + UPDATED_QUESTION_4);

        // Get all the serverList where question4 equals to UPDATED_QUESTION_4
        defaultServerShouldNotBeFound("question4.in=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllServersByQuestion4IsNullOrNotNull() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question4 is not null
        defaultServerShouldBeFound("question4.specified=true");

        // Get all the serverList where question4 is null
        defaultServerShouldNotBeFound("question4.specified=false");
    }

    @Test
    @Transactional
    void getAllServersByQuestion4ContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question4 contains DEFAULT_QUESTION_4
        defaultServerShouldBeFound("question4.contains=" + DEFAULT_QUESTION_4);

        // Get all the serverList where question4 contains UPDATED_QUESTION_4
        defaultServerShouldNotBeFound("question4.contains=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllServersByQuestion4NotContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question4 does not contain DEFAULT_QUESTION_4
        defaultServerShouldNotBeFound("question4.doesNotContain=" + DEFAULT_QUESTION_4);

        // Get all the serverList where question4 does not contain UPDATED_QUESTION_4
        defaultServerShouldBeFound("question4.doesNotContain=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllServersByQuestion5IsEqualToSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question5 equals to DEFAULT_QUESTION_5
        defaultServerShouldBeFound("question5.equals=" + DEFAULT_QUESTION_5);

        // Get all the serverList where question5 equals to UPDATED_QUESTION_5
        defaultServerShouldNotBeFound("question5.equals=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllServersByQuestion5IsInShouldWork() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question5 in DEFAULT_QUESTION_5 or UPDATED_QUESTION_5
        defaultServerShouldBeFound("question5.in=" + DEFAULT_QUESTION_5 + "," + UPDATED_QUESTION_5);

        // Get all the serverList where question5 equals to UPDATED_QUESTION_5
        defaultServerShouldNotBeFound("question5.in=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllServersByQuestion5IsNullOrNotNull() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question5 is not null
        defaultServerShouldBeFound("question5.specified=true");

        // Get all the serverList where question5 is null
        defaultServerShouldNotBeFound("question5.specified=false");
    }

    @Test
    @Transactional
    void getAllServersByQuestion5ContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question5 contains DEFAULT_QUESTION_5
        defaultServerShouldBeFound("question5.contains=" + DEFAULT_QUESTION_5);

        // Get all the serverList where question5 contains UPDATED_QUESTION_5
        defaultServerShouldNotBeFound("question5.contains=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllServersByQuestion5NotContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question5 does not contain DEFAULT_QUESTION_5
        defaultServerShouldNotBeFound("question5.doesNotContain=" + DEFAULT_QUESTION_5);

        // Get all the serverList where question5 does not contain UPDATED_QUESTION_5
        defaultServerShouldBeFound("question5.doesNotContain=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllServersByQuestion6IsEqualToSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question6 equals to DEFAULT_QUESTION_6
        defaultServerShouldBeFound("question6.equals=" + DEFAULT_QUESTION_6);

        // Get all the serverList where question6 equals to UPDATED_QUESTION_6
        defaultServerShouldNotBeFound("question6.equals=" + UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void getAllServersByQuestion6IsInShouldWork() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question6 in DEFAULT_QUESTION_6 or UPDATED_QUESTION_6
        defaultServerShouldBeFound("question6.in=" + DEFAULT_QUESTION_6 + "," + UPDATED_QUESTION_6);

        // Get all the serverList where question6 equals to UPDATED_QUESTION_6
        defaultServerShouldNotBeFound("question6.in=" + UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void getAllServersByQuestion6IsNullOrNotNull() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question6 is not null
        defaultServerShouldBeFound("question6.specified=true");

        // Get all the serverList where question6 is null
        defaultServerShouldNotBeFound("question6.specified=false");
    }

    @Test
    @Transactional
    void getAllServersByQuestion6ContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question6 contains DEFAULT_QUESTION_6
        defaultServerShouldBeFound("question6.contains=" + DEFAULT_QUESTION_6);

        // Get all the serverList where question6 contains UPDATED_QUESTION_6
        defaultServerShouldNotBeFound("question6.contains=" + UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void getAllServersByQuestion6NotContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question6 does not contain DEFAULT_QUESTION_6
        defaultServerShouldNotBeFound("question6.doesNotContain=" + DEFAULT_QUESTION_6);

        // Get all the serverList where question6 does not contain UPDATED_QUESTION_6
        defaultServerShouldBeFound("question6.doesNotContain=" + UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void getAllServersByQuestion7IsEqualToSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question7 equals to DEFAULT_QUESTION_7
        defaultServerShouldBeFound("question7.equals=" + DEFAULT_QUESTION_7);

        // Get all the serverList where question7 equals to UPDATED_QUESTION_7
        defaultServerShouldNotBeFound("question7.equals=" + UPDATED_QUESTION_7);
    }

    @Test
    @Transactional
    void getAllServersByQuestion7IsInShouldWork() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question7 in DEFAULT_QUESTION_7 or UPDATED_QUESTION_7
        defaultServerShouldBeFound("question7.in=" + DEFAULT_QUESTION_7 + "," + UPDATED_QUESTION_7);

        // Get all the serverList where question7 equals to UPDATED_QUESTION_7
        defaultServerShouldNotBeFound("question7.in=" + UPDATED_QUESTION_7);
    }

    @Test
    @Transactional
    void getAllServersByQuestion7IsNullOrNotNull() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question7 is not null
        defaultServerShouldBeFound("question7.specified=true");

        // Get all the serverList where question7 is null
        defaultServerShouldNotBeFound("question7.specified=false");
    }

    @Test
    @Transactional
    void getAllServersByQuestion7ContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question7 contains DEFAULT_QUESTION_7
        defaultServerShouldBeFound("question7.contains=" + DEFAULT_QUESTION_7);

        // Get all the serverList where question7 contains UPDATED_QUESTION_7
        defaultServerShouldNotBeFound("question7.contains=" + UPDATED_QUESTION_7);
    }

    @Test
    @Transactional
    void getAllServersByQuestion7NotContainsSomething() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList where question7 does not contain DEFAULT_QUESTION_7
        defaultServerShouldNotBeFound("question7.doesNotContain=" + DEFAULT_QUESTION_7);

        // Get all the serverList where question7 does not contain UPDATED_QUESTION_7
        defaultServerShouldBeFound("question7.doesNotContain=" + UPDATED_QUESTION_7);
    }

    @Test
    @Transactional
    void getAllServersByFormIsEqualToSomething() throws Exception {
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            serverRepository.saveAndFlush(server);
            form = FormResourceIT.createEntity(em);
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        em.persist(form);
        em.flush();
        server.setForm(form);
        serverRepository.saveAndFlush(server);
        Long formId = form.getId();
        // Get all the serverList where form equals to formId
        defaultServerShouldBeFound("formId.equals=" + formId);

        // Get all the serverList where form equals to (formId + 1)
        defaultServerShouldNotBeFound("formId.equals=" + (formId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServerShouldBeFound(String filter) throws Exception {
        restServerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(server.getId().intValue())))
            .andExpect(jsonPath("$.[*].question1").value(hasItem(DEFAULT_QUESTION_1)))
            .andExpect(jsonPath("$.[*].question2").value(hasItem(DEFAULT_QUESTION_2)))
            .andExpect(jsonPath("$.[*].question3").value(hasItem(DEFAULT_QUESTION_3)))
            .andExpect(jsonPath("$.[*].question4").value(hasItem(DEFAULT_QUESTION_4)))
            .andExpect(jsonPath("$.[*].question5").value(hasItem(DEFAULT_QUESTION_5)))
            .andExpect(jsonPath("$.[*].question6").value(hasItem(DEFAULT_QUESTION_6)))
            .andExpect(jsonPath("$.[*].question7").value(hasItem(DEFAULT_QUESTION_7)));

        // Check, that the count call also returns 1
        restServerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServerShouldNotBeFound(String filter) throws Exception {
        restServerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingServer() throws Exception {
        // Get the server
        restServerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServer() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        int databaseSizeBeforeUpdate = serverRepository.findAll().size();

        // Update the server
        Server updatedServer = serverRepository.findById(server.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServer are not directly saved in db
        em.detach(updatedServer);
        updatedServer
            .question1(UPDATED_QUESTION_1)
            .question2(UPDATED_QUESTION_2)
            .question3(UPDATED_QUESTION_3)
            .question4(UPDATED_QUESTION_4)
            .question5(UPDATED_QUESTION_5)
            .question6(UPDATED_QUESTION_6)
            .question7(UPDATED_QUESTION_7);

        restServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedServer))
            )
            .andExpect(status().isOk());

        // Validate the Server in the database
        List<Server> serverList = serverRepository.findAll();
        assertThat(serverList).hasSize(databaseSizeBeforeUpdate);
        Server testServer = serverList.get(serverList.size() - 1);
        assertThat(testServer.getQuestion1()).isEqualTo(UPDATED_QUESTION_1);
        assertThat(testServer.getQuestion2()).isEqualTo(UPDATED_QUESTION_2);
        assertThat(testServer.getQuestion3()).isEqualTo(UPDATED_QUESTION_3);
        assertThat(testServer.getQuestion4()).isEqualTo(UPDATED_QUESTION_4);
        assertThat(testServer.getQuestion5()).isEqualTo(UPDATED_QUESTION_5);
        assertThat(testServer.getQuestion6()).isEqualTo(UPDATED_QUESTION_6);
        assertThat(testServer.getQuestion7()).isEqualTo(UPDATED_QUESTION_7);
    }

    @Test
    @Transactional
    void putNonExistingServer() throws Exception {
        int databaseSizeBeforeUpdate = serverRepository.findAll().size();
        server.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, server.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(server))
            )
            .andExpect(status().isBadRequest());

        // Validate the Server in the database
        List<Server> serverList = serverRepository.findAll();
        assertThat(serverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServer() throws Exception {
        int databaseSizeBeforeUpdate = serverRepository.findAll().size();
        server.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(server))
            )
            .andExpect(status().isBadRequest());

        // Validate the Server in the database
        List<Server> serverList = serverRepository.findAll();
        assertThat(serverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServer() throws Exception {
        int databaseSizeBeforeUpdate = serverRepository.findAll().size();
        server.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(server)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Server in the database
        List<Server> serverList = serverRepository.findAll();
        assertThat(serverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServerWithPatch() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        int databaseSizeBeforeUpdate = serverRepository.findAll().size();

        // Update the server using partial update
        Server partialUpdatedServer = new Server();
        partialUpdatedServer.setId(server.getId());

        partialUpdatedServer
            .question2(UPDATED_QUESTION_2)
            .question3(UPDATED_QUESTION_3)
            .question4(UPDATED_QUESTION_4)
            .question5(UPDATED_QUESTION_5)
            .question6(UPDATED_QUESTION_6);

        restServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServer))
            )
            .andExpect(status().isOk());

        // Validate the Server in the database
        List<Server> serverList = serverRepository.findAll();
        assertThat(serverList).hasSize(databaseSizeBeforeUpdate);
        Server testServer = serverList.get(serverList.size() - 1);
        assertThat(testServer.getQuestion1()).isEqualTo(DEFAULT_QUESTION_1);
        assertThat(testServer.getQuestion2()).isEqualTo(UPDATED_QUESTION_2);
        assertThat(testServer.getQuestion3()).isEqualTo(UPDATED_QUESTION_3);
        assertThat(testServer.getQuestion4()).isEqualTo(UPDATED_QUESTION_4);
        assertThat(testServer.getQuestion5()).isEqualTo(UPDATED_QUESTION_5);
        assertThat(testServer.getQuestion6()).isEqualTo(UPDATED_QUESTION_6);
        assertThat(testServer.getQuestion7()).isEqualTo(DEFAULT_QUESTION_7);
    }

    @Test
    @Transactional
    void fullUpdateServerWithPatch() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        int databaseSizeBeforeUpdate = serverRepository.findAll().size();

        // Update the server using partial update
        Server partialUpdatedServer = new Server();
        partialUpdatedServer.setId(server.getId());

        partialUpdatedServer
            .question1(UPDATED_QUESTION_1)
            .question2(UPDATED_QUESTION_2)
            .question3(UPDATED_QUESTION_3)
            .question4(UPDATED_QUESTION_4)
            .question5(UPDATED_QUESTION_5)
            .question6(UPDATED_QUESTION_6)
            .question7(UPDATED_QUESTION_7);

        restServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServer))
            )
            .andExpect(status().isOk());

        // Validate the Server in the database
        List<Server> serverList = serverRepository.findAll();
        assertThat(serverList).hasSize(databaseSizeBeforeUpdate);
        Server testServer = serverList.get(serverList.size() - 1);
        assertThat(testServer.getQuestion1()).isEqualTo(UPDATED_QUESTION_1);
        assertThat(testServer.getQuestion2()).isEqualTo(UPDATED_QUESTION_2);
        assertThat(testServer.getQuestion3()).isEqualTo(UPDATED_QUESTION_3);
        assertThat(testServer.getQuestion4()).isEqualTo(UPDATED_QUESTION_4);
        assertThat(testServer.getQuestion5()).isEqualTo(UPDATED_QUESTION_5);
        assertThat(testServer.getQuestion6()).isEqualTo(UPDATED_QUESTION_6);
        assertThat(testServer.getQuestion7()).isEqualTo(UPDATED_QUESTION_7);
    }

    @Test
    @Transactional
    void patchNonExistingServer() throws Exception {
        int databaseSizeBeforeUpdate = serverRepository.findAll().size();
        server.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, server.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(server))
            )
            .andExpect(status().isBadRequest());

        // Validate the Server in the database
        List<Server> serverList = serverRepository.findAll();
        assertThat(serverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServer() throws Exception {
        int databaseSizeBeforeUpdate = serverRepository.findAll().size();
        server.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(server))
            )
            .andExpect(status().isBadRequest());

        // Validate the Server in the database
        List<Server> serverList = serverRepository.findAll();
        assertThat(serverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServer() throws Exception {
        int databaseSizeBeforeUpdate = serverRepository.findAll().size();
        server.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(server)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Server in the database
        List<Server> serverList = serverRepository.findAll();
        assertThat(serverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServer() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        int databaseSizeBeforeDelete = serverRepository.findAll().size();

        // Delete the server
        restServerMockMvc
            .perform(delete(ENTITY_API_URL_ID, server.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Server> serverList = serverRepository.findAll();
        assertThat(serverList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
