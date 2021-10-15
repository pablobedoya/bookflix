package com.br.bookflix.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class InternalServerError extends BookflixException {

    private static final long serialVersionUID = 1L;

    public InternalServerError(String message, String details) {
    	super(message, details, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}