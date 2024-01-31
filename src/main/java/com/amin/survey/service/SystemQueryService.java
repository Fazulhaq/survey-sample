package com.amin.survey.service;

import com.amin.survey.domain.*; // for static metamodels
import com.amin.survey.domain.System;
import com.amin.survey.repository.SystemRepository;
import com.amin.survey.service.criteria.SystemCriteria;
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
 * Service for executing complex queries for {@link System} entities in the database.
 * The main input is a {@link SystemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link System} or a {@link Page} of {@link System} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SystemQueryService extends QueryService<System> {

    private final Logger log = LoggerFactory.getLogger(SystemQueryService.class);

    private final SystemRepository systemRepository;

    public SystemQueryService(SystemRepository systemRepository) {
        this.systemRepository = systemRepository;
    }

    /**
     * Return a {@link List} of {@link System} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<System> findByCriteria(SystemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<System> specification = createSpecification(criteria);
        return systemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link System} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<System> findByCriteria(SystemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<System> specification = createSpecification(criteria);
        return systemRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SystemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<System> specification = createSpecification(criteria);
        return systemRepository.count(specification);
    }

    /**
     * Function to convert {@link SystemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<System> createSpecification(SystemCriteria criteria) {
        Specification<System> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), System_.id));
            }
            if (criteria.getQuestion1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion1(), System_.question1));
            }
            if (criteria.getQuestion2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion2(), System_.question2));
            }
            if (criteria.getQuestion3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion3(), System_.question3));
            }
            if (criteria.getQuestion4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion4(), System_.question4));
            }
            if (criteria.getQuestion5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion5(), System_.question5));
            }
            if (criteria.getFormId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFormId(), root -> root.join(System_.form, JoinType.LEFT).get(Form_.id))
                    );
            }
        }
        return specification;
    }
}
