package com.amin.survey.web.rest;

import com.amin.survey.domain.Form;
import com.amin.survey.repository.FormRepository;
import com.amin.survey.service.BackupService;
import com.amin.survey.service.DatacenterDeviceService;
import com.amin.survey.service.FormQueryService;
import com.amin.survey.service.FormService;
import com.amin.survey.service.InternetService;
import com.amin.survey.service.ItDeviceService;
import com.amin.survey.service.NetworkConfigCheckListService;
import com.amin.survey.service.OrgResponsiblePersonService;
import com.amin.survey.service.ServerService;
import com.amin.survey.service.SystemService;
import com.amin.survey.service.criteria.FormCriteria;
import com.amin.survey.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * REST controller for managing {@link com.amin.survey.domain.Form}.
 */
@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

    private static final String ENTITY_NAME = "form";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormService formService;
    private final ServerService serverService;
    private final SystemService systemService;
    private final BackupService backupService;
    private final DatacenterDeviceService datacenterDeviceService;
    private final InternetService internetService;
    private final ItDeviceService itDeviceService;
    private final NetworkConfigCheckListService networkConfigCheckListService;
    private final OrgResponsiblePersonService orgResponsiblePersonService;
    private final FormRepository formRepository;
    private final FormQueryService formQueryService;

    public FormResource(
        FormService formService,
        FormRepository formRepository,
        FormQueryService formQueryService,
        BackupService backupService,
        ServerService serverService,
        SystemService systemService,
        DatacenterDeviceService datacenterDeviceService,
        InternetService internetService,
        ItDeviceService itDeviceService,
        NetworkConfigCheckListService networkConfigCheckListService,
        OrgResponsiblePersonService orgResponsiblePersonService
    ) {
        this.formService = formService;
        this.serverService = serverService;
        this.systemService = systemService;
        this.backupService = backupService;
        this.datacenterDeviceService = datacenterDeviceService;
        this.internetService = internetService;
        this.itDeviceService = itDeviceService;
        this.networkConfigCheckListService = networkConfigCheckListService;
        this.orgResponsiblePersonService = orgResponsiblePersonService;
        this.formRepository = formRepository;
        this.formQueryService = formQueryService;
    }

    /**
     * {@code POST  /forms} : Create a new form.
     *
     * @param form the form to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new form, or with status {@code 400 (Bad Request)} if the
     *         form has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forms")
    public ResponseEntity<Form> createForm(@Valid @RequestBody Form form) throws URISyntaxException {
        log.debug("REST request to save Form : {}", form);
        if (form.getId() != null) {
            throw new BadRequestAlertException("A new form cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Form result = formService.save(form);
        return ResponseEntity
            .created(new URI("/api/forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forms/:id} : Updates an existing form.
     *
     * @param id   the id of the form to save.
     * @param form the form to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated form,
     *         or with status {@code 400 (Bad Request)} if the form is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the form
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forms/{id}")
    public ResponseEntity<Form> updateForm(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Form form)
        throws URISyntaxException {
        log.debug("REST request to update Form : {}, {}", id, form);
        if (form.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, form.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Form result = formService.update(form);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, form.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /forms/:id} : Partial updates given fields of an existing form,
     * field will ignore if it is null
     *
     * @param id   the id of the form to save.
     * @param form the form to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated form,
     *         or with status {@code 400 (Bad Request)} if the form is not valid,
     *         or with status {@code 404 (Not Found)} if the form is not found,
     *         or with status {@code 500 (Internal Server Error)} if the form
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/forms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Form> partialUpdateForm(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Form form
    ) throws URISyntaxException {
        log.debug("REST request to partial update Form partially : {}, {}", id, form);
        if (form.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, form.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Form> result = formService.partialUpdate(form);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, form.getId().toString())
        );
    }

    /**
     * {@code GET  /forms} : get all the forms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of forms in body.
     */
    @GetMapping("/forms")
    public ResponseEntity<List<Form>> getAllForms(
        FormCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Forms by criteria: {}", criteria);

        Page<Form> page = formQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /forms/count} : count all the forms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/forms/count")
    public ResponseEntity<Long> countForms(FormCriteria criteria) {
        log.debug("REST request to count Forms by criteria: {}", criteria);
        return ResponseEntity.ok().body(formQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /forms/:id} : get the "id" form.
     *
     * @param id the id of the form to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the form, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forms/{id}")
    public ResponseEntity<Form> getForm(@PathVariable Long id) {
        log.debug("REST request to get Form : {}", id);
        Optional<Form> form = formService.findOne(id);
        return ResponseUtil.wrapOrNotFound(form);
    }

    /**
     * {@code DELETE  /forms/:id} : delete the "id" form.
     *
     * @param id the id of the form to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forms/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        log.debug("REST request to delete Form : {}", id);
        formService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/forms/surveydata")
    public ResponseEntity<Map<Long, List<Object>>> getAllSurveyData() {
        List<Form> forms = formService.findAllForms();
        Map<Long, List<Object>> surveyData = new HashMap<>();
        for (Form form : forms) {
            if (form.getId() != null) {
                List<Object> objects = new ArrayList<>();
                objects.add(form);
                objects.add(serverService.findByFormId(form.getId()));
                objects.add(backupService.findByFormId(form.getId()));
                objects.add(systemService.findByFormId(form.getId()));
                objects.add(datacenterDeviceService.findDataCenterByFromId(form.getId()));
                objects.add(internetService.findByFormId(form.getId()));
                objects.add(itDeviceService.findItDevicesByFormId(form.getId()));
                objects.add(networkConfigCheckListService.findByFormId(form.getId()));
                objects.add(orgResponsiblePersonService.findByFormId(form.getId()));
                surveyData.put(form.getId(), objects);
            }
        }
        return ResponseEntity.ok(surveyData);
    }
}
