package com.ptit.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ptit.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptit.exception.ResourceNotFoundException;

import com.ptit.repository.PublishingCompanyDao;
import com.ptit.service.AuthorService;
import com.ptit.service.BookService;
import com.ptit.service.CategoryService;
import com.ptit.service.PublishingCompanyService;
import com.ptit.service.ReviewService;
import com.ptit.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/book")
public class BookDetailController {

	@Autowired
	BookService bookservice; 
	 
	@Autowired
	AuthorService authorService; 
	
	@Autowired
	CategoryService categoryService; 
	
	@Autowired
	PublishingCompanyService companyService; 
	
	@Autowired
	ReviewService reviewService; 
	
	@Autowired
	UserService userService;
	@Autowired
	CartManager cartManager;
	
		@GetMapping("/{id}")
		public String productDetail(@PathVariable long id, ModelMap map, Principal principal, HttpSession session) throws ResourceNotFoundException  {
			
			Book book;
			Author author; 
			PublishingCompany company; 
			List<Review> listReviews; 
			try {
				book = bookservice.getBookById(id);
				author = authorService.getAuthorById(book.getAuthor().getIdAuthor()); 
				company = companyService.getCompanyById(book.getCompany().getIdCompany()); 
				listReviews = reviewService.getAllReviewViaBook(book); 
				
				float star = 0; 
				for(Review r: listReviews) {
					star +=r.getStar(); 
					
				}
				
				map.addAttribute("star", Math.ceil(star/listReviews.size())); 
			} catch (ResourceNotFoundException e) {
				return "notfound"; 
			}
			int cartNumber = 0;
			Cart cart = cartManager.getCart(session);
			if(cart != null && cart.getItems().size() > 0){
				cartNumber = cart.getItems().size();
			}
			List<Category> listCategory = categoryService.getAllCategories();
			boolean login = false; 
			if(principal!=null) {
				login = true; 
				String username = principal.getName(); 
				User user = userService.getUserByUsername(username); 
				boolean checkReviewed = reviewService.checkReviewed(user, book);
				if(checkReviewed==true) map.addAttribute("reviewed", true); 
			}
			map.addAttribute("login", login); 
			map.addAttribute("book", book); 
			map.addAttribute("author", author); 
			map.addAttribute("company", company); 
			map.addAttribute("categories", listCategory);
			map.addAttribute("cardNumber", cartNumber);
			map.addAttribute("reviews", listReviews); 
			
			List<Book> listAuthorBook= bookservice.getBookByAuthor(author);
			List<Book> listCategoryBook = bookservice.getBookByCategory(book.getCategory()); 
			listAuthorBook.remove(book);
			listCategoryBook.remove(book);
			map.addAttribute("listAuthorBook", listAuthorBook); 
			map.addAttribute("listCategoryBook", listCategoryBook); 
			
			String[] listDescription=book.getDescribeBook().split("\n");
			map.addAttribute("listDescription", listDescription); 
			return "product-detail"; 
		}
		
//		public String bookByCategory(String categoryId) {
//			List<Book> list = bookservice.getBookByCategory(categoryId); 
//			
//		}
}



















