package com.amin.survey.repository;

import com.amin.survey.domain.System;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the System entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemRepository extends JpaRepository<System, Long>, JpaSpecificationExecutor<System> {}
