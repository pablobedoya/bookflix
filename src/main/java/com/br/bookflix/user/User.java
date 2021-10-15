package com.br.bookflix.user;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Entity
@Table(name = "user")
@Data
public class User {
	
	/**
	 * User identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * User first name.
	 * <br/>
	 * Required with max length 255.
	 */
	@Column(name = "first_name", nullable = false, length = 255)
	private String firstName;
	
	/**
	 * User last name.
	 */
	@Column(name = "last_name", length = 255)
	private String lastName;
	
	/**
	 * User CPF.
	 * <br/>
	 * Required with max length 14.
	 */
	@Column(nullable = false, length = 14)
	private String cpf;
	
	/**
	 * User email.
	 * <br/>
	 * Required with max length 255.
	 */
	@Column(nullable = false, length = 255)
	private String email;
	
	/**
	 * User date of birth.
	 */
	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;
	
	/**
	 * User password.
	 * <br/>
	 * Required with max length 255.
	 */
	@ApiModelProperty(hidden = true)
	@Column(nullable = false, length = 255)
	private String password;

}
