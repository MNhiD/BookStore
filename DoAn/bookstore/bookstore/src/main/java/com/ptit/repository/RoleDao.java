package com.ptit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ptit.model.Role;
@Repository
public interface RoleDao extends JpaRepository<Role, Long>{
	public Role findByRoleName(String roleName); 
}
