package com.ptit.service;

import java.util.List;

import com.ptit.model.District;
import com.ptit.model.Village;

public interface VillageService {
	public List<Village> getVillageByDistrict(District district); 
	public Village getById(String id);
}
