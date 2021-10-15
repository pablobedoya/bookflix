package com.br.bookflix.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.exception.InternalServerError;
import com.br.bookflix.exception.ResourceNotFoundException;
import com.br.bookflix.exception.UnprocessableEntityException;
import com.br.bookflix.user.User;
import com.br.bookflix.user.repository.UserRepository;
import com.br.bookflix.utils.ValidationUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// ----------------------------------------------------
	// Read
	// ----------------------------------------------------
	@Override
	public User findOne(Long id) throws BookflixException {
		try {
			Optional<User> user = repository.findById(id);

			if (user.isPresent()) {
				return user.get();
			}
			throw new ResourceNotFoundException("Entity not found", "Could not retrieve user with id " + id);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerError("Error retrieving user with id " + id, e.getMessage());
		}
	}

	@Override
	public List<User> findAll() throws InternalServerError {
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
	public User save(User user) throws BookflixException {
		try {
			User testUser = repository.getByEmail(user.getEmail());
			if (testUser == null) {
				validate(user);
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				return repository.save(user);
			}
			throw new UnprocessableEntityException("Entity not created", "The email " + user.getEmail() + " is already registered");
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerError("Could not save user", e.getMessage());
		}
	}

	@Override
	public User update(User user, Long id) throws BookflixException {
		try {
			findOne(id);
			return save(user);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerError("Could not update user with id " + id, e.getMessage());
		}
	}

	// ----------------------------------------------------
	// Delete
	// ----------------------------------------------------
	@Override
	public void delete(Long id) throws BookflixException {
		try {
			User user = findOne(id);
			repository.delete(user);
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerError("Could not delete user by id " + id, e.getMessage());
		}

	}

	// ----------------------------------------------------
	// Validation
	// ----------------------------------------------------
	@Override
	public void validate(User user) throws BookflixException {
		// First name
		ValidationUtils.checkIfEmpty(user.getFirstName(), "First Name");
		ValidationUtils.checkIfExceeds(user.getFirstName(), 255, "First Name");

		// CPF
		ValidationUtils.checkIfEmpty(user.getCpf(), "CPF");
		ValidationUtils.checkIfExceeds(user.getCpf(), 14, "CPF");

		// Email
		ValidationUtils.checkIfEmpty(user.getEmail(), "Email");
		ValidationUtils.checkIfExceeds(user.getEmail(), 255, "Email");
		ValidationUtils.isEmailValid(user.getEmail(), "Email");

		// Date of Birthday
		ValidationUtils.checkIfDateIsInPast(user.getDateOfBirth(), "Date of Birthday");

		// Password
		ValidationUtils.checkIfEmpty(user.getPassword(), "Password");
		ValidationUtils.checkIfExceeds(user.getPassword(), 255, "Password");
	}

}
