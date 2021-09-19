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

import com.brandeis.lmsapp.domain.LibraryResource;
import com.brandeis.lmsapp.domain.*; // for static metamodels
import com.brandeis.lmsapp.repository.LibraryResourceRepository;
import com.brandeis.lmsapp.service.dto.LibraryResourceCriteria;

/**
 * Service for executing complex queries for LibraryResource entities in the database.
 * The main input is a {@link LibraryResourceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LibraryResource} or a {@link Page} of {@link LibraryResource} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LibraryResourceQueryService extends QueryService<LibraryResource> {

    private final Logger log = LoggerFactory.getLogger(LibraryResourceQueryService.class);

    private final LibraryResourceRepository libraryResourceRepository;

    public LibraryResourceQueryService(LibraryResourceRepository libraryResourceRepository) {
        this.libraryResourceRepository = libraryResourceRepository;
    }

    /**
     * Return a {@link List} of {@link LibraryResource} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LibraryResource> findByCriteria(LibraryResourceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LibraryResource> specification = createSpecification(criteria);
        return libraryResourceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LibraryResource} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LibraryResource> findByCriteria(LibraryResourceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LibraryResource> specification = createSpecification(criteria);
        return libraryResourceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LibraryResourceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LibraryResource> specification = createSpecification(criteria);
        return libraryResourceRepository.count(specification);
    }

    /**
     * Function to convert LibraryResourceCriteria to a {@link Specification}
     */
    private Specification<LibraryResource> createSpecification(LibraryResourceCriteria criteria) {
        Specification<LibraryResource> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LibraryResource_.id));
            }
            if (criteria.getResourceTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResourceTitle(), LibraryResource_.resourceTitle));
            }
            if (criteria.getResourceDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResourceDescription(), LibraryResource_.resourceDescription));
            }
            if (criteria.getCallNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCallNumber(), LibraryResource_.callNumber));
            }
            if (criteria.getAuthorId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuthorId(),
                    root -> root.join(LibraryResource_.author, JoinType.LEFT).get(Author_.id)));
            }
            if (criteria.getSubjectId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubjectId(),
                    root -> root.join(LibraryResource_.subject, JoinType.LEFT).get(Subject_.id)));
            }
            if (criteria.getResourceStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getResourceStatusId(),
                    root -> root.join(LibraryResource_.resourceStatus, JoinType.LEFT).get(ResourceStatus_.id)));
            }
            if (criteria.getResourceTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getResourceTypeId(),
                    root -> root.join(LibraryResource_.resourceType, JoinType.LEFT).get(ResourceType_.id)));
            }
            if (criteria.getRentalTransactionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRentalTransactionId(),
                    root -> root.join(LibraryResource_.rentalTransactions, JoinType.LEFT).get(RentalTransaction_.id)));
            }
            if (criteria.getWaitingListId() != null) {
                specification = specification.and(buildSpecification(criteria.getWaitingListId(),
                    root -> root.join(LibraryResource_.waitingLists, JoinType.LEFT).get(WaitingList_.id)));
            }
        }
        return specification;
    }
}
