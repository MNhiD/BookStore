package com.ptit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.model.Author;
import com.ptit.model.Book;
import com.ptit.model.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long>{
	public Optional<Category> findByNameAllIgnoreCase(String categoryName); 
	
	@Query("SELECT  MAX(categoryId) from Category")
	public int getLastIdCategory();
	
	@Query(value = "select * from bookstore.category where category.name = :name and category.category_id != :id", nativeQuery = true)
	public List<Category> findCategoryWhenUpdate(String name,long id); 
	
	public Page<Category> findByNameContainsAllIgnoreCaseOrderByNameAsc(String name, Pageable pageable); 
}
