package com.br.bookflix.subject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.subject.Subject;
import com.br.bookflix.subject.repository.SubjectRepository;
import com.br.bookflix.utils.ValidationUtils;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	private SubjectRepository repository;

	@Override
	public Subject findOne(Long id) throws BookflixException {
		try {
			Optional<Subject> user = repository.findById(id);

			if (user.isPresent()) {
				return user.get();
			}
			throw new BookflixException("Entity not found", "Could not retrieve subject with id " + id,
					HttpStatus.NOT_FOUND);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Error retrieving author with id " + id, e);
		}
	}

	@Override
	public List<Subject> findAll() throws BookflixException {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new BookflixException("Could not retrieve subjects" + e.getMessage(), e);
		}
	}

	@Override
	public Subject save(Subject subject) throws BookflixException {
		try {
			validate(subject);
			return repository.save(subject);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Could not save subject", e);
		}
	}
	
	@Override
	public List<Subject> saveAll(List<Subject> subjects) throws BookflixException {
		try {
			for(Subject a : subjects) {
				validate(a);
			}
			return repository.saveAll(subjects);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Could not save subjects", e);
		}
	}

	@Override
	public Subject update(Subject subject, Long id) throws BookflixException {
		try {
			findOne(id);
			return save(subject);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Could not update subject with id" + id, e);
		}
	}

	@Override
	public void delete(Long id) throws BookflixException {
		try {
			Subject subject = findOne(id);
			repository.delete(subject);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new BookflixException("Could not delete subject by id" + id, e);
		}
	}

	@Override
	public void validate(Subject subject) throws BookflixException {
		ValidationUtils.checkIfExceeds(subject.getName(), 255, "Subject name");
	}
	
}