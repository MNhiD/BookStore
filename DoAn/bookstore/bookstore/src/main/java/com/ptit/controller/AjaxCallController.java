package com.ptit.controller;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.ptit.model.*;
import com.ptit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ptit.exception.OverMaximumQuantityException;
import com.ptit.exception.ResourceNotFoundException;
import com.ptit.serviceImp.ItemsServiceImp;

@RestController
public class AjaxCallController {
	@Autowired
	UserService userService;

	@Autowired
	BookService bookService;

	@Autowired
	CartManager cartManager;

	@Autowired
	ItemsService itemserService;

	@Autowired
	ProvinceService provinceService;

	@Autowired
	DistrictService districtService;

	@Autowired
	VillageService villageService;

	@Autowired
	AddressService addressService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetailService orderDetailService;
	
	@Autowired
	ReviewService reviewService;

	@Autowired
	 PurchasingOrderService purchasingOrderService;
	@Autowired
	PurchasingService purchasingService;
	@PostMapping("/verify-old-password")
	@ResponseBody
	public String checkOldPassword(@RequestParam String password, Principal principal) {

		if (userService.verifyOldPassword(password, principal.getName()))
			return "true";

		return "false";
	}

	@PostMapping("/update-new-password")
	@ResponseBody
	public String updateNewPassword(@RequestParam String password, @RequestParam String verifyPassword,
			Principal principal) {

		if (password.length() <5)
			return "1";
		if (password.equals(verifyPassword) == false)
			return "2";

		if (userService.updatePassword(password, principal.getName()))
			return "0";

		return "3";
	}

	@PostMapping("/add-to-cart")
	@ResponseBody
	public String addToCart(HttpSession session, @RequestParam long id, @RequestParam Optional<Integer> quantity,
			Principal principal) {
		Book book = null;
		try {
			book = bookService.getBookById(id);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		if (book.getTotalQuantity() == 0) {
			return "false";
		} else {
			int quantityofBook = quantity.orElse(1);
			Cart cart = cartManager.getCart(session);
			int action = cart.addItem(book, quantityofBook); // 1: update 2:insert

			if (action == 1 && principal != null) {
				String username = principal.getName();
				User user = userService.getUserByUsername(username);
				itemserService.updateItemsCartIncreaseQuantity(user, book, quantityofBook);

			} else if (action == 2 && principal != null) {

				String username = principal.getName();
				User user = userService.getUserByUsername(username);
				itemserService.addNewItems(user, book, quantityofBook);

			}

			return "true";
		}
	}

	@PostMapping("/update-cart")
	@ResponseBody
	public String updateCart(HttpSession session, @RequestParam long id, @RequestParam Optional<Integer> quantity,
			Principal principal) {
		Book book = null;
		try {
			book = bookService.getBookById(id);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		int quantityofBook = quantity.orElse(1);
		int maximumQuantity = book.getTotalQuantity();
		if (quantityofBook > maximumQuantity) {
			return "1"; // vượt quá số lượng hiện có
		}
		Cart cart = cartManager.getCart(session);
		cart.updateItem(book, quantityofBook);

		if (principal != null) {
			User user = userService.getUserByUsername(principal.getName());
			itemserService.updateItemsCartChangeQuantity(user, book, quantityofBook);
		}

		return cart.getTotal() + "";
	}

	@PostMapping("/remove-item")
	@ResponseBody
	public String removeItem(HttpSession session, @RequestParam long id, Principal principal) {
		Book book = null;
		try {
			book = bookService.getBookById(id);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		Cart cart = cartManager.getCart(session);
		cart.removeItem(book);
		if (principal != null) {
			User user = userService.getUserByUsername(principal.getName());
			itemserService.deleteItems(user, book);
		}
		return cart.getTotal() + "";
	}

	@PostMapping("/check-can-buy")
	public boolean canBuy(HttpSession session) {
		
		Cart cart = cartManager.getCart(session); 
		
		BigDecimal zeroValue = new BigDecimal(0); 
		if(cart.getTotal().compareTo(zeroValue)==0) return false; 
		return true; 
	}
	
	
	
	@PostMapping("/get-district")
	@ResponseBody
	public String findAllDistrictByProvince(@RequestParam String provinceId) {
		Province province = provinceService.getProvinceById(provinceId);
		List<District> listDistrict = districtService.getDistrictByProvince(province);
		String districtStr =""; 
		
		districtStr+="<select id=\"dropdown-district\" class=\"form-select\" aria-label=\"Default select example\" name=\"district\">\n"; 
		for (District d : listDistrict) {

			districtStr+="<option value=\"" + d.getDistrictId() + "\"> " + d.getDistrictName() + "</option> \n";
		}
		districtStr+="</select> "; 

		return districtStr;
	}

	@PostMapping("/get-village")
	@ResponseBody
	public String findAllVillageByDistrict(@RequestParam String districtId) {
		District district = districtService.getDistrictById(districtId);
		List<Village> listVillage = villageService.getVillageByDistrict(district);
		String villageStr = "";
		villageStr +="<select id=\"dropdown-village\" class=\"form-select\" aria-label=\"Default select example\" name=\"village\">\n";
		for (Village v : listVillage) {

			villageStr += "<option value=\"" + v.getVillageId() + "\"> " + v.getVillageName() + "</option> \n";
		}

		return villageStr;
	}

	@PostMapping("/order")
	public String order(@RequestParam String fullname, @RequestParam String phone, @RequestParam String village, @RequestParam String address, HttpSession session, Principal principal) {
		
		 
		//kiểm tra số lượng hàng tồn còn đủ hay không?
		
		Cart cart  = cartManager.getCart(session);
		List<Items> list = cart.getItems(); 
		User user=null; 
		if(principal!=null) {
			 user = userService.getUserByUsername(principal.getName()); 
		}
		boolean  result=false;
		try {
			result = orderService.createNewOrder(fullname, phone, user, list, cart.getTotal(), address, village);
		} catch (OverMaximumQuantityException e) {
			return e.getMessage(); 
		} 
		if(result) {
			
			cartManager.removeCart(session); 
			if(principal!=null) {
				itemserService.deleteItemsAfterBuy(user); 
			}
			
			return "true"; 
		}
		return "false";
	}
	@PostMapping("/getAllBook")
	public List<Book> GetAllBook(){

		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		Page<Book> lstPageBook = bookService.getAllBooks(pageable);
		List<Book> lstBook = lstPageBook.getContent();
		return lstBook;
	}
	@PostMapping("/getBookValue")
	public StringBuffer GetBookByValue(@RequestParam String strValue){

		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		Page<Book> lstPageBook =  bookService.findByBookNameContains(strValue, 1, Integer.MAX_VALUE, "bookName", "desc");
		List<Book> lstBook = lstPageBook.getContent();
		if(lstBook != null && lstBook.size() > 0){
			StringBuffer stringBuffer = new StringBuffer();
			for(Book item : lstBook){
				stringBuffer.append("<span id='"+item.getIdBook()+"' price='"+item.getPrice()+"' onclick='AddItem(event)' >"+ item.getBookName()+"</span></br>");
			}
			return  stringBuffer;
		}
		return new StringBuffer();
	}

	@PostMapping("/savePurchasingOrder")
	public List<Purchasing> GetBookByValue(@RequestBody List<PurchasingOrderDTO> lstOrder){

		if(lstOrder.size() > 0){

			int id_ncc = lstOrder.stream().findFirst().get().id_ncc; // lấy ra
			Purchasing purchasing = new Purchasing();
			purchasing.setId_supplier(id_ncc);
			purchasing.setStatus(1);
			int insert = purchasingService.save(purchasing); // lưu bảng cha
			int id = purchasingService.get();
			if(id > 0){
				for(PurchasingOrderDTO orderDTO : lstOrder){
					// lưu tất cả bảng con
					PurchasingOrder order = new PurchasingOrder();
					order.setId_book(orderDTO.Id);
					BigDecimal temDecimal = new BigDecimal(orderDTO.Price);
					order.setPrice(temDecimal);
					order.setQuantity(orderDTO.Quantity);
					order.setId_purchasing(id);
					int result = purchasingOrderService.save(order); //lưu xong cập nhât bảng book
					// cập nhật thêm SL vào bảng book , truyền id book vs SL vô
					bookService.UpdateQuantityBook(orderDTO.Id,orderDTO.Quantity);

				}
			}


		}

		return purchasingService.getAllOrder();
	}
	@PostMapping("/get-detail-purchasing")
	public List<PurchasingOrder> getDetailPurchasing(@RequestParam long id){
		return purchasingOrderService.getOrderById(id);
	}
	@PostMapping("/order-detail")
	public StringBuffer orderDetail(@RequestParam long orderId, Principal principal) {
		
		List<OrderDetail> list = orderDetailService.getListDetailByOrderId(orderId); 
		StringBuffer buffer = new StringBuffer(); 
		
		buffer.append("<div class=\"col-md-4 col-sm-12\" style=\"display: none\" id=\"order-detail-block\"> ");
		buffer.append("<div class=\"row\">");
		
		buffer.append("<div class=\"col-md-1\"><div class=\"vl\"\n"
				+ "	style=\"border-left: 6px solid green; height: 500px;\"></div></div>");
		buffer.append("<div class=\"col-md-11 col-sm-12\" style=\"margin-top: 10%; \" > \n");
		buffer.append("<h1 style=\"color: red\">Chi tiết</h1>"); 
		for(OrderDetail detail : list) {
			
			buffer.append("<p class=\"book\"> <span style=\"font-size: 20px; font-weight: bold\"><i class=\"fas fa-book\"></i>"+" "+detail.getBook().getBookName() +"</span></p>\n");
			buffer.append("<p class=\"book-price\">Đơn giá: <span style=\"font-weight:bold\">"+ detail.getPrice()+"</span></p>\n");
			buffer.append("<p style=\" margin-right: 20px\"> Số lượng: <span style=\"font-weight:bold\">"+detail.getQuantity()+"</span></p>"); 
			buffer.append("<a style=\"text-decoration: none; margin-left: 10%; \" href=\"\\book\\"+detail.getBook().getIdBook()+"\" >\n"
					+ "								<button type=\"button\" class=\"btn btn-info\">Xem sản phẩm</button></a> <hr>");
			 
		}
		buffer.append("<a href=\"/account/order-again/"+orderId+"\" style=\"margin-left: 25%; text-decoration: none\"><button id=\"btn-order-again\" th:data-orderid = \"sang\" type=\"button\" class=\"btn btn-danger\">Mua lại đơn hàng</button></a>");

		buffer.append("</div>\n");
		buffer.append("</div>\n");
		buffer.append("</div>\n"); 
		return buffer; 
	}
	
	
	@PostMapping("/cancel-order")
	public void cancelOrder(@RequestParam long orderId) {
		orderService.cancelOrder(orderId); 
		
	}
	
	
	@PostMapping("/review")
	public void review(@RequestParam long idBook, @RequestParam String comment, @RequestParam float star, Principal principal) {
		String username = principal.getName(); 
		User user = userService.getUserByUsername(username); 
		
		try {
			Book book = bookService.getBookById(idBook);
			reviewService.addReview(user, book, star, comment); 
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
	
	@PostMapping("/check-quantity")
	@ResponseBody
	public String checkQuantity(HttpSession session, @RequestParam long id, @RequestParam Optional<Integer> quantity) {
		Book book = null;
		try {
			book = bookService.getBookById(id);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		int quantityofBook = quantity.orElse(1);
		int maximumQuantity = book.getTotalQuantity();
		if (quantityofBook > maximumQuantity) {
			return maximumQuantity+""; // vượt quá số lượng hiện có
		}
		
		return "-1"; //hợp lệ
	}
}



















