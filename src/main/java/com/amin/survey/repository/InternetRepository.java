package com.amin.survey.repository;

import com.amin.survey.domain.Internet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Internet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternetRepository extends JpaRepository<Internet, Long>, JpaSpecificationExecutor<Internet> {
    @Query(value = "SELECT * FROM internet where form_id=:id", nativeQuery = true)
    Optional<Internet> findByFormId(@Param("id") Long id);
}
