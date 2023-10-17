package com.ptit.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ptit.model.MessageNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.ptit.exception.ResourceNotFoundException;
import com.ptit.model.Author;
import com.ptit.model.Book;
import com.ptit.model.Category;
import com.ptit.repository.AuthorDao;
import com.ptit.service.AuthorService;

@Controller
@RequestMapping("/admin")
public class AuthorController {
	
	@Autowired
	AuthorService authorService;
	
	@Autowired
	AuthorDao authorDao;
	
	
	@GetMapping("/author")
	public String getHomeauthor(Model model) {
		model.addAttribute("author", new Author());
		return getauthor(model, 1);
	}
	

	@GetMapping("/author/{pageNo}")
	public String getauthor(Model model, @PathVariable(value = "pageNo") int pageNo) {
		int pageSize = 6;
		int pageFirst = 1;
		model.addAttribute("author", new Author());
		Page<Author> page = authorService.findPaginated(pageNo, pageSize);

		List<Author> listauthor = page.getContent();
		model.addAttribute("listauthor", listauthor);

		model.addAttribute("pageFirst", pageFirst);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItem", page.getTotalElements());

		return "/admin/author";
	}

	@GetMapping("/author/search")
	public String searchDefault(Model model,@RequestParam String searchvalue, ModelMap map) {
		model.addAttribute("book", new Book());
		return getAuthorSearch(model, 1,searchvalue);
		
	}
	
	
	@GetMapping("/author/search/{pageNo}")
	public String getAuthorSearch(Model model, @PathVariable(value = "pageNo") int pageNo,@RequestParam String searchvalue) {
		int pageSize = 6;
		int pageFirst = 1;
		model.addAttribute("author", new Author());
		Page<Author> page = authorService.findAuthor(searchvalue,pageNo, pageSize);

		List<Author> listauthor = page.getContent();
		model.addAttribute("listauthor", listauthor);

		model.addAttribute("pageFirst", pageFirst);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItem", page.getTotalElements());

		return "/admin/author";
	}
	
	@PostMapping("/author/save")
	public String saveauthor(@ModelAttribute("author") Author author,
			RedirectAttributes ra)
			throws IOException, ResourceNotFoundException {
		Optional<Author> author2 = authorDao.findById(author.getIdAuthor());
		
		if(!author2.isPresent()) {
			boolean checkName = authorService.checkNameExitWhenInsert(author.getName());
			// nếu tồn tại
			if(checkName) {
				ra.addFlashAttribute("erorrMes", MessageNotify.AUTHOR_SAVE_FAILED_001+author.getName()+MessageNotify.AUTHOR_SAVE_FAILED_002);
			}else { // nếu không tồn tại
				authorService.save(author);
				ra.addFlashAttribute("successMes", MessageNotify.AUTHOR_SAVE_SUCCESS);
			}
		}else {
			boolean checkName = authorService.checkNameExitWhenUpdate(author.getName(), author.getIdAuthor());
			// nếu tồn tại
			if(checkName) {
				ra.addFlashAttribute("erorrMes", MessageNotify.AUTHOR_EDIT_FAILED_001+author.getName()+MessageNotify.AUTHOR_EDIT_FAILED_002);
			}else { // nếu không tồn tại
				authorService.save(author);
				ra.addFlashAttribute("successMes", MessageNotify.AUTHOR_EDIT_SUCCESS);
			}
		}
		return "redirect:/admin/author";
	}
	
	@PostMapping("/author/delete")
	public String deleteauthor(@RequestParam("id") Long id,RedirectAttributes ra) {
		
		try {
			if(authorService.checkExitAuthorInBook(authorService.getAuthorById(id))) {
				// nếu như tồn tại
				ra.addFlashAttribute("erorrMes", MessageNotify.AUTHOR_DEL_FAILED);
			}
			else {
				authorService.deleteById(id);
				ra.addFlashAttribute("successMes", MessageNotify.AUTHOR_DEL_SUCCESS);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/admin/author";
	}
}
