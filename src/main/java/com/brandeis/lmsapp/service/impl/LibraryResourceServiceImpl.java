package com.brandeis.lmsapp.service.impl;

import com.brandeis.lmsapp.service.LibraryResourceService;
import com.brandeis.lmsapp.domain.LibraryResource;
import com.brandeis.lmsapp.repository.LibraryResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing LibraryResource.
 */
@Service
@Transactional
public class LibraryResourceServiceImpl implements LibraryResourceService {

    private final Logger log = LoggerFactory.getLogger(LibraryResourceServiceImpl.class);

    private final LibraryResourceRepository libraryResourceRepository;

    public LibraryResourceServiceImpl(LibraryResourceRepository libraryResourceRepository) {
        this.libraryResourceRepository = libraryResourceRepository;
    }

    /**
     * Save a libraryResource.
     *
     * @param libraryResource the entity to save
     * @return the persisted entity
     */
    @Override
    public LibraryResource save(LibraryResource libraryResource) {
        log.debug("Request to save LibraryResource : {}", libraryResource);
        log.debug("Added by Pushpa");
        return libraryResourceRepository.save(libraryResource);
        
    }

    /**
     * Get all the libraryResources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LibraryResource> findAll(Pageable pageable) {
        log.debug("Request to get all LibraryResources");
        return libraryResourceRepository.findAll(pageable);
    }

    /**
     * Get one libraryResource by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LibraryResource> findOne(Long id) {
        log.debug("Request to get LibraryResource : {}", id);
        return libraryResourceRepository.findById(id);
    }

    /**
     * Delete the libraryResource by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LibraryResource : {}", id);
        libraryResourceRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<LibraryResource> findByStatusId(Long id, Pageable pageable) {
        log.debug("Request to get LibraryResource by Status : {}", id);
        return libraryResourceRepository.findByStatusId(id, pageable);
    }

    @Transactional(readOnly = true)
    public Page<LibraryResource> findByTypeId(Long id, Pageable pageable) {
        log.debug("Request to get LibraryResource by Resource Type : {}", id);
        return libraryResourceRepository.findByTypeId(id, pageable);
    }

    @Transactional(readOnly = true)
    public Page<LibraryResource> findByRentalId(Long id, Pageable pageable) {
        log.debug("Request to get LibraryResource by Rental ID : {}", id);
        return libraryResourceRepository.findByRentalId(id, pageable);
    }

    @Transactional(readOnly = true)
    public Page<LibraryResource> findByWaitingListId(Long id, Pageable pageable) {
        log.debug("Request to get LibraryResource by Waiting List ID : {}", id);
        return libraryResourceRepository.findByWaitingListId(id, pageable);
    }
}
