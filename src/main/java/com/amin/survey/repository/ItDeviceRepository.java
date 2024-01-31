package com.amin.survey.repository;

import com.amin.survey.domain.ItDevice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ItDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItDeviceRepository extends JpaRepository<ItDevice, Long>, JpaSpecificationExecutor<ItDevice> {}
