package com.ptit.admin.controller.api;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptit.model.Address;
import com.ptit.model.Book;
import com.ptit.model.District;
import com.ptit.model.Order;
import com.ptit.model.OrderDetail;
import com.ptit.model.Province;
import com.ptit.model.Village;
import com.ptit.repository.AddressDao;
import com.ptit.repository.DistrictDao;
import com.ptit.repository.ProvinceDao;
import com.ptit.repository.VillageDao;


@RestController
@RequestMapping("/api/address")
public class AddressAPI {
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private DistrictDao districtDao;
    @Autowired
    private VillageDao villageDao;
    @Autowired
    private AddressDao addressDao;
    
    @GetMapping("/province")
    private List<Province> getAllProvince(){
    	ArrayList<Province> list = (ArrayList<Province>) provinceDao.findAll();
        return list;
    }
    //Get all district by province_id
    @GetMapping("/district/{id}")
    private List<District> getAllDistrictByProvinceId(@PathVariable("id") String provinceId){
        return districtDao.findAllDistrictByIdProvince(provinceId);
    }
    
    @GetMapping("/village/{id}")
    private List<Village> getAllVillageByDistrictId(@PathVariable("id") String districtId){
        return  villageDao.findAllVillageByIdDistrict(districtId);
    }
    
    @GetMapping("/{id}")
    private List<String> getAddressById(@PathVariable("id") long addressId){
    	Address address=addressDao.findByAddressId(addressId);
    	List<String> listAddressValues= new ArrayList<>();
    	listAddressValues.add(String.valueOf(address.getAddressId()));
    	listAddressValues.add(address.getAddressName());
    	listAddressValues.add(address.getVillage().getVillageId());
    	listAddressValues.add(address.getVillage().getDistrict().getDistrictId());
    	listAddressValues.add(address.getVillage().getDistrict().getProvince().getProvinceId());
        return  listAddressValues;
    }
//    get all ward by district_id
//    @GetMapping("/ward/{id}")
//    private List<Village> getAllWardByDistrictId(@PathVariable("id") String districtId){
//        return villageDao.findAllByIdDistrict(districtId);
//    }
    
//    @GetMapping("/district/{id}")
//    private void huyen(@PathVariable("id") String provinceId,HttpServletResponse respon){
//    	try {
//			
//			List<District> dis = districtDao.findAllDistrictByIdProvince(provinceId);
//			
//			respon.setContentType("text/html;charset=UTF-8");
//			PrintWriter out = respon.getWriter(); 
//			
//			
//			for (int i = 0; i < dis.size(); i++) {
//				
//				out.println("<select id=\"district\">\r\n"
//						+ "											<option value=\""+dis.get(i).getDistrictId()+"\">"+dis.get(i).getDistrictName()+"</option>\r\n"
//						+ "										</select>");
//				
//				
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
}

