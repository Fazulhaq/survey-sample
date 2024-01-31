package com.amin.survey.service;

import com.amin.survey.domain.*; // for static metamodels
import com.amin.survey.domain.ItDevice;
import com.amin.survey.repository.ItDeviceRepository;
import com.amin.survey.service.criteria.ItDeviceCriteria;
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
 * Service for executing complex queries for {@link ItDevice} entities in the database.
 * The main input is a {@link ItDeviceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItDevice} or a {@link Page} of {@link ItDevice} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItDeviceQueryService extends QueryService<ItDevice> {

    private final Logger log = LoggerFactory.getLogger(ItDeviceQueryService.class);

    private final ItDeviceRepository itDeviceRepository;

    public ItDeviceQueryService(ItDeviceRepository itDeviceRepository) {
        this.itDeviceRepository = itDeviceRepository;
    }

    /**
     * Return a {@link List} of {@link ItDevice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItDevice> findByCriteria(ItDeviceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItDevice> specification = createSpecification(criteria);
        return itDeviceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ItDevice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItDevice> findByCriteria(ItDeviceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItDevice> specification = createSpecification(criteria);
        return itDeviceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItDeviceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItDevice> specification = createSpecification(criteria);
        return itDeviceRepository.count(specification);
    }

    /**
     * Function to convert {@link ItDeviceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItDevice> createSpecification(ItDeviceCriteria criteria) {
        Specification<ItDevice> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItDevice_.id));
            }
            if (criteria.getDeviceType() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceType(), ItDevice_.deviceType));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuantity(), ItDevice_.quantity));
            }
            if (criteria.getBrandAndModel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrandAndModel(), ItDevice_.brandAndModel));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAge(), ItDevice_.age));
            }
            if (criteria.getPurpose() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurpose(), ItDevice_.purpose));
            }
            if (criteria.getCurrentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentStatus(), ItDevice_.currentStatus));
            }
            if (criteria.getFormId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFormId(), root -> root.join(ItDevice_.form, JoinType.LEFT).get(Form_.id))
                    );
            }
        }
        return specification;
    }
}
