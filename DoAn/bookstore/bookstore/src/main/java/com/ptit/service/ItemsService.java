package com.ptit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ptit.model.Book;
import com.ptit.model.Items;
import com.ptit.model.User;


public interface ItemsService {
	public void updateItemsCartIncreaseQuantity(User user, Book book, int quantity); 
	public void updateItemsCartChangeQuantity(User user, Book book , int quantity); 
	public void addNewItems(User user, Book book, int quantity); 
	public void deleteItems(User user, Book book); 
	public List<Items> getAllItemsByUser(User user); 
	public void updateListItems(List<Items> list); 
	public void deleteItemsAfterBuy(User user); 
}
