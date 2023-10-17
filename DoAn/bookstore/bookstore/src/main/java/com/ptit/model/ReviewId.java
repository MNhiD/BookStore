package com.ptit.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReviewId implements Serializable{
	@Column(name="user_id")
	private long userId;
	
	@Column(name="id_book")
	private long idBook; 
	
	public ReviewId() {
		
	}
	public ReviewId(long userId, long idBook) {
		this.userId = userId; 
		this.idBook = idBook; 
	}
	
	
	
	 public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getIdBook() {
		return idBook;
	}
	public void setIdBook(long idBook) {
		this.idBook = idBook;
	}
	@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof ReviewId)) return false;
	        ReviewId that = (ReviewId) o;
	        return Objects.equals(getUserId(), that.getUserId()) &&
	                Objects.equals(getIdBook(), that.getIdBook());
	    }
	 
	    @Override
	    public int hashCode() {
	        return Objects.hash(getUserId(), getIdBook());
	    }
}
