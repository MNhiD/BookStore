package com.ptit.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;
import javax.swing.event.TableColumnModelListener;

import com.ptit.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.ptit.service.DistrictService;
import com.ptit.service.OrderDetailService;
import com.ptit.service.OrderService;
import com.ptit.service.ProvinceService;
import com.ptit.service.UserService;
import com.ptit.service.VillageService;
import com.ptit.util.WebUtils;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	UserService userService; 
	
	@Autowired
	CartManager cartManager; 
	
	@Autowired
	ProvinceService provinceService; 
	
	
	@Autowired
	DistrictService districtService; 
	
	@Autowired
	VillageService villageService; 
	
	
	@Autowired
	OrderDetailService orderDetailService;
	private static final String EMAIL_REGEX = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$";
	private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);
	
	@Autowired
	OrderService orderService; 
	@GetMapping("/login")
	public String login() {
		return "login"; 
	}

	@GetMapping("/signup")
	public String signUpDefault() {
		return "signup"; 
	}
	
	
	@PostMapping("/signup")
	public String signUp(ModelMap map, @RequestParam String username, @RequestParam String phone, @RequestParam String password, @RequestParam String verifyPassword) {



		boolean checkPhone = userService.checkExistPhoneInfo(phone);
		boolean checkUsername = userService.checkExistUsernameInfo(username);;
		boolean checkUsernameNull=false;
		if(username.isEmpty()){
			checkUsernameNull=true;
		}
		boolean checkPhoneLength = false; 
		boolean verifyPasswordFailed = false; 
		boolean emptyPassword = false; 
		int minLengthPass = 5; 
		
		if(phone.length()!=10) checkPhoneLength = true; 
		if(password.equals(verifyPassword)==false) verifyPasswordFailed = true; 
		if(password.length()<minLengthPass) {
			emptyPassword = true;  
			
		}

		
		if(checkPhone==true || checkUsername==true || checkPhoneLength==true || verifyPasswordFailed==true ||emptyPassword==true || checkUsernameNull==true) {
			map.addAttribute("checkPhone", checkPhone); 
			map.addAttribute("checkUsername", checkUsername); 
			map.addAttribute("phoneLength", checkPhoneLength);
			map.addAttribute("failedPassword", verifyPasswordFailed); 
			map.addAttribute("emptyPassword", emptyPassword);
			map.addAttribute("checkUsernameNull",checkUsernameNull);
			map.addAttribute("messagePass", MessageNotify.VERIFY_PASSWORD_001 +minLengthPass+MessageNotify.VERIFY_PASSWORD_002);
			
			map.addAttribute("username", username);
			map.addAttribute("phone", phone); 
			return "signup"; 
		}
		else {
			boolean checkAdd = userService.addAccount(username, password, phone); 
			
		}
		return "redirect:/account/login";
	}
	
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
		
		
		if(principal!=null) {
			 // Sau khi user login thanh cong se co principal
	        String userName = principal.getName();

	        

	        User loginedUser = (User) ((Authentication) principal).getPrincipal();

	        String userInfo = WebUtils.toString(loginedUser);
	        
	        model.addAttribute("userInfo", userInfo);
	        model.addAttribute("username", userName); 
	        
	        com.ptit.model.User user = userService.getUserByUsername(userName); 
	        model.addAttribute("user", user); 
	        
	        LocalDate date = LocalDate.now(); 
	        int currentYear = date.getYear(); 
	        int minimumYear = currentYear - 80;  
	        model.addAttribute("minimumYear", minimumYear); 
	        model.addAttribute("year", currentYear-16);
	        
	        
	        return "userInfo";
		}else {
			return "redirect:/account/login"; 
		}
       
    }
	
	
	
	@GetMapping(value = "/logout-success")
    public String logoutSuccess(ModelMap model, HttpSession session) {

        cartManager.removeCart(session); 
        model.addAttribute("logoutSuccess", true);

        return "login";
    }
	
	
	
	 @GetMapping(value = "/check-login")
	 public String accessDenied(Model model, Principal principal) {

       if (principal != null) {
           return "redirect:/account/userInfo"; 

       }else {
       	return "redirect:/account/login"; 
       }
       
	 }
	 
//	 @PostMapping("/update")
//	 public String updateInfo(@RequestBody User user) {
//		 System.out.println("gia tri cua user: "+user.toString());
//		 return "userInfo"; 
//	 }
	 
	 @PostMapping("/update")
	 public String updateInfo(@RequestParam String email, @RequestParam String phone, @RequestParam String cccd, @RequestParam boolean gender, @RequestParam int age, Principal principal, Model model) {
		 
		 
		 
		 
		 
		 if(principal!=null) {

			 boolean checkEmailRegex=false;
			 boolean checkPhone = userService.checkExistPhoneInfo(phone, principal.getName()); 
			 boolean checkEmail = userService.checkExistEmailInfo(email, principal.getName());
			 boolean checkCccd = userService.checkExistCCCDInfo(cccd, principal.getName());

			 boolean checkPhoneLength = false; 
			 if(phone.length()!=10) checkPhoneLength = true;
			 if(pattern.matcher(email).matches()==false){
				 checkEmailRegex=true;
			 }
			 
			 com.ptit.model.User user = userService.getUserByUsername(principal.getName()); 
		        user.setEmail(email);
				user.setCccd(cccd);
		        user.setPhone(phone);
		        user.setAge(age); 
		        model.addAttribute("user", user);
		        
		        
		        LocalDate date = LocalDate.now(); 
		        int currentYear = date.getYear(); 
		        int minimumYear = currentYear - 80;  
		        model.addAttribute("minimumYear", minimumYear); 
		        model.addAttribute("year", currentYear-16);
		        
		        
			 if(checkPhone==true || checkEmail==true || checkPhoneLength==true || checkEmailRegex==true || checkCccd==true) {
				
				 
			        model.addAttribute("checkPhone", checkPhone); 
					model.addAttribute("checkEmail", checkEmail);
				 	model.addAttribute("checkCccd", checkCccd);
				 	model.addAttribute("checkPhoneLength", checkPhoneLength);
					model.addAttribute("checkEmailRegex",checkEmailRegex);
					return "userInfo"; 
			 }else {
				
				 boolean checkUpdate = userService.updateUserInfo(principal.getName(), email, cccd, phone, gender, age);
				 return "redirect:/account/userInfo?updateSuccess=true"; 
				 
			 }
			 
			 
			
		 }else {
			 return "redirect:/account/login"; 
		 }
		 
		 
		 
		
	 }
	 
	 @GetMapping("/update-password")
	 public String updatePassword(Principal principal, Model model) {
		 if(principal==null) return "redirect:/account/login"; 
		 model.addAttribute("username", principal.getName()); 
		 return "updatePassword"; 
	 }
	 
	 @GetMapping("/cart")
	 public String cartUser(HttpSession session, Model model, Principal principal ) {
		 if(principal==null) return "redirect:/cart"; 
		 Cart cart = cartManager.getCart(session); 
			List<Items> list = cart.getItems(); 
			model.addAttribute("listItems", list);
			model.addAttribute("totalPrice", cart.getTotal()); 
			
			List<Province> listProvince = provinceService.getAllProvince();
			List<District> listDistrict = districtService.getDistrictByProvince(listProvince.get(0)); 
			List<Village>  listVillage = villageService.getVillageByDistrict(listDistrict.get(0)); 
			model.addAttribute("username", principal.getName());
			model.addAttribute("listProvince", listProvince); 
			model.addAttribute("listDistrict", listDistrict); 
			model.addAttribute("listVillage", listVillage); 
			return "userCart"; 
	 }
	 
	 
	 @GetMapping("/getAllOrder")
		public String getView(ModelMap map) {
			
			
			return "redirect:/account/getAllOrder/1"; 
		}
	 
	 @GetMapping("/getAllOrder/{pageNo}")
		public String getAllByUser(ModelMap model,Principal principal, @PathVariable Optional<String> pageNo) {
			if(principal!=null) {
				int pageSize = 3; 
				String sortDir = "desc"; 
				String sortField = "orderId"; 
				int pageNoOffical = 1; 
				
				
				try {
					pageNoOffical = Integer.parseInt(pageNo.get()); 
					if(pageNoOffical<1) return "redirect:/account/getAllOrder/1"; 
				}catch (Exception e) {
					return "redirect:/account/getAllOrder/1"; 
				}
				
				com.ptit.model.User user = userService.getUserByUsername(principal.getName()); 
				
				
				Page<Order> page = orderService.findPaginatedListOrderFindByUser(pageNoOffical, pageSize, sortField, sortDir, -1,user); 
				List<Order> orders = page.getContent(); 
				
				model.addAttribute("currentPage", pageNoOffical); 
				model.addAttribute("totalPage", page.getTotalPages()); 
				model.addAttribute("pageFirst", 1); 
				
				model.addAttribute("orders", orders); 
				model.addAttribute("username",principal.getName()); 
				return "userOrders"; 
			}
			return "redirect:/account/login"; 
		}
	 
	 @GetMapping("/order-again/{orderId}")
	 public String orderAgain(@PathVariable long orderId, Principal principal, HttpSession session) {
		 
		 if(principal== null) return "redirect:/account/login"; 
		 com.ptit.model.User user = userService.getUserByUsername(principal.getName()); 
		 List<OrderDetail> listDetail = orderDetailService.getListDetailByOrderId(orderId); 
		 //lưu vào list item
		 
		 Cart cart = cartManager.getCart(session); 
		 for(OrderDetail detail : listDetail) {
			 cart.addItem(detail.getBook(), detail.getQuantity()); 
		 }
		
		 return "redirect:/home/synccart?orderAgain=true"; 
	 }
}
	
	
	
	
	
	
	





//	 @RequestMapping(value = "/403", method = RequestMethod.GET)
//	    public String accessDenied(Model model, Principal principal) {
//
//	        if (principal != null) {
//	            User loginedUser = (User) ((Authentication) principal).getPrincipal();
//
//	            String userInfo = WebUtils.toString(loginedUser);
//
//	            model.addAttribute("userInfo", userInfo);
//
//	            String message = "Hi " + principal.getName() //
//	                    + "<br> You do not have permission to access this page!";
//	            model.addAttribute("message", message);
//
//	        }
//
//	        return "403Page";
//	    }
	
//  @RequestMapping(value = "/admin", method = RequestMethod.GET)
//  public String adminPage(Model model, Principal principal) {
//
//      User loginedUser = (User) ((Authentication) principal).getPrincipal();
//
//      String userInfo = WebUtils.toString(loginedUser);
//      model.addAttribute("userInfo", userInfo);
//
//      return "adminPage";
//  }

