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

	@GetMapping("/search")
	public String searchDefault(Model model,@RequestParam String searchvalue, ModelMap map) {
		model.addAttribute("book", new Book());
		return getBookSearch(model, 1, "username", "asc",searchvalue);

	}


	@GetMapping("/search/{pageNo}")
	public String getBookSearch(Model model, @PathVariable(value = "pageNo") int pageNo,
								@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir,@RequestParam String searchvalue) {
		int pageSize = 5;
		int pageFirst = 1;
		model.addAttribute("user", new User());
		model.addAttribute("address", new Address());
		Page<User> page =  userService.findUserByUsername(pageNo, pageSize, sortField, sortDir, searchvalue);
		System.out.println("okok");
		List<User> listUser = page.getContent();
		//List<User> listUser2 = listUser.stream().filter(u -> 2==userRoleDao.getByUser(u).getRole().getIdRole()).collect(Collectors.toList());
		model.addAttribute("listUser", listUser);
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

		//11/10
//		Page<PurchasingOrder> lstPagePurchasingOrder = bookService.getAllPurchasingOrder(pageable);
//		List<PurchasingOrder> lstPurchasingOrder = lstPagePurchasingOrder.getContent();
//		model.addAttribute("lstPurchasingOrder",lstPurchasingOrder);
		//

		model.addAttribute("listUser", listUser);
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

		model.addAttribute("roles", roleDao.findAll());
		boolean isAdd=true;
		model.addAttribute("isAdd", isAdd);


		return "admin/warehouse";
	}

//	@PostMapping("/Add/{pageNo}")
//	public String addu(Model model,
//			RedirectAttributes ra) throws ResourceNotFoundException {
//
////		List<Address> list = addressDao.findBySetUsers_UserId(id);
//		ra.addFlashAttribute("user", new User());
////		ra.addFlashAttribute("address2", list);
//		boolean isAdd=true;
//		ra.addFlashAttribute("isAdd", isAdd);
//		return "redirect:/admin/customer";
//	}

	@PostMapping("/save")
	public String updateUser(@ModelAttribute("user") User user,
							 RedirectAttributes ra,
							 @RequestParam(name="addressName") String addressName,
							 @RequestParam(name="villageId") String villageId) throws ResourceNotFoundException {
		boolean isError=false;

		Optional<User> user2 = userDao.findById(user.getUserId());

		//Nếu người dùng ko tồn tại (thêm mới)
		if(!user2.isPresent()) {
			//kt trùng
			boolean existUsername=userService.checkExistUsernameInfo(user.getUsername());
			boolean existPhone=userService.checkExistPhoneInfo(user.getPhone());
			boolean existEmail=user.getEmail().isEmpty()?false: userService.checkExistEmailInfo(user.getEmail());

			//trả về lỗi nếu có
			if(existUsername || existPhone || existEmail) {
				isError=true;
				ra.addFlashAttribute("isError", isError);
				ra.addFlashAttribute("existUsername", existUsername);
				ra.addFlashAttribute("existPhone", existPhone);
				ra.addFlashAttribute("existEmail", existEmail);
				ra.addFlashAttribute("user2", user);
				ra.addFlashAttribute("isAdd", true);
				ra.addFlashAttribute("erorrMes", "Thêm user thất bại, vui lòng kiểm tra lại");
				return "redirect:/admin/warehouse";
			}else {// tiến hành thêm nếu ko có lỗi
				Address ad = new Address();
				ad.setAddressName(addressName);

				//mã hóa mk
				String passwordConvert = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
				user.setPassword(passwordConvert);

				ad.setVillage(villageService.getById(villageId));
				user.getSetAddress().add(ad);

				userService.saveUser(user);
				//cập nhập lại trạng thái
				boolean edit=false;
				ra.addFlashAttribute("idEdit", edit);//trạng thái edit
				ra.addFlashAttribute("successMes", "Thêm user thành công");// trạng thái lỗi
			}
		}else {//nếu người dùng đã tồn tại (thực hiện cập nhập)

			//kt trùng
			boolean existPhone=userService.checkExistPhoneInfo(user.getPhone(),user.getUsername());
			boolean existEmail=user.getEmail().isEmpty()?false:userService.checkExistEmailInfo(user.getEmail(),user.getUsername());

			//nếu trùng trả về lỗi
			if(existPhone || existEmail) {
				isError=true;
				User editUser = userService.findById(user.getUserId());
				user.setSetAddress(editUser.getSetAddress());
				ra.addFlashAttribute("isError", isError);
				ra.addFlashAttribute("existPhone", existPhone);
				ra.addFlashAttribute("existEmail", existEmail);
				ra.addFlashAttribute("user2", user);
				ra.addFlashAttribute("idEdit", true);
				ra.addFlashAttribute("erorrMes", MessageNotify.MESSAGE_TEST);
				return "redirect:/admin/customer";
			}else {

//				String passwordConvert = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
//				user.setPassword(passwordConvert);
				userService.updateUser(user);
				boolean edit=false;
				ra.addFlashAttribute("idEdit", edit);
				ra.addFlashAttribute("successMes", "Cập nhập user thành công");
			}


		}

		return "redirect:/admin/customer";
	}


	/*
	 * @PostMapping("/saveEdit") public String
	 * updateUserEdit(@ModelAttribute("user") User user) { // String passwordConvert
	 * = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)); //
	 * user.setPassword(passwordConvert); userService.saveUser(user);
	 *
	 * return "redirect:/admin/customer"; }
	 */

	@PostMapping("/edit")
	public String edit(Model model,
					   RedirectAttributes ra,
					   @RequestParam(name="id") long id) throws ResourceNotFoundException {

		User userEdit = userService.findById(id);
		UserDto user3 = userService.convertUserDto(userEdit);

//		List<Address> list = addressDao.findBySetUsers_UserId(id);
		ra.addFlashAttribute("user3", user3);
//		ra.addFlashAttribute("address2", list);
		boolean edit=true;
		ra.addFlashAttribute("idEdit", edit);
		return "redirect:/admin/customer";
	}

	@PostMapping("/delete")
	public String updateUser(
			@RequestParam(name="id") long id,RedirectAttributes ra) throws ResourceNotFoundException {
		User user = userService.findById(id);
		int status=userService.deleteUser(user);

		if(status==0) ra.addFlashAttribute("erorrMes", "Xóa user thất bại, người dùng đã có đơn hàng trong hệ thống");
		else ra.addFlashAttribute("successMes", "Xóa user thành công");



		return "redirect:/admin/customer";
	}
}
