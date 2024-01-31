package com.amin.survey.repository;

import com.amin.survey.domain.Backup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Backup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BackupRepository extends JpaRepository<Backup, Long>, JpaSpecificationExecutor<Backup> {}
