package com.amin.survey.repository;

import com.amin.survey.domain.Backup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Backup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BackupRepository extends JpaRepository<Backup, Long>, JpaSpecificationExecutor<Backup> {
    @Query(value = "SELECT * FROM backup where form_id=:id", nativeQuery = true)
    Optional<Backup> findByFormId(@Param("id") Long id);
}
