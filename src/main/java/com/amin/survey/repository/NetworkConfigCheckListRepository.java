package com.amin.survey.repository;

import com.amin.survey.domain.NetworkConfigCheckList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NetworkConfigCheckList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NetworkConfigCheckListRepository
    extends JpaRepository<NetworkConfigCheckList, Long>, JpaSpecificationExecutor<NetworkConfigCheckList> {}
