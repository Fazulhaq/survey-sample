package com.amin.survey.service;

import com.amin.survey.domain.Organization;
import com.amin.survey.repository.OrganizationRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.amin.survey.domain.Organization}.
 */
@Service
@Transactional
public class OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationService.class);

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * Save a organization.
     *
     * @param organization the entity to save.
     * @return the persisted entity.
     */
    public Organization save(Organization organization) {
        log.debug("Request to save Organization : {}", organization);
        return organizationRepository.save(organization);
    }

    /**
     * Update a organization.
     *
     * @param organization the entity to save.
     * @return the persisted entity.
     */
    public Organization update(Organization organization) {
        log.debug("Request to update Organization : {}", organization);
        return organizationRepository.save(organization);
    }

    /**
     * Partially update a organization.
     *
     * @param organization the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Organization> partialUpdate(Organization organization) {
        log.debug("Request to partially update Organization : {}", organization);

        return organizationRepository
            .findById(organization.getId())
            .map(existingOrganization -> {
                if (organization.getName() != null) {
                    existingOrganization.setName(organization.getName());
                }
                if (organization.getCode() != null) {
                    existingOrganization.setCode(organization.getCode());
                }
                if (organization.getDescription() != null) {
                    existingOrganization.setDescription(organization.getDescription());
                }
                if (organization.getAddress() != null) {
                    existingOrganization.setAddress(organization.getAddress());
                }
                if (organization.getCreateDate() != null) {
                    existingOrganization.setCreateDate(organization.getCreateDate());
                }
                if (organization.getUpdateDate() != null) {
                    existingOrganization.setUpdateDate(organization.getUpdateDate());
                }

                return existingOrganization;
            })
            .map(organizationRepository::save);
    }

    /**
     * Get all the organizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Organization> findAll(Pageable pageable) {
        log.debug("Request to get all Organizations");
        return organizationRepository.findAll(pageable);
    }

    /**
     * Get all the organizations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Organization> findAllWithEagerRelationships(Pageable pageable) {
        return organizationRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one organization by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Organization> findOne(Long id) {
        log.debug("Request to get Organization : {}", id);
        return organizationRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the organization by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.deleteById(id);
    }
}
