package com.ptit.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptit.model.Cart;
import com.ptit.model.CartManager;
import com.ptit.model.Category;
import com.ptit.model.District;
import com.ptit.model.Items;
import com.ptit.model.Province;
import com.ptit.model.Village;
import com.ptit.service.CategoryService;
import com.ptit.service.DistrictService;
import com.ptit.service.ProvinceService;
import com.ptit.service.VillageService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	CartManager cartManager; 
	
	@Autowired
	CategoryService categorySerivce; 
	
	@Autowired
	ProvinceService provinceService; 
	
	
	@Autowired
	DistrictService districtService; 
	
	@Autowired
	VillageService villageService;
	
	@GetMapping
	public String index(HttpSession session, Model model, ModelMap map, Principal principal) {
		if(principal!=null) {
			return "redirect:/account/cart";
		}
		
		List<Category> listCategory = categorySerivce.getAllCategories();
		
		map.addAttribute("categories", listCategory); 
		Cart cart = cartManager.getCart(session); 
		List<Items> list = cart.getItems(); 
		model.addAttribute("listItems", list);
		model.addAttribute("totalPrice", cart.getTotal());
		
		List<Province> listProvince = provinceService.getAllProvince();
		List<District> listDistrict = districtService.getDistrictByProvince(listProvince.get(0)); 
		List<Village>  listVillage = villageService.getVillageByDistrict(listDistrict.get(0)); 
		model.addAttribute("listProvince", listProvince); 
		model.addAttribute("listDistrict", listDistrict); 
		model.addAttribute("listVillage", listVillage); 
		return "cart"; 
	}
	

	
}


















































