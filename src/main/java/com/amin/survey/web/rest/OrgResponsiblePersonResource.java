package com.amin.survey.web.rest;

import com.amin.survey.domain.OrgResponsiblePerson;
import com.amin.survey.repository.OrgResponsiblePersonRepository;
import com.amin.survey.service.OrgResponsiblePersonQueryService;
import com.amin.survey.service.OrgResponsiblePersonService;
import com.amin.survey.service.criteria.OrgResponsiblePersonCriteria;
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
 * REST controller for managing {@link com.amin.survey.domain.OrgResponsiblePerson}.
 */
@RestController
@RequestMapping("/api")
public class OrgResponsiblePersonResource {

    private final Logger log = LoggerFactory.getLogger(OrgResponsiblePersonResource.class);

    private static final String ENTITY_NAME = "orgResponsiblePerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrgResponsiblePersonService orgResponsiblePersonService;

    private final OrgResponsiblePersonRepository orgResponsiblePersonRepository;

    private final OrgResponsiblePersonQueryService orgResponsiblePersonQueryService;

    public OrgResponsiblePersonResource(
        OrgResponsiblePersonService orgResponsiblePersonService,
        OrgResponsiblePersonRepository orgResponsiblePersonRepository,
        OrgResponsiblePersonQueryService orgResponsiblePersonQueryService
    ) {
        this.orgResponsiblePersonService = orgResponsiblePersonService;
        this.orgResponsiblePersonRepository = orgResponsiblePersonRepository;
        this.orgResponsiblePersonQueryService = orgResponsiblePersonQueryService;
    }

    /**
     * {@code POST  /org-responsible-people} : Create a new orgResponsiblePerson.
     *
     * @param orgResponsiblePerson the orgResponsiblePerson to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orgResponsiblePerson, or with status {@code 400 (Bad Request)} if the orgResponsiblePerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/org-responsible-people")
    public ResponseEntity<OrgResponsiblePerson> createOrgResponsiblePerson(@Valid @RequestBody OrgResponsiblePerson orgResponsiblePerson)
        throws URISyntaxException {
        log.debug("REST request to save OrgResponsiblePerson : {}", orgResponsiblePerson);
        if (orgResponsiblePerson.getId() != null) {
            throw new BadRequestAlertException("A new orgResponsiblePerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrgResponsiblePerson result = orgResponsiblePersonService.save(orgResponsiblePerson);
        return ResponseEntity
            .created(new URI("/api/org-responsible-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /org-responsible-people/:id} : Updates an existing orgResponsiblePerson.
     *
     * @param id the id of the orgResponsiblePerson to save.
     * @param orgResponsiblePerson the orgResponsiblePerson to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgResponsiblePerson,
     * or with status {@code 400 (Bad Request)} if the orgResponsiblePerson is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orgResponsiblePerson couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/org-responsible-people/{id}")
    public ResponseEntity<OrgResponsiblePerson> updateOrgResponsiblePerson(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrgResponsiblePerson orgResponsiblePerson
    ) throws URISyntaxException {
        log.debug("REST request to update OrgResponsiblePerson : {}, {}", id, orgResponsiblePerson);
        if (orgResponsiblePerson.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgResponsiblePerson.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgResponsiblePersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrgResponsiblePerson result = orgResponsiblePersonService.update(orgResponsiblePerson);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgResponsiblePerson.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /org-responsible-people/:id} : Partial updates given fields of an existing orgResponsiblePerson, field will ignore if it is null
     *
     * @param id the id of the orgResponsiblePerson to save.
     * @param orgResponsiblePerson the orgResponsiblePerson to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgResponsiblePerson,
     * or with status {@code 400 (Bad Request)} if the orgResponsiblePerson is not valid,
     * or with status {@code 404 (Not Found)} if the orgResponsiblePerson is not found,
     * or with status {@code 500 (Internal Server Error)} if the orgResponsiblePerson couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/org-responsible-people/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrgResponsiblePerson> partialUpdateOrgResponsiblePerson(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrgResponsiblePerson orgResponsiblePerson
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrgResponsiblePerson partially : {}, {}", id, orgResponsiblePerson);
        if (orgResponsiblePerson.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgResponsiblePerson.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgResponsiblePersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrgResponsiblePerson> result = orgResponsiblePersonService.partialUpdate(orgResponsiblePerson);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgResponsiblePerson.getId().toString())
        );
    }

    /**
     * {@code GET  /org-responsible-people} : get all the orgResponsiblePeople.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orgResponsiblePeople in body.
     */
    @GetMapping("/org-responsible-people")
    public ResponseEntity<List<OrgResponsiblePerson>> getAllOrgResponsiblePeople(
        OrgResponsiblePersonCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OrgResponsiblePeople by criteria: {}", criteria);

        Page<OrgResponsiblePerson> page = orgResponsiblePersonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /org-responsible-people/count} : count all the orgResponsiblePeople.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/org-responsible-people/count")
    public ResponseEntity<Long> countOrgResponsiblePeople(OrgResponsiblePersonCriteria criteria) {
        log.debug("REST request to count OrgResponsiblePeople by criteria: {}", criteria);
        return ResponseEntity.ok().body(orgResponsiblePersonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /org-responsible-people/:id} : get the "id" orgResponsiblePerson.
     *
     * @param id the id of the orgResponsiblePerson to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orgResponsiblePerson, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/org-responsible-people/{id}")
    public ResponseEntity<OrgResponsiblePerson> getOrgResponsiblePerson(@PathVariable Long id) {
        log.debug("REST request to get OrgResponsiblePerson : {}", id);
        Optional<OrgResponsiblePerson> orgResponsiblePerson = orgResponsiblePersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orgResponsiblePerson);
    }

    /**
     * {@code DELETE  /org-responsible-people/:id} : delete the "id" orgResponsiblePerson.
     *
     * @param id the id of the orgResponsiblePerson to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/org-responsible-people/{id}")
    public ResponseEntity<Void> deleteOrgResponsiblePerson(@PathVariable Long id) {
        log.debug("REST request to delete OrgResponsiblePerson : {}", id);
        orgResponsiblePersonService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/org-responsible-people/form/{id}")
    public ResponseEntity<OrgResponsiblePerson> getOrgResponsiblePersonByFormId(@PathVariable Long id) {
        log.debug("REST request to get OrgResponsiblePerson : {}", id);
        Optional<OrgResponsiblePerson> orgResponsiblePerson = orgResponsiblePersonService.findByFormId(id);
        return ResponseUtil.wrapOrNotFound(orgResponsiblePerson);
    }
}
