package com.ptit.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ptit.model.Book;
import com.ptit.model.Items;
import com.ptit.model.User;
@Repository
public interface ItemsDao extends JpaRepository<Items, Long>{
	
	public Items findByUserAndBook(User user, Book book);
	public List<Items> findAllByUser(User user);
	public List<Items> findAllByBook(Book book);
	
	@Transactional
	public void deleteByUser(User user); 
}
