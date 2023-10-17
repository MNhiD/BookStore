package com.ptit.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.model.Book;
import com.ptit.model.OrderDetail;

@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetail, Long>{
	
	
	@Query("select p\r\n"
			+ "from Order c,OrderDetail p \r\n"
			+ "where c.orderId = p.order\r\n"
			+ "and c.orderId = ?1")
	public List<OrderDetail> getListDetailByOrderId(long idOrder);
	
	@Query("select b\r\n"
			+ "from Order c,OrderDetail p,Book b\r\n"
			+ "where c.orderId = p.order and p.book = b.idBook\r\n"
			+ "and c.orderId = ?1")
	public List<Book> getListBookOfOrderDetail(long idOrder);
	
	@Query(value ="select sum(quantity)\r\n"
			+ "from bookstore.order_detail o,bookstore.orders d\r\n"
			+ "where o.order_id = d.order_id\r\n"
			+ "and d.order_status = 2",nativeQuery = true)
	public BigDecimal getTotalItemSold();
	
	@Query(value ="select sum(quantity*price)\r\n"
			+ "from bookstore.order_detail o,bookstore.orders d\r\n"
			+ "where o.order_id = d.order_id\r\n"
			+ "and d.order_status = 2",nativeQuery = true)
	public int getTotalEarning();
	
	public List<OrderDetail> findByBook(Book book);
}
