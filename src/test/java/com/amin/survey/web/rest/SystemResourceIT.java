package com.amin.survey.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amin.survey.IntegrationTest;
import com.amin.survey.domain.Form;
import com.amin.survey.domain.System;
import com.amin.survey.repository.SystemRepository;
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
 * Integration tests for the {@link SystemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SystemResourceIT {

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

    private static final String ENTITY_API_URL = "/api/systems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SystemRepository systemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSystemMockMvc;

    private System system;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static System createEntity(EntityManager em) {
        System system = new System()
            .question1(DEFAULT_QUESTION_1)
            .question2(DEFAULT_QUESTION_2)
            .question3(DEFAULT_QUESTION_3)
            .question4(DEFAULT_QUESTION_4)
            .question5(DEFAULT_QUESTION_5);
        // Add required entity
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            form = FormResourceIT.createEntity(em);
            em.persist(form);
            em.flush();
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        system.setForm(form);
        return system;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static System createUpdatedEntity(EntityManager em) {
        System system = new System()
            .question1(UPDATED_QUESTION_1)
            .question2(UPDATED_QUESTION_2)
            .question3(UPDATED_QUESTION_3)
            .question4(UPDATED_QUESTION_4)
            .question5(UPDATED_QUESTION_5);
        // Add required entity
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            form = FormResourceIT.createUpdatedEntity(em);
            em.persist(form);
            em.flush();
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        system.setForm(form);
        return system;
    }

    @BeforeEach
    public void initTest() {
        system = createEntity(em);
    }

    @Test
    @Transactional
    void createSystem() throws Exception {
        int databaseSizeBeforeCreate = systemRepository.findAll().size();
        // Create the System
        restSystemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(system)))
            .andExpect(status().isCreated());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeCreate + 1);
        System testSystem = systemList.get(systemList.size() - 1);
        assertThat(testSystem.getQuestion1()).isEqualTo(DEFAULT_QUESTION_1);
        assertThat(testSystem.getQuestion2()).isEqualTo(DEFAULT_QUESTION_2);
        assertThat(testSystem.getQuestion3()).isEqualTo(DEFAULT_QUESTION_3);
        assertThat(testSystem.getQuestion4()).isEqualTo(DEFAULT_QUESTION_4);
        assertThat(testSystem.getQuestion5()).isEqualTo(DEFAULT_QUESTION_5);
    }

    @Test
    @Transactional
    void createSystemWithExistingId() throws Exception {
        // Create the System with an existing ID
        system.setId(1L);

        int databaseSizeBeforeCreate = systemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(system)))
            .andExpect(status().isBadRequest());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSystems() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList
        restSystemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(system.getId().intValue())))
            .andExpect(jsonPath("$.[*].question1").value(hasItem(DEFAULT_QUESTION_1)))
            .andExpect(jsonPath("$.[*].question2").value(hasItem(DEFAULT_QUESTION_2)))
            .andExpect(jsonPath("$.[*].question3").value(hasItem(DEFAULT_QUESTION_3)))
            .andExpect(jsonPath("$.[*].question4").value(hasItem(DEFAULT_QUESTION_4)))
            .andExpect(jsonPath("$.[*].question5").value(hasItem(DEFAULT_QUESTION_5)));
    }

    @Test
    @Transactional
    void getSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get the system
        restSystemMockMvc
            .perform(get(ENTITY_API_URL_ID, system.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(system.getId().intValue()))
            .andExpect(jsonPath("$.question1").value(DEFAULT_QUESTION_1))
            .andExpect(jsonPath("$.question2").value(DEFAULT_QUESTION_2))
            .andExpect(jsonPath("$.question3").value(DEFAULT_QUESTION_3))
            .andExpect(jsonPath("$.question4").value(DEFAULT_QUESTION_4))
            .andExpect(jsonPath("$.question5").value(DEFAULT_QUESTION_5));
    }

    @Test
    @Transactional
    void getSystemsByIdFiltering() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        Long id = system.getId();

        defaultSystemShouldBeFound("id.equals=" + id);
        defaultSystemShouldNotBeFound("id.notEquals=" + id);

        defaultSystemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSystemShouldNotBeFound("id.greaterThan=" + id);

        defaultSystemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSystemShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion1IsEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question1 equals to DEFAULT_QUESTION_1
        defaultSystemShouldBeFound("question1.equals=" + DEFAULT_QUESTION_1);

        // Get all the systemList where question1 equals to UPDATED_QUESTION_1
        defaultSystemShouldNotBeFound("question1.equals=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion1IsInShouldWork() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question1 in DEFAULT_QUESTION_1 or UPDATED_QUESTION_1
        defaultSystemShouldBeFound("question1.in=" + DEFAULT_QUESTION_1 + "," + UPDATED_QUESTION_1);

        // Get all the systemList where question1 equals to UPDATED_QUESTION_1
        defaultSystemShouldNotBeFound("question1.in=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion1IsNullOrNotNull() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question1 is not null
        defaultSystemShouldBeFound("question1.specified=true");

        // Get all the systemList where question1 is null
        defaultSystemShouldNotBeFound("question1.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion1ContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question1 contains DEFAULT_QUESTION_1
        defaultSystemShouldBeFound("question1.contains=" + DEFAULT_QUESTION_1);

        // Get all the systemList where question1 contains UPDATED_QUESTION_1
        defaultSystemShouldNotBeFound("question1.contains=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion1NotContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question1 does not contain DEFAULT_QUESTION_1
        defaultSystemShouldNotBeFound("question1.doesNotContain=" + DEFAULT_QUESTION_1);

        // Get all the systemList where question1 does not contain UPDATED_QUESTION_1
        defaultSystemShouldBeFound("question1.doesNotContain=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion2IsEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question2 equals to DEFAULT_QUESTION_2
        defaultSystemShouldBeFound("question2.equals=" + DEFAULT_QUESTION_2);

        // Get all the systemList where question2 equals to UPDATED_QUESTION_2
        defaultSystemShouldNotBeFound("question2.equals=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion2IsInShouldWork() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question2 in DEFAULT_QUESTION_2 or UPDATED_QUESTION_2
        defaultSystemShouldBeFound("question2.in=" + DEFAULT_QUESTION_2 + "," + UPDATED_QUESTION_2);

        // Get all the systemList where question2 equals to UPDATED_QUESTION_2
        defaultSystemShouldNotBeFound("question2.in=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion2IsNullOrNotNull() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question2 is not null
        defaultSystemShouldBeFound("question2.specified=true");

        // Get all the systemList where question2 is null
        defaultSystemShouldNotBeFound("question2.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion2ContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question2 contains DEFAULT_QUESTION_2
        defaultSystemShouldBeFound("question2.contains=" + DEFAULT_QUESTION_2);

        // Get all the systemList where question2 contains UPDATED_QUESTION_2
        defaultSystemShouldNotBeFound("question2.contains=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion2NotContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question2 does not contain DEFAULT_QUESTION_2
        defaultSystemShouldNotBeFound("question2.doesNotContain=" + DEFAULT_QUESTION_2);

        // Get all the systemList where question2 does not contain UPDATED_QUESTION_2
        defaultSystemShouldBeFound("question2.doesNotContain=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion3IsEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question3 equals to DEFAULT_QUESTION_3
        defaultSystemShouldBeFound("question3.equals=" + DEFAULT_QUESTION_3);

        // Get all the systemList where question3 equals to UPDATED_QUESTION_3
        defaultSystemShouldNotBeFound("question3.equals=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion3IsInShouldWork() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question3 in DEFAULT_QUESTION_3 or UPDATED_QUESTION_3
        defaultSystemShouldBeFound("question3.in=" + DEFAULT_QUESTION_3 + "," + UPDATED_QUESTION_3);

        // Get all the systemList where question3 equals to UPDATED_QUESTION_3
        defaultSystemShouldNotBeFound("question3.in=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion3IsNullOrNotNull() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question3 is not null
        defaultSystemShouldBeFound("question3.specified=true");

        // Get all the systemList where question3 is null
        defaultSystemShouldNotBeFound("question3.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion3ContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question3 contains DEFAULT_QUESTION_3
        defaultSystemShouldBeFound("question3.contains=" + DEFAULT_QUESTION_3);

        // Get all the systemList where question3 contains UPDATED_QUESTION_3
        defaultSystemShouldNotBeFound("question3.contains=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion3NotContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question3 does not contain DEFAULT_QUESTION_3
        defaultSystemShouldNotBeFound("question3.doesNotContain=" + DEFAULT_QUESTION_3);

        // Get all the systemList where question3 does not contain UPDATED_QUESTION_3
        defaultSystemShouldBeFound("question3.doesNotContain=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion4IsEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question4 equals to DEFAULT_QUESTION_4
        defaultSystemShouldBeFound("question4.equals=" + DEFAULT_QUESTION_4);

        // Get all the systemList where question4 equals to UPDATED_QUESTION_4
        defaultSystemShouldNotBeFound("question4.equals=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion4IsInShouldWork() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question4 in DEFAULT_QUESTION_4 or UPDATED_QUESTION_4
        defaultSystemShouldBeFound("question4.in=" + DEFAULT_QUESTION_4 + "," + UPDATED_QUESTION_4);

        // Get all the systemList where question4 equals to UPDATED_QUESTION_4
        defaultSystemShouldNotBeFound("question4.in=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion4IsNullOrNotNull() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question4 is not null
        defaultSystemShouldBeFound("question4.specified=true");

        // Get all the systemList where question4 is null
        defaultSystemShouldNotBeFound("question4.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion4ContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question4 contains DEFAULT_QUESTION_4
        defaultSystemShouldBeFound("question4.contains=" + DEFAULT_QUESTION_4);

        // Get all the systemList where question4 contains UPDATED_QUESTION_4
        defaultSystemShouldNotBeFound("question4.contains=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion4NotContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question4 does not contain DEFAULT_QUESTION_4
        defaultSystemShouldNotBeFound("question4.doesNotContain=" + DEFAULT_QUESTION_4);

        // Get all the systemList where question4 does not contain UPDATED_QUESTION_4
        defaultSystemShouldBeFound("question4.doesNotContain=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion5IsEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question5 equals to DEFAULT_QUESTION_5
        defaultSystemShouldBeFound("question5.equals=" + DEFAULT_QUESTION_5);

        // Get all the systemList where question5 equals to UPDATED_QUESTION_5
        defaultSystemShouldNotBeFound("question5.equals=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion5IsInShouldWork() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question5 in DEFAULT_QUESTION_5 or UPDATED_QUESTION_5
        defaultSystemShouldBeFound("question5.in=" + DEFAULT_QUESTION_5 + "," + UPDATED_QUESTION_5);

        // Get all the systemList where question5 equals to UPDATED_QUESTION_5
        defaultSystemShouldNotBeFound("question5.in=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion5IsNullOrNotNull() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question5 is not null
        defaultSystemShouldBeFound("question5.specified=true");

        // Get all the systemList where question5 is null
        defaultSystemShouldNotBeFound("question5.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion5ContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question5 contains DEFAULT_QUESTION_5
        defaultSystemShouldBeFound("question5.contains=" + DEFAULT_QUESTION_5);

        // Get all the systemList where question5 contains UPDATED_QUESTION_5
        defaultSystemShouldNotBeFound("question5.contains=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllSystemsByQuestion5NotContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where question5 does not contain DEFAULT_QUESTION_5
        defaultSystemShouldNotBeFound("question5.doesNotContain=" + DEFAULT_QUESTION_5);

        // Get all the systemList where question5 does not contain UPDATED_QUESTION_5
        defaultSystemShouldBeFound("question5.doesNotContain=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllSystemsByFormIsEqualToSomething() throws Exception {
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            systemRepository.saveAndFlush(system);
            form = FormResourceIT.createEntity(em);
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        em.persist(form);
        em.flush();
        system.setForm(form);
        systemRepository.saveAndFlush(system);
        Long formId = form.getId();
        // Get all the systemList where form equals to formId
        defaultSystemShouldBeFound("formId.equals=" + formId);

        // Get all the systemList where form equals to (formId + 1)
        defaultSystemShouldNotBeFound("formId.equals=" + (formId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSystemShouldBeFound(String filter) throws Exception {
        restSystemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(system.getId().intValue())))
            .andExpect(jsonPath("$.[*].question1").value(hasItem(DEFAULT_QUESTION_1)))
            .andExpect(jsonPath("$.[*].question2").value(hasItem(DEFAULT_QUESTION_2)))
            .andExpect(jsonPath("$.[*].question3").value(hasItem(DEFAULT_QUESTION_3)))
            .andExpect(jsonPath("$.[*].question4").value(hasItem(DEFAULT_QUESTION_4)))
            .andExpect(jsonPath("$.[*].question5").value(hasItem(DEFAULT_QUESTION_5)));

        // Check, that the count call also returns 1
        restSystemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSystemShouldNotBeFound(String filter) throws Exception {
        restSystemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSystemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSystem() throws Exception {
        // Get the system
        restSystemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        int databaseSizeBeforeUpdate = systemRepository.findAll().size();

        // Update the system
        System updatedSystem = systemRepository.findById(system.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSystem are not directly saved in db
        em.detach(updatedSystem);
        updatedSystem
            .question1(UPDATED_QUESTION_1)
            .question2(UPDATED_QUESTION_2)
            .question3(UPDATED_QUESTION_3)
            .question4(UPDATED_QUESTION_4)
            .question5(UPDATED_QUESTION_5);

        restSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSystem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSystem))
            )
            .andExpect(status().isOk());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
        System testSystem = systemList.get(systemList.size() - 1);
        assertThat(testSystem.getQuestion1()).isEqualTo(UPDATED_QUESTION_1);
        assertThat(testSystem.getQuestion2()).isEqualTo(UPDATED_QUESTION_2);
        assertThat(testSystem.getQuestion3()).isEqualTo(UPDATED_QUESTION_3);
        assertThat(testSystem.getQuestion4()).isEqualTo(UPDATED_QUESTION_4);
        assertThat(testSystem.getQuestion5()).isEqualTo(UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void putNonExistingSystem() throws Exception {
        int databaseSizeBeforeUpdate = systemRepository.findAll().size();
        system.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, system.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(system))
            )
            .andExpect(status().isBadRequest());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSystem() throws Exception {
        int databaseSizeBeforeUpdate = systemRepository.findAll().size();
        system.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(system))
            )
            .andExpect(status().isBadRequest());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSystem() throws Exception {
        int databaseSizeBeforeUpdate = systemRepository.findAll().size();
        system.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(system)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSystemWithPatch() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        int databaseSizeBeforeUpdate = systemRepository.findAll().size();

        // Update the system using partial update
        System partialUpdatedSystem = new System();
        partialUpdatedSystem.setId(system.getId());

        partialUpdatedSystem.question1(UPDATED_QUESTION_1).question2(UPDATED_QUESTION_2).question3(UPDATED_QUESTION_3);

        restSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSystem))
            )
            .andExpect(status().isOk());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
        System testSystem = systemList.get(systemList.size() - 1);
        assertThat(testSystem.getQuestion1()).isEqualTo(UPDATED_QUESTION_1);
        assertThat(testSystem.getQuestion2()).isEqualTo(UPDATED_QUESTION_2);
        assertThat(testSystem.getQuestion3()).isEqualTo(UPDATED_QUESTION_3);
        assertThat(testSystem.getQuestion4()).isEqualTo(DEFAULT_QUESTION_4);
        assertThat(testSystem.getQuestion5()).isEqualTo(DEFAULT_QUESTION_5);
    }

    @Test
    @Transactional
    void fullUpdateSystemWithPatch() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        int databaseSizeBeforeUpdate = systemRepository.findAll().size();

        // Update the system using partial update
        System partialUpdatedSystem = new System();
        partialUpdatedSystem.setId(system.getId());

        partialUpdatedSystem
            .question1(UPDATED_QUESTION_1)
            .question2(UPDATED_QUESTION_2)
            .question3(UPDATED_QUESTION_3)
            .question4(UPDATED_QUESTION_4)
            .question5(UPDATED_QUESTION_5);

        restSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSystem))
            )
            .andExpect(status().isOk());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
        System testSystem = systemList.get(systemList.size() - 1);
        assertThat(testSystem.getQuestion1()).isEqualTo(UPDATED_QUESTION_1);
        assertThat(testSystem.getQuestion2()).isEqualTo(UPDATED_QUESTION_2);
        assertThat(testSystem.getQuestion3()).isEqualTo(UPDATED_QUESTION_3);
        assertThat(testSystem.getQuestion4()).isEqualTo(UPDATED_QUESTION_4);
        assertThat(testSystem.getQuestion5()).isEqualTo(UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void patchNonExistingSystem() throws Exception {
        int databaseSizeBeforeUpdate = systemRepository.findAll().size();
        system.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, system.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(system))
            )
            .andExpect(status().isBadRequest());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSystem() throws Exception {
        int databaseSizeBeforeUpdate = systemRepository.findAll().size();
        system.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(system))
            )
            .andExpect(status().isBadRequest());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSystem() throws Exception {
        int databaseSizeBeforeUpdate = systemRepository.findAll().size();
        system.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(system)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        int databaseSizeBeforeDelete = systemRepository.findAll().size();

        // Delete the system
        restSystemMockMvc
            .perform(delete(ENTITY_API_URL_ID, system.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
