package com.br.bookflix.book.service;

import org.springframework.stereotype.Service;

import com.br.bookflix.book.Book;
import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.exception.PreconditionFailedException;
import com.br.bookflix.utils.Constants;
import com.br.bookflix.utils.ValidationUtils;

@Service
public class BookServiceImpl implements BookService {
	
	@Override
	public void validate(Book book) throws BookflixException {
		// Title
		ValidationUtils.checkIfEmpty(book.getTitle(), "Book title");
		ValidationUtils.checkIfExceeds(book.getTitle(), 500, "Book title");
		
		// Description
		ValidationUtils.checkIfEmpty(book.getDescription(), "Book description");
		ValidationUtils.checkIfExceeds(book.getDescription(), 500, "Book description");
		
		// Authors
		if(book.getAuthors() == null || book.getAuthors().isEmpty()) {
			throw new PreconditionFailedException(Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "Book authors"));
		}
		
		// Genre
		ValidationUtils.checkIfEmpty(book.getGenre(), "Book genre");
		ValidationUtils.checkIfExceeds(book.getGenre(), 100, "Book genre");
	}
	
}
