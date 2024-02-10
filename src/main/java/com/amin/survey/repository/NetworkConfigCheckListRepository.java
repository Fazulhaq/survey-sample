package com.amin.survey.repository;

import com.amin.survey.domain.NetworkConfigCheckList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NetworkConfigCheckList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NetworkConfigCheckListRepository
    extends JpaRepository<NetworkConfigCheckList, Long>, JpaSpecificationExecutor<NetworkConfigCheckList> {
    @Query(value = "SELECT * FROM network_config_check_list WHERE form_id=:id", nativeQuery = true)
    Optional<NetworkConfigCheckList> findByFormId(@Param("id") Long id);
}
