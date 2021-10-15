package com.br.bookflix.login.service;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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
import com.br.bookflix.login.UserLogin;
import com.br.bookflix.user.User;
import com.br.bookflix.user.service.UserService;
import com.br.bookflix.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookflixApplication.class, properties = {"spring.config.name=loginDb","bookflix.trx.datasource.url=jdbc:h2:mem:loginDb"})
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class LoginServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginService loginService;
	
	@Test
	@DisplayName("Test if user is able to login using correct credentials")
	public void loginWithCorrectCredentials() throws BookflixException {
		User user = TestUtils.getUser();
		user.setPassword("myPassword");
		user = userService.save(user);
		
		UserLogin ul = new UserLogin();
		TestUtils.set(ul, "email", user.getEmail());
		TestUtils.set(ul, "password", "myPassword");
		
		User result = loginService.login(ul);
		assertTrue(result.getId() != null);
	}
	
	@Test
	@DisplayName("Test if user is able to login using invalid email")
	public void loginWithInvalidEmail() throws BookflixException {
		User user = TestUtils.getUser();
		user.setPassword("myPassword");
		user = userService.save(user);
		
		UserLogin ul = new UserLogin();
		TestUtils.set(ul, "email", "another");
		TestUtils.set(ul, "password", "myPassword");
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			loginService.login(ul);
		});

		TestUtils.assertBookflixException(thrown, "Wrong credentials", "Incorrect email or password");
	}
	
	@Test
	@DisplayName("Test if user is able to login using invalid password")
	public void loginWithInvalidPassword() throws BookflixException {
		User user = TestUtils.getUser();
		user.setPassword("myPassword");
		user = userService.save(user);
		
		UserLogin ul = new UserLogin();
		TestUtils.set(ul, "email", user.getEmail());
		TestUtils.set(ul, "password", "wrongPassword");
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			loginService.login(ul);
		});

		TestUtils.assertBookflixException(thrown, "Wrong credentials", "Incorrect email or password");
	}
		
}
