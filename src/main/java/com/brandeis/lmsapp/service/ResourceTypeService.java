package com.brandeis.lmsapp.service;

import com.brandeis.lmsapp.domain.ResourceType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ResourceType.
 */
public interface ResourceTypeService {

    /**
     * Save a resourceType.
     *
     * @param resourceType the entity to save
     * @return the persisted entity
     */
    ResourceType save(ResourceType resourceType);

    /**
     * Get all the resourceTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResourceType> findAll(Pageable pageable);


    /**
     * Get the "id" resourceType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ResourceType> findOne(Long id);

    /**
     * Delete the "id" resourceType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
