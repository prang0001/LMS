package com.brandeis.lmsapp.service.impl;

import com.brandeis.lmsapp.service.WaitingListService;
import com.brandeis.lmsapp.domain.WaitingList;
import com.brandeis.lmsapp.repository.WaitingListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing WaitingList.
 */
@Service
@Transactional
public class WaitingListServiceImpl implements WaitingListService {

    private final Logger log = LoggerFactory.getLogger(WaitingListServiceImpl.class);

    private final WaitingListRepository waitingListRepository;

    public WaitingListServiceImpl(WaitingListRepository waitingListRepository) {
        this.waitingListRepository = waitingListRepository;
    }

    /**
     * Save a waitingList.
     *
     * @param waitingList the entity to save
     * @return the persisted entity
     */
    @Override
    public WaitingList save(WaitingList waitingList) {
        log.debug("Request to save WaitingList : {}", waitingList);
        return waitingListRepository.save(waitingList);
    }

    /**
     * Get all the waitingLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WaitingList> findAll(Pageable pageable) {
        log.debug("Request to get all WaitingLists");
        return waitingListRepository.findAll(pageable);
    }

    /**
     * Get all the WaitingList with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<WaitingList> findAllWithEagerRelationships(Pageable pageable) {
        return waitingListRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one waitingList by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WaitingList> findOne(Long id) {
        log.debug("Request to get WaitingList : {}", id);
        return waitingListRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the waitingList by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WaitingList : {}", id);
        waitingListRepository.deleteById(id);
    }
}
