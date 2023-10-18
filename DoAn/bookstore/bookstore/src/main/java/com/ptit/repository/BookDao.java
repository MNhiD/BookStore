package com.ptit.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.model.Author;
import com.ptit.model.Book;
import com.ptit.model.Category;
import com.ptit.model.PublishingCompany;

@Repository
public interface BookDao extends JpaRepository<Book, Long>,JpaSpecificationExecutor<Book>{

	public List<Book> findByCategory(Category category); 
	public Page<Book> findByBookNameContainsOrDescribeBookContainsAllIgnoreCaseOrderByBookNameAsc(String bookName,String describe, Pageable pageable);
	public Page<Book> findByBookNameContains(String bookName, Pageable pageable);
	public Page<Book> findByCategory(Category category, Pageable pageable); 
	@Query("SELECT  MAX(idBook)\r\n"
			+ "from Book")
	public int getLastIdBook();
	
	
	@Query(value = "SELECT B.* FROM  bookstore.order_detail OD, bookstore.book B, (SELECT * FROM bookstore.orders WHERE order_status=2) O\r\n"
			+ "WHERE OD.id_book=B.id_book  AND OD.order_id=O.order_id\r\n"
			+ "group by id_book\r\n"
			+ "order by sum(quantity) desc\r\n"
			+ "LIMIT 10;", nativeQuery = true)
	List<Book> getTopBook();
	
	@Query(value = "SELECT * FROM bookstore.book B\r\n"
			+ "order by B.publish_day desc\r\n"
			+ "limit 8", nativeQuery = true)
	List<Book> getNewBook();
	
	public List<Book> findByAuthor(Author author); 
	public List<Book> findByCompany(PublishingCompany p); 

}
