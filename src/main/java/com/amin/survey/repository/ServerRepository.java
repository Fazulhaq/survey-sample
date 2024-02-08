package com.amin.survey.repository;

import com.amin.survey.domain.Server;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Server entity.
 */

@Repository
public interface ServerRepository extends JpaRepository<Server, Long>, JpaSpecificationExecutor<Server> {
    @Query(value = "SELECT * FROM server where form_id=:id", nativeQuery = true)
    Optional<Server> findByFormId(@Param("id") Long id);
}
