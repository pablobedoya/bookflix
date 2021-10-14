package com.br.bookflix.unpublished;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.br.bookflix.book.Book;
import com.br.bookflix.published.Published;
import com.br.bookflix.user.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name="unpublished")
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper=true)
public class Unpublished extends Book {
	
	/**
	 * Serialization of unpublished book.
	 * <br>
	 * Required.
	 */
	@Column(nullable = false)
	private byte[] file;
	
	@ManyToMany
	@JoinTable(name = "related_books", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = { @JoinColumn(name = "author_id") })
	private List<Published> relatedBooks;
	
	/**
	 * User that uploaded the unpublished book.
	 * <br>
	 * Required.
	 */
	@ManyToOne
	@JoinColumn(name = "uploaded_by")
	private User uploadedBy;
	
	/**
	 * Date of creation of unpublished book
	 * <br>
	 * Required.
	 */
	@Column(name = "created_date", nullable = false)
	private LocalDateTime createdDate;

}
