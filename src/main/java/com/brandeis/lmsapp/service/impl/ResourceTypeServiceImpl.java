package com.brandeis.lmsapp.service.impl;

import com.brandeis.lmsapp.service.ResourceTypeService;
import com.brandeis.lmsapp.domain.ResourceType;
import com.brandeis.lmsapp.repository.ResourceTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ResourceType.
 */
@Service
@Transactional
public class ResourceTypeServiceImpl implements ResourceTypeService {

    private final Logger log = LoggerFactory.getLogger(ResourceTypeServiceImpl.class);

    private final ResourceTypeRepository resourceTypeRepository;

    public ResourceTypeServiceImpl(ResourceTypeRepository resourceTypeRepository) {
        this.resourceTypeRepository = resourceTypeRepository;
    }

    /**
     * Save a resourceType.
     *
     * @param resourceType the entity to save
     * @return the persisted entity
     */
    @Override
    public ResourceType save(ResourceType resourceType) {
        log.debug("Request to save ResourceType : {}", resourceType);
        return resourceTypeRepository.save(resourceType);
    }

    /**
     * Get all the resourceTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResourceType> findAll(Pageable pageable) {
        log.debug("Request to get all ResourceTypes");
        return resourceTypeRepository.findAll(pageable);
    }


    /**
     * Get one resourceType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResourceType> findOne(Long id) {
        log.debug("Request to get ResourceType : {}", id);
        return resourceTypeRepository.findById(id);
    }

    /**
     * Delete the resourceType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResourceType : {}", id);
        resourceTypeRepository.deleteById(id);
    }
}
