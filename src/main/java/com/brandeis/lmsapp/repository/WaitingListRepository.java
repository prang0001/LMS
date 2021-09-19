package com.brandeis.lmsapp.repository;

import com.brandeis.lmsapp.domain.WaitingList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the WaitingList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WaitingListRepository extends JpaRepository<WaitingList, Long>, JpaSpecificationExecutor<WaitingList> {

    @Query(value = "select distinct waiting_list from WaitingList waiting_list left join fetch waiting_list.libraryResources left join fetch waiting_list.patrons",
        countQuery = "select count(distinct waiting_list) from WaitingList waiting_list")
    Page<WaitingList> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct waiting_list from WaitingList waiting_list left join fetch waiting_list.libraryResources left join fetch waiting_list.patrons")
    List<WaitingList> findAllWithEagerRelationships();

    @Query("select waiting_list from WaitingList waiting_list left join fetch waiting_list.libraryResources left join fetch waiting_list.patrons where waiting_list.id =:id")
    Optional<WaitingList> findOneWithEagerRelationships(@Param("id") Long id);

}
