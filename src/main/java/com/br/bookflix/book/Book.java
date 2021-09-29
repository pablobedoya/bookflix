package com.br.bookflix.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Book {
	
	/**
	 * Book identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Book title.
	 */
	@Column(name = "title", nullable = false, length = 500)
	private String title;

	/**
	 * Book description.
	 * <br/>
	 * Required with max length 500.
	 */
	@Column(name = "description", nullable = false, length = 500)
	private String description;

}
