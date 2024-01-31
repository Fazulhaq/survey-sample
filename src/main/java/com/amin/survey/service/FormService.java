package com.amin.survey.service;

import com.amin.survey.domain.Form;
import com.amin.survey.repository.FormRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.amin.survey.domain.Form}.
 */
@Service
@Transactional
public class FormService {

    private final Logger log = LoggerFactory.getLogger(FormService.class);

    private final FormRepository formRepository;

    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    /**
     * Save a form.
     *
     * @param form the entity to save.
     * @return the persisted entity.
     */
    public Form save(Form form) {
        log.debug("Request to save Form : {}", form);
        return formRepository.save(form);
    }

    /**
     * Update a form.
     *
     * @param form the entity to save.
     * @return the persisted entity.
     */
    public Form update(Form form) {
        log.debug("Request to update Form : {}", form);
        return formRepository.save(form);
    }

    /**
     * Partially update a form.
     *
     * @param form the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Form> partialUpdate(Form form) {
        log.debug("Request to partially update Form : {}", form);

        return formRepository
            .findById(form.getId())
            .map(existingForm -> {
                if (form.getFuturePlan() != null) {
                    existingForm.setFuturePlan(form.getFuturePlan());
                }
                if (form.getStatus() != null) {
                    existingForm.setStatus(form.getStatus());
                }
                if (form.getCreateDate() != null) {
                    existingForm.setCreateDate(form.getCreateDate());
                }
                if (form.getUpdateDate() != null) {
                    existingForm.setUpdateDate(form.getUpdateDate());
                }

                return existingForm;
            })
            .map(formRepository::save);
    }

    /**
     * Get all the forms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Form> findAll(Pageable pageable) {
        log.debug("Request to get all Forms");
        return formRepository.findAll(pageable);
    }

    /**
     * Get all the forms with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Form> findAllWithEagerRelationships(Pageable pageable) {
        return formRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one form by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Form> findOne(Long id) {
        log.debug("Request to get Form : {}", id);
        return formRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the form by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Form : {}", id);
        formRepository.deleteById(id);
    }
}
