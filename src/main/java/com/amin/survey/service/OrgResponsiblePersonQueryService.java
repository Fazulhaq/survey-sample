package com.amin.survey.service;

import com.amin.survey.domain.*; // for static metamodels
import com.amin.survey.domain.OrgResponsiblePerson;
import com.amin.survey.repository.OrgResponsiblePersonRepository;
import com.amin.survey.service.criteria.OrgResponsiblePersonCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link OrgResponsiblePerson} entities in the database.
 * The main input is a {@link OrgResponsiblePersonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrgResponsiblePerson} or a {@link Page} of {@link OrgResponsiblePerson} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrgResponsiblePersonQueryService extends QueryService<OrgResponsiblePerson> {

    private final Logger log = LoggerFactory.getLogger(OrgResponsiblePersonQueryService.class);

    private final OrgResponsiblePersonRepository orgResponsiblePersonRepository;

    public OrgResponsiblePersonQueryService(OrgResponsiblePersonRepository orgResponsiblePersonRepository) {
        this.orgResponsiblePersonRepository = orgResponsiblePersonRepository;
    }

    /**
     * Return a {@link List} of {@link OrgResponsiblePerson} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrgResponsiblePerson> findByCriteria(OrgResponsiblePersonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrgResponsiblePerson> specification = createSpecification(criteria);
        return orgResponsiblePersonRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OrgResponsiblePerson} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrgResponsiblePerson> findByCriteria(OrgResponsiblePersonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrgResponsiblePerson> specification = createSpecification(criteria);
        return orgResponsiblePersonRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrgResponsiblePersonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrgResponsiblePerson> specification = createSpecification(criteria);
        return orgResponsiblePersonRepository.count(specification);
    }

    /**
     * Function to convert {@link OrgResponsiblePersonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrgResponsiblePerson> createSpecification(OrgResponsiblePersonCriteria criteria) {
        Specification<OrgResponsiblePerson> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrgResponsiblePerson_.id));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), OrgResponsiblePerson_.fullName));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), OrgResponsiblePerson_.position));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), OrgResponsiblePerson_.contact));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), OrgResponsiblePerson_.date));
            }
            if (criteria.getFormId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFormId(), root -> root.join(OrgResponsiblePerson_.form, JoinType.LEFT).get(Form_.id))
                    );
            }
        }
        return specification;
    }
}
