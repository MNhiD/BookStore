package com.ptit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class OverMaximumQuantityException extends Exception{
	public OverMaximumQuantityException(String message) {
		super(message); 
	}
}
