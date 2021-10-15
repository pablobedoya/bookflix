package com.br.bookflix.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.UnexpectedRollbackException;

public class ExceptionUtils {
	
	public static String getMessage(Exception exception) {
		String message = null;
		
		if(exception instanceof DataIntegrityViolationException) {
			message = ((DataIntegrityViolationException) exception).getMostSpecificCause().getMessage();
		} else if(exception instanceof UnexpectedRollbackException) {
			message = ((UnexpectedRollbackException) exception).getMostSpecificCause().getMessage();
		} else if(exception instanceof DataIntegrityViolationException) {
			message = ((BookflixException) exception).getDetails();
		} else {
			message = exception.getMessage();
		}
		
		return message;
	}

}
