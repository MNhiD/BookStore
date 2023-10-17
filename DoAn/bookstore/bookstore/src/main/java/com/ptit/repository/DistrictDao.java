package com.ptit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.model.District;
import com.ptit.model.Province;

@Repository
public interface DistrictDao extends JpaRepository<District, String>{
	public List<District> findByProvince(Province province); 
	
	@Query(value = "select * \r\n"
			+ "from district \r\n"
			+ "where district.province_id = ?1", nativeQuery = true)
	public List<District> findAllDistrictByIdProvince(String id);
}
