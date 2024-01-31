package com.amin.survey.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amin.survey.IntegrationTest;
import com.amin.survey.domain.Backup;
import com.amin.survey.domain.Form;
import com.amin.survey.repository.BackupRepository;
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
 * Integration tests for the {@link BackupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BackupResourceIT {

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

    private static final String ENTITY_API_URL = "/api/backups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BackupRepository backupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBackupMockMvc;

    private Backup backup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Backup createEntity(EntityManager em) {
        Backup backup = new Backup()
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
        backup.setForm(form);
        return backup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Backup createUpdatedEntity(EntityManager em) {
        Backup backup = new Backup()
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
        backup.setForm(form);
        return backup;
    }

    @BeforeEach
    public void initTest() {
        backup = createEntity(em);
    }

    @Test
    @Transactional
    void createBackup() throws Exception {
        int databaseSizeBeforeCreate = backupRepository.findAll().size();
        // Create the Backup
        restBackupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(backup)))
            .andExpect(status().isCreated());

        // Validate the Backup in the database
        List<Backup> backupList = backupRepository.findAll();
        assertThat(backupList).hasSize(databaseSizeBeforeCreate + 1);
        Backup testBackup = backupList.get(backupList.size() - 1);
        assertThat(testBackup.getQuestion1()).isEqualTo(DEFAULT_QUESTION_1);
        assertThat(testBackup.getQuestion2()).isEqualTo(DEFAULT_QUESTION_2);
        assertThat(testBackup.getQuestion3()).isEqualTo(DEFAULT_QUESTION_3);
        assertThat(testBackup.getQuestion4()).isEqualTo(DEFAULT_QUESTION_4);
        assertThat(testBackup.getQuestion5()).isEqualTo(DEFAULT_QUESTION_5);
        assertThat(testBackup.getQuestion6()).isEqualTo(DEFAULT_QUESTION_6);
        assertThat(testBackup.getQuestion7()).isEqualTo(DEFAULT_QUESTION_7);
    }

    @Test
    @Transactional
    void createBackupWithExistingId() throws Exception {
        // Create the Backup with an existing ID
        backup.setId(1L);

        int databaseSizeBeforeCreate = backupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBackupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(backup)))
            .andExpect(status().isBadRequest());

        // Validate the Backup in the database
        List<Backup> backupList = backupRepository.findAll();
        assertThat(backupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBackups() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList
        restBackupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(backup.getId().intValue())))
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
    void getBackup() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get the backup
        restBackupMockMvc
            .perform(get(ENTITY_API_URL_ID, backup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(backup.getId().intValue()))
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
    void getBackupsByIdFiltering() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        Long id = backup.getId();

        defaultBackupShouldBeFound("id.equals=" + id);
        defaultBackupShouldNotBeFound("id.notEquals=" + id);

        defaultBackupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBackupShouldNotBeFound("id.greaterThan=" + id);

        defaultBackupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBackupShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion1IsEqualToSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question1 equals to DEFAULT_QUESTION_1
        defaultBackupShouldBeFound("question1.equals=" + DEFAULT_QUESTION_1);

        // Get all the backupList where question1 equals to UPDATED_QUESTION_1
        defaultBackupShouldNotBeFound("question1.equals=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion1IsInShouldWork() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question1 in DEFAULT_QUESTION_1 or UPDATED_QUESTION_1
        defaultBackupShouldBeFound("question1.in=" + DEFAULT_QUESTION_1 + "," + UPDATED_QUESTION_1);

        // Get all the backupList where question1 equals to UPDATED_QUESTION_1
        defaultBackupShouldNotBeFound("question1.in=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion1IsNullOrNotNull() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question1 is not null
        defaultBackupShouldBeFound("question1.specified=true");

        // Get all the backupList where question1 is null
        defaultBackupShouldNotBeFound("question1.specified=false");
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion1ContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question1 contains DEFAULT_QUESTION_1
        defaultBackupShouldBeFound("question1.contains=" + DEFAULT_QUESTION_1);

        // Get all the backupList where question1 contains UPDATED_QUESTION_1
        defaultBackupShouldNotBeFound("question1.contains=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion1NotContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question1 does not contain DEFAULT_QUESTION_1
        defaultBackupShouldNotBeFound("question1.doesNotContain=" + DEFAULT_QUESTION_1);

        // Get all the backupList where question1 does not contain UPDATED_QUESTION_1
        defaultBackupShouldBeFound("question1.doesNotContain=" + UPDATED_QUESTION_1);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion2IsEqualToSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question2 equals to DEFAULT_QUESTION_2
        defaultBackupShouldBeFound("question2.equals=" + DEFAULT_QUESTION_2);

        // Get all the backupList where question2 equals to UPDATED_QUESTION_2
        defaultBackupShouldNotBeFound("question2.equals=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion2IsInShouldWork() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question2 in DEFAULT_QUESTION_2 or UPDATED_QUESTION_2
        defaultBackupShouldBeFound("question2.in=" + DEFAULT_QUESTION_2 + "," + UPDATED_QUESTION_2);

        // Get all the backupList where question2 equals to UPDATED_QUESTION_2
        defaultBackupShouldNotBeFound("question2.in=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion2IsNullOrNotNull() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question2 is not null
        defaultBackupShouldBeFound("question2.specified=true");

        // Get all the backupList where question2 is null
        defaultBackupShouldNotBeFound("question2.specified=false");
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion2ContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question2 contains DEFAULT_QUESTION_2
        defaultBackupShouldBeFound("question2.contains=" + DEFAULT_QUESTION_2);

        // Get all the backupList where question2 contains UPDATED_QUESTION_2
        defaultBackupShouldNotBeFound("question2.contains=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion2NotContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question2 does not contain DEFAULT_QUESTION_2
        defaultBackupShouldNotBeFound("question2.doesNotContain=" + DEFAULT_QUESTION_2);

        // Get all the backupList where question2 does not contain UPDATED_QUESTION_2
        defaultBackupShouldBeFound("question2.doesNotContain=" + UPDATED_QUESTION_2);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion3IsEqualToSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question3 equals to DEFAULT_QUESTION_3
        defaultBackupShouldBeFound("question3.equals=" + DEFAULT_QUESTION_3);

        // Get all the backupList where question3 equals to UPDATED_QUESTION_3
        defaultBackupShouldNotBeFound("question3.equals=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion3IsInShouldWork() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question3 in DEFAULT_QUESTION_3 or UPDATED_QUESTION_3
        defaultBackupShouldBeFound("question3.in=" + DEFAULT_QUESTION_3 + "," + UPDATED_QUESTION_3);

        // Get all the backupList where question3 equals to UPDATED_QUESTION_3
        defaultBackupShouldNotBeFound("question3.in=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion3IsNullOrNotNull() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question3 is not null
        defaultBackupShouldBeFound("question3.specified=true");

        // Get all the backupList where question3 is null
        defaultBackupShouldNotBeFound("question3.specified=false");
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion3ContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question3 contains DEFAULT_QUESTION_3
        defaultBackupShouldBeFound("question3.contains=" + DEFAULT_QUESTION_3);

        // Get all the backupList where question3 contains UPDATED_QUESTION_3
        defaultBackupShouldNotBeFound("question3.contains=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion3NotContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question3 does not contain DEFAULT_QUESTION_3
        defaultBackupShouldNotBeFound("question3.doesNotContain=" + DEFAULT_QUESTION_3);

        // Get all the backupList where question3 does not contain UPDATED_QUESTION_3
        defaultBackupShouldBeFound("question3.doesNotContain=" + UPDATED_QUESTION_3);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion4IsEqualToSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question4 equals to DEFAULT_QUESTION_4
        defaultBackupShouldBeFound("question4.equals=" + DEFAULT_QUESTION_4);

        // Get all the backupList where question4 equals to UPDATED_QUESTION_4
        defaultBackupShouldNotBeFound("question4.equals=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion4IsInShouldWork() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question4 in DEFAULT_QUESTION_4 or UPDATED_QUESTION_4
        defaultBackupShouldBeFound("question4.in=" + DEFAULT_QUESTION_4 + "," + UPDATED_QUESTION_4);

        // Get all the backupList where question4 equals to UPDATED_QUESTION_4
        defaultBackupShouldNotBeFound("question4.in=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion4IsNullOrNotNull() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question4 is not null
        defaultBackupShouldBeFound("question4.specified=true");

        // Get all the backupList where question4 is null
        defaultBackupShouldNotBeFound("question4.specified=false");
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion4ContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question4 contains DEFAULT_QUESTION_4
        defaultBackupShouldBeFound("question4.contains=" + DEFAULT_QUESTION_4);

        // Get all the backupList where question4 contains UPDATED_QUESTION_4
        defaultBackupShouldNotBeFound("question4.contains=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion4NotContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question4 does not contain DEFAULT_QUESTION_4
        defaultBackupShouldNotBeFound("question4.doesNotContain=" + DEFAULT_QUESTION_4);

        // Get all the backupList where question4 does not contain UPDATED_QUESTION_4
        defaultBackupShouldBeFound("question4.doesNotContain=" + UPDATED_QUESTION_4);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion5IsEqualToSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question5 equals to DEFAULT_QUESTION_5
        defaultBackupShouldBeFound("question5.equals=" + DEFAULT_QUESTION_5);

        // Get all the backupList where question5 equals to UPDATED_QUESTION_5
        defaultBackupShouldNotBeFound("question5.equals=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion5IsInShouldWork() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question5 in DEFAULT_QUESTION_5 or UPDATED_QUESTION_5
        defaultBackupShouldBeFound("question5.in=" + DEFAULT_QUESTION_5 + "," + UPDATED_QUESTION_5);

        // Get all the backupList where question5 equals to UPDATED_QUESTION_5
        defaultBackupShouldNotBeFound("question5.in=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion5IsNullOrNotNull() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question5 is not null
        defaultBackupShouldBeFound("question5.specified=true");

        // Get all the backupList where question5 is null
        defaultBackupShouldNotBeFound("question5.specified=false");
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion5ContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question5 contains DEFAULT_QUESTION_5
        defaultBackupShouldBeFound("question5.contains=" + DEFAULT_QUESTION_5);

        // Get all the backupList where question5 contains UPDATED_QUESTION_5
        defaultBackupShouldNotBeFound("question5.contains=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion5NotContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question5 does not contain DEFAULT_QUESTION_5
        defaultBackupShouldNotBeFound("question5.doesNotContain=" + DEFAULT_QUESTION_5);

        // Get all the backupList where question5 does not contain UPDATED_QUESTION_5
        defaultBackupShouldBeFound("question5.doesNotContain=" + UPDATED_QUESTION_5);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion6IsEqualToSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question6 equals to DEFAULT_QUESTION_6
        defaultBackupShouldBeFound("question6.equals=" + DEFAULT_QUESTION_6);

        // Get all the backupList where question6 equals to UPDATED_QUESTION_6
        defaultBackupShouldNotBeFound("question6.equals=" + UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion6IsInShouldWork() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question6 in DEFAULT_QUESTION_6 or UPDATED_QUESTION_6
        defaultBackupShouldBeFound("question6.in=" + DEFAULT_QUESTION_6 + "," + UPDATED_QUESTION_6);

        // Get all the backupList where question6 equals to UPDATED_QUESTION_6
        defaultBackupShouldNotBeFound("question6.in=" + UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion6IsNullOrNotNull() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question6 is not null
        defaultBackupShouldBeFound("question6.specified=true");

        // Get all the backupList where question6 is null
        defaultBackupShouldNotBeFound("question6.specified=false");
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion6ContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question6 contains DEFAULT_QUESTION_6
        defaultBackupShouldBeFound("question6.contains=" + DEFAULT_QUESTION_6);

        // Get all the backupList where question6 contains UPDATED_QUESTION_6
        defaultBackupShouldNotBeFound("question6.contains=" + UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion6NotContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question6 does not contain DEFAULT_QUESTION_6
        defaultBackupShouldNotBeFound("question6.doesNotContain=" + DEFAULT_QUESTION_6);

        // Get all the backupList where question6 does not contain UPDATED_QUESTION_6
        defaultBackupShouldBeFound("question6.doesNotContain=" + UPDATED_QUESTION_6);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion7IsEqualToSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question7 equals to DEFAULT_QUESTION_7
        defaultBackupShouldBeFound("question7.equals=" + DEFAULT_QUESTION_7);

        // Get all the backupList where question7 equals to UPDATED_QUESTION_7
        defaultBackupShouldNotBeFound("question7.equals=" + UPDATED_QUESTION_7);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion7IsInShouldWork() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question7 in DEFAULT_QUESTION_7 or UPDATED_QUESTION_7
        defaultBackupShouldBeFound("question7.in=" + DEFAULT_QUESTION_7 + "," + UPDATED_QUESTION_7);

        // Get all the backupList where question7 equals to UPDATED_QUESTION_7
        defaultBackupShouldNotBeFound("question7.in=" + UPDATED_QUESTION_7);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion7IsNullOrNotNull() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question7 is not null
        defaultBackupShouldBeFound("question7.specified=true");

        // Get all the backupList where question7 is null
        defaultBackupShouldNotBeFound("question7.specified=false");
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion7ContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question7 contains DEFAULT_QUESTION_7
        defaultBackupShouldBeFound("question7.contains=" + DEFAULT_QUESTION_7);

        // Get all the backupList where question7 contains UPDATED_QUESTION_7
        defaultBackupShouldNotBeFound("question7.contains=" + UPDATED_QUESTION_7);
    }

    @Test
    @Transactional
    void getAllBackupsByQuestion7NotContainsSomething() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        // Get all the backupList where question7 does not contain DEFAULT_QUESTION_7
        defaultBackupShouldNotBeFound("question7.doesNotContain=" + DEFAULT_QUESTION_7);

        // Get all the backupList where question7 does not contain UPDATED_QUESTION_7
        defaultBackupShouldBeFound("question7.doesNotContain=" + UPDATED_QUESTION_7);
    }

    @Test
    @Transactional
    void getAllBackupsByFormIsEqualToSomething() throws Exception {
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            backupRepository.saveAndFlush(backup);
            form = FormResourceIT.createEntity(em);
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        em.persist(form);
        em.flush();
        backup.setForm(form);
        backupRepository.saveAndFlush(backup);
        Long formId = form.getId();
        // Get all the backupList where form equals to formId
        defaultBackupShouldBeFound("formId.equals=" + formId);

        // Get all the backupList where form equals to (formId + 1)
        defaultBackupShouldNotBeFound("formId.equals=" + (formId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBackupShouldBeFound(String filter) throws Exception {
        restBackupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(backup.getId().intValue())))
            .andExpect(jsonPath("$.[*].question1").value(hasItem(DEFAULT_QUESTION_1)))
            .andExpect(jsonPath("$.[*].question2").value(hasItem(DEFAULT_QUESTION_2)))
            .andExpect(jsonPath("$.[*].question3").value(hasItem(DEFAULT_QUESTION_3)))
            .andExpect(jsonPath("$.[*].question4").value(hasItem(DEFAULT_QUESTION_4)))
            .andExpect(jsonPath("$.[*].question5").value(hasItem(DEFAULT_QUESTION_5)))
            .andExpect(jsonPath("$.[*].question6").value(hasItem(DEFAULT_QUESTION_6)))
            .andExpect(jsonPath("$.[*].question7").value(hasItem(DEFAULT_QUESTION_7)));

        // Check, that the count call also returns 1
        restBackupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBackupShouldNotBeFound(String filter) throws Exception {
        restBackupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBackupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBackup() throws Exception {
        // Get the backup
        restBackupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBackup() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        int databaseSizeBeforeUpdate = backupRepository.findAll().size();

        // Update the backup
        Backup updatedBackup = backupRepository.findById(backup.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBackup are not directly saved in db
        em.detach(updatedBackup);
        updatedBackup
            .question1(UPDATED_QUESTION_1)
            .question2(UPDATED_QUESTION_2)
            .question3(UPDATED_QUESTION_3)
            .question4(UPDATED_QUESTION_4)
            .question5(UPDATED_QUESTION_5)
            .question6(UPDATED_QUESTION_6)
            .question7(UPDATED_QUESTION_7);

        restBackupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBackup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBackup))
            )
            .andExpect(status().isOk());

        // Validate the Backup in the database
        List<Backup> backupList = backupRepository.findAll();
        assertThat(backupList).hasSize(databaseSizeBeforeUpdate);
        Backup testBackup = backupList.get(backupList.size() - 1);
        assertThat(testBackup.getQuestion1()).isEqualTo(UPDATED_QUESTION_1);
        assertThat(testBackup.getQuestion2()).isEqualTo(UPDATED_QUESTION_2);
        assertThat(testBackup.getQuestion3()).isEqualTo(UPDATED_QUESTION_3);
        assertThat(testBackup.getQuestion4()).isEqualTo(UPDATED_QUESTION_4);
        assertThat(testBackup.getQuestion5()).isEqualTo(UPDATED_QUESTION_5);
        assertThat(testBackup.getQuestion6()).isEqualTo(UPDATED_QUESTION_6);
        assertThat(testBackup.getQuestion7()).isEqualTo(UPDATED_QUESTION_7);
    }

    @Test
    @Transactional
    void putNonExistingBackup() throws Exception {
        int databaseSizeBeforeUpdate = backupRepository.findAll().size();
        backup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBackupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, backup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(backup))
            )
            .andExpect(status().isBadRequest());

        // Validate the Backup in the database
        List<Backup> backupList = backupRepository.findAll();
        assertThat(backupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBackup() throws Exception {
        int databaseSizeBeforeUpdate = backupRepository.findAll().size();
        backup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBackupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(backup))
            )
            .andExpect(status().isBadRequest());

        // Validate the Backup in the database
        List<Backup> backupList = backupRepository.findAll();
        assertThat(backupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBackup() throws Exception {
        int databaseSizeBeforeUpdate = backupRepository.findAll().size();
        backup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBackupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(backup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Backup in the database
        List<Backup> backupList = backupRepository.findAll();
        assertThat(backupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBackupWithPatch() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        int databaseSizeBeforeUpdate = backupRepository.findAll().size();

        // Update the backup using partial update
        Backup partialUpdatedBackup = new Backup();
        partialUpdatedBackup.setId(backup.getId());

        partialUpdatedBackup.question3(UPDATED_QUESTION_3).question5(UPDATED_QUESTION_5);

        restBackupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBackup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBackup))
            )
            .andExpect(status().isOk());

        // Validate the Backup in the database
        List<Backup> backupList = backupRepository.findAll();
        assertThat(backupList).hasSize(databaseSizeBeforeUpdate);
        Backup testBackup = backupList.get(backupList.size() - 1);
        assertThat(testBackup.getQuestion1()).isEqualTo(DEFAULT_QUESTION_1);
        assertThat(testBackup.getQuestion2()).isEqualTo(DEFAULT_QUESTION_2);
        assertThat(testBackup.getQuestion3()).isEqualTo(UPDATED_QUESTION_3);
        assertThat(testBackup.getQuestion4()).isEqualTo(DEFAULT_QUESTION_4);
        assertThat(testBackup.getQuestion5()).isEqualTo(UPDATED_QUESTION_5);
        assertThat(testBackup.getQuestion6()).isEqualTo(DEFAULT_QUESTION_6);
        assertThat(testBackup.getQuestion7()).isEqualTo(DEFAULT_QUESTION_7);
    }

    @Test
    @Transactional
    void fullUpdateBackupWithPatch() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        int databaseSizeBeforeUpdate = backupRepository.findAll().size();

        // Update the backup using partial update
        Backup partialUpdatedBackup = new Backup();
        partialUpdatedBackup.setId(backup.getId());

        partialUpdatedBackup
            .question1(UPDATED_QUESTION_1)
            .question2(UPDATED_QUESTION_2)
            .question3(UPDATED_QUESTION_3)
            .question4(UPDATED_QUESTION_4)
            .question5(UPDATED_QUESTION_5)
            .question6(UPDATED_QUESTION_6)
            .question7(UPDATED_QUESTION_7);

        restBackupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBackup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBackup))
            )
            .andExpect(status().isOk());

        // Validate the Backup in the database
        List<Backup> backupList = backupRepository.findAll();
        assertThat(backupList).hasSize(databaseSizeBeforeUpdate);
        Backup testBackup = backupList.get(backupList.size() - 1);
        assertThat(testBackup.getQuestion1()).isEqualTo(UPDATED_QUESTION_1);
        assertThat(testBackup.getQuestion2()).isEqualTo(UPDATED_QUESTION_2);
        assertThat(testBackup.getQuestion3()).isEqualTo(UPDATED_QUESTION_3);
        assertThat(testBackup.getQuestion4()).isEqualTo(UPDATED_QUESTION_4);
        assertThat(testBackup.getQuestion5()).isEqualTo(UPDATED_QUESTION_5);
        assertThat(testBackup.getQuestion6()).isEqualTo(UPDATED_QUESTION_6);
        assertThat(testBackup.getQuestion7()).isEqualTo(UPDATED_QUESTION_7);
    }

    @Test
    @Transactional
    void patchNonExistingBackup() throws Exception {
        int databaseSizeBeforeUpdate = backupRepository.findAll().size();
        backup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBackupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, backup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(backup))
            )
            .andExpect(status().isBadRequest());

        // Validate the Backup in the database
        List<Backup> backupList = backupRepository.findAll();
        assertThat(backupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBackup() throws Exception {
        int databaseSizeBeforeUpdate = backupRepository.findAll().size();
        backup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBackupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(backup))
            )
            .andExpect(status().isBadRequest());

        // Validate the Backup in the database
        List<Backup> backupList = backupRepository.findAll();
        assertThat(backupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBackup() throws Exception {
        int databaseSizeBeforeUpdate = backupRepository.findAll().size();
        backup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBackupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(backup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Backup in the database
        List<Backup> backupList = backupRepository.findAll();
        assertThat(backupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBackup() throws Exception {
        // Initialize the database
        backupRepository.saveAndFlush(backup);

        int databaseSizeBeforeDelete = backupRepository.findAll().size();

        // Delete the backup
        restBackupMockMvc
            .perform(delete(ENTITY_API_URL_ID, backup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Backup> backupList = backupRepository.findAll();
        assertThat(backupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
