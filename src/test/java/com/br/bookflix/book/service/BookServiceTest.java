package com.br.bookflix.book.service;

import static org.junit.Assert.assertThrows;

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
import com.br.bookflix.published.Published;
import com.br.bookflix.published.service.PublishedService;
import com.br.bookflix.utils.Constants;
import com.br.bookflix.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookflixApplication.class, properties = {"spring.config.name=bookDb","bookflix.trx.datasource.url=jdbc:h2:mem:bookDb"})
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class BookServiceTest {
	
	@Autowired
	private PublishedService publishedService;
	
	@Test
	@DisplayName("Test if a book without title is persisted")
	public void persistBookWithoutTitle() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setTitle(null);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "Book title"));
	}
	
	@Test
	@DisplayName("Test if a book with title that exceeds limit is persisted")
	public void persistBookWithTitleThatExceedsLimit() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setTitle(RandomStringUtils.randomAlphabetic(510));
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.STRING_GREATER, "Book title", 500));
	}
	
	@Test
	@DisplayName("Test if a book without description is persisted")
	public void persistBookWithoutDescription() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setDescription(null);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "Book description"));
	}
	
	@Test
	@DisplayName("Test if a book with description that exceeds limit is persisted")
	public void persistBookWithDescriptionThatExceedsLimit() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setDescription(RandomStringUtils.randomAlphabetic(510));
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES,  String.format(Constants.STRING_GREATER, "Book description", 500));
	}
	
	@Test
	@DisplayName("Test if a book without genre is persisted")
	public void persistBookWithoutGenre() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setGenre(null);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "Book genre"));
	}
	
	@Test
	@DisplayName("Test if a book with genre that exceeds limit is persisted")
	public void persistBookWithGenreThatExceedsLimit() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setGenre(RandomStringUtils.randomAlphabetic(110));
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES,  String.format(Constants.STRING_GREATER, "Book genre", 100));
	}
	
	@Test
	@DisplayName("Test if a book without authors is persisted")
	public void persistBookWithoutAuthors() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setAuthors(null);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "Book authors"));
	}
		
}
