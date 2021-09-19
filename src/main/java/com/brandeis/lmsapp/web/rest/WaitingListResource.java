package com.brandeis.lmsapp.web.rest;

import com.brandeis.lmsapp.domain.ResourceStatus;
import com.codahale.metrics.annotation.Timed;
import com.brandeis.lmsapp.domain.WaitingList;
import com.brandeis.lmsapp.service.WaitingListService;
import com.brandeis.lmsapp.web.rest.errors.BadRequestAlertException;
import com.brandeis.lmsapp.web.rest.util.HeaderUtil;
import com.brandeis.lmsapp.web.rest.util.PaginationUtil;
import com.brandeis.lmsapp.service.dto.WaitingListCriteria;
import com.brandeis.lmsapp.service.WaitingListQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WaitingList.
 */
@RestController
@RequestMapping("/api")
public class WaitingListResource {

    private final Logger log = LoggerFactory.getLogger(WaitingListResource.class);

    private static final String ENTITY_NAME = "waitingList";

    private final WaitingListService waitingListService;

    private final WaitingListQueryService waitingListQueryService;

    public WaitingListResource(WaitingListService waitingListService, WaitingListQueryService waitingListQueryService) {
        this.waitingListService = waitingListService;
        this.waitingListQueryService = waitingListQueryService;
    }

    /**
     * POST  /waiting-lists : Create a new waitingList.
     *
     * @param waitingList the waitingList to create
     * @return the ResponseEntity with status 201 (Created) and with body the new waitingList, or with status 400 (Bad Request) if the waitingList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/waiting-lists")
    @Timed
    public ResponseEntity<WaitingList> createWaitingList(@RequestBody WaitingList waitingList) throws URISyntaxException {
        log.debug("REST request to save WaitingList : {}", waitingList);
        if (waitingList.getId() != null) {
            throw new BadRequestAlertException("A new waitingList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WaitingList result = waitingListService.save(waitingList);
        return ResponseEntity.created(new URI("/api/waiting-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /waiting-lists : Updates an existing waitingList.
     *
     * @param waitingList the waitingList to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated waitingList,
     * or with status 400 (Bad Request) if the waitingList is not valid,
     * or with status 500 (Internal Server Error) if the waitingList couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/waiting-lists")
    @Timed
    public ResponseEntity<WaitingList> updateWaitingList(@RequestBody WaitingList waitingList) throws URISyntaxException {
        log.debug("REST request to update WaitingList : {}", waitingList);
        if (waitingList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WaitingList result = waitingListService.save(waitingList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, waitingList.getId().toString()))
            .body(result);
    }

    /**
     * GET  /waiting-lists : get all the waitingLists.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of waitingLists in body
     */
    @GetMapping("/waiting-lists")
    @Timed
    public ResponseEntity<List<WaitingList>> getAllWaitingLists(WaitingListCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WaitingLists by criteria: {}", criteria);
        Page<WaitingList> page = waitingListQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/waiting-lists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /waiting-lists/count : count all the waitingLists.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/waiting-lists/count")
    @Timed
    public ResponseEntity<Long> countWaitingLists (WaitingListCriteria criteria) {
        log.debug("REST request to count WaitingLists by criteria: {}", criteria);
        return ResponseEntity.ok().body(waitingListQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /waiting-lists/:id : get the "id" waitingList.
     *
     * @param id the id of the waitingList to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the waitingList, or with status 404 (Not Found)
     */
    @GetMapping("/waiting-lists/{id}")
    @Timed
    public ResponseEntity<WaitingList> getWaitingList(@PathVariable Long id) {
        log.debug("REST request to get WaitingList : {}", id);
        Optional<WaitingList> waitingList = waitingListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(waitingList);
    }

    /**
     * DELETE  /waiting-lists/:id : delete the "id" waitingList.
     *
     * @param id the id of the waitingList to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/waiting-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteWaitingList(@PathVariable Long id) {
        log.debug("REST request to delete WaitingList : {}", id);
        waitingListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/by-waiting-lists")
    @Timed
    public ResponseEntity<List<WaitingList>> getAllWaitingLists1(Pageable pageable) {
        log.debug("REST request to get all Waiting List IDs");
        Page<WaitingList> page1;
        page1 = waitingListService.findAllWithEagerRelationships(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page1, String.format("/api/by-waiting-lists"));
        return new ResponseEntity<>(page1.getContent(), headers, HttpStatus.OK);
    }
}
