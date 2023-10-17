package com.ptit.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="orders")
public class Order  implements Serializable{
	//bo: ngayketthuc
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	private long orderId; 
	
	@Column(name="order_status")
	private int orderStatus; 
	
	@Column(name="total_price")
	private BigDecimal totalPrice;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="order_day")
	private Date orderDay; 
	
	@Column(name="name_of_customer")
	@Size(max=45)
	private String nameOfCustomer; 
	
	@Column(name="phone_of_customer")
	@Size(max=10)
	private String phoneOfCustomer; 
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user; 
	
	@ManyToOne
	@JoinColumn(name="address_id")
	private Address address; 
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="order_id")
	private Set<OrderDetail> setDetails = new HashSet<OrderDetail>();

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDay() {
		return orderDay;
	}

	public void setOrderDay(Date orderDay) {
		this.orderDay = orderDay;
	}

	public String getNameOfCustomer() {
		return nameOfCustomer;
	}

	public void setNameOfCustomer(String nameOfCustomer) {
		this.nameOfCustomer = nameOfCustomer;
	}

	public String getPhoneOfCustomer() {
		return phoneOfCustomer;
	}

	public void setPhoneOfCustomer(String phoneOfCustomer) {
		this.phoneOfCustomer = phoneOfCustomer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<OrderDetail> getSetDetails() {
		return setDetails;
	}

	public void setSetDetails(Set<OrderDetail> setDetails) {
		this.setDetails = setDetails;
	}

	public Order(long orderId, int orderStatus, BigDecimal totalPrice, Date orderDay,
			@Size(max = 45) String nameOfCustomer, @Size(max = 10) String phoneOfCustomer, User user, Address address,
			Set<OrderDetail> setDetails) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.totalPrice = totalPrice;
		this.orderDay = orderDay;
		this.nameOfCustomer = nameOfCustomer;
		this.phoneOfCustomer = phoneOfCustomer;
		this.user = user;
		this.address = address;
		this.setDetails = setDetails;
	}

	public Order() {
		super();
	} 
	
	
	

}














