package com.amin.survey.repository;

import com.amin.survey.domain.OrgResponsiblePerson;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrgResponsiblePerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrgResponsiblePersonRepository
    extends JpaRepository<OrgResponsiblePerson, Long>, JpaSpecificationExecutor<OrgResponsiblePerson> {}
