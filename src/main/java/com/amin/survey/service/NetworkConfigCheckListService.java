package com.amin.survey.service;

import com.amin.survey.domain.NetworkConfigCheckList;
import com.amin.survey.repository.NetworkConfigCheckListRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.amin.survey.domain.NetworkConfigCheckList}.
 */
@Service
@Transactional
public class NetworkConfigCheckListService {

    private final Logger log = LoggerFactory.getLogger(NetworkConfigCheckListService.class);

    private final NetworkConfigCheckListRepository networkConfigCheckListRepository;

    public NetworkConfigCheckListService(NetworkConfigCheckListRepository networkConfigCheckListRepository) {
        this.networkConfigCheckListRepository = networkConfigCheckListRepository;
    }

    /**
     * Save a networkConfigCheckList.
     *
     * @param networkConfigCheckList the entity to save.
     * @return the persisted entity.
     */
    public NetworkConfigCheckList save(NetworkConfigCheckList networkConfigCheckList) {
        log.debug("Request to save NetworkConfigCheckList : {}", networkConfigCheckList);
        return networkConfigCheckListRepository.save(networkConfigCheckList);
    }

    /**
     * Update a networkConfigCheckList.
     *
     * @param networkConfigCheckList the entity to save.
     * @return the persisted entity.
     */
    public NetworkConfigCheckList update(NetworkConfigCheckList networkConfigCheckList) {
        log.debug("Request to update NetworkConfigCheckList : {}", networkConfigCheckList);
        return networkConfigCheckListRepository.save(networkConfigCheckList);
    }

    /**
     * Partially update a networkConfigCheckList.
     *
     * @param networkConfigCheckList the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NetworkConfigCheckList> partialUpdate(NetworkConfigCheckList networkConfigCheckList) {
        log.debug("Request to partially update NetworkConfigCheckList : {}", networkConfigCheckList);

        return networkConfigCheckListRepository
            .findById(networkConfigCheckList.getId())
            .map(existingNetworkConfigCheckList -> {
                if (networkConfigCheckList.getDhcp() != null) {
                    existingNetworkConfigCheckList.setDhcp(networkConfigCheckList.getDhcp());
                }
                if (networkConfigCheckList.getDns() != null) {
                    existingNetworkConfigCheckList.setDns(networkConfigCheckList.getDns());
                }
                if (networkConfigCheckList.getActiveDirectory() != null) {
                    existingNetworkConfigCheckList.setActiveDirectory(networkConfigCheckList.getActiveDirectory());
                }
                if (networkConfigCheckList.getSharedDrives() != null) {
                    existingNetworkConfigCheckList.setSharedDrives(networkConfigCheckList.getSharedDrives());
                }
                if (networkConfigCheckList.getMailServer() != null) {
                    existingNetworkConfigCheckList.setMailServer(networkConfigCheckList.getMailServer());
                }
                if (networkConfigCheckList.getFirewalls() != null) {
                    existingNetworkConfigCheckList.setFirewalls(networkConfigCheckList.getFirewalls());
                }
                if (networkConfigCheckList.getLoadBalancing() != null) {
                    existingNetworkConfigCheckList.setLoadBalancing(networkConfigCheckList.getLoadBalancing());
                }
                if (networkConfigCheckList.getNetworkMonitoring() != null) {
                    existingNetworkConfigCheckList.setNetworkMonitoring(networkConfigCheckList.getNetworkMonitoring());
                }
                if (networkConfigCheckList.getAntivirus() != null) {
                    existingNetworkConfigCheckList.setAntivirus(networkConfigCheckList.getAntivirus());
                }
                if (networkConfigCheckList.getIntegratedSystems() != null) {
                    existingNetworkConfigCheckList.setIntegratedSystems(networkConfigCheckList.getIntegratedSystems());
                }
                if (networkConfigCheckList.getAntiSpam() != null) {
                    existingNetworkConfigCheckList.setAntiSpam(networkConfigCheckList.getAntiSpam());
                }
                if (networkConfigCheckList.getWpa() != null) {
                    existingNetworkConfigCheckList.setWpa(networkConfigCheckList.getWpa());
                }
                if (networkConfigCheckList.getAutoBackup() != null) {
                    existingNetworkConfigCheckList.setAutoBackup(networkConfigCheckList.getAutoBackup());
                }
                if (networkConfigCheckList.getPhysicalSecurity() != null) {
                    existingNetworkConfigCheckList.setPhysicalSecurity(networkConfigCheckList.getPhysicalSecurity());
                }
                if (networkConfigCheckList.getStorageServer() != null) {
                    existingNetworkConfigCheckList.setStorageServer(networkConfigCheckList.getStorageServer());
                }
                if (networkConfigCheckList.getSecurityAudit() != null) {
                    existingNetworkConfigCheckList.setSecurityAudit(networkConfigCheckList.getSecurityAudit());
                }
                if (networkConfigCheckList.getDisasterRecovery() != null) {
                    existingNetworkConfigCheckList.setDisasterRecovery(networkConfigCheckList.getDisasterRecovery());
                }
                if (networkConfigCheckList.getProxyServer() != null) {
                    existingNetworkConfigCheckList.setProxyServer(networkConfigCheckList.getProxyServer());
                }
                if (networkConfigCheckList.getWdsServer() != null) {
                    existingNetworkConfigCheckList.setWdsServer(networkConfigCheckList.getWdsServer());
                }

                return existingNetworkConfigCheckList;
            })
            .map(networkConfigCheckListRepository::save);
    }

    /**
     * Get all the networkConfigCheckLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NetworkConfigCheckList> findAll(Pageable pageable) {
        log.debug("Request to get all NetworkConfigCheckLists");
        return networkConfigCheckListRepository.findAll(pageable);
    }

    /**
     * Get one networkConfigCheckList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NetworkConfigCheckList> findOne(Long id) {
        log.debug("Request to get NetworkConfigCheckList : {}", id);
        return networkConfigCheckListRepository.findById(id);
    }

    /**
     * Delete the networkConfigCheckList by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NetworkConfigCheckList : {}", id);
        networkConfigCheckListRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<NetworkConfigCheckList> findByFormId(Long id) {
        log.debug("Request to get NetworkConfigCheckList : {}", id);
        return networkConfigCheckListRepository.findByFormId(id);
    }
}
