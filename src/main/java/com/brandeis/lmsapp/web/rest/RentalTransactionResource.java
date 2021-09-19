package com.brandeis.lmsapp.web.rest;

import com.brandeis.lmsapp.domain.Patron;
import com.brandeis.lmsapp.domain.ResourceStatus;
import com.codahale.metrics.annotation.Timed;
import com.brandeis.lmsapp.domain.RentalTransaction;
import com.brandeis.lmsapp.service.RentalTransactionService;
import com.brandeis.lmsapp.web.rest.errors.BadRequestAlertException;
import com.brandeis.lmsapp.web.rest.util.HeaderUtil;
import com.brandeis.lmsapp.web.rest.util.PaginationUtil;
import com.brandeis.lmsapp.service.dto.RentalTransactionCriteria;
import com.brandeis.lmsapp.service.RentalTransactionQueryService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RentalTransaction.
 */
@RestController
@RequestMapping("/api")
public class RentalTransactionResource {

    private final Logger log = LoggerFactory.getLogger(RentalTransactionResource.class);

    private static final String ENTITY_NAME = "rentalTransaction";

    private final RentalTransactionService rentalTransactionService;

    private final RentalTransactionQueryService rentalTransactionQueryService;

    public RentalTransactionResource(RentalTransactionService rentalTransactionService, RentalTransactionQueryService rentalTransactionQueryService) {
        this.rentalTransactionService = rentalTransactionService;
        this.rentalTransactionQueryService = rentalTransactionQueryService;
    }

    /**
     * POST  /rental-transactions : Create a new rentalTransaction.
     *
     * @param rentalTransaction the rentalTransaction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rentalTransaction, or with status 400 (Bad Request) if the rentalTransaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rental-transactions")
    @Timed
    public ResponseEntity<RentalTransaction> createRentalTransaction(@RequestBody RentalTransaction rentalTransaction) throws URISyntaxException {
        log.debug("REST request to save RentalTransaction : {}", rentalTransaction);
        if (rentalTransaction.getId() != null) {
            throw new BadRequestAlertException("A new rentalTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RentalTransaction result = rentalTransactionService.save(rentalTransaction);
        return ResponseEntity.created(new URI("/api/rental-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rental-transactions : Updates an existing rentalTransaction.
     *
     * @param rentalTransaction the rentalTransaction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rentalTransaction,
     * or with status 400 (Bad Request) if the rentalTransaction is not valid,
     * or with status 500 (Internal Server Error) if the rentalTransaction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rental-transactions")
    @Timed
    public ResponseEntity<RentalTransaction> updateRentalTransaction(@RequestBody RentalTransaction rentalTransaction) throws URISyntaxException {
        log.debug("REST request to update RentalTransaction : {}", rentalTransaction);
        if (rentalTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RentalTransaction result = rentalTransactionService.save(rentalTransaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rentalTransaction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rental-transactions : get all the rentalTransactions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of rentalTransactions in body
     */
    @GetMapping("/rental-transactions")
    @Timed
    public ResponseEntity<List<RentalTransaction>> getAllRentalTransactions(RentalTransactionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RentalTransactions by criteria: {}", criteria);
        Page<RentalTransaction> page = rentalTransactionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rental-transactions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/rental-transactions-for-user")
    @Timed
    public ResponseEntity<List<RentalTransaction>> getAllRentalTransactionsForUser(@RequestParam(value="login") String login) {
        log.debug("REST request to get RentalTransactions for a patron login: {}", login);
        List<RentalTransaction> rtl = new ArrayList<>();
        rtl = rentalTransactionQueryService.findByPatron(login);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(null, "/api/rental-transactions-for-user");
        return new ResponseEntity(rtl, HttpStatus.OK);
    }

    /**
    * GET  /rental-transactions/count : count all the rentalTransactions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/rental-transactions/count")
    @Timed
    public ResponseEntity<Long> countRentalTransactions (RentalTransactionCriteria criteria) {
        log.debug("REST request to count RentalTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(rentalTransactionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /rental-transactions/:id : get the "id" rentalTransaction.
     *
     * @param id the id of the rentalTransaction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rentalTransaction, or with status 404 (Not Found)
     */
    @GetMapping("/rental-transactions/{id}")
    @Timed
    public ResponseEntity<RentalTransaction> getRentalTransaction(@PathVariable Long id) {
        log.debug("REST request to get RentalTransaction : {}", id);
        Optional<RentalTransaction> rentalTransaction = rentalTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rentalTransaction);
    }

    /**
     * DELETE  /rental-transactions/:id : delete the "id" rentalTransaction.
     *
     * @param id the id of the rentalTransaction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rental-transactions/{id}")
    @Timed
    public ResponseEntity<Void> deleteRentalTransaction(@PathVariable Long id) {
        log.debug("REST request to delete RentalTransaction : {}", id);
        rentalTransactionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/by-rental-transactions")
    @Timed
    public ResponseEntity<List<RentalTransaction>> getAllRentalTransactions1(Pageable pageable) {
        log.debug("REST request to get all Statuses");
        Page<RentalTransaction> page1;
        page1 = rentalTransactionService.findAllWithEagerRelationships(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page1, String.format("/api/by-rental-transactions"));
        return new ResponseEntity<>(page1.getContent(), headers, HttpStatus.OK);
    }
}
