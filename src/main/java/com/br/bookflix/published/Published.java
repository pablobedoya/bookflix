package com.br.bookflix.published;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.br.bookflix.book.Book;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name="published")
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper=true)
public class Published extends Book {
	
	/**
	 * The ISBN10 of the published book.
	 */
	@Column(name="isbn_10", length = 10)
	private String isbn10;
	
	/**
	 * The ISBN13 of the published book.
	 */
	@Column(name="isbn_13", length = 13)
	private String isbn13;
	
	/**
	 * The publisher of the published book.
	 * <br/>
	 * Required with max length 255.
	 */
	@Column(length = 255, nullable = false)
	private String publisher;
	
	/**
	 * The publication year of the book.
	 */
	@Column(name="publication_year")
	private Integer publicationYear;
	
	/**
	 * The number of pages of the published book.
	 * <br/>
	 * Required with max length 50.
	 */
	@Column(length = 50)
	private String pages;

}
