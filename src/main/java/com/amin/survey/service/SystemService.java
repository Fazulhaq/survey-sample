package com.amin.survey.service;

import com.amin.survey.domain.System;
import com.amin.survey.repository.SystemRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.amin.survey.domain.System}.
 */
@Service
@Transactional
public class SystemService {

    private final Logger log = LoggerFactory.getLogger(SystemService.class);

    private final SystemRepository systemRepository;

    public SystemService(SystemRepository systemRepository) {
        this.systemRepository = systemRepository;
    }

    /**
     * Save a system.
     *
     * @param system the entity to save.
     * @return the persisted entity.
     */
    public System save(System system) {
        log.debug("Request to save System : {}", system);
        return systemRepository.save(system);
    }

    /**
     * Update a system.
     *
     * @param system the entity to save.
     * @return the persisted entity.
     */
    public System update(System system) {
        log.debug("Request to update System : {}", system);
        return systemRepository.save(system);
    }

    /**
     * Partially update a system.
     *
     * @param system the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<System> partialUpdate(System system) {
        log.debug("Request to partially update System : {}", system);

        return systemRepository
            .findById(system.getId())
            .map(existingSystem -> {
                if (system.getQuestion1() != null) {
                    existingSystem.setQuestion1(system.getQuestion1());
                }
                if (system.getQuestion2() != null) {
                    existingSystem.setQuestion2(system.getQuestion2());
                }
                if (system.getQuestion3() != null) {
                    existingSystem.setQuestion3(system.getQuestion3());
                }
                if (system.getQuestion4() != null) {
                    existingSystem.setQuestion4(system.getQuestion4());
                }
                if (system.getQuestion5() != null) {
                    existingSystem.setQuestion5(system.getQuestion5());
                }

                return existingSystem;
            })
            .map(systemRepository::save);
    }

    /**
     * Get all the systems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<System> findAll(Pageable pageable) {
        log.debug("Request to get all Systems");
        return systemRepository.findAll(pageable);
    }

    /**
     * Get one system by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<System> findOne(Long id) {
        log.debug("Request to get System : {}", id);
        return systemRepository.findById(id);
    }

    /**
     * Delete the system by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete System : {}", id);
        systemRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<System> findByFormId(Long id) {
        log.debug("Request to get System : {}", id);
        return systemRepository.findByFormId(id);
    }
}
