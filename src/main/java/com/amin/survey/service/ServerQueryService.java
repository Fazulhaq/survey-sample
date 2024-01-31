package com.amin.survey.service;

import com.amin.survey.domain.*; // for static metamodels
import com.amin.survey.domain.Server;
import com.amin.survey.repository.ServerRepository;
import com.amin.survey.service.criteria.ServerCriteria;
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
 * Service for executing complex queries for {@link Server} entities in the database.
 * The main input is a {@link ServerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Server} or a {@link Page} of {@link Server} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServerQueryService extends QueryService<Server> {

    private final Logger log = LoggerFactory.getLogger(ServerQueryService.class);

    private final ServerRepository serverRepository;

    public ServerQueryService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    /**
     * Return a {@link List} of {@link Server} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Server> findByCriteria(ServerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Server> specification = createSpecification(criteria);
        return serverRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Server} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Server> findByCriteria(ServerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Server> specification = createSpecification(criteria);
        return serverRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Server> specification = createSpecification(criteria);
        return serverRepository.count(specification);
    }

    /**
     * Function to convert {@link ServerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Server> createSpecification(ServerCriteria criteria) {
        Specification<Server> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Server_.id));
            }
            if (criteria.getQuestion1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion1(), Server_.question1));
            }
            if (criteria.getQuestion2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion2(), Server_.question2));
            }
            if (criteria.getQuestion3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion3(), Server_.question3));
            }
            if (criteria.getQuestion4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion4(), Server_.question4));
            }
            if (criteria.getQuestion5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion5(), Server_.question5));
            }
            if (criteria.getQuestion6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion6(), Server_.question6));
            }
            if (criteria.getQuestion7() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion7(), Server_.question7));
            }
            if (criteria.getFormId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFormId(), root -> root.join(Server_.form, JoinType.LEFT).get(Form_.id))
                    );
            }
        }
        return specification;
    }
}
