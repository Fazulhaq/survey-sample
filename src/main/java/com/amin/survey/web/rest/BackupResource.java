package com.amin.survey.web.rest;

import com.amin.survey.domain.Backup;
import com.amin.survey.repository.BackupRepository;
import com.amin.survey.service.BackupQueryService;
import com.amin.survey.service.BackupService;
import com.amin.survey.service.criteria.BackupCriteria;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.amin.survey.domain.Backup}.
 */
@RestController
@RequestMapping("/api")
public class BackupResource {

    private final Logger log = LoggerFactory.getLogger(BackupResource.class);

    private static final String ENTITY_NAME = "backup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BackupService backupService;

    private final BackupRepository backupRepository;

    private final BackupQueryService backupQueryService;

    public BackupResource(BackupService backupService, BackupRepository backupRepository, BackupQueryService backupQueryService) {
        this.backupService = backupService;
        this.backupRepository = backupRepository;
        this.backupQueryService = backupQueryService;
    }

    /**
     * {@code POST  /backups} : Create a new backup.
     *
     * @param backup the backup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new backup, or with status {@code 400 (Bad Request)} if the backup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/backups")
    public ResponseEntity<Backup> createBackup(@Valid @RequestBody Backup backup) throws URISyntaxException {
        log.debug("REST request to save Backup : {}", backup);
        if (backup.getId() != null) {
            throw new BadRequestAlertException("A new backup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Backup result = backupService.save(backup);
        return ResponseEntity
            .created(new URI("/api/backups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /backups/:id} : Updates an existing backup.
     *
     * @param id the id of the backup to save.
     * @param backup the backup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated backup,
     * or with status {@code 400 (Bad Request)} if the backup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the backup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/backups/{id}")
    public ResponseEntity<Backup> updateBackup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Backup backup
    ) throws URISyntaxException {
        log.debug("REST request to update Backup : {}, {}", id, backup);
        if (backup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, backup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!backupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Backup result = backupService.update(backup);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, backup.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /backups/:id} : Partial updates given fields of an existing backup, field will ignore if it is null
     *
     * @param id the id of the backup to save.
     * @param backup the backup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated backup,
     * or with status {@code 400 (Bad Request)} if the backup is not valid,
     * or with status {@code 404 (Not Found)} if the backup is not found,
     * or with status {@code 500 (Internal Server Error)} if the backup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/backups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Backup> partialUpdateBackup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Backup backup
    ) throws URISyntaxException {
        log.debug("REST request to partial update Backup partially : {}, {}", id, backup);
        if (backup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, backup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!backupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Backup> result = backupService.partialUpdate(backup);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, backup.getId().toString())
        );
    }

    /**
     * {@code GET  /backups} : get all the backups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of backups in body.
     */
    @GetMapping("/backups")
    public ResponseEntity<List<Backup>> getAllBackups(
        BackupCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Backups by criteria: {}", criteria);

        Page<Backup> page = backupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /backups/count} : count all the backups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/backups/count")
    public ResponseEntity<Long> countBackups(BackupCriteria criteria) {
        log.debug("REST request to count Backups by criteria: {}", criteria);
        return ResponseEntity.ok().body(backupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /backups/:id} : get the "id" backup.
     *
     * @param id the id of the backup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the backup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/backups/{id}")
    public ResponseEntity<Backup> getBackup(@PathVariable Long id) {
        log.debug("REST request to get Backup : {}", id);
        Optional<Backup> backup = backupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(backup);
    }

    /**
     * {@code DELETE  /backups/:id} : delete the "id" backup.
     *
     * @param id the id of the backup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/backups/{id}")
    public ResponseEntity<Void> deleteBackup(@PathVariable Long id) {
        log.debug("REST request to delete Backup : {}", id);
        backupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
