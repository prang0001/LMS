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

import com.brandeis.lmsapp.domain.Patron;
import com.brandeis.lmsapp.domain.*; // for static metamodels
import com.brandeis.lmsapp.repository.PatronRepository;
import com.brandeis.lmsapp.service.dto.PatronCriteria;

/**
 * Service for executing complex queries for Patron entities in the database.
 * The main input is a {@link PatronCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Patron} or a {@link Page} of {@link Patron} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PatronQueryService extends QueryService<Patron> {

    private final Logger log = LoggerFactory.getLogger(PatronQueryService.class);

    private final PatronRepository patronRepository;

    public PatronQueryService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    /**
     * Return a {@link List} of {@link Patron} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Patron> findByCriteria(PatronCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Patron> specification = createSpecification(criteria);
        return patronRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Patron} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Patron> findByCriteria(PatronCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Patron> specification = createSpecification(criteria);
        return patronRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PatronCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Patron> specification = createSpecification(criteria);
        return patronRepository.count(specification);
    }

    /**
     * Function to convert PatronCriteria to a {@link Specification}
     */
    private Specification<Patron> createSpecification(PatronCriteria criteria) {
        Specification<Patron> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Patron_.id));
            }
            if (criteria.getPatronStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatronStatus(), Patron_.patronStatus));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Patron_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), Patron_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Patron_.lastName));
            }
            if (criteria.getStreetAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreetAddress(), Patron_.streetAddress));
            }
            if (criteria.getMailingAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMailingAddress(), Patron_.mailingAddress));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Patron_.email));
            }
            if (criteria.getPhoneNum1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNum1(), Patron_.phoneNum1));
            }
            if (criteria.getPhoneNum2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNum2(), Patron_.phoneNum2));
            }
            if (criteria.getPhoneNum3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNum3(), Patron_.phoneNum3));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), Patron_.login));
            }
            if (criteria.getRentalTransactionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRentalTransactionId(),
                    root -> root.join(Patron_.rentalTransactions, JoinType.LEFT).get(RentalTransaction_.id)));
            }
            if (criteria.getWaitingListId() != null) {
                specification = specification.and(buildSpecification(criteria.getWaitingListId(),
                    root -> root.join(Patron_.waitingLists, JoinType.LEFT).get(WaitingList_.id)));
            }
        }
        return specification;
    }
}
