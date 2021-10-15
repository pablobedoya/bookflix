package com.br.bookflix.published.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.bookflix.book.Book;
import com.br.bookflix.book.service.BookService;
import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.exception.InternalServerError;
import com.br.bookflix.exception.ResourceNotFoundException;
import com.br.bookflix.exception.UnprocessableEntityException;
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
			
			throw new ResourceNotFoundException("Entity not found", "Could not retrieve book with id " + id);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerError("Error retrieving book with id " + id, e.getMessage());
		}
	}
	
	@Override
	public List<Published> findAll() throws InternalServerError {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new InternalServerError("Could not retrieve books", e.getMessage());
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
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerError("Could not save book", e.getMessage());
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
			throw new InternalServerError("Could not update book with id " + id, e.getMessage());
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
			throw new InternalServerError("Could not delete book with id " + id, e.getMessage());
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
			throw new UnprocessableEntityException(Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "Book ISBN10 or ISBN13"));
		}
		
		// ISBN13
		if(book.getIsbn10() != null) {
			bookFromDb = findByIsbn10(book.getIsbn10());
			if(bookFromDb != null) {
				throw new UnprocessableEntityException(Constants.INVALID_VALUES, "ISBN10 already in use");
			}
			ValidationUtils.checkIfEmpty(book.getIsbn10(), "Book ISBN10");
			ValidationUtils.checkIfExceeds(book.getIsbn10(), 10, "Book ISBN10");
		}
		
		// ISBN13
		if(book.getIsbn13() != null) {
			bookFromDb = findByIsbn13(book.getIsbn13());
			if(bookFromDb != null) {
				throw new UnprocessableEntityException(Constants.INVALID_VALUES, "ISBN13 already in use");
			}
			ValidationUtils.checkIfEmpty(book.getIsbn13(), "Book ISBN13");
			ValidationUtils.checkIfExceeds(book.getIsbn13(), 13, "Book ISBN13");
		}
		
		// publication year
		if(book.getPublicationYear() != null) {
			ValidationUtils.checkIfNegative(BigDecimal.valueOf(book.getPublicationYear()), "Book publication year");
			ValidationUtils.checkIfIsGreater(book.getPublicationYear(), LocalDate.now().getYear(), "Book publication year");
		}
		
		// pages
		if(book.getPages() != null) {
			ValidationUtils.checkIfExceeds(book.getPages(), 50, "Book pages");
		}
	}
	
	public Published findByIsbn10(String isbn10) {
		return repository.findByIsbn10(isbn10);
	}
	
	public Published findByIsbn13(String isbn3) {
		return repository.findByIsbn13(isbn3);
	}
	
}
