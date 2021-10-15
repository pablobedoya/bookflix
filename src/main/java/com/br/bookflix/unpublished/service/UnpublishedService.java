package com.br.bookflix.unpublished.service;

import java.util.List;

import com.br.bookflix.crud.AbstractCrud;
import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.unpublished.Unpublished;

public interface UnpublishedService extends AbstractCrud<Unpublished> {

	// ----------------------------------------------------
	// Read
	// ----------------------------------------------------
	public List<Unpublished> findByGenre(String genre) throws BookflixException;

}