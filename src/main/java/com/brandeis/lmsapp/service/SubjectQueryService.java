package com.brandeis.lmsapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.brandeis.lmsapp.domain.Subject;
import com.brandeis.lmsapp.domain.*; // for static metamodels
import com.brandeis.lmsapp.repository.SubjectRepository;
import com.brandeis.lmsapp.service.dto.SubjectCriteria;

/**
 * Service for executing complex queries for Subject entities in the database.
 * The main input is a {@link SubjectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Subject} or a {@link Page} of {@link Subject} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubjectQueryService extends QueryService<Subject> {

    private final Logger log = LoggerFactory.getLogger(SubjectQueryService.class);

    private final SubjectRepository subjectRepository;

    public SubjectQueryService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    /**
     * Return a {@link List} of {@link Subject} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Subject> findByCriteria(SubjectCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Subject> specification = createSpecification(criteria);
        return subjectRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Subject} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Subject> findByCriteria(SubjectCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Subject> specification = createSpecification(criteria);
        return subjectRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubjectCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Subject> specification = createSpecification(criteria);
        return subjectRepository.count(specification);
    }

    /**
     * Function to convert SubjectCriteria to a {@link Specification}
     */
    private Specification<Subject> createSpecification(SubjectCriteria criteria) {
        Specification<Subject> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Subject_.id));
            }
            if (criteria.getSubjectName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubjectName(), Subject_.subjectName));
            }
            if (criteria.getLibraryResourceId() != null) {
                specification = specification.and(buildSpecification(criteria.getLibraryResourceId(),
                    root -> root.join(Subject_.libraryResources, JoinType.LEFT).get(LibraryResource_.id)));
            }
        }
        return specification;
    }
}
