package com.ptit.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Table(name="book")
@Data
public class Book {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_book")
	private long idBook; 
	
	
	@Column(name="book_name",length=45)
	private String bookName; 
	
	
	@Column(name="publish_day")
	private Date publishDay;
	
	
	private BigDecimal price; 
	
	@Column(name="describe_book")
	private String describeBook;
	
	
	private String picture; 
	
	@Column(name="total_quantity")
	private int totalQuantity; 
	
	
	
	
	@ManyToOne
	@JoinColumn(name="id_author", nullable = true)
	private Author author; 
	
	@ManyToOne
	@JoinColumn(name="id_company",nullable = true)
	private PublishingCompany company; 
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category; 
}


























