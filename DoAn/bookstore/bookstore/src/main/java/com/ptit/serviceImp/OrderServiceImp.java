package com.ptit.serviceImp;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ptit.exception.OverMaximumQuantityException;
import com.ptit.exception.ResourceNotFoundException;
import com.ptit.model.Address;
import com.ptit.model.Book;
import com.ptit.model.Items;
import com.ptit.model.Order;
import com.ptit.model.OrderDetail;
import com.ptit.model.User;
import com.ptit.repository.BookDao;
import com.ptit.repository.OrderDao;
import com.ptit.service.AddressService;
import com.ptit.service.OrderService;

@Service
public class OrderServiceImp implements OrderService {
	@Autowired
	OrderDao orderdao;
	
	@Autowired 
	AddressService addressService; 
	
	@Autowired
	BookDao bookDao; 
	@Override
	public Page<Order> getAllOrders(Pageable page){
		return orderdao.findAll(page);
	}
	
	@Override
	public Order getOrderById(long id) throws ResourceNotFoundException{
		Optional<Order> order=orderdao.findById(id);
		if(!order.isPresent()) {
			throw new ResourceNotFoundException("Order not found by id");
		}
		return order.get();
	}
	@Override
	public List<Order> findOrder(String str){
		List<Order> list=orderdao.findByNameOfCustomerContainsOrPhoneOfCustomerContainsAllIgnoreCaseOrderByNameOfCustomerAsc(str, str);
		return list;
	}
	@Override
	public Page<Order> findPage(int pageNo, int pageSize){
		Pageable pageable=PageRequest.of(pageNo-1, pageSize);
		return orderdao.findAll(pageable);
	}
	@Override
	public Page<Order> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, int status){
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;
		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		if(status<0) {
			return orderdao.findAll(pageable);
		}
		return orderdao.findByOrderStatus(status, pageable);
	}
	
	@Override
	public Page<Order> findPaginatedListOrderFindByUser(int pageNo, int pageSize, String sortField, String sortDirection, int status,User user){
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;
		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		if(status<0) {
			return orderdao.findByUserOrderByOrderIdDesc(user, pageable);
		}
		return orderdao.findByOrderStatus(status, pageable);
	}
	@Override
	public int save(Order order) {
		orderdao.save(order);
		return 1;
	}
	
	@Override
	public Page<Order> getAllBetweenDates(int pageNo, int pageSize, String sortField, String sortDirection, int status,Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortField).ascending() : Sort.by(sortField).descending() ;
				
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		if(status<0) {
			return orderdao.getAllBetweenDates(startDate, endDate,pageable);
		}
		System.out.println(status);
		return orderdao.getAllBetweenDatesAndStatus(startDate, endDate,status, pageable);		
		
	}

	@Override
	public int updateOrderStatus(long idOrder, int status) {
		Order order = orderdao.getById(idOrder);
		order.setOrderStatus(status);
		return save(order);
	}

	@Override
	@Transactional(rollbackFor = OverMaximumQuantityException.class)
	public boolean createNewOrder(String name, String phone, User user, List<Items> list,BigDecimal totalPrice,String addressName, String villageId) throws OverMaximumQuantityException {
		
		
		Set<OrderDetail> listOrderDetail = new HashSet<OrderDetail>();
		Order order = new Order();
		for(Items item: list) {
			OrderDetail detail = new OrderDetail(); 
			detail.setBook(item.getBook());
			detail.setPrice(item.getBook().getPrice());
			
			
			
			Book book = bookDao.getById(item.getBook().getIdBook()); 
			
			//nếu vượt quá số lượng cung ứng
			if(item.getQuantityOfBooks()>book.getTotalQuantity()) {
				throw new OverMaximumQuantityException(item.getBook().getIdBook()+"-"+book.getTotalQuantity()); 
				
			}
				
			//số lượng order hợp lệ: 
			detail.setQuantity(item.getQuantityOfBooks());
			//cập nhật số lượng sách
			book.setTotalQuantity(book.getTotalQuantity()-item.getQuantityOfBooks());
		
			bookDao.save(book); 
			
			listOrderDetail.add(detail);  
			
		}
		Address address = addressService.createAddress(addressName, villageId);
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		
		 
		order.setNameOfCustomer(name);
		order.setPhoneOfCustomer(phone);
		order.setSetDetails(listOrderDetail);
		order.setAddress(address);
		order.setOrderDay(date);
		order.setOrderStatus(1);
		order.setTotalPrice(totalPrice);
	
		if(user!=null) order.setUser(user);
		
		orderdao.save(order); 
		
		
		
	
		return true; 
	}

	@Override
	public List<Order> getOrdersByUser(User user){
		
		List<Order> list = orderdao.findByUserOrderByOrderIdDesc(user); 
		
		return list; 
	}

	@Override
	public void cancelOrder(long orderId) {
		Order order = orderdao.getById(orderId); 
		order.setOrderStatus(3);
		orderdao.save(order); 
	}

	@Override
	public List<Float> getMoneyPerMonthByYear(int year) {
		List<Float> listMoney = orderdao.getPrice(year);
		List<Integer> listMonth = orderdao.getMonthByYearOrderDate(year);
		List<Float> moneyPerMonth = new ArrayList<Float>();
		float a = 0;
		moneyPerMonth.add(a);
		moneyPerMonth.add(a);
		moneyPerMonth.add(a);
		moneyPerMonth.add(a);
		moneyPerMonth.add(a);
		moneyPerMonth.add(a);
		moneyPerMonth.add(a);
		moneyPerMonth.add(a);
		moneyPerMonth.add(a);
		moneyPerMonth.add(a);
		moneyPerMonth.add(a);
		moneyPerMonth.add(a);
		
		for(int i = 0;i < listMoney.size(); i++) {
			for(int j =0; j < 12; j++) {
				if(listMonth.get(i) == j+1) {
					moneyPerMonth.set(j, listMoney.get(i));
				}
			}
		}
		return moneyPerMonth;
	}







}




































