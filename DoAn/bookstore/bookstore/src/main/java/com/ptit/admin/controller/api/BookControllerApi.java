package com.ptit.admin.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ptit.model.Book;
import com.ptit.service.BookService;

@RestController
@RequestMapping("api/admin")
public class BookControllerApi {
	@Autowired
	private BookService bookService;
	
	@RequestMapping(value = "book/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> find(@PathVariable("id") long id){
		try {
			return new ResponseEntity<Book>(bookService.getBookById(id),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
		}
	}
}
