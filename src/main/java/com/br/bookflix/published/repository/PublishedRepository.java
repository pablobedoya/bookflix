package com.br.bookflix.published.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bookflix.book.Book;
import com.br.bookflix.published.Published;

public interface PublishedRepository extends JpaRepository<Published, Long> {
	
	public Book findByIsbn10(String isbn10);
	
	public Book findByIsbn13(String isbn13);

}