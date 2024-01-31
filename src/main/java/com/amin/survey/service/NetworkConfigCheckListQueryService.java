package com.amin.survey.service;

import com.amin.survey.domain.*; // for static metamodels
import com.amin.survey.domain.NetworkConfigCheckList;
import com.amin.survey.repository.NetworkConfigCheckListRepository;
import com.amin.survey.service.criteria.NetworkConfigCheckListCriteria;
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
 * Service for executing complex queries for {@link NetworkConfigCheckList} entities in the database.
 * The main input is a {@link NetworkConfigCheckListCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NetworkConfigCheckList} or a {@link Page} of {@link NetworkConfigCheckList} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NetworkConfigCheckListQueryService extends QueryService<NetworkConfigCheckList> {

    private final Logger log = LoggerFactory.getLogger(NetworkConfigCheckListQueryService.class);

    private final NetworkConfigCheckListRepository networkConfigCheckListRepository;

    public NetworkConfigCheckListQueryService(NetworkConfigCheckListRepository networkConfigCheckListRepository) {
        this.networkConfigCheckListRepository = networkConfigCheckListRepository;
    }

    /**
     * Return a {@link List} of {@link NetworkConfigCheckList} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NetworkConfigCheckList> findByCriteria(NetworkConfigCheckListCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NetworkConfigCheckList> specification = createSpecification(criteria);
        return networkConfigCheckListRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NetworkConfigCheckList} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NetworkConfigCheckList> findByCriteria(NetworkConfigCheckListCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NetworkConfigCheckList> specification = createSpecification(criteria);
        return networkConfigCheckListRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NetworkConfigCheckListCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NetworkConfigCheckList> specification = createSpecification(criteria);
        return networkConfigCheckListRepository.count(specification);
    }

    /**
     * Function to convert {@link NetworkConfigCheckListCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NetworkConfigCheckList> createSpecification(NetworkConfigCheckListCriteria criteria) {
        Specification<NetworkConfigCheckList> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NetworkConfigCheckList_.id));
            }
            if (criteria.getDhcp() != null) {
                specification = specification.and(buildSpecification(criteria.getDhcp(), NetworkConfigCheckList_.dhcp));
            }
            if (criteria.getDns() != null) {
                specification = specification.and(buildSpecification(criteria.getDns(), NetworkConfigCheckList_.dns));
            }
            if (criteria.getActiveDirectory() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getActiveDirectory(), NetworkConfigCheckList_.activeDirectory));
            }
            if (criteria.getSharedDrives() != null) {
                specification = specification.and(buildSpecification(criteria.getSharedDrives(), NetworkConfigCheckList_.sharedDrives));
            }
            if (criteria.getMailServer() != null) {
                specification = specification.and(buildSpecification(criteria.getMailServer(), NetworkConfigCheckList_.mailServer));
            }
            if (criteria.getFirewalls() != null) {
                specification = specification.and(buildSpecification(criteria.getFirewalls(), NetworkConfigCheckList_.firewalls));
            }
            if (criteria.getLoadBalancing() != null) {
                specification = specification.and(buildSpecification(criteria.getLoadBalancing(), NetworkConfigCheckList_.loadBalancing));
            }
            if (criteria.getNetworkMonitoring() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getNetworkMonitoring(), NetworkConfigCheckList_.networkMonitoring));
            }
            if (criteria.getAntivirus() != null) {
                specification = specification.and(buildSpecification(criteria.getAntivirus(), NetworkConfigCheckList_.antivirus));
            }
            if (criteria.getIntegratedSystems() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIntegratedSystems(), NetworkConfigCheckList_.integratedSystems));
            }
            if (criteria.getAntiSpam() != null) {
                specification = specification.and(buildSpecification(criteria.getAntiSpam(), NetworkConfigCheckList_.antiSpam));
            }
            if (criteria.getWpa() != null) {
                specification = specification.and(buildSpecification(criteria.getWpa(), NetworkConfigCheckList_.wpa));
            }
            if (criteria.getAutoBackup() != null) {
                specification = specification.and(buildSpecification(criteria.getAutoBackup(), NetworkConfigCheckList_.autoBackup));
            }
            if (criteria.getPhysicalSecurity() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getPhysicalSecurity(), NetworkConfigCheckList_.physicalSecurity));
            }
            if (criteria.getStorageServer() != null) {
                specification = specification.and(buildSpecification(criteria.getStorageServer(), NetworkConfigCheckList_.storageServer));
            }
            if (criteria.getSecurityAudit() != null) {
                specification = specification.and(buildSpecification(criteria.getSecurityAudit(), NetworkConfigCheckList_.securityAudit));
            }
            if (criteria.getDisasterRecovery() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getDisasterRecovery(), NetworkConfigCheckList_.disasterRecovery));
            }
            if (criteria.getProxyServer() != null) {
                specification = specification.and(buildSpecification(criteria.getProxyServer(), NetworkConfigCheckList_.proxyServer));
            }
            if (criteria.getWdsServer() != null) {
                specification = specification.and(buildSpecification(criteria.getWdsServer(), NetworkConfigCheckList_.wdsServer));
            }
            if (criteria.getFormId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFormId(),
                            root -> root.join(NetworkConfigCheckList_.form, JoinType.LEFT).get(Form_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
