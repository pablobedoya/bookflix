package com.br.bookflix.author.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bookflix.author.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
