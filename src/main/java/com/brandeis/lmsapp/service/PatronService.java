package com.brandeis.lmsapp.service;

import com.brandeis.lmsapp.domain.Patron;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Patron.
 */
public interface PatronService {

    /**
     * Save a patron.
     *
     * @param patron the entity to save
     * @return the persisted entity
     */
    Patron save(Patron patron);

    /**
     * Get all the patrons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Patron> findAll(Pageable pageable);


    /**
     * Get the "id" patron.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Patron> findOne(Long id);

    /**
     * Delete the "id" patron.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
