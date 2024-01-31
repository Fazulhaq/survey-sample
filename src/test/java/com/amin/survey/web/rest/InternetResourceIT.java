package com.amin.survey.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amin.survey.IntegrationTest;
import com.amin.survey.domain.Form;
import com.amin.survey.domain.Internet;
import com.amin.survey.repository.InternetRepository;
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
 * Integration tests for the {@link InternetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InternetResourceIT {

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

    private static final String ENTITY_API_URL = "/api/internets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InternetRepository internetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInternetMockMvc;

    private Internet internet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Internet createEntity(EntityManager em) {
        Internet internet = new Internet()
            .question1(DEFAULT_QUESTION_1)
            .question2(DEFAULT_QUESTION_2)
            .question3(DEFAULT_QUESTION_3)
            .question4(DEFAULT_QUESTION_4)
            .question5(DEFAULT_QUESTION_5)
            .question6(DEFAULT_QUESTION_6);
        // Add required entity
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            form = FormResourceIT.createEntity(em);
            em.persist(form);
            em.flush();
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        internet.setForm(form);
        return internet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Internet createUpdatedEntity(EntityManager em) {
        Internet internet = new Internet()
            .question1(UPDATED_QUESTION_1)
            .question2(UPDATED_QUESTION_2)
            .question3(UPDATED_QUESTION_3)
            .question4(UPDATED_QUESTION_4)
            .question5(UPDATED_QUESTION_5)
            .question6(UPDATED_QUESTION_6);
        // Add required entity
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            form = FormResourceIT.createUpdatedEntity(em);
            em.persist(form);
            em.flush();
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        internet.setForm(form);
        return internet;
    }

    @BeforeEach
    public void initTest() {
        internet = createEntity(em);
    }

    @Test
    @Transactional
    void createInternet() throws Exception {
        int databaseSizeBeforeCreate = internetRepository.findAll().size();
        // Create the Internet
        restInternetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(internet)))
            .andExpect(status().isCreated());

        // Validate the Internet in the database
        List<Internet> internetList = internetRepository.findAll();
        assertThat(internetList).hasSize(databaseSizeBeforeCreate + 1);
        Internet testInternet = internetList.get(internetList.size() - 1);
        assertThat(testInternet.getQuestion1()).isEqualTo(DEFAULT_QUESTION_1);
        assertThat(testInternet.getQuestion2()).isEqualTo(DEFAULT_QUESTION_2);
        assertThat(testInternet.getQuestion3()).isEqualTo(DEFAULT_QUESTION_3);
        assertThat(testInternet.getQuestion4()).isEqualTo(DEFAULT_QUESTION_4);
        assertThat(testInternet.getQuestion5()).isEqualTo(DEFAULT_QUESTION_5);
        assertThat(testInternet.getQuestion6()).isEqualTo(DEFAULT_QUESTION_6);
    }

    @Test
    @Transactional
    void createInternetWithExistingId() throws Exception {
        // Create the Internet with an existing ID
        internet.setId(1L);

        int databaseSizeBeforeCreate = internetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(internet)))
            .andExpect(status().isBadRequest());

        // Validate the Internet in the database
        List<Internet> internetList = internetRepository.findAll();
        assertThat(internetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInternets() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList
        restInternetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internet.getId().intValue())))
            .andExpect(jsonPath("$.[*].question1").value(hasItem(DEFAULT_QUESTION_1)))
            .andExpect(jsonPath("$.[*].question2").value(hasItem(DEFAULT_QUESTION_2)))
            .andExpect(jsonPath("$.[*].question3").value(hasItem(DEFAULT_QUESTION_3)))
            .andExpect(jsonPath("$.[*].question4").value(hasItem(DEFAULT_QUESTION_4)))
            .andExpect(jsonPath("$.[*].question5").value(hasItem(DEFAULT_QUESTION_5)))
            .andExpect(jsonPath("$.[*].question6").value(hasItem(DEFAULT_QUESTION_6)));
    }

    @Test
    @Transactional
    void getInternet() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get the internet
        restInternetMockMvc
            .perform(get(ENTITY_API_URL_ID, internet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(internet.getId().intValue()))
            .andExpect(jsonPath("$.question1").value(DEFAULT_QUESTION_1))
            .andExpect(jsonPath("$.question2").value(DEFAULT_QUESTION_2))
            .andExpect(jsonPath("$.question3").value(DEFAULT_QUESTION_3))
            .andExpect(jsonPath("$.question4").value(DEFAULT_QUESTION_4))
            .andExpect(jsonPath("$.question5").value(DEFAULT_QUESTION_5))
            .andExpect(jsonPath("$.question6").value(DEFAULT_QUESTION_6));
    }

    @Test
    @Transactional
    void getInternetsByIdFiltering() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        Long id = internet.getId();

        defaultInternetShouldBeFound("id.equals=" + id);
        defaultInternetShouldNotBeFound("id.notEquals=" + id);

        defaultInternetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInternetShouldNotBeFound("id.greaterThan=" + id);

        defaultInternetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInternetShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion1IsEqualToSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question1 equals to DEFAULT_QUESTION_1
        defaultInternetShouldBeFound("question1.equals=" + DEFAULT_QUESTION_1);

        // Get all the internetList where question1 equals to UPDATED_QUESTION_1
        defaultInternetShouldNotBeFound("question1.equals=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion1IsInShouldWork() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question1 in DEFAULT_QUESTION_1 or UPDATED_QUESTION_1
        defaultInternetShouldBeFound("question1.in=" + DEFAULT_QUESTION_1 + "," + UPDATED_QUESTION_1);

        // Get all the internetList where question1 equals to UPDATED_QUESTION_1
        defaultInternetShouldNotBeFound("question1.in=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion1IsNullOrNotNull() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question1 is not null
        defaultInternetShouldBeFound("question1.specified=true");

        // Get all the internetList where question1 is null
        defaultInternetShouldNotBeFound("question1.specified=false");
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion1ContainsSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question1 contains DEFAULT_QUESTION_1
        defaultInternetShouldBeFound("question1.contains=" + DEFAULT_QUESTION_1);

        // Get all the internetList where question1 contains UPDATED_QUESTION_1
        defaultInternetShouldNotBeFound("question1.contains=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion1NotContainsSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question1 does not contain DEFAULT_QUESTION_1
        defaultInternetShouldNotBeFound("question1.doesNotContain=" + DEFAULT_QUESTION_1);

        // Get all the internetList where question1 does not contain UPDATED_QUESTION_1
        defaultInternetShouldBeFound("question1.doesNotContain=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion2IsEqualToSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question2 equals to DEFAULT_QUESTION_2
        defaultInternetShouldBeFound("question2.equals=" + DEFAULT_QUESTION_2);

        // Get all the internetList where question2 equals to UPDATED_QUESTION_2
        defaultInternetShouldNotBeFound("question2.equals=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion2IsInShouldWork() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question2 in DEFAULT_QUESTION_2 or UPDATED_QUESTION_2
        defaultInternetShouldBeFound("question2.in=" + DEFAULT_QUESTION_2 + "," + UPDATED_QUESTION_2);

        // Get all the internetList where question2 equals to UPDATED_QUESTION_2
        defaultInternetShouldNotBeFound("question2.in=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion2IsNullOrNotNull() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question2 is not null
        defaultInternetShouldBeFound("question2.specified=true");

        // Get all the internetList where question2 is null
        defaultInternetShouldNotBeFound("question2.specified=false");
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion2ContainsSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question2 contains DEFAULT_QUESTION_2
        defaultInternetShouldBeFound("question2.contains=" + DEFAULT_QUESTION_2);

        // Get all the internetList where question2 contains UPDATED_QUESTION_2
        defaultInternetShouldNotBeFound("question2.contains=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion2NotContainsSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question2 does not contain DEFAULT_QUESTION_2
        defaultInternetShouldNotBeFound("question2.doesNotContain=" + DEFAULT_QUESTION_2);

        // Get all the internetList where question2 does not contain UPDATED_QUESTION_2
        defaultInternetShouldBeFound("question2.doesNotContain=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion3IsEqualToSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question3 equals to DEFAULT_QUESTION_3
        defaultInternetShouldBeFound("question3.equals=" + DEFAULT_QUESTION_3);

        // Get all the internetList where question3 equals to UPDATED_QUESTION_3
        defaultInternetShouldNotBeFound("question3.equals=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion3IsInShouldWork() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question3 in DEFAULT_QUESTION_3 or UPDATED_QUESTION_3
        defaultInternetShouldBeFound("question3.in=" + DEFAULT_QUESTION_3 + "," + UPDATED_QUESTION_3);

        // Get all the internetList where question3 equals to UPDATED_QUESTION_3
        defaultInternetShouldNotBeFound("question3.in=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion3IsNullOrNotNull() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question3 is not null
        defaultInternetShouldBeFound("question3.specified=true");

        // Get all the internetList where question3 is null
        defaultInternetShouldNotBeFound("question3.specified=false");
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion3ContainsSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question3 contains DEFAULT_QUESTION_3
        defaultInternetShouldBeFound("question3.contains=" + DEFAULT_QUESTION_3);

        // Get all the internetList where question3 contains UPDATED_QUESTION_3
        defaultInternetShouldNotBeFound("question3.contains=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion3NotContainsSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question3 does not contain DEFAULT_QUESTION_3
        defaultInternetShouldNotBeFound("question3.doesNotContain=" + DEFAULT_QUESTION_3);

        // Get all the internetList where question3 does not contain UPDATED_QUESTION_3
        defaultInternetShouldBeFound("question3.doesNotContain=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion4IsEqualToSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question4 equals to DEFAULT_QUESTION_4
        defaultInternetShouldBeFound("question4.equals=" + DEFAULT_QUESTION_4);

        // Get all the internetList where question4 equals to UPDATED_QUESTION_4
        defaultInternetShouldNotBeFound("question4.equals=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion4IsInShouldWork() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question4 in DEFAULT_QUESTION_4 or UPDATED_QUESTION_4
        defaultInternetShouldBeFound("question4.in=" + DEFAULT_QUESTION_4 + "," + UPDATED_QUESTION_4);

        // Get all the internetList where question4 equals to UPDATED_QUESTION_4
        defaultInternetShouldNotBeFound("question4.in=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion4IsNullOrNotNull() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question4 is not null
        defaultInternetShouldBeFound("question4.specified=true");

        // Get all the internetList where question4 is null
        defaultInternetShouldNotBeFound("question4.specified=false");
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion4ContainsSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question4 contains DEFAULT_QUESTION_4
        defaultInternetShouldBeFound("question4.contains=" + DEFAULT_QUESTION_4);

        // Get all the internetList where question4 contains UPDATED_QUESTION_4
        defaultInternetShouldNotBeFound("question4.contains=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion4NotContainsSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question4 does not contain DEFAULT_QUESTION_4
        defaultInternetShouldNotBeFound("question4.doesNotContain=" + DEFAULT_QUESTION_4);

        // Get all the internetList where question4 does not contain UPDATED_QUESTION_4
        defaultInternetShouldBeFound("question4.doesNotContain=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion5IsEqualToSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question5 equals to DEFAULT_QUESTION_5
        defaultInternetShouldBeFound("question5.equals=" + DEFAULT_QUESTION_5);

        // Get all the internetList where question5 equals to UPDATED_QUESTION_5
        defaultInternetShouldNotBeFound("question5.equals=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion5IsInShouldWork() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question5 in DEFAULT_QUESTION_5 or UPDATED_QUESTION_5
        defaultInternetShouldBeFound("question5.in=" + DEFAULT_QUESTION_5 + "," + UPDATED_QUESTION_5);

        // Get all the internetList where question5 equals to UPDATED_QUESTION_5
        defaultInternetShouldNotBeFound("question5.in=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion5IsNullOrNotNull() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question5 is not null
        defaultInternetShouldBeFound("question5.specified=true");

        // Get all the internetList where question5 is null
        defaultInternetShouldNotBeFound("question5.specified=false");
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion5ContainsSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question5 contains DEFAULT_QUESTION_5
        defaultInternetShouldBeFound("question5.contains=" + DEFAULT_QUESTION_5);

        // Get all the internetList where question5 contains UPDATED_QUESTION_5
        defaultInternetShouldNotBeFound("question5.contains=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion5NotContainsSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question5 does not contain DEFAULT_QUESTION_5
        defaultInternetShouldNotBeFound("question5.doesNotContain=" + DEFAULT_QUESTION_5);

        // Get all the internetList where question5 does not contain UPDATED_QUESTION_5
        defaultInternetShouldBeFound("question5.doesNotContain=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion6IsEqualToSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question6 equals to DEFAULT_QUESTION_6
        defaultInternetShouldBeFound("question6.equals=" + DEFAULT_QUESTION_6);

        // Get all the internetList where question6 equals to UPDATED_QUESTION_6
        defaultInternetShouldNotBeFound("question6.equals=" + UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion6IsInShouldWork() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question6 in DEFAULT_QUESTION_6 or UPDATED_QUESTION_6
        defaultInternetShouldBeFound("question6.in=" + DEFAULT_QUESTION_6 + "," + UPDATED_QUESTION_6);

        // Get all the internetList where question6 equals to UPDATED_QUESTION_6
        defaultInternetShouldNotBeFound("question6.in=" + UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion6IsNullOrNotNull() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question6 is not null
        defaultInternetShouldBeFound("question6.specified=true");

        // Get all the internetList where question6 is null
        defaultInternetShouldNotBeFound("question6.specified=false");
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion6ContainsSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question6 contains DEFAULT_QUESTION_6
        defaultInternetShouldBeFound("question6.contains=" + DEFAULT_QUESTION_6);

        // Get all the internetList where question6 contains UPDATED_QUESTION_6
        defaultInternetShouldNotBeFound("question6.contains=" + UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void getAllInternetsByQuestion6NotContainsSomething() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        // Get all the internetList where question6 does not contain DEFAULT_QUESTION_6
        defaultInternetShouldNotBeFound("question6.doesNotContain=" + DEFAULT_QUESTION_6);

        // Get all the internetList where question6 does not contain UPDATED_QUESTION_6
        defaultInternetShouldBeFound("question6.doesNotContain=" + UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void getAllInternetsByFormIsEqualToSomething() throws Exception {
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            internetRepository.saveAndFlush(internet);
            form = FormResourceIT.createEntity(em);
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        em.persist(form);
        em.flush();
        internet.setForm(form);
        internetRepository.saveAndFlush(internet);
        Long formId = form.getId();
        // Get all the internetList where form equals to formId
        defaultInternetShouldBeFound("formId.equals=" + formId);

        // Get all the internetList where form equals to (formId + 1)
        defaultInternetShouldNotBeFound("formId.equals=" + (formId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInternetShouldBeFound(String filter) throws Exception {
        restInternetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internet.getId().intValue())))
            .andExpect(jsonPath("$.[*].question1").value(hasItem(DEFAULT_QUESTION_1)))
            .andExpect(jsonPath("$.[*].question2").value(hasItem(DEFAULT_QUESTION_2)))
            .andExpect(jsonPath("$.[*].question3").value(hasItem(DEFAULT_QUESTION_3)))
            .andExpect(jsonPath("$.[*].question4").value(hasItem(DEFAULT_QUESTION_4)))
            .andExpect(jsonPath("$.[*].question5").value(hasItem(DEFAULT_QUESTION_5)))
            .andExpect(jsonPath("$.[*].question6").value(hasItem(DEFAULT_QUESTION_6)));

        // Check, that the count call also returns 1
        restInternetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInternetShouldNotBeFound(String filter) throws Exception {
        restInternetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInternetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInternet() throws Exception {
        // Get the internet
        restInternetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInternet() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        int databaseSizeBeforeUpdate = internetRepository.findAll().size();

        // Update the internet
        Internet updatedInternet = internetRepository.findById(internet.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInternet are not directly saved in db
        em.detach(updatedInternet);
        updatedInternet
            .question1(UPDATED_QUESTION_1)
            .question2(UPDATED_QUESTION_2)
            .question3(UPDATED_QUESTION_3)
            .question4(UPDATED_QUESTION_4)
            .question5(UPDATED_QUESTION_5)
            .question6(UPDATED_QUESTION_6);

        restInternetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInternet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInternet))
            )
            .andExpect(status().isOk());

        // Validate the Internet in the database
        List<Internet> internetList = internetRepository.findAll();
        assertThat(internetList).hasSize(databaseSizeBeforeUpdate);
        Internet testInternet = internetList.get(internetList.size() - 1);
        assertThat(testInternet.getQuestion1()).isEqualTo(UPDATED_QUESTION_1);
        assertThat(testInternet.getQuestion2()).isEqualTo(UPDATED_QUESTION_2);
        assertThat(testInternet.getQuestion3()).isEqualTo(UPDATED_QUESTION_3);
        assertThat(testInternet.getQuestion4()).isEqualTo(UPDATED_QUESTION_4);
        assertThat(testInternet.getQuestion5()).isEqualTo(UPDATED_QUESTION_5);
        assertThat(testInternet.getQuestion6()).isEqualTo(UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void putNonExistingInternet() throws Exception {
        int databaseSizeBeforeUpdate = internetRepository.findAll().size();
        internet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, internet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(internet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Internet in the database
        List<Internet> internetList = internetRepository.findAll();
        assertThat(internetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInternet() throws Exception {
        int databaseSizeBeforeUpdate = internetRepository.findAll().size();
        internet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInternetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(internet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Internet in the database
        List<Internet> internetList = internetRepository.findAll();
        assertThat(internetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInternet() throws Exception {
        int databaseSizeBeforeUpdate = internetRepository.findAll().size();
        internet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInternetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(internet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Internet in the database
        List<Internet> internetList = internetRepository.findAll();
        assertThat(internetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInternetWithPatch() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        int databaseSizeBeforeUpdate = internetRepository.findAll().size();

        // Update the internet using partial update
        Internet partialUpdatedInternet = new Internet();
        partialUpdatedInternet.setId(internet.getId());

        partialUpdatedInternet
            .question1(UPDATED_QUESTION_1)
            .question3(UPDATED_QUESTION_3)
            .question5(UPDATED_QUESTION_5)
            .question6(UPDATED_QUESTION_6);

        restInternetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInternet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInternet))
            )
            .andExpect(status().isOk());

        // Validate the Internet in the database
        List<Internet> internetList = internetRepository.findAll();
        assertThat(internetList).hasSize(databaseSizeBeforeUpdate);
        Internet testInternet = internetList.get(internetList.size() - 1);
        assertThat(testInternet.getQuestion1()).isEqualTo(UPDATED_QUESTION_1);
        assertThat(testInternet.getQuestion2()).isEqualTo(DEFAULT_QUESTION_2);
        assertThat(testInternet.getQuestion3()).isEqualTo(UPDATED_QUESTION_3);
        assertThat(testInternet.getQuestion4()).isEqualTo(DEFAULT_QUESTION_4);
        assertThat(testInternet.getQuestion5()).isEqualTo(UPDATED_QUESTION_5);
        assertThat(testInternet.getQuestion6()).isEqualTo(UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void fullUpdateInternetWithPatch() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        int databaseSizeBeforeUpdate = internetRepository.findAll().size();

        // Update the internet using partial update
        Internet partialUpdatedInternet = new Internet();
        partialUpdatedInternet.setId(internet.getId());

        partialUpdatedInternet
            .question1(UPDATED_QUESTION_1)
            .question2(UPDATED_QUESTION_2)
            .question3(UPDATED_QUESTION_3)
            .question4(UPDATED_QUESTION_4)
            .question5(UPDATED_QUESTION_5)
            .question6(UPDATED_QUESTION_6);

        restInternetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInternet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInternet))
            )
            .andExpect(status().isOk());

        // Validate the Internet in the database
        List<Internet> internetList = internetRepository.findAll();
        assertThat(internetList).hasSize(databaseSizeBeforeUpdate);
        Internet testInternet = internetList.get(internetList.size() - 1);
        assertThat(testInternet.getQuestion1()).isEqualTo(UPDATED_QUESTION_1);
        assertThat(testInternet.getQuestion2()).isEqualTo(UPDATED_QUESTION_2);
        assertThat(testInternet.getQuestion3()).isEqualTo(UPDATED_QUESTION_3);
        assertThat(testInternet.getQuestion4()).isEqualTo(UPDATED_QUESTION_4);
        assertThat(testInternet.getQuestion5()).isEqualTo(UPDATED_QUESTION_5);
        assertThat(testInternet.getQuestion6()).isEqualTo(UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void patchNonExistingInternet() throws Exception {
        int databaseSizeBeforeUpdate = internetRepository.findAll().size();
        internet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, internet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(internet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Internet in the database
        List<Internet> internetList = internetRepository.findAll();
        assertThat(internetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInternet() throws Exception {
        int databaseSizeBeforeUpdate = internetRepository.findAll().size();
        internet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInternetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(internet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Internet in the database
        List<Internet> internetList = internetRepository.findAll();
        assertThat(internetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInternet() throws Exception {
        int databaseSizeBeforeUpdate = internetRepository.findAll().size();
        internet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInternetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(internet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Internet in the database
        List<Internet> internetList = internetRepository.findAll();
        assertThat(internetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInternet() throws Exception {
        // Initialize the database
        internetRepository.saveAndFlush(internet);

        int databaseSizeBeforeDelete = internetRepository.findAll().size();

        // Delete the internet
        restInternetMockMvc
            .perform(delete(ENTITY_API_URL_ID, internet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Internet> internetList = internetRepository.findAll();
        assertThat(internetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
