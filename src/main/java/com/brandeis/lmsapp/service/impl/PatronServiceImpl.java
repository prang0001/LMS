package com.brandeis.lmsapp.service.impl;

import com.brandeis.lmsapp.service.PatronService;
import com.brandeis.lmsapp.domain.Patron;
import com.brandeis.lmsapp.repository.PatronRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Patron.
 */
@Service
@Transactional
public class PatronServiceImpl implements PatronService {

    private final Logger log = LoggerFactory.getLogger(PatronServiceImpl.class);

    private final PatronRepository patronRepository;

    public PatronServiceImpl(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    /**
     * Save a patron.
     *
     * @param patron the entity to save
     * @return the persisted entity
     */
    @Override
    public Patron save(Patron patron) {
        log.debug("Request to save Patron : {}", patron);
        return patronRepository.save(patron);
    }

    /**
     * Get all the patrons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Patron> findAll(Pageable pageable) {
        log.debug("Request to get all Patrons");
        return patronRepository.findAll(pageable);
    }


    /**
     * Get one patron by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Patron> findOne(Long id) {
        log.debug("Request to get Patron : {}", id);
        return patronRepository.findById(id);
    }

    /**
     * Delete the patron by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Patron : {}", id);
        patronRepository.deleteById(id);
    }
}
