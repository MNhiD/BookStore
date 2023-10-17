package com.ptit.service;

import java.util.List;

import com.ptit.model.District;
import com.ptit.model.Province;

public interface DistrictService {
	public List<District> getDistrictByProvince(Province province); 
	public District getDistrictById(String id); 
}
