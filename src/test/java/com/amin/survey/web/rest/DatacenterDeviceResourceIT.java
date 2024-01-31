package com.amin.survey.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amin.survey.IntegrationTest;
import com.amin.survey.domain.DatacenterDevice;
import com.amin.survey.domain.Form;
import com.amin.survey.domain.enumeration.DataCenterDeviceType;
import com.amin.survey.repository.DatacenterDeviceRepository;
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
 * Integration tests for the {@link DatacenterDeviceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DatacenterDeviceResourceIT {

    private static final DataCenterDeviceType DEFAULT_DEVICE_TYPE = DataCenterDeviceType.Racks;
    private static final DataCenterDeviceType UPDATED_DEVICE_TYPE = DataCenterDeviceType.Servers;

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

    private static final String ENTITY_API_URL = "/api/datacenter-devices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DatacenterDeviceRepository datacenterDeviceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDatacenterDeviceMockMvc;

    private DatacenterDevice datacenterDevice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatacenterDevice createEntity(EntityManager em) {
        DatacenterDevice datacenterDevice = new DatacenterDevice()
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
        datacenterDevice.setForm(form);
        return datacenterDevice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatacenterDevice createUpdatedEntity(EntityManager em) {
        DatacenterDevice datacenterDevice = new DatacenterDevice()
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
        datacenterDevice.setForm(form);
        return datacenterDevice;
    }

    @BeforeEach
    public void initTest() {
        datacenterDevice = createEntity(em);
    }

    @Test
    @Transactional
    void createDatacenterDevice() throws Exception {
        int databaseSizeBeforeCreate = datacenterDeviceRepository.findAll().size();
        // Create the DatacenterDevice
        restDatacenterDeviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(datacenterDevice))
            )
            .andExpect(status().isCreated());

        // Validate the DatacenterDevice in the database
        List<DatacenterDevice> datacenterDeviceList = datacenterDeviceRepository.findAll();
        assertThat(datacenterDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        DatacenterDevice testDatacenterDevice = datacenterDeviceList.get(datacenterDeviceList.size() - 1);
        assertThat(testDatacenterDevice.getDeviceType()).isEqualTo(DEFAULT_DEVICE_TYPE);
        assertThat(testDatacenterDevice.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDatacenterDevice.getBrandAndModel()).isEqualTo(DEFAULT_BRAND_AND_MODEL);
        assertThat(testDatacenterDevice.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testDatacenterDevice.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testDatacenterDevice.getCurrentStatus()).isEqualTo(DEFAULT_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void createDatacenterDeviceWithExistingId() throws Exception {
        // Create the DatacenterDevice with an existing ID
        datacenterDevice.setId(1L);

        int databaseSizeBeforeCreate = datacenterDeviceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatacenterDeviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(datacenterDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatacenterDevice in the database
        List<DatacenterDevice> datacenterDeviceList = datacenterDeviceRepository.findAll();
        assertThat(datacenterDeviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDatacenterDevices() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList
        restDatacenterDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datacenterDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].brandAndModel").value(hasItem(DEFAULT_BRAND_AND_MODEL)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].currentStatus").value(hasItem(DEFAULT_CURRENT_STATUS)));
    }

    @Test
    @Transactional
    void getDatacenterDevice() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get the datacenterDevice
        restDatacenterDeviceMockMvc
            .perform(get(ENTITY_API_URL_ID, datacenterDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(datacenterDevice.getId().intValue()))
            .andExpect(jsonPath("$.deviceType").value(DEFAULT_DEVICE_TYPE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.brandAndModel").value(DEFAULT_BRAND_AND_MODEL))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.currentStatus").value(DEFAULT_CURRENT_STATUS));
    }

    @Test
    @Transactional
    void getDatacenterDevicesByIdFiltering() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        Long id = datacenterDevice.getId();

        defaultDatacenterDeviceShouldBeFound("id.equals=" + id);
        defaultDatacenterDeviceShouldNotBeFound("id.notEquals=" + id);

        defaultDatacenterDeviceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDatacenterDeviceShouldNotBeFound("id.greaterThan=" + id);

        defaultDatacenterDeviceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDatacenterDeviceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByDeviceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where deviceType equals to DEFAULT_DEVICE_TYPE
        defaultDatacenterDeviceShouldBeFound("deviceType.equals=" + DEFAULT_DEVICE_TYPE);

        // Get all the datacenterDeviceList where deviceType equals to UPDATED_DEVICE_TYPE
        defaultDatacenterDeviceShouldNotBeFound("deviceType.equals=" + UPDATED_DEVICE_TYPE);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByDeviceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where deviceType in DEFAULT_DEVICE_TYPE or UPDATED_DEVICE_TYPE
        defaultDatacenterDeviceShouldBeFound("deviceType.in=" + DEFAULT_DEVICE_TYPE + "," + UPDATED_DEVICE_TYPE);

        // Get all the datacenterDeviceList where deviceType equals to UPDATED_DEVICE_TYPE
        defaultDatacenterDeviceShouldNotBeFound("deviceType.in=" + UPDATED_DEVICE_TYPE);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByDeviceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where deviceType is not null
        defaultDatacenterDeviceShouldBeFound("deviceType.specified=true");

        // Get all the datacenterDeviceList where deviceType is null
        defaultDatacenterDeviceShouldNotBeFound("deviceType.specified=false");
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where quantity equals to DEFAULT_QUANTITY
        defaultDatacenterDeviceShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the datacenterDeviceList where quantity equals to UPDATED_QUANTITY
        defaultDatacenterDeviceShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultDatacenterDeviceShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the datacenterDeviceList where quantity equals to UPDATED_QUANTITY
        defaultDatacenterDeviceShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where quantity is not null
        defaultDatacenterDeviceShouldBeFound("quantity.specified=true");

        // Get all the datacenterDeviceList where quantity is null
        defaultDatacenterDeviceShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByQuantityContainsSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where quantity contains DEFAULT_QUANTITY
        defaultDatacenterDeviceShouldBeFound("quantity.contains=" + DEFAULT_QUANTITY);

        // Get all the datacenterDeviceList where quantity contains UPDATED_QUANTITY
        defaultDatacenterDeviceShouldNotBeFound("quantity.contains=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByQuantityNotContainsSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where quantity does not contain DEFAULT_QUANTITY
        defaultDatacenterDeviceShouldNotBeFound("quantity.doesNotContain=" + DEFAULT_QUANTITY);

        // Get all the datacenterDeviceList where quantity does not contain UPDATED_QUANTITY
        defaultDatacenterDeviceShouldBeFound("quantity.doesNotContain=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByBrandAndModelIsEqualToSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where brandAndModel equals to DEFAULT_BRAND_AND_MODEL
        defaultDatacenterDeviceShouldBeFound("brandAndModel.equals=" + DEFAULT_BRAND_AND_MODEL);

        // Get all the datacenterDeviceList where brandAndModel equals to UPDATED_BRAND_AND_MODEL
        defaultDatacenterDeviceShouldNotBeFound("brandAndModel.equals=" + UPDATED_BRAND_AND_MODEL);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByBrandAndModelIsInShouldWork() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where brandAndModel in DEFAULT_BRAND_AND_MODEL or UPDATED_BRAND_AND_MODEL
        defaultDatacenterDeviceShouldBeFound("brandAndModel.in=" + DEFAULT_BRAND_AND_MODEL + "," + UPDATED_BRAND_AND_MODEL);

        // Get all the datacenterDeviceList where brandAndModel equals to UPDATED_BRAND_AND_MODEL
        defaultDatacenterDeviceShouldNotBeFound("brandAndModel.in=" + UPDATED_BRAND_AND_MODEL);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByBrandAndModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where brandAndModel is not null
        defaultDatacenterDeviceShouldBeFound("brandAndModel.specified=true");

        // Get all the datacenterDeviceList where brandAndModel is null
        defaultDatacenterDeviceShouldNotBeFound("brandAndModel.specified=false");
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByBrandAndModelContainsSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where brandAndModel contains DEFAULT_BRAND_AND_MODEL
        defaultDatacenterDeviceShouldBeFound("brandAndModel.contains=" + DEFAULT_BRAND_AND_MODEL);

        // Get all the datacenterDeviceList where brandAndModel contains UPDATED_BRAND_AND_MODEL
        defaultDatacenterDeviceShouldNotBeFound("brandAndModel.contains=" + UPDATED_BRAND_AND_MODEL);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByBrandAndModelNotContainsSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where brandAndModel does not contain DEFAULT_BRAND_AND_MODEL
        defaultDatacenterDeviceShouldNotBeFound("brandAndModel.doesNotContain=" + DEFAULT_BRAND_AND_MODEL);

        // Get all the datacenterDeviceList where brandAndModel does not contain UPDATED_BRAND_AND_MODEL
        defaultDatacenterDeviceShouldBeFound("brandAndModel.doesNotContain=" + UPDATED_BRAND_AND_MODEL);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where age equals to DEFAULT_AGE
        defaultDatacenterDeviceShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the datacenterDeviceList where age equals to UPDATED_AGE
        defaultDatacenterDeviceShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where age in DEFAULT_AGE or UPDATED_AGE
        defaultDatacenterDeviceShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the datacenterDeviceList where age equals to UPDATED_AGE
        defaultDatacenterDeviceShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where age is not null
        defaultDatacenterDeviceShouldBeFound("age.specified=true");

        // Get all the datacenterDeviceList where age is null
        defaultDatacenterDeviceShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByAgeContainsSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where age contains DEFAULT_AGE
        defaultDatacenterDeviceShouldBeFound("age.contains=" + DEFAULT_AGE);

        // Get all the datacenterDeviceList where age contains UPDATED_AGE
        defaultDatacenterDeviceShouldNotBeFound("age.contains=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByAgeNotContainsSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where age does not contain DEFAULT_AGE
        defaultDatacenterDeviceShouldNotBeFound("age.doesNotContain=" + DEFAULT_AGE);

        // Get all the datacenterDeviceList where age does not contain UPDATED_AGE
        defaultDatacenterDeviceShouldBeFound("age.doesNotContain=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where purpose equals to DEFAULT_PURPOSE
        defaultDatacenterDeviceShouldBeFound("purpose.equals=" + DEFAULT_PURPOSE);

        // Get all the datacenterDeviceList where purpose equals to UPDATED_PURPOSE
        defaultDatacenterDeviceShouldNotBeFound("purpose.equals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByPurposeIsInShouldWork() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where purpose in DEFAULT_PURPOSE or UPDATED_PURPOSE
        defaultDatacenterDeviceShouldBeFound("purpose.in=" + DEFAULT_PURPOSE + "," + UPDATED_PURPOSE);

        // Get all the datacenterDeviceList where purpose equals to UPDATED_PURPOSE
        defaultDatacenterDeviceShouldNotBeFound("purpose.in=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByPurposeIsNullOrNotNull() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where purpose is not null
        defaultDatacenterDeviceShouldBeFound("purpose.specified=true");

        // Get all the datacenterDeviceList where purpose is null
        defaultDatacenterDeviceShouldNotBeFound("purpose.specified=false");
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByPurposeContainsSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where purpose contains DEFAULT_PURPOSE
        defaultDatacenterDeviceShouldBeFound("purpose.contains=" + DEFAULT_PURPOSE);

        // Get all the datacenterDeviceList where purpose contains UPDATED_PURPOSE
        defaultDatacenterDeviceShouldNotBeFound("purpose.contains=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByPurposeNotContainsSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where purpose does not contain DEFAULT_PURPOSE
        defaultDatacenterDeviceShouldNotBeFound("purpose.doesNotContain=" + DEFAULT_PURPOSE);

        // Get all the datacenterDeviceList where purpose does not contain UPDATED_PURPOSE
        defaultDatacenterDeviceShouldBeFound("purpose.doesNotContain=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByCurrentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where currentStatus equals to DEFAULT_CURRENT_STATUS
        defaultDatacenterDeviceShouldBeFound("currentStatus.equals=" + DEFAULT_CURRENT_STATUS);

        // Get all the datacenterDeviceList where currentStatus equals to UPDATED_CURRENT_STATUS
        defaultDatacenterDeviceShouldNotBeFound("currentStatus.equals=" + UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByCurrentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where currentStatus in DEFAULT_CURRENT_STATUS or UPDATED_CURRENT_STATUS
        defaultDatacenterDeviceShouldBeFound("currentStatus.in=" + DEFAULT_CURRENT_STATUS + "," + UPDATED_CURRENT_STATUS);

        // Get all the datacenterDeviceList where currentStatus equals to UPDATED_CURRENT_STATUS
        defaultDatacenterDeviceShouldNotBeFound("currentStatus.in=" + UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByCurrentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where currentStatus is not null
        defaultDatacenterDeviceShouldBeFound("currentStatus.specified=true");

        // Get all the datacenterDeviceList where currentStatus is null
        defaultDatacenterDeviceShouldNotBeFound("currentStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByCurrentStatusContainsSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where currentStatus contains DEFAULT_CURRENT_STATUS
        defaultDatacenterDeviceShouldBeFound("currentStatus.contains=" + DEFAULT_CURRENT_STATUS);

        // Get all the datacenterDeviceList where currentStatus contains UPDATED_CURRENT_STATUS
        defaultDatacenterDeviceShouldNotBeFound("currentStatus.contains=" + UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByCurrentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        // Get all the datacenterDeviceList where currentStatus does not contain DEFAULT_CURRENT_STATUS
        defaultDatacenterDeviceShouldNotBeFound("currentStatus.doesNotContain=" + DEFAULT_CURRENT_STATUS);

        // Get all the datacenterDeviceList where currentStatus does not contain UPDATED_CURRENT_STATUS
        defaultDatacenterDeviceShouldBeFound("currentStatus.doesNotContain=" + UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void getAllDatacenterDevicesByFormIsEqualToSomething() throws Exception {
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            datacenterDeviceRepository.saveAndFlush(datacenterDevice);
            form = FormResourceIT.createEntity(em);
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        em.persist(form);
        em.flush();
        datacenterDevice.setForm(form);
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);
        Long formId = form.getId();
        // Get all the datacenterDeviceList where form equals to formId
        defaultDatacenterDeviceShouldBeFound("formId.equals=" + formId);

        // Get all the datacenterDeviceList where form equals to (formId + 1)
        defaultDatacenterDeviceShouldNotBeFound("formId.equals=" + (formId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDatacenterDeviceShouldBeFound(String filter) throws Exception {
        restDatacenterDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datacenterDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].brandAndModel").value(hasItem(DEFAULT_BRAND_AND_MODEL)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].currentStatus").value(hasItem(DEFAULT_CURRENT_STATUS)));

        // Check, that the count call also returns 1
        restDatacenterDeviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDatacenterDeviceShouldNotBeFound(String filter) throws Exception {
        restDatacenterDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDatacenterDeviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDatacenterDevice() throws Exception {
        // Get the datacenterDevice
        restDatacenterDeviceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDatacenterDevice() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        int databaseSizeBeforeUpdate = datacenterDeviceRepository.findAll().size();

        // Update the datacenterDevice
        DatacenterDevice updatedDatacenterDevice = datacenterDeviceRepository.findById(datacenterDevice.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDatacenterDevice are not directly saved in db
        em.detach(updatedDatacenterDevice);
        updatedDatacenterDevice
            .deviceType(UPDATED_DEVICE_TYPE)
            .quantity(UPDATED_QUANTITY)
            .brandAndModel(UPDATED_BRAND_AND_MODEL)
            .age(UPDATED_AGE)
            .purpose(UPDATED_PURPOSE)
            .currentStatus(UPDATED_CURRENT_STATUS);

        restDatacenterDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDatacenterDevice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDatacenterDevice))
            )
            .andExpect(status().isOk());

        // Validate the DatacenterDevice in the database
        List<DatacenterDevice> datacenterDeviceList = datacenterDeviceRepository.findAll();
        assertThat(datacenterDeviceList).hasSize(databaseSizeBeforeUpdate);
        DatacenterDevice testDatacenterDevice = datacenterDeviceList.get(datacenterDeviceList.size() - 1);
        assertThat(testDatacenterDevice.getDeviceType()).isEqualTo(UPDATED_DEVICE_TYPE);
        assertThat(testDatacenterDevice.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDatacenterDevice.getBrandAndModel()).isEqualTo(UPDATED_BRAND_AND_MODEL);
        assertThat(testDatacenterDevice.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testDatacenterDevice.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testDatacenterDevice.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingDatacenterDevice() throws Exception {
        int databaseSizeBeforeUpdate = datacenterDeviceRepository.findAll().size();
        datacenterDevice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatacenterDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, datacenterDevice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(datacenterDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatacenterDevice in the database
        List<DatacenterDevice> datacenterDeviceList = datacenterDeviceRepository.findAll();
        assertThat(datacenterDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDatacenterDevice() throws Exception {
        int databaseSizeBeforeUpdate = datacenterDeviceRepository.findAll().size();
        datacenterDevice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatacenterDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(datacenterDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatacenterDevice in the database
        List<DatacenterDevice> datacenterDeviceList = datacenterDeviceRepository.findAll();
        assertThat(datacenterDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDatacenterDevice() throws Exception {
        int databaseSizeBeforeUpdate = datacenterDeviceRepository.findAll().size();
        datacenterDevice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatacenterDeviceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(datacenterDevice))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DatacenterDevice in the database
        List<DatacenterDevice> datacenterDeviceList = datacenterDeviceRepository.findAll();
        assertThat(datacenterDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDatacenterDeviceWithPatch() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        int databaseSizeBeforeUpdate = datacenterDeviceRepository.findAll().size();

        // Update the datacenterDevice using partial update
        DatacenterDevice partialUpdatedDatacenterDevice = new DatacenterDevice();
        partialUpdatedDatacenterDevice.setId(datacenterDevice.getId());

        partialUpdatedDatacenterDevice.age(UPDATED_AGE).purpose(UPDATED_PURPOSE).currentStatus(UPDATED_CURRENT_STATUS);

        restDatacenterDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDatacenterDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDatacenterDevice))
            )
            .andExpect(status().isOk());

        // Validate the DatacenterDevice in the database
        List<DatacenterDevice> datacenterDeviceList = datacenterDeviceRepository.findAll();
        assertThat(datacenterDeviceList).hasSize(databaseSizeBeforeUpdate);
        DatacenterDevice testDatacenterDevice = datacenterDeviceList.get(datacenterDeviceList.size() - 1);
        assertThat(testDatacenterDevice.getDeviceType()).isEqualTo(DEFAULT_DEVICE_TYPE);
        assertThat(testDatacenterDevice.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDatacenterDevice.getBrandAndModel()).isEqualTo(DEFAULT_BRAND_AND_MODEL);
        assertThat(testDatacenterDevice.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testDatacenterDevice.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testDatacenterDevice.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateDatacenterDeviceWithPatch() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        int databaseSizeBeforeUpdate = datacenterDeviceRepository.findAll().size();

        // Update the datacenterDevice using partial update
        DatacenterDevice partialUpdatedDatacenterDevice = new DatacenterDevice();
        partialUpdatedDatacenterDevice.setId(datacenterDevice.getId());

        partialUpdatedDatacenterDevice
            .deviceType(UPDATED_DEVICE_TYPE)
            .quantity(UPDATED_QUANTITY)
            .brandAndModel(UPDATED_BRAND_AND_MODEL)
            .age(UPDATED_AGE)
            .purpose(UPDATED_PURPOSE)
            .currentStatus(UPDATED_CURRENT_STATUS);

        restDatacenterDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDatacenterDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDatacenterDevice))
            )
            .andExpect(status().isOk());

        // Validate the DatacenterDevice in the database
        List<DatacenterDevice> datacenterDeviceList = datacenterDeviceRepository.findAll();
        assertThat(datacenterDeviceList).hasSize(databaseSizeBeforeUpdate);
        DatacenterDevice testDatacenterDevice = datacenterDeviceList.get(datacenterDeviceList.size() - 1);
        assertThat(testDatacenterDevice.getDeviceType()).isEqualTo(UPDATED_DEVICE_TYPE);
        assertThat(testDatacenterDevice.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDatacenterDevice.getBrandAndModel()).isEqualTo(UPDATED_BRAND_AND_MODEL);
        assertThat(testDatacenterDevice.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testDatacenterDevice.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testDatacenterDevice.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingDatacenterDevice() throws Exception {
        int databaseSizeBeforeUpdate = datacenterDeviceRepository.findAll().size();
        datacenterDevice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatacenterDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, datacenterDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(datacenterDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatacenterDevice in the database
        List<DatacenterDevice> datacenterDeviceList = datacenterDeviceRepository.findAll();
        assertThat(datacenterDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDatacenterDevice() throws Exception {
        int databaseSizeBeforeUpdate = datacenterDeviceRepository.findAll().size();
        datacenterDevice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatacenterDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(datacenterDevice))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatacenterDevice in the database
        List<DatacenterDevice> datacenterDeviceList = datacenterDeviceRepository.findAll();
        assertThat(datacenterDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDatacenterDevice() throws Exception {
        int databaseSizeBeforeUpdate = datacenterDeviceRepository.findAll().size();
        datacenterDevice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatacenterDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(datacenterDevice))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DatacenterDevice in the database
        List<DatacenterDevice> datacenterDeviceList = datacenterDeviceRepository.findAll();
        assertThat(datacenterDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDatacenterDevice() throws Exception {
        // Initialize the database
        datacenterDeviceRepository.saveAndFlush(datacenterDevice);

        int databaseSizeBeforeDelete = datacenterDeviceRepository.findAll().size();

        // Delete the datacenterDevice
        restDatacenterDeviceMockMvc
            .perform(delete(ENTITY_API_URL_ID, datacenterDevice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatacenterDevice> datacenterDeviceList = datacenterDeviceRepository.findAll();
        assertThat(datacenterDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
