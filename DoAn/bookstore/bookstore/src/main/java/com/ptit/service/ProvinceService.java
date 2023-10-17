package com.ptit.service;

import java.util.List;

import com.ptit.model.Province;

public interface ProvinceService {
	public List<Province> getAllProvince(); 
	public Province getProvinceById(String provinceId); 
}
