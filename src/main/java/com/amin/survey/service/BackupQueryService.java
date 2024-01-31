package com.amin.survey.service;

import com.amin.survey.domain.*; // for static metamodels
import com.amin.survey.domain.Backup;
import com.amin.survey.repository.BackupRepository;
import com.amin.survey.service.criteria.BackupCriteria;
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
 * Service for executing complex queries for {@link Backup} entities in the database.
 * The main input is a {@link BackupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Backup} or a {@link Page} of {@link Backup} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BackupQueryService extends QueryService<Backup> {

    private final Logger log = LoggerFactory.getLogger(BackupQueryService.class);

    private final BackupRepository backupRepository;

    public BackupQueryService(BackupRepository backupRepository) {
        this.backupRepository = backupRepository;
    }

    /**
     * Return a {@link List} of {@link Backup} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Backup> findByCriteria(BackupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Backup> specification = createSpecification(criteria);
        return backupRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Backup} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Backup> findByCriteria(BackupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Backup> specification = createSpecification(criteria);
        return backupRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BackupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Backup> specification = createSpecification(criteria);
        return backupRepository.count(specification);
    }

    /**
     * Function to convert {@link BackupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Backup> createSpecification(BackupCriteria criteria) {
        Specification<Backup> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Backup_.id));
            }
            if (criteria.getQuestion1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion1(), Backup_.question1));
            }
            if (criteria.getQuestion2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion2(), Backup_.question2));
            }
            if (criteria.getQuestion3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion3(), Backup_.question3));
            }
            if (criteria.getQuestion4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion4(), Backup_.question4));
            }
            if (criteria.getQuestion5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion5(), Backup_.question5));
            }
            if (criteria.getQuestion6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion6(), Backup_.question6));
            }
            if (criteria.getQuestion7() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion7(), Backup_.question7));
            }
            if (criteria.getFormId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFormId(), root -> root.join(Backup_.form, JoinType.LEFT).get(Form_.id))
                    );
            }
        }
        return specification;
    }
}
