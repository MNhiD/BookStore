package com.ptit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.model.Author;
import com.ptit.model.Category;
import com.ptit.model.PublishingCompany;
@Repository
public interface PublishingCompanyDao extends JpaRepository<PublishingCompany, Long>{
	public Optional<PublishingCompany> findByNameAllIgnoreCase(String name); 
	
	@Query("SELECT  MAX(idCompany) from PublishingCompany")
	public int getLastIdCompany();
	
	@Query(value = "select * from bookstore.publishing_company  where publishing_company.company_name = :name and publishing_company.id_company != :id", nativeQuery = true)
	public List<PublishingCompany> findPublishingCompanyWhenUpdate(String name,long id); 
	
	public Page<PublishingCompany> findByNameContainsAllIgnoreCaseOrderByNameAsc(String name, Pageable pageable); 
}
