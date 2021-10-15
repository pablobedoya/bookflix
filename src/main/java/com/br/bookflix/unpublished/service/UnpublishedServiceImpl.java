package com.br.bookflix.unpublished.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.br.bookflix.book.service.BookService;
import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.unpublished.Unpublished;
import com.br.bookflix.unpublished.repository.UnpublishedRepository;

@Service
public class UnpublishedServiceImpl implements UnpublishedService {

	@Autowired
	private UnpublishedRepository repository;
	
	@Autowired
	private BookService bookService;

	// ----------------------------------------------------
	// Read
	// ----------------------------------------------------
	@Override
	public Unpublished findOne(Long id) throws BookflixException {
		try {
			Optional<Unpublished> book = repository.findById(id);
			
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
	public List<Unpublished> findAll() throws BookflixException {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new BookflixException("Could not retrieve books" + e.getMessage(), e);
		}
	}
	
	@Override
	public List<Unpublished> findByGenre(String genre) throws BookflixException {
		try {
			return repository.findByGenre(genre);
		} catch (Exception e) {
			throw new BookflixException("Could not retrieve books" + e.getMessage(), e);
		}
	}
	
	// ----------------------------------------------------
	// Persist
	// ----------------------------------------------------
	@Override
	public Unpublished save(Unpublished book) throws BookflixException {
		try {
			validate(book);
			book.setCreatedDate(LocalDateTime.now());
			book.setFile(new byte[] {});
			return repository.save(book);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Could not save book", e);
		}
	}
	
	@Override
	public Unpublished update(Unpublished book, Long id) throws BookflixException {
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
			Unpublished book = findOne(id);
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
	public void validate(Unpublished book) throws BookflixException {
		// validate book common attributes
		bookService.validate(book);
	}

}