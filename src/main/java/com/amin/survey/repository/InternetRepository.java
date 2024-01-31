package com.amin.survey.repository;

import com.amin.survey.domain.Internet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Internet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternetRepository extends JpaRepository<Internet, Long>, JpaSpecificationExecutor<Internet> {}
