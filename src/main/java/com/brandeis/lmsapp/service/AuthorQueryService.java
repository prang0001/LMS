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

import com.brandeis.lmsapp.domain.Author;
import com.brandeis.lmsapp.domain.*; // for static metamodels
import com.brandeis.lmsapp.repository.AuthorRepository;
import com.brandeis.lmsapp.service.dto.AuthorCriteria;

/**
 * Service for executing complex queries for Author entities in the database.
 * The main input is a {@link AuthorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Author} or a {@link Page} of {@link Author} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AuthorQueryService extends QueryService<Author> {

    private final Logger log = LoggerFactory.getLogger(AuthorQueryService.class);

    private final AuthorRepository authorRepository;

    public AuthorQueryService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Return a {@link List} of {@link Author} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Author> findByCriteria(AuthorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Author> specification = createSpecification(criteria);
        return authorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Author} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Author> findByCriteria(AuthorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Author> specification = createSpecification(criteria);
        return authorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AuthorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Author> specification = createSpecification(criteria);
        return authorRepository.count(specification);
    }

    /**
     * Function to convert AuthorCriteria to a {@link Specification}
     */
    private Specification<Author> createSpecification(AuthorCriteria criteria) {
        Specification<Author> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Author_.id));
            }
            if (criteria.getAuthorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthorName(), Author_.authorName));
            }
            if (criteria.getLibraryResourceId() != null) {
                specification = specification.and(buildSpecification(criteria.getLibraryResourceId(),
                    root -> root.join(Author_.libraryResources, JoinType.LEFT).get(LibraryResource_.id)));
            }
        }
        return specification;
    }
}
