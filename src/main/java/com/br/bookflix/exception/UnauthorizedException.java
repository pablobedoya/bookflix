package com.br.bookflix.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BookflixException {
	
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedException(String message, String details) {
    	super(message, details, HttpStatus.UNAUTHORIZED);
    }

}
