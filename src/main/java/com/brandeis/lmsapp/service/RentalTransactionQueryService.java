package com.brandeis.lmsapp.service;

import java.util.ArrayList;
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

import com.brandeis.lmsapp.domain.RentalTransaction;
import com.brandeis.lmsapp.domain.*; // for static metamodels
import com.brandeis.lmsapp.repository.RentalTransactionRepository;
import com.brandeis.lmsapp.service.dto.RentalTransactionCriteria;

/**
 * Service for executing complex queries for RentalTransaction entities in the database.
 * The main input is a {@link RentalTransactionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RentalTransaction} or a {@link Page} of {@link RentalTransaction} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RentalTransactionQueryService extends QueryService<RentalTransaction> {

    private final Logger log = LoggerFactory.getLogger(RentalTransactionQueryService.class);

    private final RentalTransactionRepository rentalTransactionRepository;

    public RentalTransactionQueryService(RentalTransactionRepository rentalTransactionRepository) {
        this.rentalTransactionRepository = rentalTransactionRepository;
    }

    /**
     * Return a {@link List} of {@link RentalTransaction} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RentalTransaction> findByCriteria(RentalTransactionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RentalTransaction> specification = createSpecification(criteria);
        return rentalTransactionRepository.findAll(specification);
    }

    @Transactional(readOnly = true)
    public List<RentalTransaction> findByPatron(String login) {
        log.debug("find by patron : {}", login);
        List<RentalTransaction> rtl = new ArrayList<>();
        rtl = rentalTransactionRepository.findByPatron(login);
        return rtl;
    }

    /**
     * Return a {@link Page} of {@link RentalTransaction} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RentalTransaction> findByCriteria(RentalTransactionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RentalTransaction> specification = createSpecification(criteria);
        return rentalTransactionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RentalTransactionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RentalTransaction> specification = createSpecification(criteria);
        return rentalTransactionRepository.count(specification);
    }

    /**
     * Function to convert RentalTransactionCriteria to a {@link Specification}
     */
    private Specification<RentalTransaction> createSpecification(RentalTransactionCriteria criteria) {
        Specification<RentalTransaction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RentalTransaction_.id));
            }
            if (criteria.getRentalPeriod() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRentalPeriod(), RentalTransaction_.rentalPeriod));
            }
            if (criteria.getRentalStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRentalStartDate(), RentalTransaction_.rentalStartDate));
            }
            if (criteria.getRentalDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRentalDueDate(), RentalTransaction_.rentalDueDate));
            }
            if (criteria.getExtendRental() != null) {
                specification = specification.and(buildSpecification(criteria.getExtendRental(), RentalTransaction_.extendRental));
            }
            if (criteria.getRentalFinalDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRentalFinalDueDate(), RentalTransaction_.rentalFinalDueDate));
            }
            if (criteria.getOverdue() != null) {
                specification = specification.and(buildSpecification(criteria.getOverdue(), RentalTransaction_.overdue));
            }
            if (criteria.getDaysOverdue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDaysOverdue(), RentalTransaction_.daysOverdue));
            }
            if (criteria.getFinesOverdue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinesOverdue(), RentalTransaction_.finesOverdue));
            }
            if (criteria.getLibraryResourceId() != null) {
                specification = specification.and(buildSpecification(criteria.getLibraryResourceId(),
                    root -> root.join(RentalTransaction_.libraryResources, JoinType.LEFT).get(LibraryResource_.id)));
            }
            if (criteria.getPatronId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatronId(),
                    root -> root.join(RentalTransaction_.patrons, JoinType.LEFT).get(Patron_.id)));
            }
        }
        return specification;
    }
}
