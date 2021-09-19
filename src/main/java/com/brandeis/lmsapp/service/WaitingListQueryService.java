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

import com.brandeis.lmsapp.domain.WaitingList;
import com.brandeis.lmsapp.domain.*; // for static metamodels
import com.brandeis.lmsapp.repository.WaitingListRepository;
import com.brandeis.lmsapp.service.dto.WaitingListCriteria;

/**
 * Service for executing complex queries for WaitingList entities in the database.
 * The main input is a {@link WaitingListCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WaitingList} or a {@link Page} of {@link WaitingList} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WaitingListQueryService extends QueryService<WaitingList> {

    private final Logger log = LoggerFactory.getLogger(WaitingListQueryService.class);

    private final WaitingListRepository waitingListRepository;

    public WaitingListQueryService(WaitingListRepository waitingListRepository) {
        this.waitingListRepository = waitingListRepository;
    }

    /**
     * Return a {@link List} of {@link WaitingList} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WaitingList> findByCriteria(WaitingListCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WaitingList> specification = createSpecification(criteria);
        return waitingListRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WaitingList} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WaitingList> findByCriteria(WaitingListCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WaitingList> specification = createSpecification(criteria);
        return waitingListRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WaitingListCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WaitingList> specification = createSpecification(criteria);
        return waitingListRepository.count(specification);
    }

    /**
     * Function to convert WaitingListCriteria to a {@link Specification}
     */
    private Specification<WaitingList> createSpecification(WaitingListCriteria criteria) {
        Specification<WaitingList> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), WaitingList_.id));
            }
            if (criteria.getDateRequest() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRequest(), WaitingList_.dateRequest));
            }
            if (criteria.getRequested() != null) {
                specification = specification.and(buildSpecification(criteria.getRequested(), WaitingList_.requested));
            }
            if (criteria.getLibraryResourceId() != null) {
                specification = specification.and(buildSpecification(criteria.getLibraryResourceId(),
                    root -> root.join(WaitingList_.libraryResources, JoinType.LEFT).get(LibraryResource_.id)));
            }
            if (criteria.getPatronId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatronId(),
                    root -> root.join(WaitingList_.patrons, JoinType.LEFT).get(Patron_.id)));
            }
        }
        return specification;
    }
}
