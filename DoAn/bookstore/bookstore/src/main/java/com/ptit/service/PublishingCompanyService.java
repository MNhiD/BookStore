package com.ptit.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ptit.exception.ResourceNotFoundException;
import com.ptit.model.Category;
import com.ptit.model.PublishingCompany;

public interface PublishingCompanyService {
	public List<PublishingCompany> getAllCompanyService();
	public int getTotalCompanyService();
	public long getCompanyIdByName(String name) throws ResourceNotFoundException;;
	public int save(PublishingCompany company);
	public PublishingCompany selectOrUpdateCompany(String name);
	public PublishingCompany getCompanyById(long id); 
	
	public Page<PublishingCompany> getAllPublishingCompany(Pageable page);
	
	public Page<PublishingCompany> findPaginated(int pageNo, int pageSize);
	
	public boolean checkNameExitWhenUpdate(String name,long id);
	public boolean checkNameExitWhenInsert(String name);
	
	public boolean checkExitPublishingCompanyInBook(PublishingCompany category);
	
	public int deleteById(long id);
	public Page<PublishingCompany> findCompany(String key,int pageNo, int pageSize); 
}
