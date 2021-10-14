package com.br.bookflix.book.service;

import com.br.bookflix.book.Book;
import com.br.bookflix.exception.BookflixException;

public interface BookService {
	
	public void validate(Book book) throws BookflixException;
	
}
