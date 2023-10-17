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
import com.ptit.model.PublishingCompany;
import com.ptit.repository.BookDao;
import com.ptit.repository.PublishingCompanyDao;
import com.ptit.service.PublishingCompanyService;
@Service
public class PublishingCompanyServiceImp implements PublishingCompanyService{

	@Autowired
	PublishingCompanyDao publishingCompanyDao;
	
	@Autowired
	BookDao bookDao;
	
	@Override
	public List<PublishingCompany> getAllCompanyService() {
		// TODO Auto-generated method stub
		return publishingCompanyDao.findAll();
	}

	@Override
	public int getTotalCompanyService() {
		// TODO Auto-generated method stub
		return publishingCompanyDao.findAll().size();
	}

	@Override
	public long getCompanyIdByName(String name) throws ResourceNotFoundException{
		Optional<PublishingCompany> company=publishingCompanyDao.findByNameAllIgnoreCase(name);
		if(!company.isPresent()) {
			throw new ResourceNotFoundException("Author doesn't exists"); 
		}
		return company.get().getIdCompany();
	}
	
	@Override
	public int save(PublishingCompany company) {
		publishingCompanyDao.save(company);
		return publishingCompanyDao.getLastIdCompany();
	}
	
	@Override
	public PublishingCompany selectOrUpdateCompany(String name) {
		long id = 0;
		try {
			id=this.getCompanyIdByName(name);
			return publishingCompanyDao.getById(id);
		} catch (ResourceNotFoundException e) {
			PublishingCompany newComany=new PublishingCompany();
			newComany.setName(name);
			id=this.save(newComany);
			return publishingCompanyDao.getById(id);
		}
	}

	@Override
	public PublishingCompany getCompanyById(long id) {
		return publishingCompanyDao.getById(id);
	}

	@Override
	public Page<PublishingCompany> getAllPublishingCompany(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PublishingCompany> findPaginated(int pageNo, int pageSize) {
		Sort sort = Sort.by("name").ascending() ;
		
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return publishingCompanyDao.findAll(pageable);

	}

	@Override
	public boolean checkNameExitWhenInsert(String name) {
		Optional<PublishingCompany> company = publishingCompanyDao.findByNameAllIgnoreCase(name);
		if(!company.isPresent()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkNameExitWhenUpdate(String name, long id) {
		List<PublishingCompany> list = publishingCompanyDao.findPublishingCompanyWhenUpdate(name, id);
		if(list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkExitPublishingCompanyInBook(PublishingCompany publishingCompany) {
		List<Book> list = bookDao.findByCompany(publishingCompany);
		if(list.size() > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public int deleteById(long id) {
		publishingCompanyDao.deleteById(id);
		return 1;
	}
	
	@Override
	public Page<PublishingCompany> findCompany(String key,int pageNo, int pageSize){
		Sort sort = Sort.by("name").ascending() ;
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return publishingCompanyDao.findByNameContainsAllIgnoreCaseOrderByNameAsc(key,pageable);
	}
}
