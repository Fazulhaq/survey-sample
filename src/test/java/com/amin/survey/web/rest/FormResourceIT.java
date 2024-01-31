package com.amin.survey.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amin.survey.IntegrationTest;
import com.amin.survey.domain.Form;
import com.amin.survey.domain.Organization;
import com.amin.survey.domain.User;
import com.amin.survey.domain.enumeration.FormStatus;
import com.amin.survey.repository.FormRepository;
import com.amin.survey.service.FormService;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FormResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FormResourceIT {

    private static final String DEFAULT_FUTURE_PLAN = "AAAAAAAAAA";
    private static final String UPDATED_FUTURE_PLAN = "BBBBBBBBBB";

    private static final FormStatus DEFAULT_STATUS = FormStatus.INPROGRESS;
    private static final FormStatus UPDATED_STATUS = FormStatus.SUBMITTED;

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/forms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FormRepository formRepository;

    @Mock
    private FormRepository formRepositoryMock;

    @Mock
    private FormService formServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormMockMvc;

    private Form form;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Form createEntity(EntityManager em) {
        Form form = new Form()
            .futurePlan(DEFAULT_FUTURE_PLAN)
            .status(DEFAULT_STATUS)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        form.setUser(user);
        // Add required entity
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            organization = OrganizationResourceIT.createEntity(em);
            em.persist(organization);
            em.flush();
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        form.setOrganization(organization);
        return form;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Form createUpdatedEntity(EntityManager em) {
        Form form = new Form()
            .futurePlan(UPDATED_FUTURE_PLAN)
            .status(UPDATED_STATUS)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        form.setUser(user);
        // Add required entity
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            organization = OrganizationResourceIT.createUpdatedEntity(em);
            em.persist(organization);
            em.flush();
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        form.setOrganization(organization);
        return form;
    }

    @BeforeEach
    public void initTest() {
        form = createEntity(em);
    }

    @Test
    @Transactional
    void createForm() throws Exception {
        int databaseSizeBeforeCreate = formRepository.findAll().size();
        // Create the Form
        restFormMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(form)))
            .andExpect(status().isCreated());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeCreate + 1);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getFuturePlan()).isEqualTo(DEFAULT_FUTURE_PLAN);
        assertThat(testForm.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testForm.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testForm.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createFormWithExistingId() throws Exception {
        // Create the Form with an existing ID
        form.setId(1L);

        int databaseSizeBeforeCreate = formRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(form)))
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllForms() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList
        restFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(form.getId().intValue())))
            .andExpect(jsonPath("$.[*].futurePlan").value(hasItem(DEFAULT_FUTURE_PLAN)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFormsWithEagerRelationshipsIsEnabled() throws Exception {
        when(formServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFormMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(formServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFormsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(formServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFormMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(formRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get the form
        restFormMockMvc
            .perform(get(ENTITY_API_URL_ID, form.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(form.getId().intValue()))
            .andExpect(jsonPath("$.futurePlan").value(DEFAULT_FUTURE_PLAN))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getFormsByIdFiltering() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        Long id = form.getId();

        defaultFormShouldBeFound("id.equals=" + id);
        defaultFormShouldNotBeFound("id.notEquals=" + id);

        defaultFormShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFormShouldNotBeFound("id.greaterThan=" + id);

        defaultFormShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFormShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFormsByFuturePlanIsEqualToSomething() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where futurePlan equals to DEFAULT_FUTURE_PLAN
        defaultFormShouldBeFound("futurePlan.equals=" + DEFAULT_FUTURE_PLAN);

        // Get all the formList where futurePlan equals to UPDATED_FUTURE_PLAN
        defaultFormShouldNotBeFound("futurePlan.equals=" + UPDATED_FUTURE_PLAN);
    }

    @Test
    @Transactional
    void getAllFormsByFuturePlanIsInShouldWork() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where futurePlan in DEFAULT_FUTURE_PLAN or UPDATED_FUTURE_PLAN
        defaultFormShouldBeFound("futurePlan.in=" + DEFAULT_FUTURE_PLAN + "," + UPDATED_FUTURE_PLAN);

        // Get all the formList where futurePlan equals to UPDATED_FUTURE_PLAN
        defaultFormShouldNotBeFound("futurePlan.in=" + UPDATED_FUTURE_PLAN);
    }

    @Test
    @Transactional
    void getAllFormsByFuturePlanIsNullOrNotNull() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where futurePlan is not null
        defaultFormShouldBeFound("futurePlan.specified=true");

        // Get all the formList where futurePlan is null
        defaultFormShouldNotBeFound("futurePlan.specified=false");
    }

    @Test
    @Transactional
    void getAllFormsByFuturePlanContainsSomething() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where futurePlan contains DEFAULT_FUTURE_PLAN
        defaultFormShouldBeFound("futurePlan.contains=" + DEFAULT_FUTURE_PLAN);

        // Get all the formList where futurePlan contains UPDATED_FUTURE_PLAN
        defaultFormShouldNotBeFound("futurePlan.contains=" + UPDATED_FUTURE_PLAN);
    }

    @Test
    @Transactional
    void getAllFormsByFuturePlanNotContainsSomething() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where futurePlan does not contain DEFAULT_FUTURE_PLAN
        defaultFormShouldNotBeFound("futurePlan.doesNotContain=" + DEFAULT_FUTURE_PLAN);

        // Get all the formList where futurePlan does not contain UPDATED_FUTURE_PLAN
        defaultFormShouldBeFound("futurePlan.doesNotContain=" + UPDATED_FUTURE_PLAN);
    }

    @Test
    @Transactional
    void getAllFormsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where status equals to DEFAULT_STATUS
        defaultFormShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the formList where status equals to UPDATED_STATUS
        defaultFormShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllFormsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultFormShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the formList where status equals to UPDATED_STATUS
        defaultFormShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllFormsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where status is not null
        defaultFormShouldBeFound("status.specified=true");

        // Get all the formList where status is null
        defaultFormShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllFormsByCreateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where createDate equals to DEFAULT_CREATE_DATE
        defaultFormShouldBeFound("createDate.equals=" + DEFAULT_CREATE_DATE);

        // Get all the formList where createDate equals to UPDATED_CREATE_DATE
        defaultFormShouldNotBeFound("createDate.equals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    void getAllFormsByCreateDateIsInShouldWork() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where createDate in DEFAULT_CREATE_DATE or UPDATED_CREATE_DATE
        defaultFormShouldBeFound("createDate.in=" + DEFAULT_CREATE_DATE + "," + UPDATED_CREATE_DATE);

        // Get all the formList where createDate equals to UPDATED_CREATE_DATE
        defaultFormShouldNotBeFound("createDate.in=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    void getAllFormsByCreateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where createDate is not null
        defaultFormShouldBeFound("createDate.specified=true");

        // Get all the formList where createDate is null
        defaultFormShouldNotBeFound("createDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFormsByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultFormShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the formList where updateDate equals to UPDATED_UPDATE_DATE
        defaultFormShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllFormsByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultFormShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the formList where updateDate equals to UPDATED_UPDATE_DATE
        defaultFormShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllFormsByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList where updateDate is not null
        defaultFormShouldBeFound("updateDate.specified=true");

        // Get all the formList where updateDate is null
        defaultFormShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFormsByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            formRepository.saveAndFlush(form);
            user = UserResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        form.setUser(user);
        formRepository.saveAndFlush(form);
        Long userId = user.getId();
        // Get all the formList where user equals to userId
        defaultFormShouldBeFound("userId.equals=" + userId);

        // Get all the formList where user equals to (userId + 1)
        defaultFormShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllFormsByOrganizationIsEqualToSomething() throws Exception {
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            formRepository.saveAndFlush(form);
            organization = OrganizationResourceIT.createEntity(em);
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        em.persist(organization);
        em.flush();
        form.setOrganization(organization);
        formRepository.saveAndFlush(form);
        Long organizationId = organization.getId();
        // Get all the formList where organization equals to organizationId
        defaultFormShouldBeFound("organizationId.equals=" + organizationId);

        // Get all the formList where organization equals to (organizationId + 1)
        defaultFormShouldNotBeFound("organizationId.equals=" + (organizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFormShouldBeFound(String filter) throws Exception {
        restFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(form.getId().intValue())))
            .andExpect(jsonPath("$.[*].futurePlan").value(hasItem(DEFAULT_FUTURE_PLAN)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restFormMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFormShouldNotBeFound(String filter) throws Exception {
        restFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFormMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingForm() throws Exception {
        // Get the form
        restFormMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // Update the form
        Form updatedForm = formRepository.findById(form.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedForm are not directly saved in db
        em.detach(updatedForm);
        updatedForm.futurePlan(UPDATED_FUTURE_PLAN).status(UPDATED_STATUS).createDate(UPDATED_CREATE_DATE).updateDate(UPDATED_UPDATE_DATE);

        restFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedForm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedForm))
            )
            .andExpect(status().isOk());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getFuturePlan()).isEqualTo(UPDATED_FUTURE_PLAN);
        assertThat(testForm.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testForm.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testForm.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingForm() throws Exception {
        int databaseSizeBeforeUpdate = formRepository.findAll().size();
        form.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, form.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(form))
            )
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchForm() throws Exception {
        int databaseSizeBeforeUpdate = formRepository.findAll().size();
        form.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(form))
            )
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamForm() throws Exception {
        int databaseSizeBeforeUpdate = formRepository.findAll().size();
        form.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(form)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormWithPatch() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // Update the form using partial update
        Form partialUpdatedForm = new Form();
        partialUpdatedForm.setId(form.getId());

        partialUpdatedForm.futurePlan(UPDATED_FUTURE_PLAN).status(UPDATED_STATUS).updateDate(UPDATED_UPDATE_DATE);

        restFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedForm))
            )
            .andExpect(status().isOk());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getFuturePlan()).isEqualTo(UPDATED_FUTURE_PLAN);
        assertThat(testForm.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testForm.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testForm.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFormWithPatch() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // Update the form using partial update
        Form partialUpdatedForm = new Form();
        partialUpdatedForm.setId(form.getId());

        partialUpdatedForm
            .futurePlan(UPDATED_FUTURE_PLAN)
            .status(UPDATED_STATUS)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedForm))
            )
            .andExpect(status().isOk());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getFuturePlan()).isEqualTo(UPDATED_FUTURE_PLAN);
        assertThat(testForm.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testForm.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testForm.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingForm() throws Exception {
        int databaseSizeBeforeUpdate = formRepository.findAll().size();
        form.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, form.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(form))
            )
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchForm() throws Exception {
        int databaseSizeBeforeUpdate = formRepository.findAll().size();
        form.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(form))
            )
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamForm() throws Exception {
        int databaseSizeBeforeUpdate = formRepository.findAll().size();
        form.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(form)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        int databaseSizeBeforeDelete = formRepository.findAll().size();

        // Delete the form
        restFormMockMvc
            .perform(delete(ENTITY_API_URL_ID, form.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
