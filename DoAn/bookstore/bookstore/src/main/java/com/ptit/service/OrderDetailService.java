package com.ptit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ptit.model.Book;
import com.ptit.model.OrderDetail;

@Service
public interface OrderDetailService {
	public List<OrderDetail> getListDetailByOrderId(long idOrder);
	public List<Book> getListBookOfOrderDetail(long idOrder);
	
	public List<OrderDetail> getListDetailByBook(Book book);
}
