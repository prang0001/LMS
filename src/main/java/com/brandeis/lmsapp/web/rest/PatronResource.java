package com.brandeis.lmsapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brandeis.lmsapp.domain.Patron;
import com.brandeis.lmsapp.service.PatronService;
import com.brandeis.lmsapp.web.rest.errors.BadRequestAlertException;
import com.brandeis.lmsapp.web.rest.util.HeaderUtil;
import com.brandeis.lmsapp.web.rest.util.PaginationUtil;
import com.brandeis.lmsapp.service.dto.PatronCriteria;
import com.brandeis.lmsapp.service.PatronQueryService;
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
 * REST controller for managing Patron.
 */
@RestController
@RequestMapping("/api")
public class PatronResource {

    private final Logger log = LoggerFactory.getLogger(PatronResource.class);

    private static final String ENTITY_NAME = "patron";

    private final PatronService patronService;

    private final PatronQueryService patronQueryService;

    public PatronResource(PatronService patronService, PatronQueryService patronQueryService) {
        this.patronService = patronService;
        this.patronQueryService = patronQueryService;
    }

    /**
     * POST  /patrons : Create a new patron.
     *
     * @param patron the patron to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patron, or with status 400 (Bad Request) if the patron has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patrons")
    @Timed
    public ResponseEntity<Patron> createPatron(@Valid @RequestBody Patron patron) throws URISyntaxException {
        log.debug("REST request to save Patron : {}", patron);
        if (patron.getId() != null) {
            throw new BadRequestAlertException("A new patron cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Patron result = patronService.save(patron);
        return ResponseEntity.created(new URI("/api/patrons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patrons : Updates an existing patron.
     *
     * @param patron the patron to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patron,
     * or with status 400 (Bad Request) if the patron is not valid,
     * or with status 500 (Internal Server Error) if the patron couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patrons")
    @Timed
    public ResponseEntity<Patron> updatePatron(@Valid @RequestBody Patron patron) throws URISyntaxException {
        log.debug("REST request to update Patron : {}", patron);
        if (patron.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Patron result = patronService.save(patron);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, patron.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patrons : get all the patrons.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of patrons in body
     */
    @GetMapping("/patrons")
    @Timed
    public ResponseEntity<List<Patron>> getAllPatrons(PatronCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Patrons by criteria: {}", criteria);
        Page<Patron> page = patronQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/patrons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /patrons/count : count all the patrons.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/patrons/count")
    @Timed
    public ResponseEntity<Long> countPatrons (PatronCriteria criteria) {
        log.debug("REST request to count Patrons by criteria: {}", criteria);
        return ResponseEntity.ok().body(patronQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /patrons/:id : get the "id" patron.
     *
     * @param id the id of the patron to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patron, or with status 404 (Not Found)
     */
    @GetMapping("/patrons/{id}")
    @Timed
    public ResponseEntity<Patron> getPatron(@PathVariable Long id) {
        log.debug("REST request to get Patron : {}", id);
        Optional<Patron> patron = patronService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patron);
    }

    /**
     * DELETE  /patrons/:id : delete the "id" patron.
     *
     * @param id the id of the patron to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patrons/{id}")
    @Timed
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        log.debug("REST request to delete Patron : {}", id);
        patronService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
