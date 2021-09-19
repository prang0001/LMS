package com.brandeis.lmsapp.service;

import com.brandeis.lmsapp.domain.LibraryResource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing LibraryResource.
 */
public interface LibraryResourceService {

    /**
     * Save a libraryResource.
     *
     * @param libraryResource the entity to save
     * @return the persisted entity
     */
    LibraryResource save(LibraryResource libraryResource);

    /**
     * Get all the libraryResources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LibraryResource> findAll(Pageable pageable);

     /**
     * Get the "id" libraryResource.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LibraryResource> findOne(Long id);

    /**
     * Delete the "id" libraryResource.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Page<LibraryResource> findByStatusId(Long id, Pageable pageable);

    Page<LibraryResource> findByTypeId(Long id, Pageable pageable);

    Page<LibraryResource> findByRentalId(Long id, Pageable pageable);

    Page<LibraryResource> findByWaitingListId(Long id, Pageable pageable);
}
