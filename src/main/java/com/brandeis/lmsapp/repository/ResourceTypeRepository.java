package com.brandeis.lmsapp.repository;

import com.brandeis.lmsapp.domain.ResourceType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResourceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceTypeRepository extends JpaRepository<ResourceType, Long>, JpaSpecificationExecutor<ResourceType> {

}
