package com.ptit.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.model.District;
import com.ptit.model.Village;
import com.ptit.repository.VillageDao;
import com.ptit.service.VillageService;
@Service
public class VillageServiceImp implements VillageService{

	@Autowired
	VillageDao villageDao; 
	
	@Override
	public List<Village> getVillageByDistrict(District district) {
		return villageDao.findByDistrict(district); 
	}

	@Override
	public Village getById(String id) {
		// TODO Auto-generated method stub
		return villageDao.getById(id);
	}

}
