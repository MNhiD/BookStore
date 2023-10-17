package com.ptit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ptit.model.Province;
@Repository
public interface ProvinceDao extends JpaRepository<Province, String>{

}
