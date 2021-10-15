package com.br.bookflix.unpublished.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bookflix.unpublished.Unpublished;

public interface UnpublishedRepository extends JpaRepository<Unpublished, Long> {
	
	public List<Unpublished> findByGenre(String genre);

}