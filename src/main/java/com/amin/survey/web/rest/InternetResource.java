package com.amin.survey.web.rest;

import com.amin.survey.domain.Internet;
import com.amin.survey.repository.InternetRepository;
import com.amin.survey.service.InternetQueryService;
import com.amin.survey.service.InternetService;
import com.amin.survey.service.criteria.InternetCriteria;
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
 * REST controller for managing {@link com.amin.survey.domain.Internet}.
 */
@RestController
@RequestMapping("/api")
public class InternetResource {

    private final Logger log = LoggerFactory.getLogger(InternetResource.class);

    private static final String ENTITY_NAME = "internet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InternetService internetService;

    private final InternetRepository internetRepository;

    private final InternetQueryService internetQueryService;

    public InternetResource(
        InternetService internetService,
        InternetRepository internetRepository,
        InternetQueryService internetQueryService
    ) {
        this.internetService = internetService;
        this.internetRepository = internetRepository;
        this.internetQueryService = internetQueryService;
    }

    /**
     * {@code POST  /internets} : Create a new internet.
     *
     * @param internet the internet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new internet, or with status {@code 400 (Bad Request)} if the internet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/internets")
    public ResponseEntity<Internet> createInternet(@Valid @RequestBody Internet internet) throws URISyntaxException {
        log.debug("REST request to save Internet : {}", internet);
        if (internet.getId() != null) {
            throw new BadRequestAlertException("A new internet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Internet result = internetService.save(internet);
        return ResponseEntity
            .created(new URI("/api/internets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /internets/:id} : Updates an existing internet.
     *
     * @param id the id of the internet to save.
     * @param internet the internet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated internet,
     * or with status {@code 400 (Bad Request)} if the internet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the internet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/internets/{id}")
    public ResponseEntity<Internet> updateInternet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Internet internet
    ) throws URISyntaxException {
        log.debug("REST request to update Internet : {}, {}", id, internet);
        if (internet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, internet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!internetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Internet result = internetService.update(internet);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, internet.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /internets/:id} : Partial updates given fields of an existing internet, field will ignore if it is null
     *
     * @param id the id of the internet to save.
     * @param internet the internet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated internet,
     * or with status {@code 400 (Bad Request)} if the internet is not valid,
     * or with status {@code 404 (Not Found)} if the internet is not found,
     * or with status {@code 500 (Internal Server Error)} if the internet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/internets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Internet> partialUpdateInternet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Internet internet
    ) throws URISyntaxException {
        log.debug("REST request to partial update Internet partially : {}, {}", id, internet);
        if (internet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, internet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!internetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Internet> result = internetService.partialUpdate(internet);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, internet.getId().toString())
        );
    }

    /**
     * {@code GET  /internets} : get all the internets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of internets in body.
     */
    @GetMapping("/internets")
    public ResponseEntity<List<Internet>> getAllInternets(
        InternetCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Internets by criteria: {}", criteria);

        Page<Internet> page = internetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /internets/count} : count all the internets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/internets/count")
    public ResponseEntity<Long> countInternets(InternetCriteria criteria) {
        log.debug("REST request to count Internets by criteria: {}", criteria);
        return ResponseEntity.ok().body(internetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /internets/:id} : get the "id" internet.
     *
     * @param id the id of the internet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the internet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/internets/{id}")
    public ResponseEntity<Internet> getInternet(@PathVariable Long id) {
        log.debug("REST request to get Internet : {}", id);
        Optional<Internet> internet = internetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(internet);
    }

    /**
     * {@code DELETE  /internets/:id} : delete the "id" internet.
     *
     * @param id the id of the internet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/internets/{id}")
    public ResponseEntity<Void> deleteInternet(@PathVariable Long id) {
        log.debug("REST request to delete Internet : {}", id);
        internetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/internets/form/{id}")
    public ResponseEntity<Internet> getInternetByFormId(@PathVariable Long id) {
        log.debug("REST request to get Internet : {}", id);
        Optional<Internet> internet = internetService.findByFormId(id);
        return ResponseUtil.wrapOrNotFound(internet);
    }
}
