package com.amin.survey.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amin.survey.IntegrationTest;
import com.amin.survey.domain.Form;
import com.amin.survey.domain.NetworkConfigCheckList;
import com.amin.survey.repository.NetworkConfigCheckListRepository;
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
 * Integration tests for the {@link NetworkConfigCheckListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NetworkConfigCheckListResourceIT {

    private static final Boolean DEFAULT_DHCP = false;
    private static final Boolean UPDATED_DHCP = true;

    private static final Boolean DEFAULT_DNS = false;
    private static final Boolean UPDATED_DNS = true;

    private static final Boolean DEFAULT_ACTIVE_DIRECTORY = false;
    private static final Boolean UPDATED_ACTIVE_DIRECTORY = true;

    private static final Boolean DEFAULT_SHARED_DRIVES = false;
    private static final Boolean UPDATED_SHARED_DRIVES = true;

    private static final Boolean DEFAULT_MAIL_SERVER = false;
    private static final Boolean UPDATED_MAIL_SERVER = true;

    private static final Boolean DEFAULT_FIREWALLS = false;
    private static final Boolean UPDATED_FIREWALLS = true;

    private static final Boolean DEFAULT_LOAD_BALANCING = false;
    private static final Boolean UPDATED_LOAD_BALANCING = true;

    private static final Boolean DEFAULT_NETWORK_MONITORING = false;
    private static final Boolean UPDATED_NETWORK_MONITORING = true;

    private static final Boolean DEFAULT_ANTIVIRUS = false;
    private static final Boolean UPDATED_ANTIVIRUS = true;

    private static final Boolean DEFAULT_INTEGRATED_SYSTEMS = false;
    private static final Boolean UPDATED_INTEGRATED_SYSTEMS = true;

    private static final Boolean DEFAULT_ANTI_SPAM = false;
    private static final Boolean UPDATED_ANTI_SPAM = true;

    private static final Boolean DEFAULT_WPA = false;
    private static final Boolean UPDATED_WPA = true;

    private static final Boolean DEFAULT_AUTO_BACKUP = false;
    private static final Boolean UPDATED_AUTO_BACKUP = true;

    private static final Boolean DEFAULT_PHYSICAL_SECURITY = false;
    private static final Boolean UPDATED_PHYSICAL_SECURITY = true;

    private static final Boolean DEFAULT_STORAGE_SERVER = false;
    private static final Boolean UPDATED_STORAGE_SERVER = true;

    private static final Boolean DEFAULT_SECURITY_AUDIT = false;
    private static final Boolean UPDATED_SECURITY_AUDIT = true;

    private static final Boolean DEFAULT_DISASTER_RECOVERY = false;
    private static final Boolean UPDATED_DISASTER_RECOVERY = true;

    private static final Boolean DEFAULT_PROXY_SERVER = false;
    private static final Boolean UPDATED_PROXY_SERVER = true;

    private static final Boolean DEFAULT_WDS_SERVER = false;
    private static final Boolean UPDATED_WDS_SERVER = true;

    private static final String ENTITY_API_URL = "/api/network-config-check-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NetworkConfigCheckListRepository networkConfigCheckListRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNetworkConfigCheckListMockMvc;

    private NetworkConfigCheckList networkConfigCheckList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetworkConfigCheckList createEntity(EntityManager em) {
        NetworkConfigCheckList networkConfigCheckList = new NetworkConfigCheckList()
            .dhcp(DEFAULT_DHCP)
            .dns(DEFAULT_DNS)
            .activeDirectory(DEFAULT_ACTIVE_DIRECTORY)
            .sharedDrives(DEFAULT_SHARED_DRIVES)
            .mailServer(DEFAULT_MAIL_SERVER)
            .firewalls(DEFAULT_FIREWALLS)
            .loadBalancing(DEFAULT_LOAD_BALANCING)
            .networkMonitoring(DEFAULT_NETWORK_MONITORING)
            .antivirus(DEFAULT_ANTIVIRUS)
            .integratedSystems(DEFAULT_INTEGRATED_SYSTEMS)
            .antiSpam(DEFAULT_ANTI_SPAM)
            .wpa(DEFAULT_WPA)
            .autoBackup(DEFAULT_AUTO_BACKUP)
            .physicalSecurity(DEFAULT_PHYSICAL_SECURITY)
            .storageServer(DEFAULT_STORAGE_SERVER)
            .securityAudit(DEFAULT_SECURITY_AUDIT)
            .disasterRecovery(DEFAULT_DISASTER_RECOVERY)
            .proxyServer(DEFAULT_PROXY_SERVER)
            .wdsServer(DEFAULT_WDS_SERVER);
        // Add required entity
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            form = FormResourceIT.createEntity(em);
            em.persist(form);
            em.flush();
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        networkConfigCheckList.setForm(form);
        return networkConfigCheckList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetworkConfigCheckList createUpdatedEntity(EntityManager em) {
        NetworkConfigCheckList networkConfigCheckList = new NetworkConfigCheckList()
            .dhcp(UPDATED_DHCP)
            .dns(UPDATED_DNS)
            .activeDirectory(UPDATED_ACTIVE_DIRECTORY)
            .sharedDrives(UPDATED_SHARED_DRIVES)
            .mailServer(UPDATED_MAIL_SERVER)
            .firewalls(UPDATED_FIREWALLS)
            .loadBalancing(UPDATED_LOAD_BALANCING)
            .networkMonitoring(UPDATED_NETWORK_MONITORING)
            .antivirus(UPDATED_ANTIVIRUS)
            .integratedSystems(UPDATED_INTEGRATED_SYSTEMS)
            .antiSpam(UPDATED_ANTI_SPAM)
            .wpa(UPDATED_WPA)
            .autoBackup(UPDATED_AUTO_BACKUP)
            .physicalSecurity(UPDATED_PHYSICAL_SECURITY)
            .storageServer(UPDATED_STORAGE_SERVER)
            .securityAudit(UPDATED_SECURITY_AUDIT)
            .disasterRecovery(UPDATED_DISASTER_RECOVERY)
            .proxyServer(UPDATED_PROXY_SERVER)
            .wdsServer(UPDATED_WDS_SERVER);
        // Add required entity
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            form = FormResourceIT.createUpdatedEntity(em);
            em.persist(form);
            em.flush();
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        networkConfigCheckList.setForm(form);
        return networkConfigCheckList;
    }

    @BeforeEach
    public void initTest() {
        networkConfigCheckList = createEntity(em);
    }

    @Test
    @Transactional
    void createNetworkConfigCheckList() throws Exception {
        int databaseSizeBeforeCreate = networkConfigCheckListRepository.findAll().size();
        // Create the NetworkConfigCheckList
        restNetworkConfigCheckListMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(networkConfigCheckList))
            )
            .andExpect(status().isCreated());

        // Validate the NetworkConfigCheckList in the database
        List<NetworkConfigCheckList> networkConfigCheckListList = networkConfigCheckListRepository.findAll();
        assertThat(networkConfigCheckListList).hasSize(databaseSizeBeforeCreate + 1);
        NetworkConfigCheckList testNetworkConfigCheckList = networkConfigCheckListList.get(networkConfigCheckListList.size() - 1);
        assertThat(testNetworkConfigCheckList.getDhcp()).isEqualTo(DEFAULT_DHCP);
        assertThat(testNetworkConfigCheckList.getDns()).isEqualTo(DEFAULT_DNS);
        assertThat(testNetworkConfigCheckList.getActiveDirectory()).isEqualTo(DEFAULT_ACTIVE_DIRECTORY);
        assertThat(testNetworkConfigCheckList.getSharedDrives()).isEqualTo(DEFAULT_SHARED_DRIVES);
        assertThat(testNetworkConfigCheckList.getMailServer()).isEqualTo(DEFAULT_MAIL_SERVER);
        assertThat(testNetworkConfigCheckList.getFirewalls()).isEqualTo(DEFAULT_FIREWALLS);
        assertThat(testNetworkConfigCheckList.getLoadBalancing()).isEqualTo(DEFAULT_LOAD_BALANCING);
        assertThat(testNetworkConfigCheckList.getNetworkMonitoring()).isEqualTo(DEFAULT_NETWORK_MONITORING);
        assertThat(testNetworkConfigCheckList.getAntivirus()).isEqualTo(DEFAULT_ANTIVIRUS);
        assertThat(testNetworkConfigCheckList.getIntegratedSystems()).isEqualTo(DEFAULT_INTEGRATED_SYSTEMS);
        assertThat(testNetworkConfigCheckList.getAntiSpam()).isEqualTo(DEFAULT_ANTI_SPAM);
        assertThat(testNetworkConfigCheckList.getWpa()).isEqualTo(DEFAULT_WPA);
        assertThat(testNetworkConfigCheckList.getAutoBackup()).isEqualTo(DEFAULT_AUTO_BACKUP);
        assertThat(testNetworkConfigCheckList.getPhysicalSecurity()).isEqualTo(DEFAULT_PHYSICAL_SECURITY);
        assertThat(testNetworkConfigCheckList.getStorageServer()).isEqualTo(DEFAULT_STORAGE_SERVER);
        assertThat(testNetworkConfigCheckList.getSecurityAudit()).isEqualTo(DEFAULT_SECURITY_AUDIT);
        assertThat(testNetworkConfigCheckList.getDisasterRecovery()).isEqualTo(DEFAULT_DISASTER_RECOVERY);
        assertThat(testNetworkConfigCheckList.getProxyServer()).isEqualTo(DEFAULT_PROXY_SERVER);
        assertThat(testNetworkConfigCheckList.getWdsServer()).isEqualTo(DEFAULT_WDS_SERVER);
    }

    @Test
    @Transactional
    void createNetworkConfigCheckListWithExistingId() throws Exception {
        // Create the NetworkConfigCheckList with an existing ID
        networkConfigCheckList.setId(1L);

        int databaseSizeBeforeCreate = networkConfigCheckListRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetworkConfigCheckListMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(networkConfigCheckList))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetworkConfigCheckList in the database
        List<NetworkConfigCheckList> networkConfigCheckListList = networkConfigCheckListRepository.findAll();
        assertThat(networkConfigCheckListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckLists() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList
        restNetworkConfigCheckListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networkConfigCheckList.getId().intValue())))
            .andExpect(jsonPath("$.[*].dhcp").value(hasItem(DEFAULT_DHCP.booleanValue())))
            .andExpect(jsonPath("$.[*].dns").value(hasItem(DEFAULT_DNS.booleanValue())))
            .andExpect(jsonPath("$.[*].activeDirectory").value(hasItem(DEFAULT_ACTIVE_DIRECTORY.booleanValue())))
            .andExpect(jsonPath("$.[*].sharedDrives").value(hasItem(DEFAULT_SHARED_DRIVES.booleanValue())))
            .andExpect(jsonPath("$.[*].mailServer").value(hasItem(DEFAULT_MAIL_SERVER.booleanValue())))
            .andExpect(jsonPath("$.[*].firewalls").value(hasItem(DEFAULT_FIREWALLS.booleanValue())))
            .andExpect(jsonPath("$.[*].loadBalancing").value(hasItem(DEFAULT_LOAD_BALANCING.booleanValue())))
            .andExpect(jsonPath("$.[*].networkMonitoring").value(hasItem(DEFAULT_NETWORK_MONITORING.booleanValue())))
            .andExpect(jsonPath("$.[*].antivirus").value(hasItem(DEFAULT_ANTIVIRUS.booleanValue())))
            .andExpect(jsonPath("$.[*].integratedSystems").value(hasItem(DEFAULT_INTEGRATED_SYSTEMS.booleanValue())))
            .andExpect(jsonPath("$.[*].antiSpam").value(hasItem(DEFAULT_ANTI_SPAM.booleanValue())))
            .andExpect(jsonPath("$.[*].wpa").value(hasItem(DEFAULT_WPA.booleanValue())))
            .andExpect(jsonPath("$.[*].autoBackup").value(hasItem(DEFAULT_AUTO_BACKUP.booleanValue())))
            .andExpect(jsonPath("$.[*].physicalSecurity").value(hasItem(DEFAULT_PHYSICAL_SECURITY.booleanValue())))
            .andExpect(jsonPath("$.[*].storageServer").value(hasItem(DEFAULT_STORAGE_SERVER.booleanValue())))
            .andExpect(jsonPath("$.[*].securityAudit").value(hasItem(DEFAULT_SECURITY_AUDIT.booleanValue())))
            .andExpect(jsonPath("$.[*].disasterRecovery").value(hasItem(DEFAULT_DISASTER_RECOVERY.booleanValue())))
            .andExpect(jsonPath("$.[*].proxyServer").value(hasItem(DEFAULT_PROXY_SERVER.booleanValue())))
            .andExpect(jsonPath("$.[*].wdsServer").value(hasItem(DEFAULT_WDS_SERVER.booleanValue())));
    }

    @Test
    @Transactional
    void getNetworkConfigCheckList() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get the networkConfigCheckList
        restNetworkConfigCheckListMockMvc
            .perform(get(ENTITY_API_URL_ID, networkConfigCheckList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(networkConfigCheckList.getId().intValue()))
            .andExpect(jsonPath("$.dhcp").value(DEFAULT_DHCP.booleanValue()))
            .andExpect(jsonPath("$.dns").value(DEFAULT_DNS.booleanValue()))
            .andExpect(jsonPath("$.activeDirectory").value(DEFAULT_ACTIVE_DIRECTORY.booleanValue()))
            .andExpect(jsonPath("$.sharedDrives").value(DEFAULT_SHARED_DRIVES.booleanValue()))
            .andExpect(jsonPath("$.mailServer").value(DEFAULT_MAIL_SERVER.booleanValue()))
            .andExpect(jsonPath("$.firewalls").value(DEFAULT_FIREWALLS.booleanValue()))
            .andExpect(jsonPath("$.loadBalancing").value(DEFAULT_LOAD_BALANCING.booleanValue()))
            .andExpect(jsonPath("$.networkMonitoring").value(DEFAULT_NETWORK_MONITORING.booleanValue()))
            .andExpect(jsonPath("$.antivirus").value(DEFAULT_ANTIVIRUS.booleanValue()))
            .andExpect(jsonPath("$.integratedSystems").value(DEFAULT_INTEGRATED_SYSTEMS.booleanValue()))
            .andExpect(jsonPath("$.antiSpam").value(DEFAULT_ANTI_SPAM.booleanValue()))
            .andExpect(jsonPath("$.wpa").value(DEFAULT_WPA.booleanValue()))
            .andExpect(jsonPath("$.autoBackup").value(DEFAULT_AUTO_BACKUP.booleanValue()))
            .andExpect(jsonPath("$.physicalSecurity").value(DEFAULT_PHYSICAL_SECURITY.booleanValue()))
            .andExpect(jsonPath("$.storageServer").value(DEFAULT_STORAGE_SERVER.booleanValue()))
            .andExpect(jsonPath("$.securityAudit").value(DEFAULT_SECURITY_AUDIT.booleanValue()))
            .andExpect(jsonPath("$.disasterRecovery").value(DEFAULT_DISASTER_RECOVERY.booleanValue()))
            .andExpect(jsonPath("$.proxyServer").value(DEFAULT_PROXY_SERVER.booleanValue()))
            .andExpect(jsonPath("$.wdsServer").value(DEFAULT_WDS_SERVER.booleanValue()));
    }

    @Test
    @Transactional
    void getNetworkConfigCheckListsByIdFiltering() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        Long id = networkConfigCheckList.getId();

        defaultNetworkConfigCheckListShouldBeFound("id.equals=" + id);
        defaultNetworkConfigCheckListShouldNotBeFound("id.notEquals=" + id);

        defaultNetworkConfigCheckListShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNetworkConfigCheckListShouldNotBeFound("id.greaterThan=" + id);

        defaultNetworkConfigCheckListShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNetworkConfigCheckListShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByDhcpIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where dhcp equals to DEFAULT_DHCP
        defaultNetworkConfigCheckListShouldBeFound("dhcp.equals=" + DEFAULT_DHCP);

        // Get all the networkConfigCheckListList where dhcp equals to UPDATED_DHCP
        defaultNetworkConfigCheckListShouldNotBeFound("dhcp.equals=" + UPDATED_DHCP);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByDhcpIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where dhcp in DEFAULT_DHCP or UPDATED_DHCP
        defaultNetworkConfigCheckListShouldBeFound("dhcp.in=" + DEFAULT_DHCP + "," + UPDATED_DHCP);

        // Get all the networkConfigCheckListList where dhcp equals to UPDATED_DHCP
        defaultNetworkConfigCheckListShouldNotBeFound("dhcp.in=" + UPDATED_DHCP);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByDhcpIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where dhcp is not null
        defaultNetworkConfigCheckListShouldBeFound("dhcp.specified=true");

        // Get all the networkConfigCheckListList where dhcp is null
        defaultNetworkConfigCheckListShouldNotBeFound("dhcp.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByDnsIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where dns equals to DEFAULT_DNS
        defaultNetworkConfigCheckListShouldBeFound("dns.equals=" + DEFAULT_DNS);

        // Get all the networkConfigCheckListList where dns equals to UPDATED_DNS
        defaultNetworkConfigCheckListShouldNotBeFound("dns.equals=" + UPDATED_DNS);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByDnsIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where dns in DEFAULT_DNS or UPDATED_DNS
        defaultNetworkConfigCheckListShouldBeFound("dns.in=" + DEFAULT_DNS + "," + UPDATED_DNS);

        // Get all the networkConfigCheckListList where dns equals to UPDATED_DNS
        defaultNetworkConfigCheckListShouldNotBeFound("dns.in=" + UPDATED_DNS);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByDnsIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where dns is not null
        defaultNetworkConfigCheckListShouldBeFound("dns.specified=true");

        // Get all the networkConfigCheckListList where dns is null
        defaultNetworkConfigCheckListShouldNotBeFound("dns.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByActiveDirectoryIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where activeDirectory equals to DEFAULT_ACTIVE_DIRECTORY
        defaultNetworkConfigCheckListShouldBeFound("activeDirectory.equals=" + DEFAULT_ACTIVE_DIRECTORY);

        // Get all the networkConfigCheckListList where activeDirectory equals to UPDATED_ACTIVE_DIRECTORY
        defaultNetworkConfigCheckListShouldNotBeFound("activeDirectory.equals=" + UPDATED_ACTIVE_DIRECTORY);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByActiveDirectoryIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where activeDirectory in DEFAULT_ACTIVE_DIRECTORY or UPDATED_ACTIVE_DIRECTORY
        defaultNetworkConfigCheckListShouldBeFound("activeDirectory.in=" + DEFAULT_ACTIVE_DIRECTORY + "," + UPDATED_ACTIVE_DIRECTORY);

        // Get all the networkConfigCheckListList where activeDirectory equals to UPDATED_ACTIVE_DIRECTORY
        defaultNetworkConfigCheckListShouldNotBeFound("activeDirectory.in=" + UPDATED_ACTIVE_DIRECTORY);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByActiveDirectoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where activeDirectory is not null
        defaultNetworkConfigCheckListShouldBeFound("activeDirectory.specified=true");

        // Get all the networkConfigCheckListList where activeDirectory is null
        defaultNetworkConfigCheckListShouldNotBeFound("activeDirectory.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsBySharedDrivesIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where sharedDrives equals to DEFAULT_SHARED_DRIVES
        defaultNetworkConfigCheckListShouldBeFound("sharedDrives.equals=" + DEFAULT_SHARED_DRIVES);

        // Get all the networkConfigCheckListList where sharedDrives equals to UPDATED_SHARED_DRIVES
        defaultNetworkConfigCheckListShouldNotBeFound("sharedDrives.equals=" + UPDATED_SHARED_DRIVES);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsBySharedDrivesIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where sharedDrives in DEFAULT_SHARED_DRIVES or UPDATED_SHARED_DRIVES
        defaultNetworkConfigCheckListShouldBeFound("sharedDrives.in=" + DEFAULT_SHARED_DRIVES + "," + UPDATED_SHARED_DRIVES);

        // Get all the networkConfigCheckListList where sharedDrives equals to UPDATED_SHARED_DRIVES
        defaultNetworkConfigCheckListShouldNotBeFound("sharedDrives.in=" + UPDATED_SHARED_DRIVES);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsBySharedDrivesIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where sharedDrives is not null
        defaultNetworkConfigCheckListShouldBeFound("sharedDrives.specified=true");

        // Get all the networkConfigCheckListList where sharedDrives is null
        defaultNetworkConfigCheckListShouldNotBeFound("sharedDrives.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByMailServerIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where mailServer equals to DEFAULT_MAIL_SERVER
        defaultNetworkConfigCheckListShouldBeFound("mailServer.equals=" + DEFAULT_MAIL_SERVER);

        // Get all the networkConfigCheckListList where mailServer equals to UPDATED_MAIL_SERVER
        defaultNetworkConfigCheckListShouldNotBeFound("mailServer.equals=" + UPDATED_MAIL_SERVER);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByMailServerIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where mailServer in DEFAULT_MAIL_SERVER or UPDATED_MAIL_SERVER
        defaultNetworkConfigCheckListShouldBeFound("mailServer.in=" + DEFAULT_MAIL_SERVER + "," + UPDATED_MAIL_SERVER);

        // Get all the networkConfigCheckListList where mailServer equals to UPDATED_MAIL_SERVER
        defaultNetworkConfigCheckListShouldNotBeFound("mailServer.in=" + UPDATED_MAIL_SERVER);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByMailServerIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where mailServer is not null
        defaultNetworkConfigCheckListShouldBeFound("mailServer.specified=true");

        // Get all the networkConfigCheckListList where mailServer is null
        defaultNetworkConfigCheckListShouldNotBeFound("mailServer.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByFirewallsIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where firewalls equals to DEFAULT_FIREWALLS
        defaultNetworkConfigCheckListShouldBeFound("firewalls.equals=" + DEFAULT_FIREWALLS);

        // Get all the networkConfigCheckListList where firewalls equals to UPDATED_FIREWALLS
        defaultNetworkConfigCheckListShouldNotBeFound("firewalls.equals=" + UPDATED_FIREWALLS);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByFirewallsIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where firewalls in DEFAULT_FIREWALLS or UPDATED_FIREWALLS
        defaultNetworkConfigCheckListShouldBeFound("firewalls.in=" + DEFAULT_FIREWALLS + "," + UPDATED_FIREWALLS);

        // Get all the networkConfigCheckListList where firewalls equals to UPDATED_FIREWALLS
        defaultNetworkConfigCheckListShouldNotBeFound("firewalls.in=" + UPDATED_FIREWALLS);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByFirewallsIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where firewalls is not null
        defaultNetworkConfigCheckListShouldBeFound("firewalls.specified=true");

        // Get all the networkConfigCheckListList where firewalls is null
        defaultNetworkConfigCheckListShouldNotBeFound("firewalls.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByLoadBalancingIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where loadBalancing equals to DEFAULT_LOAD_BALANCING
        defaultNetworkConfigCheckListShouldBeFound("loadBalancing.equals=" + DEFAULT_LOAD_BALANCING);

        // Get all the networkConfigCheckListList where loadBalancing equals to UPDATED_LOAD_BALANCING
        defaultNetworkConfigCheckListShouldNotBeFound("loadBalancing.equals=" + UPDATED_LOAD_BALANCING);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByLoadBalancingIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where loadBalancing in DEFAULT_LOAD_BALANCING or UPDATED_LOAD_BALANCING
        defaultNetworkConfigCheckListShouldBeFound("loadBalancing.in=" + DEFAULT_LOAD_BALANCING + "," + UPDATED_LOAD_BALANCING);

        // Get all the networkConfigCheckListList where loadBalancing equals to UPDATED_LOAD_BALANCING
        defaultNetworkConfigCheckListShouldNotBeFound("loadBalancing.in=" + UPDATED_LOAD_BALANCING);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByLoadBalancingIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where loadBalancing is not null
        defaultNetworkConfigCheckListShouldBeFound("loadBalancing.specified=true");

        // Get all the networkConfigCheckListList where loadBalancing is null
        defaultNetworkConfigCheckListShouldNotBeFound("loadBalancing.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByNetworkMonitoringIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where networkMonitoring equals to DEFAULT_NETWORK_MONITORING
        defaultNetworkConfigCheckListShouldBeFound("networkMonitoring.equals=" + DEFAULT_NETWORK_MONITORING);

        // Get all the networkConfigCheckListList where networkMonitoring equals to UPDATED_NETWORK_MONITORING
        defaultNetworkConfigCheckListShouldNotBeFound("networkMonitoring.equals=" + UPDATED_NETWORK_MONITORING);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByNetworkMonitoringIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where networkMonitoring in DEFAULT_NETWORK_MONITORING or UPDATED_NETWORK_MONITORING
        defaultNetworkConfigCheckListShouldBeFound("networkMonitoring.in=" + DEFAULT_NETWORK_MONITORING + "," + UPDATED_NETWORK_MONITORING);

        // Get all the networkConfigCheckListList where networkMonitoring equals to UPDATED_NETWORK_MONITORING
        defaultNetworkConfigCheckListShouldNotBeFound("networkMonitoring.in=" + UPDATED_NETWORK_MONITORING);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByNetworkMonitoringIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where networkMonitoring is not null
        defaultNetworkConfigCheckListShouldBeFound("networkMonitoring.specified=true");

        // Get all the networkConfigCheckListList where networkMonitoring is null
        defaultNetworkConfigCheckListShouldNotBeFound("networkMonitoring.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByAntivirusIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where antivirus equals to DEFAULT_ANTIVIRUS
        defaultNetworkConfigCheckListShouldBeFound("antivirus.equals=" + DEFAULT_ANTIVIRUS);

        // Get all the networkConfigCheckListList where antivirus equals to UPDATED_ANTIVIRUS
        defaultNetworkConfigCheckListShouldNotBeFound("antivirus.equals=" + UPDATED_ANTIVIRUS);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByAntivirusIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where antivirus in DEFAULT_ANTIVIRUS or UPDATED_ANTIVIRUS
        defaultNetworkConfigCheckListShouldBeFound("antivirus.in=" + DEFAULT_ANTIVIRUS + "," + UPDATED_ANTIVIRUS);

        // Get all the networkConfigCheckListList where antivirus equals to UPDATED_ANTIVIRUS
        defaultNetworkConfigCheckListShouldNotBeFound("antivirus.in=" + UPDATED_ANTIVIRUS);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByAntivirusIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where antivirus is not null
        defaultNetworkConfigCheckListShouldBeFound("antivirus.specified=true");

        // Get all the networkConfigCheckListList where antivirus is null
        defaultNetworkConfigCheckListShouldNotBeFound("antivirus.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByIntegratedSystemsIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where integratedSystems equals to DEFAULT_INTEGRATED_SYSTEMS
        defaultNetworkConfigCheckListShouldBeFound("integratedSystems.equals=" + DEFAULT_INTEGRATED_SYSTEMS);

        // Get all the networkConfigCheckListList where integratedSystems equals to UPDATED_INTEGRATED_SYSTEMS
        defaultNetworkConfigCheckListShouldNotBeFound("integratedSystems.equals=" + UPDATED_INTEGRATED_SYSTEMS);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByIntegratedSystemsIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where integratedSystems in DEFAULT_INTEGRATED_SYSTEMS or UPDATED_INTEGRATED_SYSTEMS
        defaultNetworkConfigCheckListShouldBeFound("integratedSystems.in=" + DEFAULT_INTEGRATED_SYSTEMS + "," + UPDATED_INTEGRATED_SYSTEMS);

        // Get all the networkConfigCheckListList where integratedSystems equals to UPDATED_INTEGRATED_SYSTEMS
        defaultNetworkConfigCheckListShouldNotBeFound("integratedSystems.in=" + UPDATED_INTEGRATED_SYSTEMS);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByIntegratedSystemsIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where integratedSystems is not null
        defaultNetworkConfigCheckListShouldBeFound("integratedSystems.specified=true");

        // Get all the networkConfigCheckListList where integratedSystems is null
        defaultNetworkConfigCheckListShouldNotBeFound("integratedSystems.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByAntiSpamIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where antiSpam equals to DEFAULT_ANTI_SPAM
        defaultNetworkConfigCheckListShouldBeFound("antiSpam.equals=" + DEFAULT_ANTI_SPAM);

        // Get all the networkConfigCheckListList where antiSpam equals to UPDATED_ANTI_SPAM
        defaultNetworkConfigCheckListShouldNotBeFound("antiSpam.equals=" + UPDATED_ANTI_SPAM);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByAntiSpamIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where antiSpam in DEFAULT_ANTI_SPAM or UPDATED_ANTI_SPAM
        defaultNetworkConfigCheckListShouldBeFound("antiSpam.in=" + DEFAULT_ANTI_SPAM + "," + UPDATED_ANTI_SPAM);

        // Get all the networkConfigCheckListList where antiSpam equals to UPDATED_ANTI_SPAM
        defaultNetworkConfigCheckListShouldNotBeFound("antiSpam.in=" + UPDATED_ANTI_SPAM);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByAntiSpamIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where antiSpam is not null
        defaultNetworkConfigCheckListShouldBeFound("antiSpam.specified=true");

        // Get all the networkConfigCheckListList where antiSpam is null
        defaultNetworkConfigCheckListShouldNotBeFound("antiSpam.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByWpaIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where wpa equals to DEFAULT_WPA
        defaultNetworkConfigCheckListShouldBeFound("wpa.equals=" + DEFAULT_WPA);

        // Get all the networkConfigCheckListList where wpa equals to UPDATED_WPA
        defaultNetworkConfigCheckListShouldNotBeFound("wpa.equals=" + UPDATED_WPA);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByWpaIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where wpa in DEFAULT_WPA or UPDATED_WPA
        defaultNetworkConfigCheckListShouldBeFound("wpa.in=" + DEFAULT_WPA + "," + UPDATED_WPA);

        // Get all the networkConfigCheckListList where wpa equals to UPDATED_WPA
        defaultNetworkConfigCheckListShouldNotBeFound("wpa.in=" + UPDATED_WPA);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByWpaIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where wpa is not null
        defaultNetworkConfigCheckListShouldBeFound("wpa.specified=true");

        // Get all the networkConfigCheckListList where wpa is null
        defaultNetworkConfigCheckListShouldNotBeFound("wpa.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByAutoBackupIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where autoBackup equals to DEFAULT_AUTO_BACKUP
        defaultNetworkConfigCheckListShouldBeFound("autoBackup.equals=" + DEFAULT_AUTO_BACKUP);

        // Get all the networkConfigCheckListList where autoBackup equals to UPDATED_AUTO_BACKUP
        defaultNetworkConfigCheckListShouldNotBeFound("autoBackup.equals=" + UPDATED_AUTO_BACKUP);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByAutoBackupIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where autoBackup in DEFAULT_AUTO_BACKUP or UPDATED_AUTO_BACKUP
        defaultNetworkConfigCheckListShouldBeFound("autoBackup.in=" + DEFAULT_AUTO_BACKUP + "," + UPDATED_AUTO_BACKUP);

        // Get all the networkConfigCheckListList where autoBackup equals to UPDATED_AUTO_BACKUP
        defaultNetworkConfigCheckListShouldNotBeFound("autoBackup.in=" + UPDATED_AUTO_BACKUP);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByAutoBackupIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where autoBackup is not null
        defaultNetworkConfigCheckListShouldBeFound("autoBackup.specified=true");

        // Get all the networkConfigCheckListList where autoBackup is null
        defaultNetworkConfigCheckListShouldNotBeFound("autoBackup.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByPhysicalSecurityIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where physicalSecurity equals to DEFAULT_PHYSICAL_SECURITY
        defaultNetworkConfigCheckListShouldBeFound("physicalSecurity.equals=" + DEFAULT_PHYSICAL_SECURITY);

        // Get all the networkConfigCheckListList where physicalSecurity equals to UPDATED_PHYSICAL_SECURITY
        defaultNetworkConfigCheckListShouldNotBeFound("physicalSecurity.equals=" + UPDATED_PHYSICAL_SECURITY);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByPhysicalSecurityIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where physicalSecurity in DEFAULT_PHYSICAL_SECURITY or UPDATED_PHYSICAL_SECURITY
        defaultNetworkConfigCheckListShouldBeFound("physicalSecurity.in=" + DEFAULT_PHYSICAL_SECURITY + "," + UPDATED_PHYSICAL_SECURITY);

        // Get all the networkConfigCheckListList where physicalSecurity equals to UPDATED_PHYSICAL_SECURITY
        defaultNetworkConfigCheckListShouldNotBeFound("physicalSecurity.in=" + UPDATED_PHYSICAL_SECURITY);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByPhysicalSecurityIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where physicalSecurity is not null
        defaultNetworkConfigCheckListShouldBeFound("physicalSecurity.specified=true");

        // Get all the networkConfigCheckListList where physicalSecurity is null
        defaultNetworkConfigCheckListShouldNotBeFound("physicalSecurity.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByStorageServerIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where storageServer equals to DEFAULT_STORAGE_SERVER
        defaultNetworkConfigCheckListShouldBeFound("storageServer.equals=" + DEFAULT_STORAGE_SERVER);

        // Get all the networkConfigCheckListList where storageServer equals to UPDATED_STORAGE_SERVER
        defaultNetworkConfigCheckListShouldNotBeFound("storageServer.equals=" + UPDATED_STORAGE_SERVER);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByStorageServerIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where storageServer in DEFAULT_STORAGE_SERVER or UPDATED_STORAGE_SERVER
        defaultNetworkConfigCheckListShouldBeFound("storageServer.in=" + DEFAULT_STORAGE_SERVER + "," + UPDATED_STORAGE_SERVER);

        // Get all the networkConfigCheckListList where storageServer equals to UPDATED_STORAGE_SERVER
        defaultNetworkConfigCheckListShouldNotBeFound("storageServer.in=" + UPDATED_STORAGE_SERVER);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByStorageServerIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where storageServer is not null
        defaultNetworkConfigCheckListShouldBeFound("storageServer.specified=true");

        // Get all the networkConfigCheckListList where storageServer is null
        defaultNetworkConfigCheckListShouldNotBeFound("storageServer.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsBySecurityAuditIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where securityAudit equals to DEFAULT_SECURITY_AUDIT
        defaultNetworkConfigCheckListShouldBeFound("securityAudit.equals=" + DEFAULT_SECURITY_AUDIT);

        // Get all the networkConfigCheckListList where securityAudit equals to UPDATED_SECURITY_AUDIT
        defaultNetworkConfigCheckListShouldNotBeFound("securityAudit.equals=" + UPDATED_SECURITY_AUDIT);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsBySecurityAuditIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where securityAudit in DEFAULT_SECURITY_AUDIT or UPDATED_SECURITY_AUDIT
        defaultNetworkConfigCheckListShouldBeFound("securityAudit.in=" + DEFAULT_SECURITY_AUDIT + "," + UPDATED_SECURITY_AUDIT);

        // Get all the networkConfigCheckListList where securityAudit equals to UPDATED_SECURITY_AUDIT
        defaultNetworkConfigCheckListShouldNotBeFound("securityAudit.in=" + UPDATED_SECURITY_AUDIT);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsBySecurityAuditIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where securityAudit is not null
        defaultNetworkConfigCheckListShouldBeFound("securityAudit.specified=true");

        // Get all the networkConfigCheckListList where securityAudit is null
        defaultNetworkConfigCheckListShouldNotBeFound("securityAudit.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByDisasterRecoveryIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where disasterRecovery equals to DEFAULT_DISASTER_RECOVERY
        defaultNetworkConfigCheckListShouldBeFound("disasterRecovery.equals=" + DEFAULT_DISASTER_RECOVERY);

        // Get all the networkConfigCheckListList where disasterRecovery equals to UPDATED_DISASTER_RECOVERY
        defaultNetworkConfigCheckListShouldNotBeFound("disasterRecovery.equals=" + UPDATED_DISASTER_RECOVERY);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByDisasterRecoveryIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where disasterRecovery in DEFAULT_DISASTER_RECOVERY or UPDATED_DISASTER_RECOVERY
        defaultNetworkConfigCheckListShouldBeFound("disasterRecovery.in=" + DEFAULT_DISASTER_RECOVERY + "," + UPDATED_DISASTER_RECOVERY);

        // Get all the networkConfigCheckListList where disasterRecovery equals to UPDATED_DISASTER_RECOVERY
        defaultNetworkConfigCheckListShouldNotBeFound("disasterRecovery.in=" + UPDATED_DISASTER_RECOVERY);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByDisasterRecoveryIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where disasterRecovery is not null
        defaultNetworkConfigCheckListShouldBeFound("disasterRecovery.specified=true");

        // Get all the networkConfigCheckListList where disasterRecovery is null
        defaultNetworkConfigCheckListShouldNotBeFound("disasterRecovery.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByProxyServerIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where proxyServer equals to DEFAULT_PROXY_SERVER
        defaultNetworkConfigCheckListShouldBeFound("proxyServer.equals=" + DEFAULT_PROXY_SERVER);

        // Get all the networkConfigCheckListList where proxyServer equals to UPDATED_PROXY_SERVER
        defaultNetworkConfigCheckListShouldNotBeFound("proxyServer.equals=" + UPDATED_PROXY_SERVER);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByProxyServerIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where proxyServer in DEFAULT_PROXY_SERVER or UPDATED_PROXY_SERVER
        defaultNetworkConfigCheckListShouldBeFound("proxyServer.in=" + DEFAULT_PROXY_SERVER + "," + UPDATED_PROXY_SERVER);

        // Get all the networkConfigCheckListList where proxyServer equals to UPDATED_PROXY_SERVER
        defaultNetworkConfigCheckListShouldNotBeFound("proxyServer.in=" + UPDATED_PROXY_SERVER);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByProxyServerIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where proxyServer is not null
        defaultNetworkConfigCheckListShouldBeFound("proxyServer.specified=true");

        // Get all the networkConfigCheckListList where proxyServer is null
        defaultNetworkConfigCheckListShouldNotBeFound("proxyServer.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByWdsServerIsEqualToSomething() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where wdsServer equals to DEFAULT_WDS_SERVER
        defaultNetworkConfigCheckListShouldBeFound("wdsServer.equals=" + DEFAULT_WDS_SERVER);

        // Get all the networkConfigCheckListList where wdsServer equals to UPDATED_WDS_SERVER
        defaultNetworkConfigCheckListShouldNotBeFound("wdsServer.equals=" + UPDATED_WDS_SERVER);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByWdsServerIsInShouldWork() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where wdsServer in DEFAULT_WDS_SERVER or UPDATED_WDS_SERVER
        defaultNetworkConfigCheckListShouldBeFound("wdsServer.in=" + DEFAULT_WDS_SERVER + "," + UPDATED_WDS_SERVER);

        // Get all the networkConfigCheckListList where wdsServer equals to UPDATED_WDS_SERVER
        defaultNetworkConfigCheckListShouldNotBeFound("wdsServer.in=" + UPDATED_WDS_SERVER);
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByWdsServerIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        // Get all the networkConfigCheckListList where wdsServer is not null
        defaultNetworkConfigCheckListShouldBeFound("wdsServer.specified=true");

        // Get all the networkConfigCheckListList where wdsServer is null
        defaultNetworkConfigCheckListShouldNotBeFound("wdsServer.specified=false");
    }

    @Test
    @Transactional
    void getAllNetworkConfigCheckListsByFormIsEqualToSomething() throws Exception {
        Form form;
        if (TestUtil.findAll(em, Form.class).isEmpty()) {
            networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);
            form = FormResourceIT.createEntity(em);
        } else {
            form = TestUtil.findAll(em, Form.class).get(0);
        }
        em.persist(form);
        em.flush();
        networkConfigCheckList.setForm(form);
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);
        Long formId = form.getId();
        // Get all the networkConfigCheckListList where form equals to formId
        defaultNetworkConfigCheckListShouldBeFound("formId.equals=" + formId);

        // Get all the networkConfigCheckListList where form equals to (formId + 1)
        defaultNetworkConfigCheckListShouldNotBeFound("formId.equals=" + (formId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNetworkConfigCheckListShouldBeFound(String filter) throws Exception {
        restNetworkConfigCheckListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networkConfigCheckList.getId().intValue())))
            .andExpect(jsonPath("$.[*].dhcp").value(hasItem(DEFAULT_DHCP.booleanValue())))
            .andExpect(jsonPath("$.[*].dns").value(hasItem(DEFAULT_DNS.booleanValue())))
            .andExpect(jsonPath("$.[*].activeDirectory").value(hasItem(DEFAULT_ACTIVE_DIRECTORY.booleanValue())))
            .andExpect(jsonPath("$.[*].sharedDrives").value(hasItem(DEFAULT_SHARED_DRIVES.booleanValue())))
            .andExpect(jsonPath("$.[*].mailServer").value(hasItem(DEFAULT_MAIL_SERVER.booleanValue())))
            .andExpect(jsonPath("$.[*].firewalls").value(hasItem(DEFAULT_FIREWALLS.booleanValue())))
            .andExpect(jsonPath("$.[*].loadBalancing").value(hasItem(DEFAULT_LOAD_BALANCING.booleanValue())))
            .andExpect(jsonPath("$.[*].networkMonitoring").value(hasItem(DEFAULT_NETWORK_MONITORING.booleanValue())))
            .andExpect(jsonPath("$.[*].antivirus").value(hasItem(DEFAULT_ANTIVIRUS.booleanValue())))
            .andExpect(jsonPath("$.[*].integratedSystems").value(hasItem(DEFAULT_INTEGRATED_SYSTEMS.booleanValue())))
            .andExpect(jsonPath("$.[*].antiSpam").value(hasItem(DEFAULT_ANTI_SPAM.booleanValue())))
            .andExpect(jsonPath("$.[*].wpa").value(hasItem(DEFAULT_WPA.booleanValue())))
            .andExpect(jsonPath("$.[*].autoBackup").value(hasItem(DEFAULT_AUTO_BACKUP.booleanValue())))
            .andExpect(jsonPath("$.[*].physicalSecurity").value(hasItem(DEFAULT_PHYSICAL_SECURITY.booleanValue())))
            .andExpect(jsonPath("$.[*].storageServer").value(hasItem(DEFAULT_STORAGE_SERVER.booleanValue())))
            .andExpect(jsonPath("$.[*].securityAudit").value(hasItem(DEFAULT_SECURITY_AUDIT.booleanValue())))
            .andExpect(jsonPath("$.[*].disasterRecovery").value(hasItem(DEFAULT_DISASTER_RECOVERY.booleanValue())))
            .andExpect(jsonPath("$.[*].proxyServer").value(hasItem(DEFAULT_PROXY_SERVER.booleanValue())))
            .andExpect(jsonPath("$.[*].wdsServer").value(hasItem(DEFAULT_WDS_SERVER.booleanValue())));

        // Check, that the count call also returns 1
        restNetworkConfigCheckListMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNetworkConfigCheckListShouldNotBeFound(String filter) throws Exception {
        restNetworkConfigCheckListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNetworkConfigCheckListMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNetworkConfigCheckList() throws Exception {
        // Get the networkConfigCheckList
        restNetworkConfigCheckListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNetworkConfigCheckList() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        int databaseSizeBeforeUpdate = networkConfigCheckListRepository.findAll().size();

        // Update the networkConfigCheckList
        NetworkConfigCheckList updatedNetworkConfigCheckList = networkConfigCheckListRepository
            .findById(networkConfigCheckList.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNetworkConfigCheckList are not directly saved in db
        em.detach(updatedNetworkConfigCheckList);
        updatedNetworkConfigCheckList
            .dhcp(UPDATED_DHCP)
            .dns(UPDATED_DNS)
            .activeDirectory(UPDATED_ACTIVE_DIRECTORY)
            .sharedDrives(UPDATED_SHARED_DRIVES)
            .mailServer(UPDATED_MAIL_SERVER)
            .firewalls(UPDATED_FIREWALLS)
            .loadBalancing(UPDATED_LOAD_BALANCING)
            .networkMonitoring(UPDATED_NETWORK_MONITORING)
            .antivirus(UPDATED_ANTIVIRUS)
            .integratedSystems(UPDATED_INTEGRATED_SYSTEMS)
            .antiSpam(UPDATED_ANTI_SPAM)
            .wpa(UPDATED_WPA)
            .autoBackup(UPDATED_AUTO_BACKUP)
            .physicalSecurity(UPDATED_PHYSICAL_SECURITY)
            .storageServer(UPDATED_STORAGE_SERVER)
            .securityAudit(UPDATED_SECURITY_AUDIT)
            .disasterRecovery(UPDATED_DISASTER_RECOVERY)
            .proxyServer(UPDATED_PROXY_SERVER)
            .wdsServer(UPDATED_WDS_SERVER);

        restNetworkConfigCheckListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNetworkConfigCheckList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNetworkConfigCheckList))
            )
            .andExpect(status().isOk());

        // Validate the NetworkConfigCheckList in the database
        List<NetworkConfigCheckList> networkConfigCheckListList = networkConfigCheckListRepository.findAll();
        assertThat(networkConfigCheckListList).hasSize(databaseSizeBeforeUpdate);
        NetworkConfigCheckList testNetworkConfigCheckList = networkConfigCheckListList.get(networkConfigCheckListList.size() - 1);
        assertThat(testNetworkConfigCheckList.getDhcp()).isEqualTo(UPDATED_DHCP);
        assertThat(testNetworkConfigCheckList.getDns()).isEqualTo(UPDATED_DNS);
        assertThat(testNetworkConfigCheckList.getActiveDirectory()).isEqualTo(UPDATED_ACTIVE_DIRECTORY);
        assertThat(testNetworkConfigCheckList.getSharedDrives()).isEqualTo(UPDATED_SHARED_DRIVES);
        assertThat(testNetworkConfigCheckList.getMailServer()).isEqualTo(UPDATED_MAIL_SERVER);
        assertThat(testNetworkConfigCheckList.getFirewalls()).isEqualTo(UPDATED_FIREWALLS);
        assertThat(testNetworkConfigCheckList.getLoadBalancing()).isEqualTo(UPDATED_LOAD_BALANCING);
        assertThat(testNetworkConfigCheckList.getNetworkMonitoring()).isEqualTo(UPDATED_NETWORK_MONITORING);
        assertThat(testNetworkConfigCheckList.getAntivirus()).isEqualTo(UPDATED_ANTIVIRUS);
        assertThat(testNetworkConfigCheckList.getIntegratedSystems()).isEqualTo(UPDATED_INTEGRATED_SYSTEMS);
        assertThat(testNetworkConfigCheckList.getAntiSpam()).isEqualTo(UPDATED_ANTI_SPAM);
        assertThat(testNetworkConfigCheckList.getWpa()).isEqualTo(UPDATED_WPA);
        assertThat(testNetworkConfigCheckList.getAutoBackup()).isEqualTo(UPDATED_AUTO_BACKUP);
        assertThat(testNetworkConfigCheckList.getPhysicalSecurity()).isEqualTo(UPDATED_PHYSICAL_SECURITY);
        assertThat(testNetworkConfigCheckList.getStorageServer()).isEqualTo(UPDATED_STORAGE_SERVER);
        assertThat(testNetworkConfigCheckList.getSecurityAudit()).isEqualTo(UPDATED_SECURITY_AUDIT);
        assertThat(testNetworkConfigCheckList.getDisasterRecovery()).isEqualTo(UPDATED_DISASTER_RECOVERY);
        assertThat(testNetworkConfigCheckList.getProxyServer()).isEqualTo(UPDATED_PROXY_SERVER);
        assertThat(testNetworkConfigCheckList.getWdsServer()).isEqualTo(UPDATED_WDS_SERVER);
    }

    @Test
    @Transactional
    void putNonExistingNetworkConfigCheckList() throws Exception {
        int databaseSizeBeforeUpdate = networkConfigCheckListRepository.findAll().size();
        networkConfigCheckList.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetworkConfigCheckListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, networkConfigCheckList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(networkConfigCheckList))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetworkConfigCheckList in the database
        List<NetworkConfigCheckList> networkConfigCheckListList = networkConfigCheckListRepository.findAll();
        assertThat(networkConfigCheckListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNetworkConfigCheckList() throws Exception {
        int databaseSizeBeforeUpdate = networkConfigCheckListRepository.findAll().size();
        networkConfigCheckList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetworkConfigCheckListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(networkConfigCheckList))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetworkConfigCheckList in the database
        List<NetworkConfigCheckList> networkConfigCheckListList = networkConfigCheckListRepository.findAll();
        assertThat(networkConfigCheckListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNetworkConfigCheckList() throws Exception {
        int databaseSizeBeforeUpdate = networkConfigCheckListRepository.findAll().size();
        networkConfigCheckList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetworkConfigCheckListMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(networkConfigCheckList))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NetworkConfigCheckList in the database
        List<NetworkConfigCheckList> networkConfigCheckListList = networkConfigCheckListRepository.findAll();
        assertThat(networkConfigCheckListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNetworkConfigCheckListWithPatch() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        int databaseSizeBeforeUpdate = networkConfigCheckListRepository.findAll().size();

        // Update the networkConfigCheckList using partial update
        NetworkConfigCheckList partialUpdatedNetworkConfigCheckList = new NetworkConfigCheckList();
        partialUpdatedNetworkConfigCheckList.setId(networkConfigCheckList.getId());

        partialUpdatedNetworkConfigCheckList
            .dhcp(UPDATED_DHCP)
            .dns(UPDATED_DNS)
            .mailServer(UPDATED_MAIL_SERVER)
            .firewalls(UPDATED_FIREWALLS)
            .loadBalancing(UPDATED_LOAD_BALANCING)
            .networkMonitoring(UPDATED_NETWORK_MONITORING)
            .integratedSystems(UPDATED_INTEGRATED_SYSTEMS)
            .autoBackup(UPDATED_AUTO_BACKUP)
            .physicalSecurity(UPDATED_PHYSICAL_SECURITY)
            .disasterRecovery(UPDATED_DISASTER_RECOVERY)
            .proxyServer(UPDATED_PROXY_SERVER);

        restNetworkConfigCheckListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNetworkConfigCheckList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNetworkConfigCheckList))
            )
            .andExpect(status().isOk());

        // Validate the NetworkConfigCheckList in the database
        List<NetworkConfigCheckList> networkConfigCheckListList = networkConfigCheckListRepository.findAll();
        assertThat(networkConfigCheckListList).hasSize(databaseSizeBeforeUpdate);
        NetworkConfigCheckList testNetworkConfigCheckList = networkConfigCheckListList.get(networkConfigCheckListList.size() - 1);
        assertThat(testNetworkConfigCheckList.getDhcp()).isEqualTo(UPDATED_DHCP);
        assertThat(testNetworkConfigCheckList.getDns()).isEqualTo(UPDATED_DNS);
        assertThat(testNetworkConfigCheckList.getActiveDirectory()).isEqualTo(DEFAULT_ACTIVE_DIRECTORY);
        assertThat(testNetworkConfigCheckList.getSharedDrives()).isEqualTo(DEFAULT_SHARED_DRIVES);
        assertThat(testNetworkConfigCheckList.getMailServer()).isEqualTo(UPDATED_MAIL_SERVER);
        assertThat(testNetworkConfigCheckList.getFirewalls()).isEqualTo(UPDATED_FIREWALLS);
        assertThat(testNetworkConfigCheckList.getLoadBalancing()).isEqualTo(UPDATED_LOAD_BALANCING);
        assertThat(testNetworkConfigCheckList.getNetworkMonitoring()).isEqualTo(UPDATED_NETWORK_MONITORING);
        assertThat(testNetworkConfigCheckList.getAntivirus()).isEqualTo(DEFAULT_ANTIVIRUS);
        assertThat(testNetworkConfigCheckList.getIntegratedSystems()).isEqualTo(UPDATED_INTEGRATED_SYSTEMS);
        assertThat(testNetworkConfigCheckList.getAntiSpam()).isEqualTo(DEFAULT_ANTI_SPAM);
        assertThat(testNetworkConfigCheckList.getWpa()).isEqualTo(DEFAULT_WPA);
        assertThat(testNetworkConfigCheckList.getAutoBackup()).isEqualTo(UPDATED_AUTO_BACKUP);
        assertThat(testNetworkConfigCheckList.getPhysicalSecurity()).isEqualTo(UPDATED_PHYSICAL_SECURITY);
        assertThat(testNetworkConfigCheckList.getStorageServer()).isEqualTo(DEFAULT_STORAGE_SERVER);
        assertThat(testNetworkConfigCheckList.getSecurityAudit()).isEqualTo(DEFAULT_SECURITY_AUDIT);
        assertThat(testNetworkConfigCheckList.getDisasterRecovery()).isEqualTo(UPDATED_DISASTER_RECOVERY);
        assertThat(testNetworkConfigCheckList.getProxyServer()).isEqualTo(UPDATED_PROXY_SERVER);
        assertThat(testNetworkConfigCheckList.getWdsServer()).isEqualTo(DEFAULT_WDS_SERVER);
    }

    @Test
    @Transactional
    void fullUpdateNetworkConfigCheckListWithPatch() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        int databaseSizeBeforeUpdate = networkConfigCheckListRepository.findAll().size();

        // Update the networkConfigCheckList using partial update
        NetworkConfigCheckList partialUpdatedNetworkConfigCheckList = new NetworkConfigCheckList();
        partialUpdatedNetworkConfigCheckList.setId(networkConfigCheckList.getId());

        partialUpdatedNetworkConfigCheckList
            .dhcp(UPDATED_DHCP)
            .dns(UPDATED_DNS)
            .activeDirectory(UPDATED_ACTIVE_DIRECTORY)
            .sharedDrives(UPDATED_SHARED_DRIVES)
            .mailServer(UPDATED_MAIL_SERVER)
            .firewalls(UPDATED_FIREWALLS)
            .loadBalancing(UPDATED_LOAD_BALANCING)
            .networkMonitoring(UPDATED_NETWORK_MONITORING)
            .antivirus(UPDATED_ANTIVIRUS)
            .integratedSystems(UPDATED_INTEGRATED_SYSTEMS)
            .antiSpam(UPDATED_ANTI_SPAM)
            .wpa(UPDATED_WPA)
            .autoBackup(UPDATED_AUTO_BACKUP)
            .physicalSecurity(UPDATED_PHYSICAL_SECURITY)
            .storageServer(UPDATED_STORAGE_SERVER)
            .securityAudit(UPDATED_SECURITY_AUDIT)
            .disasterRecovery(UPDATED_DISASTER_RECOVERY)
            .proxyServer(UPDATED_PROXY_SERVER)
            .wdsServer(UPDATED_WDS_SERVER);

        restNetworkConfigCheckListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNetworkConfigCheckList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNetworkConfigCheckList))
            )
            .andExpect(status().isOk());

        // Validate the NetworkConfigCheckList in the database
        List<NetworkConfigCheckList> networkConfigCheckListList = networkConfigCheckListRepository.findAll();
        assertThat(networkConfigCheckListList).hasSize(databaseSizeBeforeUpdate);
        NetworkConfigCheckList testNetworkConfigCheckList = networkConfigCheckListList.get(networkConfigCheckListList.size() - 1);
        assertThat(testNetworkConfigCheckList.getDhcp()).isEqualTo(UPDATED_DHCP);
        assertThat(testNetworkConfigCheckList.getDns()).isEqualTo(UPDATED_DNS);
        assertThat(testNetworkConfigCheckList.getActiveDirectory()).isEqualTo(UPDATED_ACTIVE_DIRECTORY);
        assertThat(testNetworkConfigCheckList.getSharedDrives()).isEqualTo(UPDATED_SHARED_DRIVES);
        assertThat(testNetworkConfigCheckList.getMailServer()).isEqualTo(UPDATED_MAIL_SERVER);
        assertThat(testNetworkConfigCheckList.getFirewalls()).isEqualTo(UPDATED_FIREWALLS);
        assertThat(testNetworkConfigCheckList.getLoadBalancing()).isEqualTo(UPDATED_LOAD_BALANCING);
        assertThat(testNetworkConfigCheckList.getNetworkMonitoring()).isEqualTo(UPDATED_NETWORK_MONITORING);
        assertThat(testNetworkConfigCheckList.getAntivirus()).isEqualTo(UPDATED_ANTIVIRUS);
        assertThat(testNetworkConfigCheckList.getIntegratedSystems()).isEqualTo(UPDATED_INTEGRATED_SYSTEMS);
        assertThat(testNetworkConfigCheckList.getAntiSpam()).isEqualTo(UPDATED_ANTI_SPAM);
        assertThat(testNetworkConfigCheckList.getWpa()).isEqualTo(UPDATED_WPA);
        assertThat(testNetworkConfigCheckList.getAutoBackup()).isEqualTo(UPDATED_AUTO_BACKUP);
        assertThat(testNetworkConfigCheckList.getPhysicalSecurity()).isEqualTo(UPDATED_PHYSICAL_SECURITY);
        assertThat(testNetworkConfigCheckList.getStorageServer()).isEqualTo(UPDATED_STORAGE_SERVER);
        assertThat(testNetworkConfigCheckList.getSecurityAudit()).isEqualTo(UPDATED_SECURITY_AUDIT);
        assertThat(testNetworkConfigCheckList.getDisasterRecovery()).isEqualTo(UPDATED_DISASTER_RECOVERY);
        assertThat(testNetworkConfigCheckList.getProxyServer()).isEqualTo(UPDATED_PROXY_SERVER);
        assertThat(testNetworkConfigCheckList.getWdsServer()).isEqualTo(UPDATED_WDS_SERVER);
    }

    @Test
    @Transactional
    void patchNonExistingNetworkConfigCheckList() throws Exception {
        int databaseSizeBeforeUpdate = networkConfigCheckListRepository.findAll().size();
        networkConfigCheckList.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetworkConfigCheckListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, networkConfigCheckList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(networkConfigCheckList))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetworkConfigCheckList in the database
        List<NetworkConfigCheckList> networkConfigCheckListList = networkConfigCheckListRepository.findAll();
        assertThat(networkConfigCheckListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNetworkConfigCheckList() throws Exception {
        int databaseSizeBeforeUpdate = networkConfigCheckListRepository.findAll().size();
        networkConfigCheckList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetworkConfigCheckListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(networkConfigCheckList))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetworkConfigCheckList in the database
        List<NetworkConfigCheckList> networkConfigCheckListList = networkConfigCheckListRepository.findAll();
        assertThat(networkConfigCheckListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNetworkConfigCheckList() throws Exception {
        int databaseSizeBeforeUpdate = networkConfigCheckListRepository.findAll().size();
        networkConfigCheckList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetworkConfigCheckListMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(networkConfigCheckList))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NetworkConfigCheckList in the database
        List<NetworkConfigCheckList> networkConfigCheckListList = networkConfigCheckListRepository.findAll();
        assertThat(networkConfigCheckListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNetworkConfigCheckList() throws Exception {
        // Initialize the database
        networkConfigCheckListRepository.saveAndFlush(networkConfigCheckList);

        int databaseSizeBeforeDelete = networkConfigCheckListRepository.findAll().size();

        // Delete the networkConfigCheckList
        restNetworkConfigCheckListMockMvc
            .perform(delete(ENTITY_API_URL_ID, networkConfigCheckList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NetworkConfigCheckList> networkConfigCheckListList = networkConfigCheckListRepository.findAll();
        assertThat(networkConfigCheckListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
