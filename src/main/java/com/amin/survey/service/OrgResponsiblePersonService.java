package com.amin.survey.service;

import com.amin.survey.domain.OrgResponsiblePerson;
import com.amin.survey.repository.OrgResponsiblePersonRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.amin.survey.domain.OrgResponsiblePerson}.
 */
@Service
@Transactional
public class OrgResponsiblePersonService {

    private final Logger log = LoggerFactory.getLogger(OrgResponsiblePersonService.class);

    private final OrgResponsiblePersonRepository orgResponsiblePersonRepository;

    public OrgResponsiblePersonService(OrgResponsiblePersonRepository orgResponsiblePersonRepository) {
        this.orgResponsiblePersonRepository = orgResponsiblePersonRepository;
    }

    /**
     * Save a orgResponsiblePerson.
     *
     * @param orgResponsiblePerson the entity to save.
     * @return the persisted entity.
     */
    public OrgResponsiblePerson save(OrgResponsiblePerson orgResponsiblePerson) {
        log.debug("Request to save OrgResponsiblePerson : {}", orgResponsiblePerson);
        return orgResponsiblePersonRepository.save(orgResponsiblePerson);
    }

    /**
     * Update a orgResponsiblePerson.
     *
     * @param orgResponsiblePerson the entity to save.
     * @return the persisted entity.
     */
    public OrgResponsiblePerson update(OrgResponsiblePerson orgResponsiblePerson) {
        log.debug("Request to update OrgResponsiblePerson : {}", orgResponsiblePerson);
        return orgResponsiblePersonRepository.save(orgResponsiblePerson);
    }

    /**
     * Partially update a orgResponsiblePerson.
     *
     * @param orgResponsiblePerson the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrgResponsiblePerson> partialUpdate(OrgResponsiblePerson orgResponsiblePerson) {
        log.debug("Request to partially update OrgResponsiblePerson : {}", orgResponsiblePerson);

        return orgResponsiblePersonRepository
            .findById(orgResponsiblePerson.getId())
            .map(existingOrgResponsiblePerson -> {
                if (orgResponsiblePerson.getFullName() != null) {
                    existingOrgResponsiblePerson.setFullName(orgResponsiblePerson.getFullName());
                }
                if (orgResponsiblePerson.getPosition() != null) {
                    existingOrgResponsiblePerson.setPosition(orgResponsiblePerson.getPosition());
                }
                if (orgResponsiblePerson.getContact() != null) {
                    existingOrgResponsiblePerson.setContact(orgResponsiblePerson.getContact());
                }
                if (orgResponsiblePerson.getDate() != null) {
                    existingOrgResponsiblePerson.setDate(orgResponsiblePerson.getDate());
                }

                return existingOrgResponsiblePerson;
            })
            .map(orgResponsiblePersonRepository::save);
    }

    /**
     * Get all the orgResponsiblePeople.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrgResponsiblePerson> findAll(Pageable pageable) {
        log.debug("Request to get all OrgResponsiblePeople");
        return orgResponsiblePersonRepository.findAll(pageable);
    }

    /**
     * Get one orgResponsiblePerson by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrgResponsiblePerson> findOne(Long id) {
        log.debug("Request to get OrgResponsiblePerson : {}", id);
        return orgResponsiblePersonRepository.findById(id);
    }

    /**
     * Delete the orgResponsiblePerson by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrgResponsiblePerson : {}", id);
        orgResponsiblePersonRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<OrgResponsiblePerson> findByFormId(Long id) {
        log.debug("Request to get OrgResponsiblePerson : {}", id);
        return orgResponsiblePersonRepository.findByFormId(id);
    }
}
