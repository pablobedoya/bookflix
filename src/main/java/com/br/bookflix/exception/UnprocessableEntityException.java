package com.br.bookflix.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UnprocessableEntityException extends BookflixException {

    private static final long serialVersionUID = 1L;

    public UnprocessableEntityException(String message, String details) {
    	super(message, details, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}