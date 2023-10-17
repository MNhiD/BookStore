package com.ptit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.model.District;
import com.ptit.model.Village;

@Repository
public interface VillageDao extends JpaRepository<Village, String>{
	public List<Village> findByDistrict(District district);
	
	@Query(value = "select * \r\n"
			+ "from village \r\n"
			+ "where village.district_id = ?1", nativeQuery = true)
	public List<Village> findAllVillageByIdDistrict(String id);
}
