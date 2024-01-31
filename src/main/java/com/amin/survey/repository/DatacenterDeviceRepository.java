package com.amin.survey.repository;

import com.amin.survey.domain.DatacenterDevice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DatacenterDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatacenterDeviceRepository extends JpaRepository<DatacenterDevice, Long>, JpaSpecificationExecutor<DatacenterDevice> {}
