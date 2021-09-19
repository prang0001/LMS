package com.brandeis.lmsapp.service;

import com.brandeis.lmsapp.domain.ResourceStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ResourceStatus.
 */
public interface ResourceStatusService {

    /**
     * Save a resourceStatus.
     *
     * @param resourceStatus the entity to save
     * @return the persisted entity
     */
    ResourceStatus save(ResourceStatus resourceStatus);

    /**
     * Get all the resourceStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResourceStatus> findAll(Pageable pageable);


    /**
     * Get the "id" resourceStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ResourceStatus> findOne(Long id);

    /**
     * Delete the "id" resourceStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
