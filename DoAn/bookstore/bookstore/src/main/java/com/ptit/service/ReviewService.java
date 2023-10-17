package com.ptit.service;

import java.util.List;

import com.ptit.model.Book;
import com.ptit.model.Review;
import com.ptit.model.ReviewId;
import com.ptit.model.User;

public interface ReviewService {
	public List<Review> getAllReviewViaBook(Book book); 
	public void addReview(User user, Book book, float star, String comments); 
	public boolean checkReviewed(User user, Book book); 
	
	public List<Review> findListByLeng(long id, int leng);
	public void deleteById(ReviewId id);
}
