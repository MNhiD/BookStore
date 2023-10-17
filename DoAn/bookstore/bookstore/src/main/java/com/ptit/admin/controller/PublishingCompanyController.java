package com.ptit.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ptit.model.MessageNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PostAuthorize;
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
import com.ptit.model.PublishingCompany;
import com.ptit.repository.PublishingCompanyDao;
import com.ptit.service.PublishingCompanyService;

@Controller
@RequestMapping("/admin")
public class PublishingCompanyController {
	
	
	@Autowired
	PublishingCompanyService publishingCompanyService;
	
	@Autowired
	PublishingCompanyDao publishingCompanyDao;
	
	@GetMapping("/company")
	public String getHomecompany(Model model) {
		model.addAttribute("company", new PublishingCompany());
		return getcompany(model, 1);
	}
	

	@GetMapping("/company/{pageNo}")
	public String getcompany(Model model, @PathVariable(value = "pageNo") int pageNo) {
		int pageSize = 6;
		int pageFirst = 1;
		model.addAttribute("company", new PublishingCompany());
		Page<PublishingCompany> page = publishingCompanyService.findPaginated(pageNo, pageSize);

		List<PublishingCompany> listCompany = page.getContent();
		model.addAttribute("listCompany", listCompany);

		model.addAttribute("pageFirst", pageFirst);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItem", page.getTotalElements());

		return "/admin/company";
	}
	
	@GetMapping("/company/search")
	public String searchDefault(Model model,@RequestParam String searchvalue, ModelMap map) {
		model.addAttribute("book", new Book());
		return getCompanySearch(model, 1,searchvalue);
		
	}
	
	
	@GetMapping("/company/search/{pageNo}")
	public String getCompanySearch(Model model, @PathVariable(value = "pageNo") int pageNo,@RequestParam String searchvalue) {
		int pageSize = 6;
		int pageFirst = 1;
		model.addAttribute("company", new PublishingCompany());
		Page<PublishingCompany> page = publishingCompanyService.findCompany(searchvalue,pageNo, pageSize);

		List<PublishingCompany> listCompany = page.getContent();
		model.addAttribute("listCompany", listCompany);

		model.addAttribute("pageFirst", pageFirst);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItem", page.getTotalElements());

		return "/admin/company";
	}

	@PostMapping("/company/save")
	public String savecompany(@ModelAttribute("publishingCompany") PublishingCompany publishingCompany,
			RedirectAttributes ra)
			throws IOException, ResourceNotFoundException {
		Optional<PublishingCompany> company2 = publishingCompanyDao.findById(publishingCompany.getIdCompany());
		
		if(!company2.isPresent()) {
			boolean checkName = publishingCompanyService.checkNameExitWhenInsert(publishingCompany.getName());
			// nếu tồn tại
			if(checkName) {
				ra.addFlashAttribute("erorrMes", MessageNotify.PUBLISHINGCOMPANY_SAVE_FAILED_001+publishingCompany.getName()+MessageNotify.PUBLISHINGCOMPANY_SAVE_FAILED_002);
			}else { // nếu không tồn tại
				publishingCompanyService.save(publishingCompany);
				ra.addFlashAttribute("successMes", MessageNotify. PUBLISHINGCOMPANY_SAVE_SUCCESS);
			}
		}else {
			boolean checkName = publishingCompanyService.checkNameExitWhenUpdate(publishingCompany.getName(), publishingCompany.getIdCompany());
			// nếu tồn tại
			if(checkName) {
				ra.addFlashAttribute("erorrMes", MessageNotify.PUBLISHINGCOMPANY_EDIT_FAILED_001+publishingCompany.getName()+MessageNotify.PUBLISHINGCOMPANY_EDIT_FAILED_002);
			}else { // nếu không tồn tại
				publishingCompanyService.save(publishingCompany);
				ra.addFlashAttribute("successMes", MessageNotify.PUBLISHINGCOMPANY_EDIT_SUCCESS);
			}
		}
		
		return "redirect:/admin/company";
	}
	
	@PostMapping("/company/delete")
	public String deletecompany(@RequestParam("id") Long id,RedirectAttributes ra) {
		
		try {
			if(publishingCompanyService.checkExitPublishingCompanyInBook(publishingCompanyService.getCompanyById(id))) {
				// nếu như tồn tại
				ra.addFlashAttribute("erorrMes", MessageNotify.PUBLISHINGCOMPANY_DEL_FAILED);
			}else {
				publishingCompanyService.deleteById(id);
				ra.addFlashAttribute("successMes", MessageNotify.PUBLISHINGCOMPANY_DEL_SUCCESS);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/admin/company";
	}
}
