package com.ptit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import lombok.Data;

@Entity
@Table(name="items")
@Data
public class Items {
	//thay the cho:  gio hang
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="item_id")
	private long itemId; 
	
	@Column(name="quantity_books")
	@Min(1)
	private int quantityOfBooks; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_book")
	private Book book; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user; 
}















