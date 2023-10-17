package com.ptit.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ptit.repository.OrderDao;
import com.ptit.repository.OrderDetailDao;
import com.ptit.service.OrderService;
import com.ptit.service.UserService;

@Controller
@RequestMapping("admin")
public class DashboardController {
	
	@Autowired
	UserService userService;
	@Autowired
	OrderDao orderDao;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailDao orderDetailDao;
	
	
	@RequestMapping(value={"", "/","statisticts"})
	public String index(Model model) {

		model.addAttribute("NumberUser", userService.countUsers());
		List<Float> list = orderService.getMoneyPerMonthByYear(orderDao.getListYear().get(0));
		model.addAttribute("price", list);
		model.addAttribute("Totalearning", orderDetailDao.getTotalEarning());
		model.addAttribute("TotalItem", orderDetailDao.getTotalItemSold());
		
		model.addAttribute("listYear", orderDao.getListYear());
		model.addAttribute("listMonth", orderDao.getListMonth());
		
		return "admin/statisticts";
	}
	
	@PostMapping("/statisticts")
	public String index2(Model model, 	@RequestParam(name="year")  int year, @RequestParam(name = "month") int month) {
		
		model.addAttribute("NumberUser", userService.countUsers());
		List<Float> list = orderService.getMoneyPerMonthByYear(year);
//		List<Float> listMonthMoney = orderService.getyear);

		model.addAttribute("price", list);
		model.addAttribute("Totalearning", orderDetailDao.getTotalEarning());
		model.addAttribute("TotalItem", orderDetailDao.getTotalItemSold());
		
		model.addAttribute("listYear", orderDao.getListYear());
		model.addAttribute("listMonth", orderDao.getListMonth());
		return "admin/statisticts";
	}
	
	
	// Added to test 500 page
    @RequestMapping(path = "/tigger-error", produces = MediaType.APPLICATION_JSON_VALUE)
    public void error500() throws Exception {
        throw new Exception();
    }
}
