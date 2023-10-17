package com.ptit.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import com.ptit.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ptit.exception.ResourceNotFoundException;
import com.ptit.service.AuthorService;
import com.ptit.service.BookService;
import com.ptit.service.CategoryService;
import com.ptit.service.PublishingCompanyService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BookController {

	@Autowired
	PublishingCompanyService publishingCompanyService;

	@Autowired
	AuthorService authorService;

	@Autowired
	BookService bookService;

	@Autowired
	CategoryService categoryService;
	@Autowired
	CartManager cartManager;
	
	

	@GetMapping("/book")
	public String getHomeBook(Model model, HttpSession session) {
		model.addAttribute("book", new Book());
		int cartNumber = 0;
		Cart cart = cartManager.getCart(session);
		if(cart != null && cart.getItems().size() > 0){
			cartNumber = cart.getItems().size();
		}
		model.addAttribute("cardNumber", cartNumber);
		return getBook(model, 1, "bookName", "asc");
	}
	

	@GetMapping("/book/{pageNo}")
	public String getBook(Model model, @PathVariable(value = "pageNo") int pageNo,
						  @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {
		int pageSize = 5;
		int pageFirst = 1;
		model.addAttribute("book", new Book());
		Page<Book> page = bookService.findPaginated(pageNo, pageSize, sortField, sortDir);

		List<Book> listBook = page.getContent();
		model.addAttribute("listBook", listBook);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("pageFirst", pageFirst);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItem", page.getTotalElements());


		// get Publishing Company
		model.addAttribute("listCompany", publishingCompanyService.getAllCompanyService());

		// Get category to view
		List<Category> listCategory = categoryService.getAllCategories();
		model.addAttribute("listCategory", listCategory);

		// get author
		model.addAttribute("listAuthor", authorService.getAllAuthor());

		return "/admin/book";
	}

	@PostMapping("/book/save")
	public String saveBook(@ModelAttribute("book") Book book, 
			RedirectAttributes ra,
			@RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam(name="authorName") String authorName,
			@RequestParam(name="companyName") String companyName,
			@RequestParam(name="categoryName") String categoryName)
			throws IOException {
		
			
		
		String fileName = StringUtils.cleanPath(fileImage.getOriginalFilename());
		System.out.println(fileName.toString()+" dd");
		if(!fileName.isEmpty()) {//
			book.setPicture(fileName);
			System.out.println(fileName.toString()+" go dd");
			String uploadDir = "./src/main/resources/static/image/";
	
			Path uploadPath = Paths.get(uploadDir);
	
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
	
			try (InputStream inputStream = fileImage.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				System.out.println(filePath.toFile().getAbsolutePath());
	
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				ra.addFlashAttribute("successMes", MessageNotify.BOOK_SAVE_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				book.setPicture(bookService.getBookById(book.getIdBook()).getPicture());
				ra.addFlashAttribute("successMes", MessageNotify.BOOK_EDIT_SUCCESS);
			} catch (ResourceNotFoundException e) {
				//do nothing
			}
		}
		book.setAuthor(authorService.selectOrUpdateAuthor(authorName));
		book.setCategory(categoryService.selectOrUpdateCategory(categoryName));
		book.setCompany(publishingCompanyService.selectOrUpdateCompany(companyName));
		bookService.save(book);
		
		
//		System.out.println(authorService.selectOrUpdateAuthor(authorName).getIdAuthor()+" "+authorService.selectOrUpdateAuthor(authorName).getName());
//		System.out.println(categoryName+" "+categoryService.getCategoryIdByName(categoryName));
//		System.out.println(companyName+" "+publishingCompanyService.getCompanyIdByName(companyName));
		return "redirect:/admin/book";
	}
	
	@PostMapping("/book/delete")
	public String deleteBook(@RequestParam("id") Long idBook,RedirectAttributes ra) {
		
		int status=bookService.deleteById(idBook);
		if(status==0) ra.addFlashAttribute("erorrMes", MessageNotify.BOOK_DEL_FAILED_001);
		else if(status==1) ra.addFlashAttribute("erorrMes", MessageNotify.BOOK_DEL_FAILED_002);
		else if(status==2) ra.addFlashAttribute("erorrMes", MessageNotify.BOOK_DEL_FAILED_003);
		else ra.addFlashAttribute("successMes", MessageNotify.BOOK_DEL_SUCCESS);
		return "redirect:/admin/book";
	}
	
	@GetMapping("/book/search")
	public String searchDefault(Model model,@RequestParam String searchvalue, ModelMap map) {
		model.addAttribute("book", new Book());
		return getBookSearch(model, 1, "bookName", "asc",searchvalue);
		
	}
	
	
	@GetMapping("/book/search/{pageNo}")
	public String getBookSearch(Model model, @PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir,@RequestParam String searchvalue) {
		int pageSize = 5;
		int pageFirst = 1;
		model.addAttribute("book", new Book());
		Page<Book> page = bookService.findBook(searchvalue, pageNo, pageSize, sortField, sortDir);

		List<Book> listBook = page.getContent();
		model.addAttribute("listBook", listBook);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("pageFirst", pageFirst);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItem", page.getTotalElements());

		// get Publishing Company
		model.addAttribute("listCompany", publishingCompanyService.getAllCompanyService());

		// Get category to view
		List<Category> listCategory = categoryService.getAllCategories();
		model.addAttribute("listCategory", listCategory);

		// get author
		model.addAttribute("listAuthor", authorService.getAllAuthor());

		return "/admin/book";
	}
	
	
	@GetMapping
	public String admin() {
		return "tempadmin"; 
	}
}
