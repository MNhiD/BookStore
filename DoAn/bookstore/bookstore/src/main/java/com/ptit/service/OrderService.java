package com.ptit.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.ptit.exception.OverMaximumQuantityException;
import com.ptit.exception.ResourceNotFoundException;
import com.ptit.model.Address;
import com.ptit.model.Items;
import com.ptit.model.Order;
import com.ptit.model.User;

public interface OrderService {
	public Page<Order> getAllOrders(Pageable page);
	public Order getOrderById(long id) throws ResourceNotFoundException;
	public List<Order> findOrder(String str);
	public Page<Order> findPage(int pageNo, int pageSize);
	public Page<Order> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, int status);
	
	public int save(Order order);
	public Page<Order> getAllBetweenDates(int pageNo, int pageSize, String sortField, String sortDirection, int status,Date startDate, Date endDate);
	public int updateOrderStatus(long idOrder, int status);
	public boolean createNewOrder(String name, String phone, User user, List<Items> list,BigDecimal totalPrice, String addressName, String villageId) throws OverMaximumQuantityException ; 
	public List<Order> getOrdersByUser(User user);
	public void cancelOrder(long orderId); 
	public List<Float> getMoneyPerMonthByYear(int year);
	Page<Order> findPaginatedListOrderFindByUser(int pageNo, int pageSize, String sortField, String sortDirection,
			int status, User user);
}
