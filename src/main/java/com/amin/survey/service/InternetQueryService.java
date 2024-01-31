package com.amin.survey.service;

import com.amin.survey.domain.*; // for static metamodels
import com.amin.survey.domain.Internet;
import com.amin.survey.repository.InternetRepository;
import com.amin.survey.service.criteria.InternetCriteria;
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
 * Service for executing complex queries for {@link Internet} entities in the database.
 * The main input is a {@link InternetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Internet} or a {@link Page} of {@link Internet} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InternetQueryService extends QueryService<Internet> {

    private final Logger log = LoggerFactory.getLogger(InternetQueryService.class);

    private final InternetRepository internetRepository;

    public InternetQueryService(InternetRepository internetRepository) {
        this.internetRepository = internetRepository;
    }

    /**
     * Return a {@link List} of {@link Internet} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Internet> findByCriteria(InternetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Internet> specification = createSpecification(criteria);
        return internetRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Internet} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Internet> findByCriteria(InternetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Internet> specification = createSpecification(criteria);
        return internetRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InternetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Internet> specification = createSpecification(criteria);
        return internetRepository.count(specification);
    }

    /**
     * Function to convert {@link InternetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Internet> createSpecification(InternetCriteria criteria) {
        Specification<Internet> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Internet_.id));
            }
            if (criteria.getQuestion1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion1(), Internet_.question1));
            }
            if (criteria.getQuestion2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion2(), Internet_.question2));
            }
            if (criteria.getQuestion3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion3(), Internet_.question3));
            }
            if (criteria.getQuestion4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion4(), Internet_.question4));
            }
            if (criteria.getQuestion5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion5(), Internet_.question5));
            }
            if (criteria.getQuestion6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion6(), Internet_.question6));
            }
            if (criteria.getFormId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFormId(), root -> root.join(Internet_.form, JoinType.LEFT).get(Form_.id))
                    );
            }
        }
        return specification;
    }
}
