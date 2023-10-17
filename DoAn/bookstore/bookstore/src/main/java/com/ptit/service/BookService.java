package com.ptit.service;

import java.util.ArrayList;
import java.util.List;

import com.ptit.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ptit.exception.ResourceNotFoundException;

public interface BookService{

	public  Page<Supplier> getAllSupplier(Pageable pageable);
	public Page<Book> getAllBooks(Pageable page); 
	public int getTotalQuantity(); 
	public Book getBookById(long id)  throws ResourceNotFoundException;
	public List<Book> getBookByCategory(Category category); 
	public Page<Book> findBook(String key,int pageNo, int pageSize, String sortField, String sortDirection); 
	public Page<Book> findPage(int pageNo, int pageSize);
	public Page<Book> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	public Page<Book> getPageViaCategory(int pageNo, int pageSize, String sortField,String sortDirection,Category category); 
	public int save(Book book);
	public int deleteById(long idBook);
	
	public List<Book> getTopBook();
	public List<Book> getNewBook();
	public List<Book> getBookByAuthor(Author author);

	//11/10
	public  Page<PurchasingOrder> getAllPurchasingOrder(Pageable pageable);
}
