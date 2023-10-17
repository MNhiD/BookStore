package com.ptit.model;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ptit.service.ItemsService;
import com.ptit.service.UserService;
import com.ptit.serviceImp.ItemsServiceImp;
import com.ptit.serviceImp.UserServiceImp;

import lombok.Data;
@Data
public class Cart {
	

	
	private final List<Items> items; 
	
	private BigDecimal total;

	public Cart() {
		super();
		this.items = new ArrayList<Items>();
		this.total = new BigDecimal(0); 
		
	} 
	public Items getItem(Book book) {
		for(Items item: items) {
			if(item.getBook().getIdBook() == book.getIdBook()) {
				return item; 
			}
		}
		return null; 
	}
	
	public int getItemCount() {
		return items.size(); 
	}
	
	public void addItem() {
		
	}
	public int addItem(Book book, int quantity) {
		Items item = this.getItem(book); 
		this.total =this.total.add(book.getPrice().multiply(new BigDecimal(quantity)));  
		if(item!=null) {//tồn tại rồi: ->update 
			item.setQuantityOfBooks(item.getQuantityOfBooks()+quantity);
			
			return 1; //update
		}//chưa tồn tại: ->insert new 
		else {
			item = new Items(); 
			item.setBook(book);
			item.setQuantityOfBooks(quantity);
			items.add(item); 
			
			
			return 2 ; //insert
		}
		
	}
	
	public void addItemFromDB(Items itemsSample) {
		
			Items item = new Items(); 
			item.setItemId(itemsSample.getItemId());
			item.setBook(itemsSample.getBook());
			item.setQuantityOfBooks(itemsSample.getQuantityOfBooks());
			items.add(item); 
			
			//update total: 
			this.total =this.total.add(item.getBook().getPrice().multiply(new BigDecimal(item.getQuantityOfBooks())));  
	
		
	}
	
	
	public void updateItem(Book book, int quantity) {
		Items item = this.getItem(book); 
		if(item!=null) {
			item.setQuantityOfBooks(quantity);
			
			this.total  = BigDecimal.valueOf(0); 
			
			this.callTotalPrice();
		}
		
	}
	public void removeItem(Book book) {
		Items item = this.getItem(book); 
		if(item!=null) {
			items.remove(item); 
			
			this.total  = BigDecimal.valueOf(0); 
			this.callTotalPrice();
		}
	}
	public void clear() {
		this.items.clear();
		this.total = BigDecimal.valueOf(0); 
	}
	public boolean isEmpty() {
		return items.isEmpty(); 
	}
	public void callTotalPrice() {
		for(Items i: items) {
			BigDecimal priceOfItem = i.getBook().getPrice().multiply(BigDecimal.valueOf(i.getQuantityOfBooks())); 
			
			total = total.add(priceOfItem); 
		}
	}
}









































