package com.br.bookflix.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.exception.PreconditionFailedException;

public class ValidationUtils {
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-\\+]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static void checkIfEmpty(Object object, String label) throws PreconditionFailedException {
		if(object == null) {
			throw new PreconditionFailedException(Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, label));
		}
	}
	
	public static void checkIfNegative(BigDecimal value, String label) throws PreconditionFailedException {
		if(value != null && value.doubleValue() < 0) {
			throw new PreconditionFailedException(Constants.INVALID_VALUES, String.format(Constants.NEGATIVE_VALUE, label));
		}
	}
	
	public static void checkIfEmpty(String value, String label) throws PreconditionFailedException {
		if(value == null || value.isEmpty()) {
			throw new PreconditionFailedException(Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, label));
		}
	}
	
	public static void checkIfExceeds(String value, int max, String label) throws PreconditionFailedException {
		if(value != null && value.length() > max) {
			throw new PreconditionFailedException(Constants.INVALID_VALUES, String.format(Constants.STRING_GREATER, label, max));
		}
	}
	
	public static void checkIfDateIsInFuture(LocalDate date, String label) throws PreconditionFailedException {
		if(date != null && !date.isBefore(LocalDate.now())) {
			throw new PreconditionFailedException(Constants.INVALID_VALUES, String.format(Constants.DATE_ERROR, label, "past"));
		}
	}
	
	public static void checkIfDateIsInPast(LocalDate date, String label) throws PreconditionFailedException {
		if(date != null && date.isAfter(LocalDate.now())) {
			throw new PreconditionFailedException(Constants.INVALID_VALUES, String.format(Constants.DATE_ERROR, label, "future"));
		}
	}
	
	public static void checkIfIsGreater(int number, int limit, String label) throws BookflixException {
		if(number > limit) {
			throw new PreconditionFailedException(Constants.INVALID_VALUES, String.format(Constants.NUMBER_GREATER, label, limit));
		}
	}

public static void isEmailValid(final String email, String label) throws PreconditionFailedException {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches()) {
			throw new PreconditionFailedException(Constants.INVALID_VALUES, String.format(Constants.EMAIL_ERROR, email));
		}
    }

}
