package com.amin.survey.repository;

import com.amin.survey.domain.System;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the System entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemRepository extends JpaRepository<System, Long>, JpaSpecificationExecutor<System> {
    @Query(value = "SELECT * FROM system where form_id=:id", nativeQuery = true)
    Optional<System> findByFormId(@Param("id") Long id);
}
