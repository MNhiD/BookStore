package com.ptit.model;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public class CartManager {
	private static final String SESSION_KEY_CART = "cart"; 
	public Cart getCart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute(SESSION_KEY_CART); 
		if(cart==null) {
			cart = new Cart(); 
			this.setCart(session, cart);
			
		}
		return cart; 
	}
	
	public void setCart(HttpSession session, Cart cart) {
		session.setAttribute(SESSION_KEY_CART, cart);
	}
	
	public void removeCart(HttpSession session) {
		session.removeAttribute(SESSION_KEY_CART);
	}
}
