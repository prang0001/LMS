package com.brandeis.lmsapp.repository;

import com.brandeis.lmsapp.domain.Patron;
import com.brandeis.lmsapp.domain.RentalTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RentalTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentalTransactionRepository extends JpaRepository<RentalTransaction, Long>, JpaSpecificationExecutor<RentalTransaction> {

    @Query(value = "select distinct rental_transaction from RentalTransaction rental_transaction left join fetch rental_transaction.libraryResources left join fetch rental_transaction.patrons",
        countQuery = "select count(distinct rental_transaction) from RentalTransaction rental_transaction")
    Page<RentalTransaction> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct rental_transaction from RentalTransaction rental_transaction left join fetch rental_transaction.libraryResources left join fetch rental_transaction.patrons")
    List<RentalTransaction> findAllWithEagerRelationships();

    @Query("select rental_transaction from RentalTransaction rental_transaction left join fetch rental_transaction.libraryResources left join fetch rental_transaction.patrons where rental_transaction.id =:id")
    Optional<RentalTransaction> findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "select p.email from rental_transaction r" +
        " join rental_transaction_library_resource lr on lr.rental_transactions_id = r.id " +
        " join library_resource l on l.id = lr.library_resources_id" +
        " join rental_transaction_patron pr on pr.rental_transactions_id = r.id" +
        " join patron p on p.id = pr.patrons_id" +
        " where r.rental_due_date<sysdate()",  nativeQuery=true)
    List<String> findOverdueRentals();

    @Query(value = "select r.* from rental_transaction r" +
        " join rental_transaction_library_resource lr on lr.rental_transactions_id = r.id " +
        " join library_resource l on l.id = lr.library_resources_id" +
        " join rental_transaction_patron pr on pr.rental_transactions_id = r.id" +
        " join patron p on p.id = pr.patrons_id" +
        " where p.login = ?",  nativeQuery=true)
    List<RentalTransaction> findByPatron(@Param("login")String login);

}
