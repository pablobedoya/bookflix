package com.br.bookflix.subject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Subject {
	
	/**
	 * Subject identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Subject name.
	 * <br/>
	 * Required with max length 255.
	 */
	@Column(nullable = false, length = 255)
	private String name;

}
