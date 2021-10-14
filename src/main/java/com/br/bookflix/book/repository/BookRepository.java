package com.br.bookflix.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bookflix.book.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
}
