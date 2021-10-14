package com.br.bookflix.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

import com.br.bookflix.exception.BookflixException;

public class ValidationUtils {
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-\\+]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static void checkIfEmpty(Object object, String label) throws BookflixException {
		if(object == null) {
			throw new BookflixException(Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, label), HttpStatus.BAD_REQUEST);
		}
	}
	
	public static void checkIfNegative(BigDecimal value, String label) throws BookflixException {
		if(value != null && value.doubleValue() < 0) {
			throw new BookflixException(Constants.INVALID_VALUES, String.format(Constants.NEGATIVE_VALUE, label), HttpStatus.BAD_REQUEST);
		}
	}
	
	public static void checkIfEmpty(String value, String label) throws BookflixException {
		if(value == null || value.isEmpty()) {
			throw new BookflixException(Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, label), HttpStatus.BAD_REQUEST);
		}
	}
	
	public static void checkIfExceeds(String value, int max, String label) throws BookflixException {
		if(value != null && value.length() > max) {
			throw new BookflixException(Constants.INVALID_VALUES, String.format(Constants.STRING_GREATER, label, max), HttpStatus.BAD_REQUEST);
		}
	}
	
	public static void checkIfDateIsInFuture(LocalDate date, String label) throws BookflixException {
		if(date != null && !date.isBefore(LocalDate.now())) {
			throw new BookflixException(Constants.INVALID_VALUES, String.format(Constants.DATE_ERROR, label, "past"), HttpStatus.BAD_REQUEST);
		}
	}
	
	public static void checkIfDateIsInPast(LocalDate date, String label) throws BookflixException {
		if(date != null && date.isAfter(LocalDate.now())) {
			throw new BookflixException(Constants.INVALID_VALUES, String.format(Constants.DATE_ERROR, label, "future"), HttpStatus.BAD_REQUEST);
		}
	}
	
	public static void isEmailValid(final String email, String label) throws BookflixException {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches()) {
			throw new BookflixException(Constants.INVALID_VALUES, String.format(Constants.EMAIL_ERROR, email), HttpStatus.BAD_REQUEST);
		}
    }

}
