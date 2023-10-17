package com.ptit.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="review")
public class Review {

	@EmbeddedId
	private ReviewId id; 
	
	
	private Date time; 
	
	@NotEmpty
	@Size(max=200)
	private String comments; 
	
	@Max(5)
	@Min(1)
	private float star; 
	
	
	@ManyToOne
    @JoinColumn(name = "user_id",insertable = false, updatable = false)
	private User user; 
	
	
	@ManyToOne
    @JoinColumn(name = "id_book",insertable = false, updatable = false)
	private Book book;


	public ReviewId getId() {
		return id;
	}


	public void setId(ReviewId id) {
		this.id = id;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public float getStar() {
		return star;
	}


	public void setStar(float star) {
		this.star = star;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	} 
	
	
	
	
}
