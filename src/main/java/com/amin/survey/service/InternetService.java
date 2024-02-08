package com.amin.survey.service;

import com.amin.survey.domain.Internet;
import com.amin.survey.repository.InternetRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.amin.survey.domain.Internet}.
 */
@Service
@Transactional
public class InternetService {

    private final Logger log = LoggerFactory.getLogger(InternetService.class);

    private final InternetRepository internetRepository;

    public InternetService(InternetRepository internetRepository) {
        this.internetRepository = internetRepository;
    }

    /**
     * Save a internet.
     *
     * @param internet the entity to save.
     * @return the persisted entity.
     */
    public Internet save(Internet internet) {
        log.debug("Request to save Internet : {}", internet);
        return internetRepository.save(internet);
    }

    /**
     * Update a internet.
     *
     * @param internet the entity to save.
     * @return the persisted entity.
     */
    public Internet update(Internet internet) {
        log.debug("Request to update Internet : {}", internet);
        return internetRepository.save(internet);
    }

    /**
     * Partially update a internet.
     *
     * @param internet the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Internet> partialUpdate(Internet internet) {
        log.debug("Request to partially update Internet : {}", internet);

        return internetRepository
            .findById(internet.getId())
            .map(existingInternet -> {
                if (internet.getQuestion1() != null) {
                    existingInternet.setQuestion1(internet.getQuestion1());
                }
                if (internet.getQuestion2() != null) {
                    existingInternet.setQuestion2(internet.getQuestion2());
                }
                if (internet.getQuestion3() != null) {
                    existingInternet.setQuestion3(internet.getQuestion3());
                }
                if (internet.getQuestion4() != null) {
                    existingInternet.setQuestion4(internet.getQuestion4());
                }
                if (internet.getQuestion5() != null) {
                    existingInternet.setQuestion5(internet.getQuestion5());
                }
                if (internet.getQuestion6() != null) {
                    existingInternet.setQuestion6(internet.getQuestion6());
                }

                return existingInternet;
            })
            .map(internetRepository::save);
    }

    /**
     * Get all the internets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Internet> findAll(Pageable pageable) {
        log.debug("Request to get all Internets");
        return internetRepository.findAll(pageable);
    }

    /**
     * Get one internet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Internet> findOne(Long id) {
        log.debug("Request to get Internet : {}", id);
        return internetRepository.findById(id);
    }

    /**
     * Delete the internet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Internet : {}", id);
        internetRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Internet> findByFormId(Long id) {
        log.debug("Request to get Internet : {}", id);
        return internetRepository.findByFormId(id);
    }
}
