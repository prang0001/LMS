package com.brandeis.lmsapp.service.impl;

import com.brandeis.lmsapp.service.ResourceStatusService;
import com.brandeis.lmsapp.domain.ResourceStatus;
import com.brandeis.lmsapp.repository.ResourceStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ResourceStatus.
 */
@Service
@Transactional
public class ResourceStatusServiceImpl implements ResourceStatusService {

    private final Logger log = LoggerFactory.getLogger(ResourceStatusServiceImpl.class);

    private final ResourceStatusRepository resourceStatusRepository;

    public ResourceStatusServiceImpl(ResourceStatusRepository resourceStatusRepository) {
        this.resourceStatusRepository = resourceStatusRepository;
    }

    /**
     * Save a resourceStatus.
     *
     * @param resourceStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public ResourceStatus save(ResourceStatus resourceStatus) {
        log.debug("Request to save ResourceStatus : {}", resourceStatus);
        return resourceStatusRepository.save(resourceStatus);
    }

    /**
     * Get all the resourceStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResourceStatus> findAll(Pageable pageable) {
        log.debug("Request to get all ResourceStatuses");
        return resourceStatusRepository.findAll(pageable);
    }


    /**
     * Get one resourceStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResourceStatus> findOne(Long id) {
        log.debug("Request to get ResourceStatus : {}", id);
        return resourceStatusRepository.findById(id);
    }

    /**
     * Delete the resourceStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResourceStatus : {}", id);
        resourceStatusRepository.deleteById(id);
    }
}
