package com.amin.survey.service;

import com.amin.survey.domain.ItDevice;
import com.amin.survey.repository.ItDeviceRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.amin.survey.domain.ItDevice}.
 */
@Service
@Transactional
public class ItDeviceService {

    private final Logger log = LoggerFactory.getLogger(ItDeviceService.class);

    private final ItDeviceRepository itDeviceRepository;

    public ItDeviceService(ItDeviceRepository itDeviceRepository) {
        this.itDeviceRepository = itDeviceRepository;
    }

    /**
     * Save a itDevice.
     *
     * @param itDevice the entity to save.
     * @return the persisted entity.
     */
    public ItDevice save(ItDevice itDevice) {
        log.debug("Request to save ItDevice : {}", itDevice);
        return itDeviceRepository.save(itDevice);
    }

    /**
     * Update a itDevice.
     *
     * @param itDevice the entity to save.
     * @return the persisted entity.
     */
    public ItDevice update(ItDevice itDevice) {
        log.debug("Request to update ItDevice : {}", itDevice);
        return itDeviceRepository.save(itDevice);
    }

    /**
     * Partially update a itDevice.
     *
     * @param itDevice the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ItDevice> partialUpdate(ItDevice itDevice) {
        log.debug("Request to partially update ItDevice : {}", itDevice);

        return itDeviceRepository
            .findById(itDevice.getId())
            .map(existingItDevice -> {
                if (itDevice.getDeviceType() != null) {
                    existingItDevice.setDeviceType(itDevice.getDeviceType());
                }
                if (itDevice.getQuantity() != null) {
                    existingItDevice.setQuantity(itDevice.getQuantity());
                }
                if (itDevice.getBrandAndModel() != null) {
                    existingItDevice.setBrandAndModel(itDevice.getBrandAndModel());
                }
                if (itDevice.getAge() != null) {
                    existingItDevice.setAge(itDevice.getAge());
                }
                if (itDevice.getPurpose() != null) {
                    existingItDevice.setPurpose(itDevice.getPurpose());
                }
                if (itDevice.getCurrentStatus() != null) {
                    existingItDevice.setCurrentStatus(itDevice.getCurrentStatus());
                }

                return existingItDevice;
            })
            .map(itDeviceRepository::save);
    }

    /**
     * Get all the itDevices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItDevice> findAll(Pageable pageable) {
        log.debug("Request to get all ItDevices");
        return itDeviceRepository.findAll(pageable);
    }

    /**
     * Get one itDevice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItDevice> findOne(Long id) {
        log.debug("Request to get ItDevice : {}", id);
        return itDeviceRepository.findById(id);
    }

    /**
     * Delete the itDevice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItDevice : {}", id);
        itDeviceRepository.deleteById(id);
    }
}
