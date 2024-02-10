package com.amin.survey.web.rest;

import com.amin.survey.domain.ItDevice;
import com.amin.survey.repository.ItDeviceRepository;
import com.amin.survey.service.ItDeviceQueryService;
import com.amin.survey.service.ItDeviceService;
import com.amin.survey.service.criteria.ItDeviceCriteria;
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
 * REST controller for managing {@link com.amin.survey.domain.ItDevice}.
 */
@RestController
@RequestMapping("/api")
public class ItDeviceResource {

    private final Logger log = LoggerFactory.getLogger(ItDeviceResource.class);

    private static final String ENTITY_NAME = "itDevice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItDeviceService itDeviceService;

    private final ItDeviceRepository itDeviceRepository;

    private final ItDeviceQueryService itDeviceQueryService;

    public ItDeviceResource(
        ItDeviceService itDeviceService,
        ItDeviceRepository itDeviceRepository,
        ItDeviceQueryService itDeviceQueryService
    ) {
        this.itDeviceService = itDeviceService;
        this.itDeviceRepository = itDeviceRepository;
        this.itDeviceQueryService = itDeviceQueryService;
    }

    /**
     * {@code POST  /it-devices} : Create a new itDevice.
     *
     * @param itDevice the itDevice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itDevice, or with status {@code 400 (Bad Request)} if the itDevice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/it-devices")
    public ResponseEntity<ItDevice> createItDevice(@Valid @RequestBody ItDevice itDevice) throws URISyntaxException {
        log.debug("REST request to save ItDevice : {}", itDevice);
        if (itDevice.getId() != null) {
            throw new BadRequestAlertException("A new itDevice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItDevice result = itDeviceService.save(itDevice);
        return ResponseEntity
            .created(new URI("/api/it-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /it-devices/:id} : Updates an existing itDevice.
     *
     * @param id the id of the itDevice to save.
     * @param itDevice the itDevice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itDevice,
     * or with status {@code 400 (Bad Request)} if the itDevice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itDevice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/it-devices/{id}")
    public ResponseEntity<ItDevice> updateItDevice(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ItDevice itDevice
    ) throws URISyntaxException {
        log.debug("REST request to update ItDevice : {}, {}", id, itDevice);
        if (itDevice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itDevice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itDeviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItDevice result = itDeviceService.update(itDevice);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itDevice.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /it-devices/:id} : Partial updates given fields of an existing itDevice, field will ignore if it is null
     *
     * @param id the id of the itDevice to save.
     * @param itDevice the itDevice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itDevice,
     * or with status {@code 400 (Bad Request)} if the itDevice is not valid,
     * or with status {@code 404 (Not Found)} if the itDevice is not found,
     * or with status {@code 500 (Internal Server Error)} if the itDevice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/it-devices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ItDevice> partialUpdateItDevice(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ItDevice itDevice
    ) throws URISyntaxException {
        log.debug("REST request to partial update ItDevice partially : {}, {}", id, itDevice);
        if (itDevice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itDevice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itDeviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItDevice> result = itDeviceService.partialUpdate(itDevice);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itDevice.getId().toString())
        );
    }

    /**
     * {@code GET  /it-devices} : get all the itDevices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itDevices in body.
     */
    @GetMapping("/it-devices")
    public ResponseEntity<List<ItDevice>> getAllItDevices(
        ItDeviceCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ItDevices by criteria: {}", criteria);

        Page<ItDevice> page = itDeviceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /it-devices/count} : count all the itDevices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/it-devices/count")
    public ResponseEntity<Long> countItDevices(ItDeviceCriteria criteria) {
        log.debug("REST request to count ItDevices by criteria: {}", criteria);
        return ResponseEntity.ok().body(itDeviceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /it-devices/:id} : get the "id" itDevice.
     *
     * @param id the id of the itDevice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itDevice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/it-devices/{id}")
    public ResponseEntity<ItDevice> getItDevice(@PathVariable Long id) {
        log.debug("REST request to get ItDevice : {}", id);
        Optional<ItDevice> itDevice = itDeviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itDevice);
    }

    /**
     * {@code DELETE  /it-devices/:id} : delete the "id" itDevice.
     *
     * @param id the id of the itDevice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/it-devices/{id}")
    public ResponseEntity<Void> deleteItDevice(@PathVariable Long id) {
        log.debug("REST request to delete ItDevice : {}", id);
        itDeviceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/it-devices/form/{id}")
    public ResponseEntity<ItDevice> getItDeviceByFormId(@PathVariable Long id) {
        log.debug("REST request to get ItDevice : {}", id);
        Optional<ItDevice> itDevice = itDeviceService.findByFormId(id);
        return ResponseUtil.wrapOrNotFound(itDevice);
    }
}
