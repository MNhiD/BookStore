package com.ptit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.model.Review;
import com.ptit.model.ReviewId;


@Repository
public interface ReviewDao extends JpaRepository<Review, ReviewId>{
	public List<Review> findByIdIdBook(long idBook); 
	public List<Review> findByIdIdBookOrderByTimeDesc(long idBook); 
	public Review findByIdIdBookAndIdUserId(long idBook, long userId); 
	
	
	@Query(value = "select star,count(*) \r\n"
			+ "from bookstore.review where id_book=?1 \r\n"
			+ "group by star \r\n"
			+ "order by star desc",nativeQuery = true)
	public List<int[]> getListStar(long idBook);
}
