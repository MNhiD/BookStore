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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ptit.exception.ResourceNotFoundException;
import com.ptit.repository.CategoryDao;
import com.ptit.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CategoryDao categoryDao;
	

	@GetMapping("/category")
	public String getHomeCategory(Model model) {
		model.addAttribute("category", new Category());
		return getCategory(model, 1);
	}
	

	@GetMapping("/category/{pageNo}")
	public String getCategory(Model model, @PathVariable(value = "pageNo") int pageNo) {
		int pageSize = 6;
		int pageFirst = 1;
		model.addAttribute("category", new Category());
		Page<Category> page = categoryService.findPaginated(pageNo, pageSize);

		List<Category> listCategory = page.getContent();
		model.addAttribute("listCategory", listCategory);

		model.addAttribute("pageFirst", pageFirst);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItem", page.getTotalElements());

		return "/admin/category";
	}
	
	@GetMapping("/category/search")
	public String searchDefault(Model model,@RequestParam String searchvalue, ModelMap map) {
		model.addAttribute("book", new Book());
		return getCategorySearch(model, 1,searchvalue);
		
	}
	
	
	@GetMapping("/category/search/{pageNo}")
	public String getCategorySearch(Model model, @PathVariable(value = "pageNo") int pageNo,@RequestParam String searchvalue) {
		int pageSize = 6;
		int pageFirst = 1;
		model.addAttribute("category", new Category());
		Page<Category> page = categoryService.findCategory(searchvalue,pageNo, pageSize);

		List<Category> listCategory = page.getContent();
		model.addAttribute("listCategory", listCategory);

		model.addAttribute("pageFirst", pageFirst);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItem", page.getTotalElements());

		return "/admin/category";
	}

	@PostMapping("/category/save")
	public String saveCategory(@ModelAttribute("category") Category category,
			RedirectAttributes ra)
			throws IOException, ResourceNotFoundException {
		Optional<Category> category2 = categoryDao.findById(category.getCategoryId());
		
		if(!category2.isPresent()) {
			boolean checkName = categoryService.checkNameExitWhenInsert(category.getName());
			// nếu tồn tại
			if(checkName) {
				ra.addFlashAttribute("erorrMes", MessageNotify.CATEGORY_SAVE_FAILED_001+category.getName()+MessageNotify.CATEGORY_SAVE_FAILED_002);
			}else { // nếu không tồn tại
				categoryService.save(category);
				ra.addFlashAttribute("successMes", MessageNotify.CATEGORY_SAVE_SUCCESS);
			}
		}else {
			boolean checkName = categoryService.checkNameExitWhenUpdate(category.getName(), category.getCategoryId());
			// nếu tồn tại
			if(checkName) {
				ra.addFlashAttribute("erorrMes", MessageNotify.CATEGORY_EDIT_FAILED_001+category.getName()+MessageNotify.CATEGORY_EDIT_FAILED_002);
			}else { // nếu không tồn tại
				categoryService.save(category);
				ra.addFlashAttribute("successMes", MessageNotify.CATEGORY_EDIT_SUCCESS);
			}
		}
		return "redirect:/admin/category";
	}
	
	@PostMapping("/category/delete")
	public String deleteCategory(@RequestParam("id") Long id,RedirectAttributes ra) {
		
		try {
			if(categoryService.checkExitCategoryInBook(categoryService.getCategoryById(id))) {
				// nếu như tồn tại
				ra.addFlashAttribute("erorrMes", MessageNotify.CATEGORY_DEL_FAILED);
				return "redirect:/admin/category";
			}
			else {
				categoryService.deleteById(id);
				ra.addFlashAttribute("successMes", MessageNotify.CATEGORY_DEL_SUCCESS);
			}
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/admin/category";
	}
	
	
}
