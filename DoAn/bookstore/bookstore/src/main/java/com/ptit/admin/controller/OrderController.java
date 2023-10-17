package com.ptit.admin.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Date;
import java.util.List;

import com.ptit.model.MessageNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ptit.model.Book;
import com.ptit.model.Category;
import com.ptit.model.Order;
import com.ptit.service.OrderService;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping()
	public String getDashboard(Model model) {
		model.addAttribute("book", new Order());
		return getOrder(model, 1, "nameOfCustomer", "asc", -1);
	}
	
	@GetMapping("/search")
	public String searchDefault(Model model,@RequestParam Date startDate,@RequestParam Date endDate, ModelMap map,@RequestParam("statusOrderSearch") int statusOrderSearch) {
		model.addAttribute("book", new Order());
		return getOrderSearch(model, 1, "nameOfCustomer", "asc", statusOrderSearch,startDate,endDate);
		
	}
	
	
	@GetMapping("/search/{pageNo}")
	public String getOrderSearch(Model model, @PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField, 
			@RequestParam("sortDir") String sortDir,
			@RequestParam("status") int status,@RequestParam Date startDate,@RequestParam Date endDate) {
		int pageSize = 5;
		int pageFirst = 1;
		
		Page<Order> page = orderService.getAllBetweenDates(pageNo, pageSize, sortField, sortDir, status,startDate,endDate);
		List<Order> listOrder = page.getContent();
		model.addAttribute("listOrder", listOrder);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("status", status);
		model.addAttribute("pageFirst", pageFirst);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItem", page.getTotalElements());
		
		System.out.println(startDate);
		System.out.println(endDate);
//		LocalDateTime start = LocalDateTime.of(startDate.getStartDate(), LocalTime.of(0, 0, 0));
//		LocalDateTime end = LocalDateTime.of(endDate.getEndDate(), LocalTime.of(23, 59, 59));
//
//		orderService.getAllBetweenDates(start, end);
		return "/admin/order";
	}
	
	@GetMapping("/{pageNo}")
	public String getOrder(Model model, @PathVariable(value = "pageNo") int pageNo,
							@RequestParam("sortField") String sortField, 
							@RequestParam("sortDir") String sortDir,
							@RequestParam("status") int status){
		
		
		
		int pageSize = 6;
		int pageFirst = 1;
		Page<Order> page = orderService.findPaginated(pageNo, pageSize, sortField, sortDir, status);
		List<Order> listOrder = page.getContent();
		model.addAttribute("listOrder", listOrder);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("status", status);
		model.addAttribute("pageFirst", pageFirst);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItem", page.getTotalElements());
		
		
//		LocalDateTime start = LocalDateTime.of(startDate.getStartDate(), LocalTime.of(0, 0, 0));
//		LocalDateTime end = LocalDateTime.of(endDate.getEndDate(), LocalTime.of(23, 59, 59));
//
//		orderService.getAllBetweenDates(start, end);

		return "/admin/order";
	}
	
	
	@GetMapping("/save/{pageNo}")
	public String saveStatus(Model model,@PathVariable(value = "pageNo") int pageNo,
			RedirectAttributes ra,
			@RequestParam("idOrder") long idOrder,
			@RequestParam("statusNew") int statusNew,
			@RequestParam("statusOld") int statusOld
			) {
		
		int i = orderService.updateOrderStatus(idOrder, statusNew);
		if(i > 0 ) {
			ra.addFlashAttribute("successMes", MessageNotify.ORDER_SUCCESS);
		}else {
			ra.addFlashAttribute("erorrMes", MessageNotify.ORDER_FAILED);
		}
		
		//return getOrder(model, pageNo, "nameOfCustomer", "asc", statusOld);
		return "redirect:/admin/order/"+pageNo+"?sortField=nameOfCustomer"+"&sortDir=asc"+"&status="+statusOld;
	}
	
	
	
	
}
