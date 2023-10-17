package com.ptit.serviceImp;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ptit.exception.ResourceNotFoundException;
import com.ptit.model.Book;
import com.ptit.model.Category;
import com.ptit.repository.BookDao;
import com.ptit.repository.CategoryDao;
import com.ptit.service.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService{

	@Autowired
	CategoryDao categorydao; 
	
	@Autowired
	BookDao bookDao;
	
	@Override
	public Category getCategoryById(long id) throws ResourceNotFoundException {
		Optional<Category> category= categorydao.findById(id); 
		if(!category.isPresent()) {
			throw new ResourceNotFoundException("Category not found!"); 
		}
		return category.get(); 
	}
	@Override
	public int getTotalCategory() {
		
		return categorydao.findAll().size();
	}
	@Override
	public Page<Category> getAllCategory(Pageable page) {
		return categorydao.findAll(page);   
	}
	@Override
	public List<Category> getAllCategories() {
		return categorydao.findAll(); 
	}
	
	@Override
	public long getCategoryIdByName(String name) throws ResourceNotFoundException {
		Optional<Category> category = categorydao.findByNameAllIgnoreCase(name);
		if(!category.isPresent()) {
			throw new ResourceNotFoundException("Author doesn't exists"); 
		}
		return category.get().getCategoryId();
	}
	
	@Override
	public int save(Category category) {
		categorydao.save(category);
		return categorydao.getLastIdCategory();
	}
	
	@Override
	public Category selectOrUpdateCategory(String name) {
		long id = 0;
		try {
			id=this.getCategoryIdByName(name);
			return categorydao.getById(id);
		} catch (ResourceNotFoundException e) {
			Category newCategory=new Category();
			newCategory.setName(name);
			id=this.save(newCategory);
			return categorydao.getById(id);
		}
	}
	
	@Override
	public Page<Category> findPaginated(int pageNo, int pageSize){
		Sort sort = Sort.by("name").ascending() ;
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return categorydao.findAll(pageable);
	}
	@Override
	public boolean checkNameExitWhenUpdate(String name, long id) {

		List<Category> list = categorydao.findCategoryWhenUpdate(name, id);
		if(list.size() > 0) {
			return true;
		}
		return false;
	}
	@Override
	public boolean checkNameExitWhenInsert(String name) {
		Optional<Category> category = categorydao.findByNameAllIgnoreCase(name);
		if(!category.isPresent()) {
			return false;
		}
		return true;
	}
	@Override
	public boolean checkExitCategoryInBook(Category category) {
		List<Book> list = bookDao.findByCategory(category);
		if(list.size() > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public int deleteById(long id) {
		categorydao.deleteById(id);
		return 1;
	}
	
	@Override
	public Page<Category> findCategory(String key,int pageNo, int pageSize){
		Sort sort = Sort.by("name").ascending() ;
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return categorydao.findByNameContainsAllIgnoreCaseOrderByNameAsc(key,pageable);
	}
}
