package com.br.bookflix.author.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.br.bookflix.author.Author;
import com.br.bookflix.author.repository.AuthorRepository;
import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.utils.ValidationUtils;

@Service
public class AuthorServiceImpl implements AuthorService {
	
	@Autowired
	private AuthorRepository repository;

	@Override
	public Author findOne(Long id) throws BookflixException {
		try {
			Optional<Author> user = repository.findById(id);

			if (user.isPresent()) {
				return user.get();
			}
			throw new BookflixException("Entity not found", "Could not retrieve author with id " + id,
					HttpStatus.NOT_FOUND);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Error retrieving author with id " + id, e);
		}
	}

	@Override
	public List<Author> findAll() throws BookflixException {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new BookflixException("Could not retrieve authors" + e.getMessage(), e);
		}
	}

	@Override
	public Author save(Author author) throws BookflixException {
		try {
			validate(author);
			return repository.save(author);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Could not save author", e);
		}
	}
	
	@Override
	public List<Author> saveAll(List<Author> authors) throws BookflixException {
		try {
			for(Author a : authors) {
				validate(a);
			}
			return repository.saveAll(authors);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Could not save authors", e);
		}
	}

	@Override
	public Author update(Author author, Long id) throws BookflixException {
		try {
			findOne(id);
			return save(author);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Could not update author with id" + id, e);
		}
	}

	@Override
	public void delete(Long id) throws BookflixException {
		try {
			Author author = findOne(id);
			repository.delete(author);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Could not delete author by id" + id, e);
		}
	}

	@Override
	public void validate(Author author) throws BookflixException {
		ValidationUtils.checkIfExceeds(author.getName(), 255, "Author name");
	}
	
}