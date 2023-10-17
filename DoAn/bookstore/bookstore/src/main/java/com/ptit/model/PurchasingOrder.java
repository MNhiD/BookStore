package com.ptit.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name="purchasing_order")
@Data
public class PurchasingOrder {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_purchasing_order")
	private long idPurchasingOrder;
	
	
	@Column(name="quantity")
	private int quantity;

	@Column(name="price")
	private BigDecimal price;



	@Column(name="id_book")
	private long id_book;

	@Column(name="id_purchasing")
	private int id_purchasing;

}


























