package com.brandeis.lmsapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brandeis.lmsapp.domain.LibraryResource;

public interface SearchRepository extends JpaRepository<LibraryResource, Long> {
	
	@Query(value = "SELECT * FROM library_resource t where t.id = ?0", 
	        nativeQuery=true)
	public Optional<LibraryResource> searchByID(Long id);
	
	@Query(value = "SELECT * FROM library_resource t where t.resource_title like CONCAT('%',:resource_title,'%')", 
	        nativeQuery=true)
	public Optional<LibraryResource> searchByResourceTitle(@Param("resource_title")String resource_title);
//call number
	@Query(value = "SELECT * FROM library_resource t where t.call_number = :callNumber", 
	        nativeQuery=true)
	public Optional<LibraryResource> searchByCallNumber(@Param("callNumber")Long callNumber);
	
	@Query(value = "SELECT * FROM library_resource t inner join author a  where t.author_id = a.id and a.author_name like CONCAT('%',:author,'%')",
	        nativeQuery=true)
	public Optional<List<LibraryResource>> searchByAuthor(@Param("author")String author);
	
	@Query(value = "SELECT * FROM library_resource t where t.resource_description like CONCAT('%',:description,'%')",nativeQuery=true)
	public Optional<List<LibraryResource>> searchByDescription(@Param("description") String description);
	
	@Query(value = "SELECT * FROM library_resource t inner join subject a  where t.subject_id = a.id and a.subject_name like CONCAT('%',:subject,'%')",
	        nativeQuery=true)
	public Optional<List<LibraryResource>> searchBySubject(@Param("subject") String subject);

}


