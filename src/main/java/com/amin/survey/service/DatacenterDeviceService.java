package com.amin.survey.service;

import com.amin.survey.domain.DatacenterDevice;
import com.amin.survey.repository.DatacenterDeviceRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.amin.survey.domain.DatacenterDevice}.
 */
@Service
@Transactional
public class DatacenterDeviceService {

    private final Logger log = LoggerFactory.getLogger(DatacenterDeviceService.class);

    private final DatacenterDeviceRepository datacenterDeviceRepository;

    public DatacenterDeviceService(DatacenterDeviceRepository datacenterDeviceRepository) {
        this.datacenterDeviceRepository = datacenterDeviceRepository;
    }

    /**
     * Save a datacenterDevice.
     *
     * @param datacenterDevice the entity to save.
     * @return the persisted entity.
     */
    public DatacenterDevice save(DatacenterDevice datacenterDevice) {
        log.debug("Request to save DatacenterDevice : {}", datacenterDevice);
        return datacenterDeviceRepository.save(datacenterDevice);
    }

    /**
     * Update a datacenterDevice.
     *
     * @param datacenterDevice the entity to save.
     * @return the persisted entity.
     */
    public DatacenterDevice update(DatacenterDevice datacenterDevice) {
        log.debug("Request to update DatacenterDevice : {}", datacenterDevice);
        return datacenterDeviceRepository.save(datacenterDevice);
    }

    /**
     * Partially update a datacenterDevice.
     *
     * @param datacenterDevice the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DatacenterDevice> partialUpdate(DatacenterDevice datacenterDevice) {
        log.debug("Request to partially update DatacenterDevice : {}", datacenterDevice);

        return datacenterDeviceRepository
            .findById(datacenterDevice.getId())
            .map(existingDatacenterDevice -> {
                if (datacenterDevice.getDeviceType() != null) {
                    existingDatacenterDevice.setDeviceType(datacenterDevice.getDeviceType());
                }
                if (datacenterDevice.getQuantity() != null) {
                    existingDatacenterDevice.setQuantity(datacenterDevice.getQuantity());
                }
                if (datacenterDevice.getBrandAndModel() != null) {
                    existingDatacenterDevice.setBrandAndModel(datacenterDevice.getBrandAndModel());
                }
                if (datacenterDevice.getAge() != null) {
                    existingDatacenterDevice.setAge(datacenterDevice.getAge());
                }
                if (datacenterDevice.getPurpose() != null) {
                    existingDatacenterDevice.setPurpose(datacenterDevice.getPurpose());
                }
                if (datacenterDevice.getCurrentStatus() != null) {
                    existingDatacenterDevice.setCurrentStatus(datacenterDevice.getCurrentStatus());
                }

                return existingDatacenterDevice;
            })
            .map(datacenterDeviceRepository::save);
    }

    /**
     * Get all the datacenterDevices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DatacenterDevice> findAll(Pageable pageable) {
        log.debug("Request to get all DatacenterDevices");
        return datacenterDeviceRepository.findAll(pageable);
    }

    /**
     * Get one datacenterDevice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatacenterDevice> findOne(Long id) {
        log.debug("Request to get DatacenterDevice : {}", id);
        return datacenterDeviceRepository.findById(id);
    }

    /**
     * Delete the datacenterDevice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DatacenterDevice : {}", id);
        datacenterDeviceRepository.deleteById(id);
    }
}
