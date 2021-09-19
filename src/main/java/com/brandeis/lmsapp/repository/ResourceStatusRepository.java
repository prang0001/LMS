package com.brandeis.lmsapp.repository;

import com.brandeis.lmsapp.domain.ResourceStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResourceStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceStatusRepository extends JpaRepository<ResourceStatus, Long>, JpaSpecificationExecutor<ResourceStatus> {

}
