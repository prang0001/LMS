package com.brandeis.lmsapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brandeis.lmsapp.domain.LibraryResource;
import com.brandeis.lmsapp.service.LibraryResourceService;
import com.brandeis.lmsapp.web.rest.errors.BadRequestAlertException;
import com.brandeis.lmsapp.web.rest.util.HeaderUtil;
import com.brandeis.lmsapp.web.rest.util.PaginationUtil;
import com.brandeis.lmsapp.service.dto.LibraryResourceCriteria;
import com.brandeis.lmsapp.service.LibraryResourceQueryService;
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
 * REST controller for managing LibraryResource.
 */
@RestController
@RequestMapping("/api")
public class LibraryResourceResource {

    private final Logger log = LoggerFactory.getLogger(LibraryResourceResource.class);

    private static final String ENTITY_NAME = "libraryResource";

    private final LibraryResourceService libraryResourceService;

    private final LibraryResourceQueryService libraryResourceQueryService;

    public LibraryResourceResource(LibraryResourceService libraryResourceService, LibraryResourceQueryService libraryResourceQueryService) {
        this.libraryResourceService = libraryResourceService;
        this.libraryResourceQueryService = libraryResourceQueryService;
    }

    /**
     * POST  /library-resources : Create a new libraryResource.
     *
     * @param libraryResource the libraryResource to create
     * @return the ResponseEntity with status 201 (Created) and with body the new libraryResource, or with status 400 (Bad Request) if the libraryResource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/library-resources")
    @Timed
    public ResponseEntity<LibraryResource> createLibraryResource(@Valid @RequestBody LibraryResource libraryResource) throws URISyntaxException {
        log.debug("REST request to save LibraryResource : {}", libraryResource);
        if (libraryResource.getId() != null) {
            throw new BadRequestAlertException("A new libraryResource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LibraryResource result = libraryResourceService.save(libraryResource);
        return ResponseEntity.created(new URI("/api/library-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /library-resources : Updates an existing libraryResource.
     *
     * @param libraryResource the libraryResource to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated libraryResource,
     * or with status 400 (Bad Request) if the libraryResource is not valid,
     * or with status 500 (Internal Server Error) if the libraryResource couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/library-resources")
    @Timed
    public ResponseEntity<LibraryResource> updateLibraryResource(@Valid @RequestBody LibraryResource libraryResource) throws URISyntaxException {
        log.debug("REST request to update LibraryResource : {}", libraryResource);
        if (libraryResource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LibraryResource result = libraryResourceService.save(libraryResource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, libraryResource.getId().toString()))
            .body(result);
    }

    /**
     * GET  /library-resources : get all the libraryResources.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of libraryResources in body
     */
    @GetMapping("/library-resources")
    @Timed
    public ResponseEntity<List<LibraryResource>> getAllLibraryResources(LibraryResourceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LibraryResources by criteria: {}", criteria);
        Page<LibraryResource> page = libraryResourceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/library-resources");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /library-resources/count : count all the libraryResources.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/library-resources/count")
    @Timed
    public ResponseEntity<Long> countLibraryResources (LibraryResourceCriteria criteria) {
        log.debug("REST request to count LibraryResources by criteria: {}", criteria);
        return ResponseEntity.ok().body(libraryResourceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /library-resources/:id : get the "id" libraryResource.
     *
     * @param id the id of the libraryResource to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the libraryResource, or with status 404 (Not Found)
     */
    @GetMapping("/library-resources/{id}")
    @Timed
    public ResponseEntity<LibraryResource> getLibraryResource(@PathVariable Long id) {
        log.debug("REST request to get LibraryResource : {}", id);
        Optional<LibraryResource> libraryResource = libraryResourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(libraryResource);
    }

    /**
     * DELETE  /library-resources/:id : delete the "id" libraryResource.
     *
     * @param id the id of the libraryResource to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/library-resources/{id}")
    @Timed
    public ResponseEntity<Void> deleteLibraryResource(@PathVariable Long id) {
        log.debug("REST request to delete LibraryResource : {}", id);
        libraryResourceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/by-statuses/{id}")
    @Timed
    public ResponseEntity<List<LibraryResource>> getAllLibraryResourcesByStatusId(@PathVariable Long id, Pageable pageable){
        log.debug("REST request to get a page of LibraryResources by Status Id: {}", id);
        Page<LibraryResource> page1;
        page1 = libraryResourceService.findByStatusId(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page1, String.format("/api/by-statuses/" + id));
        return new ResponseEntity<>(page1.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/by-resource-types/{id}")
    @Timed
    public ResponseEntity<List<LibraryResource>> getAllLibraryResourcesByTypeId(@PathVariable Long id, Pageable pageable){
        log.debug("REST request to get a page of LibraryResources by Resource Type Id: {}", id);
        Page<LibraryResource> page1;
        page1 = libraryResourceService.findByTypeId(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page1, String.format("/api/by-resource-types/" + id));
        return new ResponseEntity<>(page1.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/by-waiting-lists/{id}")
    @Timed
    public ResponseEntity<List<LibraryResource>> getAllLibraryResourcesByWaitingListId(@PathVariable Long id, Pageable pageable){
        log.debug("REST request to get a page of LibraryResources by Waiting List Id: {}", id);
        Page<LibraryResource> page1;
        page1 = libraryResourceService.findByWaitingListId(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page1, String.format("/api/by-waiting-lists/" + id));
        return new ResponseEntity<>(page1.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/by-rental-transactions/{id}")
    @Timed
    public ResponseEntity<List<LibraryResource>> getAllLibraryResourcesByRentalId(@PathVariable Long id, Pageable pageable){
        log.debug("REST request to get a page of LibraryResources by Rental Id: {}", id);
        Page<LibraryResource> page1;
        page1 = libraryResourceService.findByRentalId(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page1, String.format("/api/by-rental-transactions/" + id));
        return new ResponseEntity<>(page1.getContent(), headers, HttpStatus.OK);
    }

}

