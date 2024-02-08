package com.amin.survey.repository;

import com.amin.survey.domain.DatacenterDevice;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DatacenterDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatacenterDeviceRepository extends JpaRepository<DatacenterDevice, Long>, JpaSpecificationExecutor<DatacenterDevice> {
    @Query(value = "SELECT * FROM datacenter_device where form_id=:id", nativeQuery = true)
    Optional<DatacenterDevice> findByFormId(@Param("id") Long id);
}
