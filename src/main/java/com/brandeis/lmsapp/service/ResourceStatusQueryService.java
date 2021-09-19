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

import com.brandeis.lmsapp.domain.ResourceStatus;
import com.brandeis.lmsapp.domain.*; // for static metamodels
import com.brandeis.lmsapp.repository.ResourceStatusRepository;
import com.brandeis.lmsapp.service.dto.ResourceStatusCriteria;

/**
 * Service for executing complex queries for ResourceStatus entities in the database.
 * The main input is a {@link ResourceStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResourceStatus} or a {@link Page} of {@link ResourceStatus} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResourceStatusQueryService extends QueryService<ResourceStatus> {

    private final Logger log = LoggerFactory.getLogger(ResourceStatusQueryService.class);

    private final ResourceStatusRepository resourceStatusRepository;

    public ResourceStatusQueryService(ResourceStatusRepository resourceStatusRepository) {
        this.resourceStatusRepository = resourceStatusRepository;
    }

    /**
     * Return a {@link List} of {@link ResourceStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResourceStatus> findByCriteria(ResourceStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResourceStatus> specification = createSpecification(criteria);
        return resourceStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ResourceStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResourceStatus> findByCriteria(ResourceStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResourceStatus> specification = createSpecification(criteria);
        return resourceStatusRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResourceStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResourceStatus> specification = createSpecification(criteria);
        return resourceStatusRepository.count(specification);
    }

    /**
     * Function to convert ResourceStatusCriteria to a {@link Specification}
     */
    private Specification<ResourceStatus> createSpecification(ResourceStatusCriteria criteria) {
        Specification<ResourceStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ResourceStatus_.id));
            }
            if (criteria.getStatusName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusName(), ResourceStatus_.statusName));
            }
            if (criteria.getLibraryResourceId() != null) {
                specification = specification.and(buildSpecification(criteria.getLibraryResourceId(),
                    root -> root.join(ResourceStatus_.libraryResources, JoinType.LEFT).get(LibraryResource_.id)));
            }
        }
        return specification;
    }
}
