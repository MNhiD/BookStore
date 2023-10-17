package com.ptit.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.model.Province;
import com.ptit.repository.ProvinceDao;
import com.ptit.service.ProvinceService;

@Service
public class ProvinceServiceImp implements ProvinceService{

	@Autowired
	ProvinceDao provinceDao;
	
	@Override
	public List<Province> getAllProvince() {
		// TODO Auto-generated method stub
		return provinceDao.findAll();
	}

	@Override
	public Province getProvinceById(String provinceId) {
		return provinceDao.getById(provinceId); 
	}
	
}
