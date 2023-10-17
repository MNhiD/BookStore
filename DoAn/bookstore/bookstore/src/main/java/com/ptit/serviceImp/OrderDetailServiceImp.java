package com.ptit.serviceImp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ptit.exception.ResourceNotFoundException;
import com.ptit.model.Book;
import com.ptit.model.Order;
import com.ptit.model.OrderDetail;
import com.ptit.repository.OrderDetailDao;
import com.ptit.service.OrderDetailService;
import com.ptit.service.OrderService;
@Service
public class OrderDetailServiceImp implements OrderDetailService{
	
	
	
	@Autowired
	private OrderDetailDao orderDetailDao;

	@Override
	public List<OrderDetail> getListDetailByOrderId(long idOrder) {
		// TODO Auto-generated method stub
		
		return  orderDetailDao.getListDetailByOrderId(idOrder);
	}

	@Override
	public List<Book> getListBookOfOrderDetail(long idOrder) {
		// TODO Auto-generated method stub
		return orderDetailDao.getListBookOfOrderDetail(idOrder);
	}

	@Override
	public List<OrderDetail> getListDetailByBook(Book book){
		return orderDetailDao.findByBook(book);
	}

}
