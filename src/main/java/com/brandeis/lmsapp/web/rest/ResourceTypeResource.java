package com.brandeis.lmsapp.web.rest;

import com.brandeis.lmsapp.domain.ResourceStatus;
import com.codahale.metrics.annotation.Timed;
import com.brandeis.lmsapp.domain.ResourceType;
import com.brandeis.lmsapp.service.ResourceTypeService;
import com.brandeis.lmsapp.web.rest.errors.BadRequestAlertException;
import com.brandeis.lmsapp.web.rest.util.HeaderUtil;
import com.brandeis.lmsapp.web.rest.util.PaginationUtil;
import com.brandeis.lmsapp.service.dto.ResourceTypeCriteria;
import com.brandeis.lmsapp.service.ResourceTypeQueryService;
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
 * REST controller for managing ResourceType.
 */
@RestController
@RequestMapping("/api")
public class ResourceTypeResource {

    private final Logger log = LoggerFactory.getLogger(ResourceTypeResource.class);

    private static final String ENTITY_NAME = "resourceType";

    private final ResourceTypeService resourceTypeService;

    private final ResourceTypeQueryService resourceTypeQueryService;

    public ResourceTypeResource(ResourceTypeService resourceTypeService, ResourceTypeQueryService resourceTypeQueryService) {
        this.resourceTypeService = resourceTypeService;
        this.resourceTypeQueryService = resourceTypeQueryService;
    }

    /**
     * POST  /resource-types : Create a new resourceType.
     *
     * @param resourceType the resourceType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resourceType, or with status 400 (Bad Request) if the resourceType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resource-types")
    @Timed
    public ResponseEntity<ResourceType> createResourceType(@Valid @RequestBody ResourceType resourceType) throws URISyntaxException {
        log.debug("REST request to save ResourceType : {}", resourceType);
        if (resourceType.getId() != null) {
            throw new BadRequestAlertException("A new resourceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResourceType result = resourceTypeService.save(resourceType);
        return ResponseEntity.created(new URI("/api/resource-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resource-types : Updates an existing resourceType.
     *
     * @param resourceType the resourceType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resourceType,
     * or with status 400 (Bad Request) if the resourceType is not valid,
     * or with status 500 (Internal Server Error) if the resourceType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resource-types")
    @Timed
    public ResponseEntity<ResourceType> updateResourceType(@Valid @RequestBody ResourceType resourceType) throws URISyntaxException {
        log.debug("REST request to update ResourceType : {}", resourceType);
        if (resourceType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResourceType result = resourceTypeService.save(resourceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resourceType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resource-types : get all the resourceTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of resourceTypes in body
     */
    @GetMapping("/resource-types")
    @Timed
    public ResponseEntity<List<ResourceType>> getAllResourceTypes(ResourceTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ResourceTypes by criteria: {}", criteria);
        Page<ResourceType> page = resourceTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resource-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /resource-types/count : count all the resourceTypes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/resource-types/count")
    @Timed
    public ResponseEntity<Long> countResourceTypes (ResourceTypeCriteria criteria) {
        log.debug("REST request to count ResourceTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(resourceTypeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /resource-types/:id : get the "id" resourceType.
     *
     * @param id the id of the resourceType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resourceType, or with status 404 (Not Found)
     */
    @GetMapping("/resource-types/{id}")
    @Timed
    public ResponseEntity<ResourceType> getResourceType(@PathVariable Long id) {
        log.debug("REST request to get ResourceType : {}", id);
        Optional<ResourceType> resourceType = resourceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resourceType);
    }

    /**
     * DELETE  /resource-types/:id : delete the "id" resourceType.
     *
     * @param id the id of the resourceType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resource-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteResourceType(@PathVariable Long id) {
        log.debug("REST request to delete ResourceType : {}", id);
        resourceTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/by-resource-types")
    @Timed
    public ResponseEntity<List<ResourceType>> getAllResourceTypes1(Pageable pageable) {
        log.debug("REST request to get all Statuses");
        Page<ResourceType> page1;
        page1 = resourceTypeService.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page1, String.format("/api/by-resource-types"));
        return new ResponseEntity<>(page1.getContent(), headers, HttpStatus.OK);
    }
}
