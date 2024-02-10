package com.amin.survey.web.rest;

import com.amin.survey.domain.NetworkConfigCheckList;
import com.amin.survey.repository.NetworkConfigCheckListRepository;
import com.amin.survey.service.NetworkConfigCheckListQueryService;
import com.amin.survey.service.NetworkConfigCheckListService;
import com.amin.survey.service.criteria.NetworkConfigCheckListCriteria;
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
 * REST controller for managing {@link com.amin.survey.domain.NetworkConfigCheckList}.
 */
@RestController
@RequestMapping("/api")
public class NetworkConfigCheckListResource {

    private final Logger log = LoggerFactory.getLogger(NetworkConfigCheckListResource.class);

    private static final String ENTITY_NAME = "networkConfigCheckList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NetworkConfigCheckListService networkConfigCheckListService;

    private final NetworkConfigCheckListRepository networkConfigCheckListRepository;

    private final NetworkConfigCheckListQueryService networkConfigCheckListQueryService;

    public NetworkConfigCheckListResource(
        NetworkConfigCheckListService networkConfigCheckListService,
        NetworkConfigCheckListRepository networkConfigCheckListRepository,
        NetworkConfigCheckListQueryService networkConfigCheckListQueryService
    ) {
        this.networkConfigCheckListService = networkConfigCheckListService;
        this.networkConfigCheckListRepository = networkConfigCheckListRepository;
        this.networkConfigCheckListQueryService = networkConfigCheckListQueryService;
    }

    /**
     * {@code POST  /network-config-check-lists} : Create a new networkConfigCheckList.
     *
     * @param networkConfigCheckList the networkConfigCheckList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new networkConfigCheckList, or with status {@code 400 (Bad Request)} if the networkConfigCheckList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/network-config-check-lists")
    public ResponseEntity<NetworkConfigCheckList> createNetworkConfigCheckList(
        @Valid @RequestBody NetworkConfigCheckList networkConfigCheckList
    ) throws URISyntaxException {
        log.debug("REST request to save NetworkConfigCheckList : {}", networkConfigCheckList);
        if (networkConfigCheckList.getId() != null) {
            throw new BadRequestAlertException("A new networkConfigCheckList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NetworkConfigCheckList result = networkConfigCheckListService.save(networkConfigCheckList);
        return ResponseEntity
            .created(new URI("/api/network-config-check-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /network-config-check-lists/:id} : Updates an existing networkConfigCheckList.
     *
     * @param id the id of the networkConfigCheckList to save.
     * @param networkConfigCheckList the networkConfigCheckList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated networkConfigCheckList,
     * or with status {@code 400 (Bad Request)} if the networkConfigCheckList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the networkConfigCheckList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/network-config-check-lists/{id}")
    public ResponseEntity<NetworkConfigCheckList> updateNetworkConfigCheckList(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NetworkConfigCheckList networkConfigCheckList
    ) throws URISyntaxException {
        log.debug("REST request to update NetworkConfigCheckList : {}, {}", id, networkConfigCheckList);
        if (networkConfigCheckList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, networkConfigCheckList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!networkConfigCheckListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NetworkConfigCheckList result = networkConfigCheckListService.update(networkConfigCheckList);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, networkConfigCheckList.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /network-config-check-lists/:id} : Partial updates given fields of an existing networkConfigCheckList, field will ignore if it is null
     *
     * @param id the id of the networkConfigCheckList to save.
     * @param networkConfigCheckList the networkConfigCheckList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated networkConfigCheckList,
     * or with status {@code 400 (Bad Request)} if the networkConfigCheckList is not valid,
     * or with status {@code 404 (Not Found)} if the networkConfigCheckList is not found,
     * or with status {@code 500 (Internal Server Error)} if the networkConfigCheckList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/network-config-check-lists/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NetworkConfigCheckList> partialUpdateNetworkConfigCheckList(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NetworkConfigCheckList networkConfigCheckList
    ) throws URISyntaxException {
        log.debug("REST request to partial update NetworkConfigCheckList partially : {}, {}", id, networkConfigCheckList);
        if (networkConfigCheckList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, networkConfigCheckList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!networkConfigCheckListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NetworkConfigCheckList> result = networkConfigCheckListService.partialUpdate(networkConfigCheckList);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, networkConfigCheckList.getId().toString())
        );
    }

    /**
     * {@code GET  /network-config-check-lists} : get all the networkConfigCheckLists.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of networkConfigCheckLists in body.
     */
    @GetMapping("/network-config-check-lists")
    public ResponseEntity<List<NetworkConfigCheckList>> getAllNetworkConfigCheckLists(
        NetworkConfigCheckListCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NetworkConfigCheckLists by criteria: {}", criteria);

        Page<NetworkConfigCheckList> page = networkConfigCheckListQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /network-config-check-lists/count} : count all the networkConfigCheckLists.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/network-config-check-lists/count")
    public ResponseEntity<Long> countNetworkConfigCheckLists(NetworkConfigCheckListCriteria criteria) {
        log.debug("REST request to count NetworkConfigCheckLists by criteria: {}", criteria);
        return ResponseEntity.ok().body(networkConfigCheckListQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /network-config-check-lists/:id} : get the "id" networkConfigCheckList.
     *
     * @param id the id of the networkConfigCheckList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the networkConfigCheckList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/network-config-check-lists/{id}")
    public ResponseEntity<NetworkConfigCheckList> getNetworkConfigCheckList(@PathVariable Long id) {
        log.debug("REST request to get NetworkConfigCheckList : {}", id);
        Optional<NetworkConfigCheckList> networkConfigCheckList = networkConfigCheckListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(networkConfigCheckList);
    }

    /**
     * {@code DELETE  /network-config-check-lists/:id} : delete the "id" networkConfigCheckList.
     *
     * @param id the id of the networkConfigCheckList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/network-config-check-lists/{id}")
    public ResponseEntity<Void> deleteNetworkConfigCheckList(@PathVariable Long id) {
        log.debug("REST request to delete NetworkConfigCheckList : {}", id);
        networkConfigCheckListService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/network-config-check-lists/form/{id}")
    public ResponseEntity<NetworkConfigCheckList> getNetworkConfigCheckListByFormId(@PathVariable Long id) {
        log.debug("REST request to get NetworkConfigCheckList : {}", id);
        Optional<NetworkConfigCheckList> networkConfigCheckList = networkConfigCheckListService.findByFormId(id);
        return ResponseUtil.wrapOrNotFound(networkConfigCheckList);
    }
}
