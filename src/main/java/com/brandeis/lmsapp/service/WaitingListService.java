package com.brandeis.lmsapp.service;

import com.brandeis.lmsapp.domain.WaitingList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing WaitingList.
 */
public interface WaitingListService {

    /**
     * Save a waitingList.
     *
     * @param waitingList the entity to save
     * @return the persisted entity
     */
    WaitingList save(WaitingList waitingList);

    /**
     * Get all the waitingLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WaitingList> findAll(Pageable pageable);

    /**
     * Get all the WaitingList with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<WaitingList> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" waitingList.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<WaitingList> findOne(Long id);

    /**
     * Delete the "id" waitingList.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
