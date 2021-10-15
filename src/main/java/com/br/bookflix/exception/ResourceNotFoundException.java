package com.br.bookflix.exception;

public class ResourceNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message, String details) {
		super(message, details);
	}

}
