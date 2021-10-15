package com.br.bookflix.login.service;

import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.login.UserLogin;
import com.br.bookflix.user.User;

public interface LoginService {
	
	public User login(UserLogin userLogin) throws BookflixException;
	
}
