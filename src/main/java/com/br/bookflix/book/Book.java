package com.br.bookflix.book;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.br.bookflix.author.Author;
import com.br.bookflix.subject.Subject;

import lombok.Data;

@Entity
@Table(name = "book")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Book {
	
	/**
	 * Book identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Book title.
	 * <br/>
	 * Required with max length 500.
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
	
	/**
	 * Serialization of book image.
	 */
	@Column(nullable = true)
	private byte[] thumbnail;
	
	/**
	 * Book subjects.
	 */
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "book_subjects", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = { @JoinColumn(name = "subject_id") })
	private Set<Subject> subjects;
	
	/**
	 * Book authors.
	 * <br/>
	 * At least one author is required.
	 */
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "book_authors", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = { @JoinColumn(name = "author_id") })
	private Set<Author> authors;
	
	/**
	 * Book genre.
	 * <br/>
	 * Required with max length 100.
	 */
	@Column(nullable = false, length = 100)
	private String genre;
	
}
