package com.ptit.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="order_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 

	private int quantity; 
	private BigDecimal price; 
	
	@ManyToOne(cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="order_id")
	private Order order; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_book")
	private Book book; 
}
