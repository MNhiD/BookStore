package com.ptit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ptit.model.User;
import com.ptit.model.UserRole;

@Repository
public interface UserRoleDao extends JpaRepository<UserRole, Long>{
	List<UserRole> findByUser(User user); 
	
	public UserRole getByUser(User user);
}
