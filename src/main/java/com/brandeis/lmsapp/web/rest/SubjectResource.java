package com.brandeis.lmsapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brandeis.lmsapp.domain.Subject;
import com.brandeis.lmsapp.service.SubjectService;
import com.brandeis.lmsapp.web.rest.errors.BadRequestAlertException;
import com.brandeis.lmsapp.web.rest.util.HeaderUtil;
import com.brandeis.lmsapp.web.rest.util.PaginationUtil;
import com.brandeis.lmsapp.service.dto.SubjectCriteria;
import com.brandeis.lmsapp.service.SubjectQueryService;
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
 * REST controller for managing Subject.
 */
@RestController
@RequestMapping("/api")
public class SubjectResource {

    private final Logger log = LoggerFactory.getLogger(SubjectResource.class);

    private static final String ENTITY_NAME = "subject";

    private final SubjectService subjectService;

    private final SubjectQueryService subjectQueryService;

    public SubjectResource(SubjectService subjectService, SubjectQueryService subjectQueryService) {
        this.subjectService = subjectService;
        this.subjectQueryService = subjectQueryService;
    }

    /**
     * POST  /subjects : Create a new subject.
     *
     * @param subject the subject to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subject, or with status 400 (Bad Request) if the subject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/subjects")
    @Timed
    public ResponseEntity<Subject> createSubject(@Valid @RequestBody Subject subject) throws URISyntaxException {
        log.debug("REST request to save Subject : {}", subject);
        if (subject.getId() != null) {
            throw new BadRequestAlertException("A new subject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Subject result = subjectService.save(subject);
        return ResponseEntity.created(new URI("/api/subjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subjects : Updates an existing subject.
     *
     * @param subject the subject to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subject,
     * or with status 400 (Bad Request) if the subject is not valid,
     * or with status 500 (Internal Server Error) if the subject couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/subjects")
    @Timed
    public ResponseEntity<Subject> updateSubject(@Valid @RequestBody Subject subject) throws URISyntaxException {
        log.debug("REST request to update Subject : {}", subject);
        if (subject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Subject result = subjectService.save(subject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subjects : get all the subjects.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of subjects in body
     */
    @GetMapping("/subjects")
    @Timed
    public ResponseEntity<List<Subject>> getAllSubjects(SubjectCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Subjects by criteria: {}", criteria);
        Page<Subject> page = subjectQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subjects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /subjects/count : count all the subjects.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/subjects/count")
    @Timed
    public ResponseEntity<Long> countSubjects (SubjectCriteria criteria) {
        log.debug("REST request to count Subjects by criteria: {}", criteria);
        return ResponseEntity.ok().body(subjectQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /subjects/:id : get the "id" subject.
     *
     * @param id the id of the subject to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subject, or with status 404 (Not Found)
     */
    @GetMapping("/subjects/{id}")
    @Timed
    public ResponseEntity<Subject> getSubject(@PathVariable Long id) {
        log.debug("REST request to get Subject : {}", id);
        Optional<Subject> subject = subjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subject);
    }

    /**
     * DELETE  /subjects/:id : delete the "id" subject.
     *
     * @param id the id of the subject to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/subjects/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        log.debug("REST request to delete Subject : {}", id);
        subjectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
