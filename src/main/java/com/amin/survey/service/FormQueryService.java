package com.amin.survey.service;

import com.amin.survey.domain.*; // for static metamodels
import com.amin.survey.domain.Form;
import com.amin.survey.repository.FormRepository;
import com.amin.survey.service.criteria.FormCriteria;
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
 * Service for executing complex queries for {@link Form} entities in the database.
 * The main input is a {@link FormCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Form} or a {@link Page} of {@link Form} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FormQueryService extends QueryService<Form> {

    private final Logger log = LoggerFactory.getLogger(FormQueryService.class);

    private final FormRepository formRepository;

    public FormQueryService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    /**
     * Return a {@link List} of {@link Form} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Form> findByCriteria(FormCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Form> specification = createSpecification(criteria);
        return formRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Form} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Form> findByCriteria(FormCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Form> specification = createSpecification(criteria);
        return formRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FormCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Form> specification = createSpecification(criteria);
        return formRepository.count(specification);
    }

    /**
     * Function to convert {@link FormCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Form> createSpecification(FormCriteria criteria) {
        Specification<Form> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Form_.id));
            }
            if (criteria.getFuturePlan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFuturePlan(), Form_.futurePlan));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Form_.status));
            }
            if (criteria.getCreateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateDate(), Form_.createDate));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), Form_.updateDate));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getUserId(), root -> root.join(Form_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getOrganizationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganizationId(),
                            root -> root.join(Form_.organization, JoinType.LEFT).get(Organization_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
