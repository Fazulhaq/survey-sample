package com.amin.survey.service;

import com.amin.survey.domain.*; // for static metamodels
import com.amin.survey.domain.DatacenterDevice;
import com.amin.survey.repository.DatacenterDeviceRepository;
import com.amin.survey.service.criteria.DatacenterDeviceCriteria;
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
 * Service for executing complex queries for {@link DatacenterDevice} entities in the database.
 * The main input is a {@link DatacenterDeviceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DatacenterDevice} or a {@link Page} of {@link DatacenterDevice} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DatacenterDeviceQueryService extends QueryService<DatacenterDevice> {

    private final Logger log = LoggerFactory.getLogger(DatacenterDeviceQueryService.class);

    private final DatacenterDeviceRepository datacenterDeviceRepository;

    public DatacenterDeviceQueryService(DatacenterDeviceRepository datacenterDeviceRepository) {
        this.datacenterDeviceRepository = datacenterDeviceRepository;
    }

    /**
     * Return a {@link List} of {@link DatacenterDevice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DatacenterDevice> findByCriteria(DatacenterDeviceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DatacenterDevice> specification = createSpecification(criteria);
        return datacenterDeviceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DatacenterDevice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DatacenterDevice> findByCriteria(DatacenterDeviceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DatacenterDevice> specification = createSpecification(criteria);
        return datacenterDeviceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DatacenterDeviceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DatacenterDevice> specification = createSpecification(criteria);
        return datacenterDeviceRepository.count(specification);
    }

    /**
     * Function to convert {@link DatacenterDeviceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DatacenterDevice> createSpecification(DatacenterDeviceCriteria criteria) {
        Specification<DatacenterDevice> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DatacenterDevice_.id));
            }
            if (criteria.getDeviceType() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceType(), DatacenterDevice_.deviceType));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuantity(), DatacenterDevice_.quantity));
            }
            if (criteria.getBrandAndModel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrandAndModel(), DatacenterDevice_.brandAndModel));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAge(), DatacenterDevice_.age));
            }
            if (criteria.getPurpose() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurpose(), DatacenterDevice_.purpose));
            }
            if (criteria.getCurrentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentStatus(), DatacenterDevice_.currentStatus));
            }
            if (criteria.getFormId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFormId(), root -> root.join(DatacenterDevice_.form, JoinType.LEFT).get(Form_.id))
                    );
            }
        }
        return specification;
    }
}
