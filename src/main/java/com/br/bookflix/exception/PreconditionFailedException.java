package com.br.bookflix.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class PreconditionFailedException extends BookflixException {

    private static final long serialVersionUID = 1L;

    public PreconditionFailedException(String message, String details) {
    	super(message, details, HttpStatus.PRECONDITION_FAILED);
    }

}