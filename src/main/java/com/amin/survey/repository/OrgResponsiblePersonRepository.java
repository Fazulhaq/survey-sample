package com.amin.survey.repository;

import com.amin.survey.domain.OrgResponsiblePerson;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrgResponsiblePerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrgResponsiblePersonRepository
    extends JpaRepository<OrgResponsiblePerson, Long>, JpaSpecificationExecutor<OrgResponsiblePerson> {
    @Query(value = "SELECT * FROM org_responsible_person WHERE form_id=:id", nativeQuery = true)
    Optional<OrgResponsiblePerson> findByFormId(@Param("id") Long id);
}
