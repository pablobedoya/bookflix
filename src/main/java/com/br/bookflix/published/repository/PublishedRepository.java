package com.br.bookflix.published.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bookflix.published.Published;

public interface PublishedRepository extends JpaRepository<Published, Long> {
	
	public Published findByIsbn10(String isbn10);
	
	public Published findByIsbn13(String isbn13);

}