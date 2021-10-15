package com.br.bookflix.user.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.br.bookflix.crud.AbstractCrud;
import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.user.User;

public interface UserService extends AbstractCrud<User>{
	
	public User findOne(Long id) throws BookflixException;
	
	public List<User> findAll() throws BookflixException;
	
	public User save(User user) throws BookflixException;
	
	public User update(User user, Long id) throws BookflixException;
	
	public void delete(Long id) throws BookflixException;
	
	public void validate(User user) throws BookflixException;
	
}
