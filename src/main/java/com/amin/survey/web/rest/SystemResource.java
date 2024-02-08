package com.amin.survey.web.rest;

import com.amin.survey.domain.System;
import com.amin.survey.repository.SystemRepository;
import com.amin.survey.service.SystemQueryService;
import com.amin.survey.service.SystemService;
import com.amin.survey.service.criteria.SystemCriteria;
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
 * REST controller for managing {@link com.amin.survey.domain.System}.
 */
@RestController
@RequestMapping("/api")
public class SystemResource {

    private final Logger log = LoggerFactory.getLogger(SystemResource.class);

    private static final String ENTITY_NAME = "system";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemService systemService;

    private final SystemRepository systemRepository;

    private final SystemQueryService systemQueryService;

    public SystemResource(SystemService systemService, SystemRepository systemRepository, SystemQueryService systemQueryService) {
        this.systemService = systemService;
        this.systemRepository = systemRepository;
        this.systemQueryService = systemQueryService;
    }

    /**
     * {@code POST  /systems} : Create a new system.
     *
     * @param system the system to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new system, or with status {@code 400 (Bad Request)} if the system has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/systems")
    public ResponseEntity<System> createSystem(@Valid @RequestBody System system) throws URISyntaxException {
        log.debug("REST request to save System : {}", system);
        if (system.getId() != null) {
            throw new BadRequestAlertException("A new system cannot already have an ID", ENTITY_NAME, "idexists");
        }
        System result = systemService.save(system);
        return ResponseEntity
            .created(new URI("/api/systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /systems/:id} : Updates an existing system.
     *
     * @param id the id of the system to save.
     * @param system the system to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated system,
     * or with status {@code 400 (Bad Request)} if the system is not valid,
     * or with status {@code 500 (Internal Server Error)} if the system couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/systems/{id}")
    public ResponseEntity<System> updateSystem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody System system
    ) throws URISyntaxException {
        log.debug("REST request to update System : {}, {}", id, system);
        if (system.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, system.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!systemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        System result = systemService.update(system);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, system.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /systems/:id} : Partial updates given fields of an existing system, field will ignore if it is null
     *
     * @param id the id of the system to save.
     * @param system the system to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated system,
     * or with status {@code 400 (Bad Request)} if the system is not valid,
     * or with status {@code 404 (Not Found)} if the system is not found,
     * or with status {@code 500 (Internal Server Error)} if the system couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/systems/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<System> partialUpdateSystem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody System system
    ) throws URISyntaxException {
        log.debug("REST request to partial update System partially : {}, {}", id, system);
        if (system.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, system.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!systemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<System> result = systemService.partialUpdate(system);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, system.getId().toString())
        );
    }

    /**
     * {@code GET  /systems} : get all the systems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systems in body.
     */
    @GetMapping("/systems")
    public ResponseEntity<List<System>> getAllSystems(
        SystemCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Systems by criteria: {}", criteria);

        Page<System> page = systemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /systems/count} : count all the systems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/systems/count")
    public ResponseEntity<Long> countSystems(SystemCriteria criteria) {
        log.debug("REST request to count Systems by criteria: {}", criteria);
        return ResponseEntity.ok().body(systemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /systems/:id} : get the "id" system.
     *
     * @param id the id of the system to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the system, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/systems/{id}")
    public ResponseEntity<System> getSystem(@PathVariable Long id) {
        log.debug("REST request to get System : {}", id);
        Optional<System> system = systemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(system);
    }

    /**
     * {@code DELETE  /systems/:id} : delete the "id" system.
     *
     * @param id the id of the system to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/systems/{id}")
    public ResponseEntity<Void> deleteSystem(@PathVariable Long id) {
        log.debug("REST request to delete System : {}", id);
        systemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/systems/form/{id}")
    public ResponseEntity<System> getSystemByFormId(@PathVariable Long id) {
        log.debug("REST request to get System : {}", id);
        Optional<System> system = systemService.findByFormId(id);
        return ResponseUtil.wrapOrNotFound(system);
    }
}
