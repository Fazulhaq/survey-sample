package com.amin.survey.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amin.survey.IntegrationTest;
import com.amin.survey.domain.Form;
import com.amin.survey.domain.ItDevice;
import com.amin.survey.domain.enumeration.ItDeviceType;
import com.amin.survey.repository.ItDeviceRepository;
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
 * Integration tests for the {@link ItDeviceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ItDeviceResourceIT {

    private static final ItDeviceType DEFAULT_DEVICE_TYPE = ItDeviceType.DesktopComputers;
    private static final ItDeviceType UPDATED_DEVICE_TYPE = ItDeviceType.Laptops;

    private static final String DEFAULT_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND_AND_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_AND_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_AGE = "AAAAAAAAAA";
    private static final String UPDATED_AGE = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/it-devices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItDeviceRepository itDeviceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItDeviceMockMvc;

    private ItDevice itDevice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItDevice createEntity(EntityManager em) {
        ItDevice itDevice = new ItDevice()
            .deviceType(DEFAULT_DEVICE_TYPE)
            .quantity(DEFAULT_QUANTITY)
            .brandAndModel(DEFAULT_BRAND_AND_MODEL)
            .age(DEFAULT_AGE)
            .purpose(DEFAULT_PURPOSE)
            .currentStatus(DEFAULT_CURRENT_STATUS);
        // Add required entity
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            form = FormResourceIT.createEntity(em);
            em.persist(form);
            em.flush();
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        itDevice.setForm(form);
        return itDevice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItDevice createUpdatedEntity(EntityManager em) {
        ItDevice itDevice = new ItDevice()
            .deviceType(UPDATED_DEVICE_TYPE)
            .quantity(UPDATED_QUANTITY)
            .brandAndModel(UPDATED_BRAND_AND_MODEL)
            .age(UPDATED_AGE)
            .purpose(UPDATED_PURPOSE)
            .currentStatus(UPDATED_CURRENT_STATUS);
        // Add required entity
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            form = FormResourceIT.createUpdatedEntity(em);
            em.persist(form);
            em.flush();
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        itDevice.setForm(form);
        return itDevice;
    }

    @BeforeEach
    public void initTest() {
        itDevice = createEntity(em);
    }

    @Test
    @Transactional
    void createItDevice() throws Exception {
        int databaseSizeBeforeCreate = itDeviceRepository.findAll().size();
        // Create the ItDevice
        restItDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itDevice)))
            .andExpect(status().isCreated());

        // Validate the ItDevice in the database
        List<ItDevice> itDeviceList = itDeviceRepository.findAll();
        assertThat(itDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        ItDevice testItDevice = itDeviceList.get(itDeviceList.size() - 1);
        assertThat(testItDevice.getDeviceType()).isEqualTo(DEFAULT_DEVICE_TYPE);
        assertThat(testItDevice.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testItDevice.getBrandAndModel()).isEqualTo(DEFAULT_BRAND_AND_MODEL);
        assertThat(testItDevice.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testItDevice.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testItDevice.getCurrentStatus()).isEqualTo(DEFAULT_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void createItDeviceWithExistingId() throws Exception {
        // Create the ItDevice with an existing ID
        itDevice.setId(1L);

        int databaseSizeBeforeCreate = itDeviceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itDevice)))
            .andExpect(status().isBadRequest());

        // Validate the ItDevice in the database
        List<ItDevice> itDeviceList = itDeviceRepository.findAll();
        assertThat(itDeviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllItDevices() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList
        restItDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].brandAndModel").value(hasItem(DEFAULT_BRAND_AND_MODEL)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].currentStatus").value(hasItem(DEFAULT_CURRENT_STATUS)));
    }

    @Test
    @Transactional
    void getItDevice() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get the itDevice
        restItDeviceMockMvc
            .perform(get(ENTITY_API_URL_ID, itDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itDevice.getId().intValue()))
            .andExpect(jsonPath("$.deviceType").value(DEFAULT_DEVICE_TYPE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.brandAndModel").value(DEFAULT_BRAND_AND_MODEL))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.currentStatus").value(DEFAULT_CURRENT_STATUS));
    }

    @Test
    @Transactional
    void getItDevicesByIdFiltering() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        Long id = itDevice.getId();

        defaultItDeviceShouldBeFound("id.equals=" + id);
        defaultItDeviceShouldNotBeFound("id.notEquals=" + id);

        defaultItDeviceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItDeviceShouldNotBeFound("id.greaterThan=" + id);

        defaultItDeviceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItDeviceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllItDevicesByDeviceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where deviceType equals to DEFAULT_DEVICE_TYPE
        defaultItDeviceShouldBeFound("deviceType.equals=" + DEFAULT_DEVICE_TYPE);

        // Get all the itDeviceList where deviceType equals to UPDATED_DEVICE_TYPE
        defaultItDeviceShouldNotBeFound("deviceType.equals=" + UPDATED_DEVICE_TYPE);
    }

    @Test
    @Transactional
    void getAllItDevicesByDeviceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where deviceType in DEFAULT_DEVICE_TYPE or UPDATED_DEVICE_TYPE
        defaultItDeviceShouldBeFound("deviceType.in=" + DEFAULT_DEVICE_TYPE + "," + UPDATED_DEVICE_TYPE);

        // Get all the itDeviceList where deviceType equals to UPDATED_DEVICE_TYPE
        defaultItDeviceShouldNotBeFound("deviceType.in=" + UPDATED_DEVICE_TYPE);
    }

    @Test
    @Transactional
    void getAllItDevicesByDeviceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where deviceType is not null
        defaultItDeviceShouldBeFound("deviceType.specified=true");

        // Get all the itDeviceList where deviceType is null
        defaultItDeviceShouldNotBeFound("deviceType.specified=false");
    }

    @Test
    @Transactional
    void getAllItDevicesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where quantity equals to DEFAULT_QUANTITY
        defaultItDeviceShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the itDeviceList where quantity equals to UPDATED_QUANTITY
        defaultItDeviceShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllItDevicesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultItDeviceShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the itDeviceList where quantity equals to UPDATED_QUANTITY
        defaultItDeviceShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllItDevicesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where quantity is not null
        defaultItDeviceShouldBeFound("quantity.specified=true");

        // Get all the itDeviceList where quantity is null
        defaultItDeviceShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllItDevicesByQuantityContainsSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where quantity contains DEFAULT_QUANTITY
        defaultItDeviceShouldBeFound("quantity.contains=" + DEFAULT_QUANTITY);

        // Get all the itDeviceList where quantity contains UPDATED_QUANTITY
        defaultItDeviceShouldNotBeFound("quantity.contains=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllItDevicesByQuantityNotContainsSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where quantity does not contain DEFAULT_QUANTITY
        defaultItDeviceShouldNotBeFound("quantity.doesNotContain=" + DEFAULT_QUANTITY);

        // Get all the itDeviceList where quantity does not contain UPDATED_QUANTITY
        defaultItDeviceShouldBeFound("quantity.doesNotContain=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllItDevicesByBrandAndModelIsEqualToSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where brandAndModel equals to DEFAULT_BRAND_AND_MODEL
        defaultItDeviceShouldBeFound("brandAndModel.equals=" + DEFAULT_BRAND_AND_MODEL);

        // Get all the itDeviceList where brandAndModel equals to UPDATED_BRAND_AND_MODEL
        defaultItDeviceShouldNotBeFound("brandAndModel.equals=" + UPDATED_BRAND_AND_MODEL);
    }

    @Test
    @Transactional
    void getAllItDevicesByBrandAndModelIsInShouldWork() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where brandAndModel in DEFAULT_BRAND_AND_MODEL or UPDATED_BRAND_AND_MODEL
        defaultItDeviceShouldBeFound("brandAndModel.in=" + DEFAULT_BRAND_AND_MODEL + "," + UPDATED_BRAND_AND_MODEL);

        // Get all the itDeviceList where brandAndModel equals to UPDATED_BRAND_AND_MODEL
        defaultItDeviceShouldNotBeFound("brandAndModel.in=" + UPDATED_BRAND_AND_MODEL);
    }

    @Test
    @Transactional
    void getAllItDevicesByBrandAndModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where brandAndModel is not null
        defaultItDeviceShouldBeFound("brandAndModel.specified=true");

        // Get all the itDeviceList where brandAndModel is null
        defaultItDeviceShouldNotBeFound("brandAndModel.specified=false");
    }

    @Test
    @Transactional
    void getAllItDevicesByBrandAndModelContainsSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where brandAndModel contains DEFAULT_BRAND_AND_MODEL
        defaultItDeviceShouldBeFound("brandAndModel.contains=" + DEFAULT_BRAND_AND_MODEL);

        // Get all the itDeviceList where brandAndModel contains UPDATED_BRAND_AND_MODEL
        defaultItDeviceShouldNotBeFound("brandAndModel.contains=" + UPDATED_BRAND_AND_MODEL);
    }

    @Test
    @Transactional
    void getAllItDevicesByBrandAndModelNotContainsSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where brandAndModel does not contain DEFAULT_BRAND_AND_MODEL
        defaultItDeviceShouldNotBeFound("brandAndModel.doesNotContain=" + DEFAULT_BRAND_AND_MODEL);

        // Get all the itDeviceList where brandAndModel does not contain UPDATED_BRAND_AND_MODEL
        defaultItDeviceShouldBeFound("brandAndModel.doesNotContain=" + UPDATED_BRAND_AND_MODEL);
    }

    @Test
    @Transactional
    void getAllItDevicesByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where age equals to DEFAULT_AGE
        defaultItDeviceShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the itDeviceList where age equals to UPDATED_AGE
        defaultItDeviceShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllItDevicesByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where age in DEFAULT_AGE or UPDATED_AGE
        defaultItDeviceShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the itDeviceList where age equals to UPDATED_AGE
        defaultItDeviceShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllItDevicesByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where age is not null
        defaultItDeviceShouldBeFound("age.specified=true");

        // Get all the itDeviceList where age is null
        defaultItDeviceShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    void getAllItDevicesByAgeContainsSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where age contains DEFAULT_AGE
        defaultItDeviceShouldBeFound("age.contains=" + DEFAULT_AGE);

        // Get all the itDeviceList where age contains UPDATED_AGE
        defaultItDeviceShouldNotBeFound("age.contains=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllItDevicesByAgeNotContainsSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where age does not contain DEFAULT_AGE
        defaultItDeviceShouldNotBeFound("age.doesNotContain=" + DEFAULT_AGE);

        // Get all the itDeviceList where age does not contain UPDATED_AGE
        defaultItDeviceShouldBeFound("age.doesNotContain=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllItDevicesByPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where purpose equals to DEFAULT_PURPOSE
        defaultItDeviceShouldBeFound("purpose.equals=" + DEFAULT_PURPOSE);

        // Get all the itDeviceList where purpose equals to UPDATED_PURPOSE
        defaultItDeviceShouldNotBeFound("purpose.equals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllItDevicesByPurposeIsInShouldWork() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where purpose in DEFAULT_PURPOSE or UPDATED_PURPOSE
        defaultItDeviceShouldBeFound("purpose.in=" + DEFAULT_PURPOSE + "," + UPDATED_PURPOSE);

        // Get all the itDeviceList where purpose equals to UPDATED_PURPOSE
        defaultItDeviceShouldNotBeFound("purpose.in=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllItDevicesByPurposeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where purpose is not null
        defaultItDeviceShouldBeFound("purpose.specified=true");

        // Get all the itDeviceList where purpose is null
        defaultItDeviceShouldNotBeFound("purpose.specified=false");
    }

    @Test
    @Transactional
    void getAllItDevicesByPurposeContainsSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where purpose contains DEFAULT_PURPOSE
        defaultItDeviceShouldBeFound("purpose.contains=" + DEFAULT_PURPOSE);

        // Get all the itDeviceList where purpose contains UPDATED_PURPOSE
        defaultItDeviceShouldNotBeFound("purpose.contains=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllItDevicesByPurposeNotContainsSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where purpose does not contain DEFAULT_PURPOSE
        defaultItDeviceShouldNotBeFound("purpose.doesNotContain=" + DEFAULT_PURPOSE);

        // Get all the itDeviceList where purpose does not contain UPDATED_PURPOSE
        defaultItDeviceShouldBeFound("purpose.doesNotContain=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllItDevicesByCurrentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where currentStatus equals to DEFAULT_CURRENT_STATUS
        defaultItDeviceShouldBeFound("currentStatus.equals=" + DEFAULT_CURRENT_STATUS);

        // Get all the itDeviceList where currentStatus equals to UPDATED_CURRENT_STATUS
        defaultItDeviceShouldNotBeFound("currentStatus.equals=" + UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void getAllItDevicesByCurrentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where currentStatus in DEFAULT_CURRENT_STATUS or UPDATED_CURRENT_STATUS
        defaultItDeviceShouldBeFound("currentStatus.in=" + DEFAULT_CURRENT_STATUS + "," + UPDATED_CURRENT_STATUS);

        // Get all the itDeviceList where currentStatus equals to UPDATED_CURRENT_STATUS
        defaultItDeviceShouldNotBeFound("currentStatus.in=" + UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void getAllItDevicesByCurrentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where currentStatus is not null
        defaultItDeviceShouldBeFound("currentStatus.specified=true");

        // Get all the itDeviceList where currentStatus is null
        defaultItDeviceShouldNotBeFound("currentStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllItDevicesByCurrentStatusContainsSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where currentStatus contains DEFAULT_CURRENT_STATUS
        defaultItDeviceShouldBeFound("currentStatus.contains=" + DEFAULT_CURRENT_STATUS);

        // Get all the itDeviceList where currentStatus contains UPDATED_CURRENT_STATUS
        defaultItDeviceShouldNotBeFound("currentStatus.contains=" + UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void getAllItDevicesByCurrentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        // Get all the itDeviceList where currentStatus does not contain DEFAULT_CURRENT_STATUS
        defaultItDeviceShouldNotBeFound("currentStatus.doesNotContain=" + DEFAULT_CURRENT_STATUS);

        // Get all the itDeviceList where currentStatus does not contain UPDATED_CURRENT_STATUS
        defaultItDeviceShouldBeFound("currentStatus.doesNotContain=" + UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void getAllItDevicesByFormIsEqualToSomething() throws Exception {
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            itDeviceRepository.saveAndFlush(itDevice);
            form = FormResourceIT.createEntity(em);
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        em.persist(form);
        em.flush();
        itDevice.setForm(form);
        itDeviceRepository.saveAndFlush(itDevice);
        Long formId = form.getId();
        // Get all the itDeviceList where form equals to formId
        defaultItDeviceShouldBeFound("formId.equals=" + formId);

        // Get all the itDeviceList where form equals to (formId + 1)
        defaultItDeviceShouldNotBeFound("formId.equals=" + (formId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItDeviceShouldBeFound(String filter) throws Exception {
        restItDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].brandAndModel").value(hasItem(DEFAULT_BRAND_AND_MODEL)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].currentStatus").value(hasItem(DEFAULT_CURRENT_STATUS)));

        // Check, that the count call also returns 1
        restItDeviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItDeviceShouldNotBeFound(String filter) throws Exception {
        restItDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItDeviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingItDevice() throws Exception {
        // Get the itDevice
        restItDeviceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingItDevice() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        int databaseSizeBeforeUpdate = itDeviceRepository.findAll().size();

        // Update the itDevice
        ItDevice updatedItDevice = itDeviceRepository.findById(itDevice.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedItDevice are not directly saved in db
        em.detach(updatedItDevice);
        updatedItDevice
            .deviceType(UPDATED_DEVICE_TYPE)
            .quantity(UPDATED_QUANTITY)
            .brandAndModel(UPDATED_BRAND_AND_MODEL)
            .age(UPDATED_AGE)
            .purpose(UPDATED_PURPOSE)
            .currentStatus(UPDATED_CURRENT_STATUS);

        restItDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedItDevice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedItDevice))
            )
            .andExpect(status().isOk());

        // Validate the ItDevice in the database
        List<ItDevice> itDeviceList = itDeviceRepository.findAll();
        assertThat(itDeviceList).hasSize(databaseSizeBeforeUpdate);
        ItDevice testItDevice = itDeviceList.get(itDeviceList.size() - 1);
        assertThat(testItDevice.getDeviceType()).isEqualTo(UPDATED_DEVICE_TYPE);
        assertThat(testItDevice.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testItDevice.getBrandAndModel()).isEqualTo(UPDATED_BRAND_AND_MODEL);
        assertThat(testItDevice.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testItDevice.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testItDevice.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingItDevice() throws Exception {
        int databaseSizeBeforeUpdate = itDeviceRepository.findAll().size();
        itDevice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itDevice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItDevice in the database
        List<ItDevice> itDeviceList = itDeviceRepository.findAll();
        assertThat(itDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItDevice() throws Exception {
        int databaseSizeBeforeUpdate = itDeviceRepository.findAll().size();
        itDevice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItDevice in the database
        List<ItDevice> itDeviceList = itDeviceRepository.findAll();
        assertThat(itDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItDevice() throws Exception {
        int databaseSizeBeforeUpdate = itDeviceRepository.findAll().size();
        itDevice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItDeviceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itDevice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItDevice in the database
        List<ItDevice> itDeviceList = itDeviceRepository.findAll();
        assertThat(itDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItDeviceWithPatch() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        int databaseSizeBeforeUpdate = itDeviceRepository.findAll().size();

        // Update the itDevice using partial update
        ItDevice partialUpdatedItDevice = new ItDevice();
        partialUpdatedItDevice.setId(itDevice.getId());

        partialUpdatedItDevice.quantity(UPDATED_QUANTITY).brandAndModel(UPDATED_BRAND_AND_MODEL).age(UPDATED_AGE).purpose(UPDATED_PURPOSE);

        restItDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItDevice))
            )
            .andExpect(status().isOk());

        // Validate the ItDevice in the database
        List<ItDevice> itDeviceList = itDeviceRepository.findAll();
        assertThat(itDeviceList).hasSize(databaseSizeBeforeUpdate);
        ItDevice testItDevice = itDeviceList.get(itDeviceList.size() - 1);
        assertThat(testItDevice.getDeviceType()).isEqualTo(DEFAULT_DEVICE_TYPE);
        assertThat(testItDevice.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testItDevice.getBrandAndModel()).isEqualTo(UPDATED_BRAND_AND_MODEL);
        assertThat(testItDevice.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testItDevice.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testItDevice.getCurrentStatus()).isEqualTo(DEFAULT_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateItDeviceWithPatch() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        int databaseSizeBeforeUpdate = itDeviceRepository.findAll().size();

        // Update the itDevice using partial update
        ItDevice partialUpdatedItDevice = new ItDevice();
        partialUpdatedItDevice.setId(itDevice.getId());

        partialUpdatedItDevice
            .deviceType(UPDATED_DEVICE_TYPE)
            .quantity(UPDATED_QUANTITY)
            .brandAndModel(UPDATED_BRAND_AND_MODEL)
            .age(UPDATED_AGE)
            .purpose(UPDATED_PURPOSE)
            .currentStatus(UPDATED_CURRENT_STATUS);

        restItDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItDevice))
            )
            .andExpect(status().isOk());

        // Validate the ItDevice in the database
        List<ItDevice> itDeviceList = itDeviceRepository.findAll();
        assertThat(itDeviceList).hasSize(databaseSizeBeforeUpdate);
        ItDevice testItDevice = itDeviceList.get(itDeviceList.size() - 1);
        assertThat(testItDevice.getDeviceType()).isEqualTo(UPDATED_DEVICE_TYPE);
        assertThat(testItDevice.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testItDevice.getBrandAndModel()).isEqualTo(UPDATED_BRAND_AND_MODEL);
        assertThat(testItDevice.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testItDevice.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testItDevice.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingItDevice() throws Exception {
        int databaseSizeBeforeUpdate = itDeviceRepository.findAll().size();
        itDevice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItDevice in the database
        List<ItDevice> itDeviceList = itDeviceRepository.findAll();
        assertThat(itDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItDevice() throws Exception {
        int databaseSizeBeforeUpdate = itDeviceRepository.findAll().size();
        itDevice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItDevice in the database
        List<ItDevice> itDeviceList = itDeviceRepository.findAll();
        assertThat(itDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItDevice() throws Exception {
        int databaseSizeBeforeUpdate = itDeviceRepository.findAll().size();
        itDevice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItDeviceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(itDevice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItDevice in the database
        List<ItDevice> itDeviceList = itDeviceRepository.findAll();
        assertThat(itDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItDevice() throws Exception {
        // Initialize the database
        itDeviceRepository.saveAndFlush(itDevice);

        int databaseSizeBeforeDelete = itDeviceRepository.findAll().size();

        // Delete the itDevice
        restItDeviceMockMvc
            .perform(delete(ENTITY_API_URL_ID, itDevice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItDevice> itDeviceList = itDeviceRepository.findAll();
        assertThat(itDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
