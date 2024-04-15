package com.amin.survey.web.rest;

import com.amin.survey.domain.DatacenterDevice;
import com.amin.survey.repository.DatacenterDeviceRepository;
import com.amin.survey.service.DatacenterDeviceQueryService;
import com.amin.survey.service.DatacenterDeviceService;
import com.amin.survey.service.criteria.DatacenterDeviceCriteria;
import com.amin.survey.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.amin.survey.domain.DatacenterDevice}.
 */
@RestController
@RequestMapping("/api")
public class DatacenterDeviceResource {

    private final Logger log = LoggerFactory.getLogger(DatacenterDeviceResource.class);

    private static final String ENTITY_NAME = "datacenterDevice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatacenterDeviceService datacenterDeviceService;

    private final DatacenterDeviceRepository datacenterDeviceRepository;

    private final DatacenterDeviceQueryService datacenterDeviceQueryService;

    public DatacenterDeviceResource(
        DatacenterDeviceService datacenterDeviceService,
        DatacenterDeviceRepository datacenterDeviceRepository,
        DatacenterDeviceQueryService datacenterDeviceQueryService
    ) {
        this.datacenterDeviceService = datacenterDeviceService;
        this.datacenterDeviceRepository = datacenterDeviceRepository;
        this.datacenterDeviceQueryService = datacenterDeviceQueryService;
    }

    /**
     * {@code POST  /datacenter-devices} : Create a new datacenterDevice.
     *
     * @param datacenterDevice the datacenterDevice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datacenterDevice, or with status {@code 400 (Bad Request)} if the datacenterDevice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/datacenter-devices")
    public ResponseEntity<DatacenterDevice> createDatacenterDevice(@Valid @RequestBody DatacenterDevice datacenterDevice)
        throws URISyntaxException {
        log.debug("REST request to save DatacenterDevice : {}", datacenterDevice);
        if (datacenterDevice.getId() != null) {
            throw new BadRequestAlertException("A new datacenterDevice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatacenterDevice result = datacenterDeviceService.save(datacenterDevice);
        return ResponseEntity
            .created(new URI("/api/datacenter-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /datacenter-devices/:id} : Updates an existing datacenterDevice.
     *
     * @param id the id of the datacenterDevice to save.
     * @param datacenterDevice the datacenterDevice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datacenterDevice,
     * or with status {@code 400 (Bad Request)} if the datacenterDevice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datacenterDevice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/datacenter-devices/{id}")
    public ResponseEntity<DatacenterDevice> updateDatacenterDevice(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DatacenterDevice datacenterDevice
    ) throws URISyntaxException {
        log.debug("REST request to update DatacenterDevice : {}, {}", id, datacenterDevice);
        if (datacenterDevice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, datacenterDevice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!datacenterDeviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DatacenterDevice result = datacenterDeviceService.update(datacenterDevice);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datacenterDevice.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /datacenter-devices/:id} : Partial updates given fields of an existing datacenterDevice, field will ignore if it is null
     *
     * @param id the id of the datacenterDevice to save.
     * @param datacenterDevice the datacenterDevice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datacenterDevice,
     * or with status {@code 400 (Bad Request)} if the datacenterDevice is not valid,
     * or with status {@code 404 (Not Found)} if the datacenterDevice is not found,
     * or with status {@code 500 (Internal Server Error)} if the datacenterDevice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/datacenter-devices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DatacenterDevice> partialUpdateDatacenterDevice(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DatacenterDevice datacenterDevice
    ) throws URISyntaxException {
        log.debug("REST request to partial update DatacenterDevice partially : {}, {}", id, datacenterDevice);
        if (datacenterDevice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, datacenterDevice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!datacenterDeviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DatacenterDevice> result = datacenterDeviceService.partialUpdate(datacenterDevice);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datacenterDevice.getId().toString())
        );
    }

    /**
     * {@code GET  /datacenter-devices} : get all the datacenterDevices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datacenterDevices in body.
     */
    @GetMapping("/datacenter-devices")
    public ResponseEntity<List<DatacenterDevice>> getAllDatacenterDevices(
        DatacenterDeviceCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DatacenterDevices by criteria: {}", criteria);

        Page<DatacenterDevice> page = datacenterDeviceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /datacenter-devices/count} : count all the datacenterDevices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/datacenter-devices/count")
    public ResponseEntity<Long> countDatacenterDevices(DatacenterDeviceCriteria criteria) {
        log.debug("REST request to count DatacenterDevices by criteria: {}", criteria);
        return ResponseEntity.ok().body(datacenterDeviceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /datacenter-devices/:id} : get the "id" datacenterDevice.
     *
     * @param id the id of the datacenterDevice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datacenterDevice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/datacenter-devices/{id}")
    public ResponseEntity<DatacenterDevice> getDatacenterDevice(@PathVariable Long id) {
        log.debug("REST request to get DatacenterDevice : {}", id);
        Optional<DatacenterDevice> datacenterDevice = datacenterDeviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datacenterDevice);
    }

    /**
     * {@code DELETE  /datacenter-devices/:id} : delete the "id" datacenterDevice.
     *
     * @param id the id of the datacenterDevice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/datacenter-devices/{id}")
    public ResponseEntity<Void> deleteDatacenterDevice(@PathVariable Long id) {
        log.debug("REST request to delete DatacenterDevice : {}", id);
        datacenterDeviceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/datacenter-devices/form/{id}")
    public ResponseEntity<DatacenterDevice> getDatacenterDeviceByFormId(@PathVariable Long id) {
        log.debug("REST request to get DatacenterDevice : {}", id);
        Optional<DatacenterDevice> datacenterDevice = datacenterDeviceService.findByFormId(id);
        return ResponseUtil.wrapOrNotFound(datacenterDevice);
    }

    @GetMapping("/datacenter-devices/forms/{id}")
    public ResponseEntity<List<DatacenterDevice>> getFormsAllDatacenterDevices(@PathVariable Long id) {
        List<DatacenterDevice> datacenterDevices = datacenterDeviceService.findDataCenterByFromId(id);
        return ResponseEntity.ok().body(datacenterDevices);
    }
}
