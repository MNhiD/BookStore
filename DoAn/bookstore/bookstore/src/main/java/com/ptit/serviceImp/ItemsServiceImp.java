package com.ptit.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.model.Book;
import com.ptit.model.Items;
import com.ptit.model.User;
import com.ptit.repository.ItemsDao;
import com.ptit.service.ItemsService;
@Service
public class ItemsServiceImp implements ItemsService{

	@Autowired
	ItemsDao itemsDao;
	
	@Override
	public void updateItemsCartIncreaseQuantity(User user, Book book, int quantity) {
		
		Items item = itemsDao.findByUserAndBook(user, book); 
		item.setQuantityOfBooks(item.getQuantityOfBooks()+quantity);
		itemsDao.save(item); 
	}

	@Override
	public void addNewItems(User user, Book book, int quantity) {
		Items item = new Items(); 
		item.setUser(user);
		item.setBook(book);
		item.setQuantityOfBooks(quantity);
		itemsDao.save(item);
	}

	@Override
	public void updateItemsCartChangeQuantity(User user, Book book, int quantity) {
		Items item = itemsDao.findByUserAndBook(user, book); 
		item.setQuantityOfBooks(quantity);
		itemsDao.save(item); 
		
	}

	@Override
	public void deleteItems(User user, Book book) {
		Items item = itemsDao.findByUserAndBook(user, book);
		itemsDao.delete(item);
		
	}

	@Override
	public List<Items> getAllItemsByUser(User user) {
		return itemsDao.findAllByUser(user); 
		
	}

	@Override
	public void updateListItems(List<Items> list) {
		for(Items item: list) {
			itemsDao.save(item); 
		}
		
	}

	@Override
	public void deleteItemsAfterBuy(User user) {
		itemsDao.deleteByUser(user);		
	}
	
}























