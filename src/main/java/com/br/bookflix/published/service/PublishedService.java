package com.br.bookflix.published.service;

import com.br.bookflix.crud.AbstractCrud;
import com.br.bookflix.published.Published;

public interface PublishedService extends AbstractCrud<Published> {

	public Published findByIsbn10(String isbn10);
	
	public Published findByIsbn13(String isbn13);

}
