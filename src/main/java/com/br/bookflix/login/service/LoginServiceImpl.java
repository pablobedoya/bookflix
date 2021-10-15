package com.br.bookflix.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.exception.InternalServerError;
import com.br.bookflix.exception.UnauthorizedException;
import com.br.bookflix.login.UserLogin;
import com.br.bookflix.user.User;
import com.br.bookflix.user.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository repository;

	@Override
	public User login(UserLogin userLogin) throws BookflixException {
		try {
			User user = repository.getByEmail(userLogin.getEmail());
			if (user != null && BCrypt.checkpw(userLogin.getPassword(), user.getPassword())) {
				return user;
			}
			throw new UnauthorizedException("Wrong credentials", "Incorrect email or password");
		} catch (BookflixException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerError("Unexpected error", e.getMessage());
		}
	}

}
