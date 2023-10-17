package com.ptit.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.model.District;
import com.ptit.model.Province;
import com.ptit.repository.DistrictDao;
import com.ptit.service.DistrictService;

@Service
public class DistrictServiceImp implements DistrictService{

	@Autowired
	DistrictDao districtDao; 
	
	@Override
	public List<District> getDistrictByProvince(Province province) {
		return districtDao.findByProvince(province); 
	}

	@Override
	public District getDistrictById(String id) {
		return districtDao.getById(id); 
	}

}
