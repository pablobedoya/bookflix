package com.br.bookflix.author.service;

import java.util.List;

import com.br.bookflix.author.Author;
import com.br.bookflix.crud.AbstractCrud;
import com.br.bookflix.exception.BookflixException;

public interface AuthorService extends AbstractCrud<Author> {
	
	public List<Author> saveAll(List<Author> authors) throws BookflixException;
	
}