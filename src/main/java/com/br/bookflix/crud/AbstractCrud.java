package com.br.bookflix.crud;

import java.util.List;

import com.br.bookflix.exception.BookflixException;

public interface AbstractCrud<T> {
	
	// ----------------------------------------------------
	// Read
	// ----------------------------------------------------
	public T findOne(Long id) throws BookflixException;
	
	public List<T> findAll() throws BookflixException;
	
	// ----------------------------------------------------
	// Persist
	// ----------------------------------------------------
	public T save(T entity) throws BookflixException;
	
	public T update(T entity, Long id) throws BookflixException;
	
	// ----------------------------------------------------
	// Delete
	// ----------------------------------------------------
	public void delete(Long id) throws BookflixException;
	
	// ----------------------------------------------------
	// Validation
	// ----------------------------------------------------
	public void validate(T entity) throws BookflixException;

}
