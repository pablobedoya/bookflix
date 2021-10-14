package com.br.bookflix.published.service;

import com.br.bookflix.book.Book;
import com.br.bookflix.crud.AbstractCrud;
import com.br.bookflix.published.Published;

public interface PublishedService extends AbstractCrud<Published> {

	public Book findByIsbn10(String isbn10);
	
	public Book findByIsbn13(String isbn13);

}
