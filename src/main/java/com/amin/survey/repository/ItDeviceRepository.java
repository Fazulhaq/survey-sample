package com.amin.survey.repository;

import com.amin.survey.domain.ItDevice;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ItDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItDeviceRepository extends JpaRepository<ItDevice, Long>, JpaSpecificationExecutor<ItDevice> {
    @Query(value = "SELECT * FROM it_device WHERE form_id=:id", nativeQuery = true)
    Optional<ItDevice> findByFormId(@Param("id") Long id);
}
