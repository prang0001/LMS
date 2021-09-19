package com.brandeis.lmsapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brandeis.lmsapp.domain.ResourceStatus;
import com.brandeis.lmsapp.service.ResourceStatusService;
import com.brandeis.lmsapp.web.rest.errors.BadRequestAlertException;
import com.brandeis.lmsapp.web.rest.util.HeaderUtil;
import com.brandeis.lmsapp.web.rest.util.PaginationUtil;
import com.brandeis.lmsapp.service.dto.ResourceStatusCriteria;
import com.brandeis.lmsapp.service.ResourceStatusQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ResourceStatus.
 */
@RestController
@RequestMapping("/api")
public class ResourceStatusResource {

    private final Logger log = LoggerFactory.getLogger(ResourceStatusResource.class);

    private static final String ENTITY_NAME = "resourceStatus";

    private final ResourceStatusService resourceStatusService;

    private final ResourceStatusQueryService resourceStatusQueryService;

    public ResourceStatusResource(ResourceStatusService resourceStatusService, ResourceStatusQueryService resourceStatusQueryService) {
        this.resourceStatusService = resourceStatusService;
        this.resourceStatusQueryService = resourceStatusQueryService;
    }

    /**
     * POST  /resource-statuses : Create a new resourceStatus.
     *
     * @param resourceStatus the resourceStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resourceStatus, or with status 400 (Bad Request) if the resourceStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resource-statuses")
    @Timed
    public ResponseEntity<ResourceStatus> createResourceStatus(@Valid @RequestBody ResourceStatus resourceStatus) throws URISyntaxException {
        log.debug("REST request to save ResourceStatus : {}", resourceStatus);
        if (resourceStatus.getId() != null) {
            throw new BadRequestAlertException("A new resourceStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResourceStatus result = resourceStatusService.save(resourceStatus);
        return ResponseEntity.created(new URI("/api/resource-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resource-statuses : Updates an existing resourceStatus.
     *
     * @param resourceStatus the resourceStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resourceStatus,
     * or with status 400 (Bad Request) if the resourceStatus is not valid,
     * or with status 500 (Internal Server Error) if the resourceStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resource-statuses")
    @Timed
    public ResponseEntity<ResourceStatus> updateResourceStatus(@Valid @RequestBody ResourceStatus resourceStatus) throws URISyntaxException {
        log.debug("REST request to update ResourceStatus : {}", resourceStatus);
        if (resourceStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResourceStatus result = resourceStatusService.save(resourceStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resourceStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resource-statuses : get all the resourceStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of resourceStatuses in body
     */
    @GetMapping("/resource-statuses")
    @Timed
    public ResponseEntity<List<ResourceStatus>> getAllResourceStatuses(ResourceStatusCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ResourceStatuses by criteria: {}", criteria);
        Page<ResourceStatus> page = resourceStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resource-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /resource-statuses/count : count all the resourceStatuses.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/resource-statuses/count")
    @Timed
    public ResponseEntity<Long> countResourceStatuses (ResourceStatusCriteria criteria) {
        log.debug("REST request to count ResourceStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(resourceStatusQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /resource-statuses/:id : get the "id" resourceStatus.
     *
     * @param id the id of the resourceStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resourceStatus, or with status 404 (Not Found)
     */
    @GetMapping("/resource-statuses/{id}")
    @Timed
    public ResponseEntity<ResourceStatus> getResourceStatus(@PathVariable Long id) {
        log.debug("REST request to get ResourceStatus : {}", id);
        Optional<ResourceStatus> resourceStatus = resourceStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resourceStatus);
    }

    /**
     * DELETE  /resource-statuses/:id : delete the "id" resourceStatus.
     *
     * @param id the id of the resourceStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resource-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteResourceStatus(@PathVariable Long id) {
        log.debug("REST request to delete ResourceStatus : {}", id);
        resourceStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/by-statuses")
    @Timed
    public ResponseEntity<List<ResourceStatus>> getAllStatuses1(Pageable pageable) {
        log.debug("REST request to get all Statuses");
        Page<ResourceStatus> page1;
        page1 = resourceStatusService.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page1, String.format("/api/by-statuses"));
        return new ResponseEntity<>(page1.getContent(), headers, HttpStatus.OK);
    }

}



