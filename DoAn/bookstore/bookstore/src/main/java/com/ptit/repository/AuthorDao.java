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
public interface AuthorDao extends JpaRepository<Author, Long>{
	public Optional<Author> findByNameAllIgnoreCase(String name); 
	
	@Query("SELECT  MAX(idAuthor) from Author")
	public int getLastIdAuthor();
	
	public Author findAuthorByIdAuthor(long idAuthor); 
	
	@Query(value = "select * from bookstore.author  where author.author_name = :name and author.id_author != :id", nativeQuery = true)
	public List<Author> findAuthorWhenUpdate(String name,long id); 
	
	public Page<Author> findByNameContainsAllIgnoreCaseOrderByNameAsc(String name, Pageable pageable); 
}
