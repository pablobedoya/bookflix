package com.br.bookflix.book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.br.bookflix.book.Book;
import com.br.bookflix.book.repository.BookRepository;
import com.br.bookflix.exception.BookflixException;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository repository;

	// ----------------------------------------------------
	// Read
	// ----------------------------------------------------
	@Override
	public Book findOne(Long id) throws BookflixException {
		try {
			Optional<Book> book = repository.findById(id);
			
			if(book.isPresent()) {
				return book.get();
			}
			
			throw new BookflixException("Entity not found", "Could not retrieve book with id " + id, HttpStatus.NOT_FOUND);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Error retrieving book with id " + id, e);
		}
	}
	
	@Override
	public List<Book> findAll() throws BookflixException {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new BookflixException("Could not retrieve books" + e.getMessage(), e);
		}
	}
	
	// ----------------------------------------------------
	// Persist
	// ----------------------------------------------------
	@Override
	public Book save(Book book) throws BookflixException {
		try {
			return repository.save(book);
		} catch (Exception e) {
			throw new BookflixException("Could not save book", e);
		}
	}
	
	@Override
	public List<Book> saveAll(List<Book> books) throws BookflixException {
		try {
			return repository.saveAll(books);
		} catch (Exception e) {
			throw new BookflixException("Could not save books", e);
		}
	}
	
	@Override
	public Book update(Book book, Long id) throws BookflixException {
		try {
			findOne(id);
			return save(book);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Could not update book with id " + id, e);
		}
	}
	
	// ----------------------------------------------------
	// Delete
	// ----------------------------------------------------
	@Override
	public void delete(Long id) throws BookflixException {
		try {
			Book book = findOne(id);
			repository.delete(book);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Could not delete book with id " + id, e);
		}
	}
	
	// ----------------------------------------------------
	// Validation
	// ----------------------------------------------------
	@Override
	public void validate(Book book) throws BookflixException {

	}

}