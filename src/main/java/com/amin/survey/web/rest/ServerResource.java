package com.amin.survey.web.rest;

import com.amin.survey.domain.Server;
import com.amin.survey.repository.ServerRepository;
import com.amin.survey.service.ServerQueryService;
import com.amin.survey.service.ServerService;
import com.amin.survey.service.criteria.ServerCriteria;
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
 * REST controller for managing {@link com.amin.survey.domain.Server}.
 */
@RestController
@RequestMapping("/api")
public class ServerResource {

    private final Logger log = LoggerFactory.getLogger(ServerResource.class);

    private static final String ENTITY_NAME = "server";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServerService serverService;

    private final ServerRepository serverRepository;

    private final ServerQueryService serverQueryService;

    public ServerResource(ServerService serverService, ServerRepository serverRepository, ServerQueryService serverQueryService) {
        this.serverService = serverService;
        this.serverRepository = serverRepository;
        this.serverQueryService = serverQueryService;
    }

    /**
     * {@code POST  /servers} : Create a new server.
     *
     * @param server the server to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new server, or with status {@code 400 (Bad Request)} if the server has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servers")
    public ResponseEntity<Server> createServer(@Valid @RequestBody Server server) throws URISyntaxException {
        log.debug("REST request to save Server : {}", server);
        if (server.getId() != null) {
            throw new BadRequestAlertException("A new server cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Server result = serverService.save(server);
        return ResponseEntity
            .created(new URI("/api/servers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servers/:id} : Updates an existing server.
     *
     * @param id the id of the server to save.
     * @param server the server to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated server,
     * or with status {@code 400 (Bad Request)} if the server is not valid,
     * or with status {@code 500 (Internal Server Error)} if the server couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servers/{id}")
    public ResponseEntity<Server> updateServer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Server server
    ) throws URISyntaxException {
        log.debug("REST request to update Server : {}, {}", id, server);
        if (server.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, server.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Server result = serverService.update(server);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, server.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /servers/:id} : Partial updates given fields of an existing server, field will ignore if it is null
     *
     * @param id the id of the server to save.
     * @param server the server to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated server,
     * or with status {@code 400 (Bad Request)} if the server is not valid,
     * or with status {@code 404 (Not Found)} if the server is not found,
     * or with status {@code 500 (Internal Server Error)} if the server couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/servers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Server> partialUpdateServer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Server server
    ) throws URISyntaxException {
        log.debug("REST request to partial update Server partially : {}, {}", id, server);
        if (server.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, server.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Server> result = serverService.partialUpdate(server);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, server.getId().toString())
        );
    }

    /**
     * {@code GET  /servers} : get all the servers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servers in body.
     */
    @GetMapping("/servers")
    public ResponseEntity<List<Server>> getAllServers(
        ServerCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Servers by criteria: {}", criteria);

        Page<Server> page = serverQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /servers/count} : count all the servers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/servers/count")
    public ResponseEntity<Long> countServers(ServerCriteria criteria) {
        log.debug("REST request to count Servers by criteria: {}", criteria);
        return ResponseEntity.ok().body(serverQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /servers/:id} : get the "id" server.
     *
     * @param id the id of the server to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the server, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servers/{id}")
    public ResponseEntity<Server> getServer(@PathVariable Long id) {
        log.debug("REST request to get Server : {}", id);
        Optional<Server> server = serverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(server);
    }

    /**
     * {@code DELETE  /servers/:id} : delete the "id" server.
     *
     * @param id the id of the server to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servers/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable Long id) {
        log.debug("REST request to delete Server : {}", id);
        serverService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/servers/form/{id}")
    public ResponseEntity<Server> getServerByFormId(@PathVariable Long id) {
        log.debug("REST request to get Server : {}", id);
        Optional<Server> server = serverService.findByFormId(id);
        return ResponseUtil.wrapOrNotFound(server);
    }
}
