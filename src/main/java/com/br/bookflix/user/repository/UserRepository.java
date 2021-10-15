package com.br.bookflix.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bookflix.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User getByEmail(String email);
}


