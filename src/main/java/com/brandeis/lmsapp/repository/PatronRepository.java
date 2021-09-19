package com.brandeis.lmsapp.repository;

import com.brandeis.lmsapp.domain.Patron;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Patron entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatronRepository extends JpaRepository<Patron, Long>, JpaSpecificationExecutor<Patron> {

}
