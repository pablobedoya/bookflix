package com.br.bookflix.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BookflixException {
	
	private static final long serialVersionUID = 1L;
	
	public NotFoundException(String message, String details) {
    	super(message, details, HttpStatus.NOT_FOUND);
    }

}
