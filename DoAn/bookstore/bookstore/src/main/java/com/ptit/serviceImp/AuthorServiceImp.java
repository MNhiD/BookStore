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
import com.ptit.model.Author;
import com.ptit.model.Book;
import com.ptit.repository.AuthorDao;
import com.ptit.repository.BookDao;
import com.ptit.service.AuthorService;
@Service
public class AuthorServiceImp implements AuthorService{
	
	@Autowired
	AuthorDao authorDao;
	
	@Autowired
	BookDao bookDao;

	@Override
	public List<Author> getAllAuthor() {
		// TODO Auto-generated method stub
		return authorDao.findAll();
	}

	@Override
	public int getTotalAuthor() {
		// TODO Auto-generated method stub
		return authorDao.findAll().size();
	}
	
	@Override
	public Author getAuthorByName(String name) throws ResourceNotFoundException {
		Optional<Author> author=authorDao.findByNameAllIgnoreCase(name);
		if(!author.isPresent()) {
			throw new ResourceNotFoundException("Author doesn't exists"); 
		}
		return author.get();
	}
	
	@Override
	public long getAuthorIdByName(String name) throws ResourceNotFoundException {
		Optional<Author> author=authorDao.findByNameAllIgnoreCase(name);
		if(!author.isPresent()) {
			throw new ResourceNotFoundException("Author doesn't exists"); 
		}
		return author.get().getIdAuthor();
	}
	
	@Override
	public int save(Author author) {
		authorDao.save(author);
		return authorDao.getLastIdAuthor();
	}
	
	@Override
	public Author selectOrUpdateAuthor(String name) {
		long id = 0;
		try {
			//id=this.getAuthorIdByName(name);
			id=authorDao.findByNameAllIgnoreCase(name).get().getIdAuthor();
			return authorDao.getById(id);
		} catch (Exception e) {
			Author newAuthor=new Author();
			newAuthor.setName(name);
			id=this.save(newAuthor);
			return authorDao.getById(id);
		}
	}

	@Override
	public Author getAuthorById(long id) {
		return authorDao.findAuthorByIdAuthor(id); 
	}

	@Override
	public Page<Author> findPaginated(int pageNo, int pageSize) {
		Sort sort = Sort.by("name").ascending() ;
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return authorDao.findAll(pageable);
	}

	@Override
	public boolean checkNameExitWhenUpdate(String name, long id) {
		List<Author> list = authorDao.findAuthorWhenUpdate(name, id);
		if(list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkNameExitWhenInsert(String name) {
		Optional<Author> author = authorDao.findByNameAllIgnoreCase(name);
		if(!author.isPresent()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkExitAuthorInBook(Author author) {
		List<Book> list = bookDao.findByAuthor(author);
		if(list.size() > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public int deleteById(long id) {
		authorDao.deleteById(id);
		return 1;
	}
	
	@Override
	public Page<Author> findAuthor(String key,int pageNo, int pageSize){
		Sort sort = Sort.by("name").ascending() ;
		Pageable pageable = PageRequest.of(pageNo -1, pageSize,sort);
		return authorDao.findByNameContainsAllIgnoreCaseOrderByNameAsc(key,pageable);
	}
}
