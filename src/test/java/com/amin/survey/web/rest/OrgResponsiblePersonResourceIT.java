package com.amin.survey.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amin.survey.IntegrationTest;
import com.amin.survey.domain.Form;
import com.amin.survey.domain.OrgResponsiblePerson;
import com.amin.survey.repository.OrgResponsiblePersonRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link OrgResponsiblePersonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrgResponsiblePersonResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/org-responsible-people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrgResponsiblePersonRepository orgResponsiblePersonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrgResponsiblePersonMockMvc;

    private OrgResponsiblePerson orgResponsiblePerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrgResponsiblePerson createEntity(EntityManager em) {
        OrgResponsiblePerson orgResponsiblePerson = new OrgResponsiblePerson()
            .fullName(DEFAULT_FULL_NAME)
            .position(DEFAULT_POSITION)
            .contact(DEFAULT_CONTACT)
            .date(DEFAULT_DATE);
        // Add required entity
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            form = FormResourceIT.createEntity(em);
            em.persist(form);
            em.flush();
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        orgResponsiblePerson.setForm(form);
        return orgResponsiblePerson;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrgResponsiblePerson createUpdatedEntity(EntityManager em) {
        OrgResponsiblePerson orgResponsiblePerson = new OrgResponsiblePerson()
            .fullName(UPDATED_FULL_NAME)
            .position(UPDATED_POSITION)
            .contact(UPDATED_CONTACT)
            .date(UPDATED_DATE);
        // Add required entity
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            form = FormResourceIT.createUpdatedEntity(em);
            em.persist(form);
            em.flush();
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        orgResponsiblePerson.setForm(form);
        return orgResponsiblePerson;
    }

    @BeforeEach
    public void initTest() {
        orgResponsiblePerson = createEntity(em);
    }

    @Test
    @Transactional
    void createOrgResponsiblePerson() throws Exception {
        int databaseSizeBeforeCreate = orgResponsiblePersonRepository.findAll().size();
        // Create the OrgResponsiblePerson
        restOrgResponsiblePersonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgResponsiblePerson))
            )
            .andExpect(status().isCreated());

        // Validate the OrgResponsiblePerson in the database
        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeCreate + 1);
        OrgResponsiblePerson testOrgResponsiblePerson = orgResponsiblePersonList.get(orgResponsiblePersonList.size() - 1);
        assertThat(testOrgResponsiblePerson.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testOrgResponsiblePerson.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testOrgResponsiblePerson.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testOrgResponsiblePerson.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createOrgResponsiblePersonWithExistingId() throws Exception {
        // Create the OrgResponsiblePerson with an existing ID
        orgResponsiblePerson.setId(1L);

        int databaseSizeBeforeCreate = orgResponsiblePersonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrgResponsiblePersonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgResponsiblePerson))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgResponsiblePerson in the database
        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = orgResponsiblePersonRepository.findAll().size();
        // set the field null
        orgResponsiblePerson.setFullName(null);

        // Create the OrgResponsiblePerson, which fails.

        restOrgResponsiblePersonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgResponsiblePerson))
            )
            .andExpect(status().isBadRequest());

        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = orgResponsiblePersonRepository.findAll().size();
        // set the field null
        orgResponsiblePerson.setPosition(null);

        // Create the OrgResponsiblePerson, which fails.

        restOrgResponsiblePersonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgResponsiblePerson))
            )
            .andExpect(status().isBadRequest());

        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = orgResponsiblePersonRepository.findAll().size();
        // set the field null
        orgResponsiblePerson.setContact(null);

        // Create the OrgResponsiblePerson, which fails.

        restOrgResponsiblePersonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgResponsiblePerson))
            )
            .andExpect(status().isBadRequest());

        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orgResponsiblePersonRepository.findAll().size();
        // set the field null
        orgResponsiblePerson.setDate(null);

        // Create the OrgResponsiblePerson, which fails.

        restOrgResponsiblePersonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgResponsiblePerson))
            )
            .andExpect(status().isBadRequest());

        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeople() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList
        restOrgResponsiblePersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgResponsiblePerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getOrgResponsiblePerson() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get the orgResponsiblePerson
        restOrgResponsiblePersonMockMvc
            .perform(get(ENTITY_API_URL_ID, orgResponsiblePerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orgResponsiblePerson.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getOrgResponsiblePeopleByIdFiltering() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        Long id = orgResponsiblePerson.getId();

        defaultOrgResponsiblePersonShouldBeFound("id.equals=" + id);
        defaultOrgResponsiblePersonShouldNotBeFound("id.notEquals=" + id);

        defaultOrgResponsiblePersonShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrgResponsiblePersonShouldNotBeFound("id.greaterThan=" + id);

        defaultOrgResponsiblePersonShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrgResponsiblePersonShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where fullName equals to DEFAULT_FULL_NAME
        defaultOrgResponsiblePersonShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the orgResponsiblePersonList where fullName equals to UPDATED_FULL_NAME
        defaultOrgResponsiblePersonShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultOrgResponsiblePersonShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the orgResponsiblePersonList where fullName equals to UPDATED_FULL_NAME
        defaultOrgResponsiblePersonShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where fullName is not null
        defaultOrgResponsiblePersonShouldBeFound("fullName.specified=true");

        // Get all the orgResponsiblePersonList where fullName is null
        defaultOrgResponsiblePersonShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByFullNameContainsSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where fullName contains DEFAULT_FULL_NAME
        defaultOrgResponsiblePersonShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the orgResponsiblePersonList where fullName contains UPDATED_FULL_NAME
        defaultOrgResponsiblePersonShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where fullName does not contain DEFAULT_FULL_NAME
        defaultOrgResponsiblePersonShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the orgResponsiblePersonList where fullName does not contain UPDATED_FULL_NAME
        defaultOrgResponsiblePersonShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where position equals to DEFAULT_POSITION
        defaultOrgResponsiblePersonShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the orgResponsiblePersonList where position equals to UPDATED_POSITION
        defaultOrgResponsiblePersonShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultOrgResponsiblePersonShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the orgResponsiblePersonList where position equals to UPDATED_POSITION
        defaultOrgResponsiblePersonShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where position is not null
        defaultOrgResponsiblePersonShouldBeFound("position.specified=true");

        // Get all the orgResponsiblePersonList where position is null
        defaultOrgResponsiblePersonShouldNotBeFound("position.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByPositionContainsSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where position contains DEFAULT_POSITION
        defaultOrgResponsiblePersonShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the orgResponsiblePersonList where position contains UPDATED_POSITION
        defaultOrgResponsiblePersonShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where position does not contain DEFAULT_POSITION
        defaultOrgResponsiblePersonShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the orgResponsiblePersonList where position does not contain UPDATED_POSITION
        defaultOrgResponsiblePersonShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where contact equals to DEFAULT_CONTACT
        defaultOrgResponsiblePersonShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the orgResponsiblePersonList where contact equals to UPDATED_CONTACT
        defaultOrgResponsiblePersonShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByContactIsInShouldWork() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultOrgResponsiblePersonShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the orgResponsiblePersonList where contact equals to UPDATED_CONTACT
        defaultOrgResponsiblePersonShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where contact is not null
        defaultOrgResponsiblePersonShouldBeFound("contact.specified=true");

        // Get all the orgResponsiblePersonList where contact is null
        defaultOrgResponsiblePersonShouldNotBeFound("contact.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByContactContainsSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where contact contains DEFAULT_CONTACT
        defaultOrgResponsiblePersonShouldBeFound("contact.contains=" + DEFAULT_CONTACT);

        // Get all the orgResponsiblePersonList where contact contains UPDATED_CONTACT
        defaultOrgResponsiblePersonShouldNotBeFound("contact.contains=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByContactNotContainsSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where contact does not contain DEFAULT_CONTACT
        defaultOrgResponsiblePersonShouldNotBeFound("contact.doesNotContain=" + DEFAULT_CONTACT);

        // Get all the orgResponsiblePersonList where contact does not contain UPDATED_CONTACT
        defaultOrgResponsiblePersonShouldBeFound("contact.doesNotContain=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where date equals to DEFAULT_DATE
        defaultOrgResponsiblePersonShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the orgResponsiblePersonList where date equals to UPDATED_DATE
        defaultOrgResponsiblePersonShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByDateIsInShouldWork() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where date in DEFAULT_DATE or UPDATED_DATE
        defaultOrgResponsiblePersonShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the orgResponsiblePersonList where date equals to UPDATED_DATE
        defaultOrgResponsiblePersonShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where date is not null
        defaultOrgResponsiblePersonShouldBeFound("date.specified=true");

        // Get all the orgResponsiblePersonList where date is null
        defaultOrgResponsiblePersonShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where date is greater than or equal to DEFAULT_DATE
        defaultOrgResponsiblePersonShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the orgResponsiblePersonList where date is greater than or equal to UPDATED_DATE
        defaultOrgResponsiblePersonShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where date is less than or equal to DEFAULT_DATE
        defaultOrgResponsiblePersonShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the orgResponsiblePersonList where date is less than or equal to SMALLER_DATE
        defaultOrgResponsiblePersonShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where date is less than DEFAULT_DATE
        defaultOrgResponsiblePersonShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the orgResponsiblePersonList where date is less than UPDATED_DATE
        defaultOrgResponsiblePersonShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        // Get all the orgResponsiblePersonList where date is greater than DEFAULT_DATE
        defaultOrgResponsiblePersonShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the orgResponsiblePersonList where date is greater than SMALLER_DATE
        defaultOrgResponsiblePersonShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllOrgResponsiblePeopleByFormIsEqualToSomething() throws Exception {
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);
            form = FormResourceIT.createEntity(em);
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        em.persist(form);
        em.flush();
        orgResponsiblePerson.setForm(form);
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);
        Long formId = form.getId();
        // Get all the orgResponsiblePersonList where form equals to formId
        defaultOrgResponsiblePersonShouldBeFound("formId.equals=" + formId);

        // Get all the orgResponsiblePersonList where form equals to (formId + 1)
        defaultOrgResponsiblePersonShouldNotBeFound("formId.equals=" + (formId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrgResponsiblePersonShouldBeFound(String filter) throws Exception {
        restOrgResponsiblePersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgResponsiblePerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restOrgResponsiblePersonMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrgResponsiblePersonShouldNotBeFound(String filter) throws Exception {
        restOrgResponsiblePersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrgResponsiblePersonMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrgResponsiblePerson() throws Exception {
        // Get the orgResponsiblePerson
        restOrgResponsiblePersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrgResponsiblePerson() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        int databaseSizeBeforeUpdate = orgResponsiblePersonRepository.findAll().size();

        // Update the orgResponsiblePerson
        OrgResponsiblePerson updatedOrgResponsiblePerson = orgResponsiblePersonRepository
            .findById(orgResponsiblePerson.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOrgResponsiblePerson are not directly saved in db
        em.detach(updatedOrgResponsiblePerson);
        updatedOrgResponsiblePerson.fullName(UPDATED_FULL_NAME).position(UPDATED_POSITION).contact(UPDATED_CONTACT).date(UPDATED_DATE);

        restOrgResponsiblePersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrgResponsiblePerson.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrgResponsiblePerson))
            )
            .andExpect(status().isOk());

        // Validate the OrgResponsiblePerson in the database
        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeUpdate);
        OrgResponsiblePerson testOrgResponsiblePerson = orgResponsiblePersonList.get(orgResponsiblePersonList.size() - 1);
        assertThat(testOrgResponsiblePerson.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testOrgResponsiblePerson.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testOrgResponsiblePerson.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testOrgResponsiblePerson.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOrgResponsiblePerson() throws Exception {
        int databaseSizeBeforeUpdate = orgResponsiblePersonRepository.findAll().size();
        orgResponsiblePerson.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgResponsiblePersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orgResponsiblePerson.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgResponsiblePerson))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgResponsiblePerson in the database
        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrgResponsiblePerson() throws Exception {
        int databaseSizeBeforeUpdate = orgResponsiblePersonRepository.findAll().size();
        orgResponsiblePerson.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgResponsiblePersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgResponsiblePerson))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgResponsiblePerson in the database
        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrgResponsiblePerson() throws Exception {
        int databaseSizeBeforeUpdate = orgResponsiblePersonRepository.findAll().size();
        orgResponsiblePerson.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgResponsiblePersonMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgResponsiblePerson))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrgResponsiblePerson in the database
        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrgResponsiblePersonWithPatch() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        int databaseSizeBeforeUpdate = orgResponsiblePersonRepository.findAll().size();

        // Update the orgResponsiblePerson using partial update
        OrgResponsiblePerson partialUpdatedOrgResponsiblePerson = new OrgResponsiblePerson();
        partialUpdatedOrgResponsiblePerson.setId(orgResponsiblePerson.getId());

        partialUpdatedOrgResponsiblePerson.position(UPDATED_POSITION).contact(UPDATED_CONTACT);

        restOrgResponsiblePersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrgResponsiblePerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrgResponsiblePerson))
            )
            .andExpect(status().isOk());

        // Validate the OrgResponsiblePerson in the database
        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeUpdate);
        OrgResponsiblePerson testOrgResponsiblePerson = orgResponsiblePersonList.get(orgResponsiblePersonList.size() - 1);
        assertThat(testOrgResponsiblePerson.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testOrgResponsiblePerson.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testOrgResponsiblePerson.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testOrgResponsiblePerson.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOrgResponsiblePersonWithPatch() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        int databaseSizeBeforeUpdate = orgResponsiblePersonRepository.findAll().size();

        // Update the orgResponsiblePerson using partial update
        OrgResponsiblePerson partialUpdatedOrgResponsiblePerson = new OrgResponsiblePerson();
        partialUpdatedOrgResponsiblePerson.setId(orgResponsiblePerson.getId());

        partialUpdatedOrgResponsiblePerson
            .fullName(UPDATED_FULL_NAME)
            .position(UPDATED_POSITION)
            .contact(UPDATED_CONTACT)
            .date(UPDATED_DATE);

        restOrgResponsiblePersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrgResponsiblePerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrgResponsiblePerson))
            )
            .andExpect(status().isOk());

        // Validate the OrgResponsiblePerson in the database
        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeUpdate);
        OrgResponsiblePerson testOrgResponsiblePerson = orgResponsiblePersonList.get(orgResponsiblePersonList.size() - 1);
        assertThat(testOrgResponsiblePerson.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testOrgResponsiblePerson.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testOrgResponsiblePerson.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testOrgResponsiblePerson.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrgResponsiblePerson() throws Exception {
        int databaseSizeBeforeUpdate = orgResponsiblePersonRepository.findAll().size();
        orgResponsiblePerson.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgResponsiblePersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orgResponsiblePerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgResponsiblePerson))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgResponsiblePerson in the database
        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrgResponsiblePerson() throws Exception {
        int databaseSizeBeforeUpdate = orgResponsiblePersonRepository.findAll().size();
        orgResponsiblePerson.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgResponsiblePersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgResponsiblePerson))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgResponsiblePerson in the database
        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrgResponsiblePerson() throws Exception {
        int databaseSizeBeforeUpdate = orgResponsiblePersonRepository.findAll().size();
        orgResponsiblePerson.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgResponsiblePersonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgResponsiblePerson))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrgResponsiblePerson in the database
        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrgResponsiblePerson() throws Exception {
        // Initialize the database
        orgResponsiblePersonRepository.saveAndFlush(orgResponsiblePerson);

        int databaseSizeBeforeDelete = orgResponsiblePersonRepository.findAll().size();

        // Delete the orgResponsiblePerson
        restOrgResponsiblePersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, orgResponsiblePerson.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrgResponsiblePerson> orgResponsiblePersonList = orgResponsiblePersonRepository.findAll();
        assertThat(orgResponsiblePersonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
