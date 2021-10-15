package com.br.bookflix.user.service;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.br.bookflix.BookflixApplication;
import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.user.User;
import com.br.bookflix.utils.Constants;
import com.br.bookflix.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookflixApplication.class, properties = {"spring.config.name=userDb","bookflix.trx.datasource.url=jdbc:h2:mem:userDb"})
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	@DisplayName("Test if an user without first name is persisted")
	public void persistUserWithoutFirstName() throws BookflixException {
		User user = TestUtils.getUser();
		user.setFirstName(null);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			userService.save(user);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "First name"));
	}
	
	@Test
	@DisplayName("Test if an user with first name that exceeds limit is persisted")
	public void persistUserWithFirstNameThatExceedsLimit() throws BookflixException {
		User user = TestUtils.getUser();
		user.setFirstName(RandomStringUtils.randomAlphabetic(260));
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			userService.save(user);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.STRING_GREATER, "First name", 255));
	}
	
	@Test
	@DisplayName("Test if an user with last name that exceeds limit is persisted")
	public void persistUserWithLaststNameThatExceedsLimit() throws BookflixException {
		User user = TestUtils.getUser();
		user.setLastName(RandomStringUtils.randomAlphabetic(260));
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			userService.save(user);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.STRING_GREATER, "Last name", 255));
	}
	
	@Test
	@DisplayName("Test if an user without CPF is persisted")
	public void persistUserWithoutCpf() throws BookflixException {
		User user = TestUtils.getUser();
		user.setCpf(null);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			userService.save(user);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "CPF"));
	}
	
	@Test
	@DisplayName("Test if an user with CPF that exceeds limit is persisted")
	public void persistUserWitCpfThatExceedsLimit() throws BookflixException {
		User user = TestUtils.getUser();
		user.setCpf(RandomStringUtils.randomAlphabetic(20));
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			userService.save(user);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.STRING_GREATER, "CPF", 14));
	}

	@Test
	@DisplayName("Test if an user without email is persisted")
	public void persistUserWithoutEmail() throws BookflixException {
		User user = TestUtils.getUser();
		user.setEmail(null);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			userService.save(user);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "Email"));
	}
	
	@Test
	@DisplayName("Test if an user with email that exceeds limit is persisted")
	public void persistUserWithEmailThatExceedsLimit() throws BookflixException {
		User user = TestUtils.getUser();
		user.setEmail(RandomStringUtils.randomAlphabetic(300));
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			userService.save(user);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.STRING_GREATER, "Email", 255));
	}
	
	@Test
	@DisplayName("Test if an user with invalid email is persisted")
	public void persistUserWithInvalidEmail() throws BookflixException {
		User user = TestUtils.getUser();
		user.setEmail("aa");
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			userService.save(user);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.EMAIL_ERROR, user.getEmail()));
	}
	
	@Test
	@DisplayName("Test if an user with date of birth in future is persisted")
	public void persistUserWithDateInFuture() throws BookflixException {
		User user = TestUtils.getUser();
		user.setDateOfBirth(LocalDate.now().plusDays(10));
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			userService.save(user);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.DATE_ERROR, "Date of birthday", "future"));
	}
	
	@Test
	@DisplayName("Test if an user without password is persisted")
	public void persistUserWithoutPassword() throws BookflixException {
		User user = TestUtils.getUser();
		user.setPassword(null);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			userService.save(user);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "Password"));
	}
	
	@Test
	@DisplayName("Test if an user with password that exceeds limit is persisted")
	public void persistUserWithPasswordThatExceedsLimit() throws BookflixException {
		User user = TestUtils.getUser();
		user.setPassword(RandomStringUtils.randomAlphabetic(300));
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			userService.save(user);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.STRING_GREATER, "Password", 255));
	}
	
	@Test
	@DisplayName("Test if user password is encrypted")
	public void persistUserAndCheckEncryptedPassword() throws BookflixException {
		User user = TestUtils.getUser();
		user.setPassword("myPassword");
		user = userService.save(user);
		
		assertTrue(user.getId() != null);
		assertTrue(!user.getPassword().equals("myPassword"));
	}
	
}
