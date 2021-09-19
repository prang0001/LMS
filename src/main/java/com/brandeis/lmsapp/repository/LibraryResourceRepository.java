package com.brandeis.lmsapp.repository;

import com.brandeis.lmsapp.domain.LibraryResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Spring Data  repository for the LibraryResource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LibraryResourceRepository extends JpaRepository<LibraryResource, Long>, JpaSpecificationExecutor<LibraryResource> {

    @Query("select l from LibraryResource l left join l.resourceStatus s where s.id =:id")
    Page<LibraryResource> findByStatusId(@Param("id") Long id, Pageable pageable);

    @Query("select l from LibraryResource l left join l.resourceType s where s.id =:id")
    Page<LibraryResource> findByTypeId(@Param("id") Long id, Pageable pageable);

    @Query("select l from LibraryResource l left join l.rentalTransactions s where s.id =:id")
    Page<LibraryResource> findByRentalId(@Param("id") Long id, Pageable pageable);

    @Query("select l from LibraryResource l left join l.waitingLists s where s.id =:id")
    Page<LibraryResource> findByWaitingListId(@Param("id") Long id, Pageable pageable);

    Page<LibraryResource> findByResourceType(Pageable pageable);

}
