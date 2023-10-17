package com.ptit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.model.Address;

@Repository
public interface AddressDao extends JpaRepository<Address, Long>{
	@Query(value = "SELECT * FROM address\r\n"
			+ "where address_id = ?1", nativeQuery = true)
	public Address findByAddressId(long id);
	
	List<Address> findBySetUsers_UserId(Long id);
}
