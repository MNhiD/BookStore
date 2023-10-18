package com.ptit.admin.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;

import com.ptit.model.*;
import com.ptit.service.BookService;
import com.ptit.service.PurchasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ptit.dto.UserDto;
import com.ptit.exception.ResourceNotFoundException;
import com.ptit.repository.AddressDao;
import com.ptit.repository.DistrictDao;
import com.ptit.repository.ProvinceDao;
import com.ptit.repository.RoleDao;
import com.ptit.repository.UserDao;
import com.ptit.repository.UserRoleDao;
import com.ptit.repository.VillageDao;
import com.ptit.service.UserService;
import com.ptit.service.VillageService;

@Controller
@RequestMapping("/admin/warehouse")
public class WarehouseController {
	@Autowired
	UserService userService;
	@Autowired
	BookService bookService;

	@Autowired
	UserRoleDao userRoleDao;

	@Autowired
	VillageDao villageDao;

	@Autowired
	DistrictDao listVil;

	@Autowired
	ProvinceDao provinceDao;

	@Autowired
	DistrictDao districtDao;

	@Autowired
	VillageService villageService;

	@Autowired
	AddressDao addressDao;

	@Autowired
	RoleDao  roleDao;

	@Autowired
	UserDao userDao;
	@Autowired
	PurchasingService purchasingService;

	@GetMapping()
	public String getHomeCustomer(Model model) {
		model.addAttribute("user", new User());
		return getUser(model, 1, "username", "asc");
	}

//	@GetMapping("/search")
//	public String searchDefault(Model model,@RequestParam String searchvalue, ModelMap map) {
//		model.addAttribute("book", new Book());
//		return getBookSearch(model, 1, "username", "asc",searchvalue);
//
//	}


//	@GetMapping("/search/{pageNo}")
//	public String getBookSearch(Model model, @PathVariable(value = "pageNo") int pageNo,
//								@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir,@RequestParam String searchvalue) {
//		int pageSize = 5;
//		int pageFirst = 1;
//		model.addAttribute("user", new User());
//		model.addAttribute("address", new Address());
//		Page<User> page =  userService.findUserByUsername(pageNo, pageSize, sortField, sortDir, searchvalue);
//		System.out.println("okok");
//		List<User> listUser = page.getContent();
//		//List<User> listUser2 = listUser.stream().filter(u -> 2==userRoleDao.getByUser(u).getRole().getIdRole()).collect(Collectors.toList());
//		model.addAttribute("listUser", listUser);
//		model.addAttribute("sortField", sortField);
//		model.addAttribute("sortDir", sortDir);
//		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
//
//		model.addAttribute("pageFirst", pageFirst);
//		model.addAttribute("currentPage", pageNo);
//		model.addAttribute("totalPage", page.getTotalPages());
//		model.addAttribute("totalItem", page.getTotalElements());
//
//		model.addAttribute("village", villageDao.findAll());
//		model.addAttribute("district", districtDao.findAll());
//		model.addAttribute("province", provinceDao.findAll());
//		return "admin/warehouse";
//	}

	@GetMapping("/{pageNo}")
	public String getUser(Model model, @PathVariable(value = "pageNo") int pageNo,
						  @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {

		int pageSize = 5;
		int pageFirst = 1;
		model.addAttribute("user", new User());
		model.addAttribute("address", new Address());
		Page<User> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir);

		List<User> listUser = page.getContent();
		//List<User> listUser2 = listUser.stream().filter(u -> 2==userRoleDao.getByUser(u).getRole().getIdRole()).collect(Collectors.toList());
		model.addAttribute("listUser", listUser);
		model.addAttribute("lstPurchasingOrder",purchasingService.getAllOrder());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("pageFirst", pageFirst);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItem", page.getTotalElements());

		model.addAttribute("village", villageDao.findAll());
		model.addAttribute("district", districtDao.findAll());
		model.addAttribute("province", provinceDao.findAll());
		return "admin/warehouse";
	}

	@GetMapping("/Add/{pageNo}")
	public String getUserAdd(RedirectAttributes ra,Model model, @PathVariable(value = "pageNo") int pageNo,
							 @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {

		int pageSize = 5;
		int pageFirst = 1;
		model.addAttribute("user", new User());
		model.addAttribute("address", new Address());
		Page<User> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir);

		List<User> listUser = page.getContent();
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		Page<Supplier> lstPageSup = bookService.getAllSupplier(pageable);
		Page<Book> lstPageBook = bookService.getAllBooks(pageable);
		List<Book> lstBook = lstPageBook.getContent();
		List<Supplier> lstSuppliers = lstPageSup.getContent();
		model.addAttribute("lstBook",lstBook);
		model.addAttribute("lstSuppliers",lstSuppliers);


		model.addAttribute("listUser", listUser);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("pageFirst", pageFirst);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItem", page.getTotalElements());

//		model.addAttribute("village", villageDao.findAll());
//		model.addAttribute("district", districtDao.findAll());
//		model.addAttribute("province", provinceDao.findAll());
//
//		model.addAttribute("roles", roleDao.findAll());
		boolean isAdd=true;
		model.addAttribute("isAdd", isAdd);


		return "admin/warehouse";
	}


}
