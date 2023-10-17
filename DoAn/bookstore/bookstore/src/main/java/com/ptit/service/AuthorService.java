package com.ptit.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ptit.exception.ResourceNotFoundException;
import com.ptit.model.Author;
import com.ptit.model.Category;
import com.ptit.model.Author;

public interface AuthorService {
	public List<Author> getAllAuthor();
	public int getTotalAuthor();
	public Author getAuthorByName(String name) throws ResourceNotFoundException;
	public long getAuthorIdByName(String name) throws ResourceNotFoundException;
	public int save(Author author);
	public Author selectOrUpdateAuthor(String name);
	public Author getAuthorById(long id); 
	
	public Page<Author> findPaginated(int pageNo, int pageSize);
	
	public boolean checkNameExitWhenUpdate(String name,long id);
	public boolean checkNameExitWhenInsert(String name);
	
	public boolean checkExitAuthorInBook(Author author);
	
	public int deleteById(long id);
	public Page<Author> findAuthor(String key,int pageNo, int pageSize); 
}
