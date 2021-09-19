package com.brandeis.lmsapp.service;

import com.brandeis.lmsapp.domain.RentalTransaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing RentalTransaction.
 */
public interface RentalTransactionService {

    /**
     * Save a rentalTransaction.
     *
     * @param rentalTransaction the entity to save
     * @return the persisted entity
     */
    RentalTransaction save(RentalTransaction rentalTransaction);

    /**
     * Get all the rentalTransactions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RentalTransaction> findAll(Pageable pageable);

    /**
     * Get all the RentalTransaction with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<RentalTransaction> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" rentalTransaction.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RentalTransaction> findOne(Long id);

    /**
     * Delete the "id" rentalTransaction.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * CH - find patrons with overdue rentals to notify to return the overdue rental
     * @return
     */
    List<String> findOverdueRentals();

    /**
     * CH - find rentals for a patron login
     * @return
     */
    List<RentalTransaction> findByPatron(String login);
}
