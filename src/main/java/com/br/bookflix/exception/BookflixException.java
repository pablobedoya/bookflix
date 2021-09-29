package com.br.bookflix.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class BookflixException extends Exception {
	
	private static final long serialVersionUID = 1L;

	@Getter
	private String details;
	
	@Getter
	private HttpStatus status;
	
	public BookflixException(String message, String details, HttpStatus status) {
		super(message);
		this.details = details;
		this.status = status;
	}
	
	public BookflixException(String message, String details) {
		this(message, details, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public BookflixException(String message, Exception exception, HttpStatus status) {
		this(message, ExceptionUtils.getMessage(exception), status);
	}
	
	public BookflixException(String message, Exception exception) {
		this(message, ExceptionUtils.getMessage(exception), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
