package com.br.bookflix.published.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.br.bookflix.book.Book;
import com.br.bookflix.book.service.BookService;
import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.published.Published;
import com.br.bookflix.published.repository.PublishedRepository;
import com.br.bookflix.utils.Constants;
import com.br.bookflix.utils.ValidationUtils;

@Transactional
@Service
public class PublishedServiceImpl implements PublishedService {

	@Autowired
	private PublishedRepository repository;
	
	@Autowired
	private BookService bookService;

	// ----------------------------------------------------
	// Read
	// ----------------------------------------------------
	@Override
	public Published findOne(Long id) throws BookflixException {
		try {
			Optional<Published> book = repository.findById(id);
			
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
	public List<Published> findAll() throws BookflixException {
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
	public Published save(Published book) throws BookflixException {
		try {
			validate(book);
			return repository.save(book);
		} catch (Exception e) {
			throw new BookflixException("Could not save book", e);
		}
	}
	
	@Override
	public Published update(Published book, Long id) throws BookflixException {
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
			Published book = findOne(id);
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
	public void validate(Published book) throws BookflixException {
		Book bookFromDb = null;
		
		// validate book common attributes
		bookService.validate(book);
		
		// ISBN10 or ISB13
		if((book.getIsbn13() == null || book.getIsbn13().isEmpty()) && (book.getIsbn10() == null || book.getIsbn10().isEmpty())) {
			throw new BookflixException(Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "Book ISBN10 or ISBN13"), HttpStatus.BAD_REQUEST);
		}
		
		// ISBN13
		if(book.getIsbn10() != null) {
			bookFromDb = findByIsbn10(book.getIsbn10());
			if(bookFromDb != null) {
				throw new BookflixException(Constants.INVALID_VALUES, "ISBN10 already in use", HttpStatus.BAD_REQUEST);
			}
			ValidationUtils.checkIfEmpty(book.getIsbn10(), "Book ISBN10");
			ValidationUtils.checkIfExceeds(book.getIsbn10(), 10, "Book ISBN10");
		}
		
		// ISBN13
		if(book.getIsbn13() != null) {
			bookFromDb = findByIsbn10(book.getIsbn13());
			if(bookFromDb != null) {
				throw new BookflixException(Constants.INVALID_VALUES, "ISBN13 already in use", HttpStatus.BAD_REQUEST);
			}
			ValidationUtils.checkIfEmpty(book.getIsbn13(), "Book ISBN13");
			ValidationUtils.checkIfExceeds(book.getIsbn13(), 13, "Book ISBN13");
		}
		
		// publication year
		if(book.getPublicationYear() != null) {
			ValidationUtils.checkIfNegative(BigDecimal.valueOf(book.getPublicationYear()), "Book publication year");
		}
		
		// pages
		if(book.getPages() != null) {
			ValidationUtils.checkIfEmpty(book.getPages(), "Book pages");
			ValidationUtils.checkIfExceeds(book.getPages(), 50, "Book pages");
		}
	}
	
	public Book findByIsbn10(String isbn10) {
		return repository.findByIsbn10(isbn10);
	}
	
	public Book findByIsbn13(String isbn3) {
		return repository.findByIsbn13(isbn3);
	}
	
}
