package com.br.bookflix.published.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.br.bookflix.BookflixApplication;
import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.published.Published;
import com.br.bookflix.utils.Constants;
import com.br.bookflix.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookflixApplication.class, properties = {"spring.config.name=publishedDb","bookflix.trx.datasource.url=jdbc:h2:mem:publishedDb"})
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class PublishedServiceTest {
	
	@Autowired
	private PublishedService publishedService;
	
	@Test
	@DisplayName("Test if a published book without ISBN10 and ISBN13 is persisted")
	public void persistBookWithoutIsbn10AndIsbn13() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setIsbn10(null);
		book.setIsbn13(null);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "Book ISBN10 or ISBN13"));
	}
	
	@Test
	@DisplayName("Test if a published book with an ISBN10 that exceeds limit is persisted")
	public void persistBookWithIsbn10ThatExceedsLimit() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setIsbn10(RandomStringUtils.randomAlphabetic(15));
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES,  String.format(Constants.STRING_GREATER, "Book ISBN10", 10));
	}
	
	@Test
	@DisplayName("Test if a published book with a duplicate ISBN10 is persisted")
	public void persistBookWithDuplicateIsbn10() throws BookflixException {
		Published book = TestUtils.getPublished();
		book = publishedService.save(book);
		
		Published book2 = TestUtils.getPublished();
		book2.setIsbn10(book.getIsbn10());
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book2);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, "ISBN10 already in use");
	}
	
	@Test
	@DisplayName("Test if a published book with a duplicate ISBN13 is persisted")
	public void persistBookWithDuplicateIsbn13() throws BookflixException {
		Published book = TestUtils.getPublished();
		book = publishedService.save(book);
		
		Published book2 = TestUtils.getPublished();
		book2.setIsbn13(book.getIsbn13());
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book2);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, "ISBN13 already in use");
	}
	
	@Test
	@DisplayName("Test if a published book with an ISBN13 that exceeds limit is persisted")
	public void persistBookWithIsbn13ThatExceedsLimit() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setIsbn13(RandomStringUtils.randomAlphabetic(15));
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.STRING_GREATER, "Book ISBN13", 13));
	}
	
	@Test
	@DisplayName("Test if a published book with a publication year in future is persisted")
	public void persistBookWithPublicationYearInFuture() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setPublicationYear(LocalDate.now().getYear()+10);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.NUMBER_GREATER, "Book publication year", LocalDate.now().getYear()));
	}
	
	@Test
	@DisplayName("Test if a published book with a negative publication year is persisted")
	public void persistBookWithNegativePublicationYear() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setPublicationYear(-1);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.NEGATIVE_VALUE, "Book publication year"));
	}
	
	@Test
	@DisplayName("Test if a published book with pages that exceeds limit is persisted")
	public void persistBookWithPagesThatExceedsLimit() throws BookflixException {
		Published book = TestUtils.getPublished();
		book.setPages(RandomStringUtils.randomAlphabetic(60));
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			publishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.STRING_GREATER, "Book pages", 50));
	}
	
	@Test
	@DisplayName("Test if a published book is persisted")
	public void persistBook() throws BookflixException {
		Published book = TestUtils.getPublished();
		Published bookFromDb = publishedService.save(book);

		assertTrue(book.getId() != null);
		assertEquals(book.getTitle(), bookFromDb.getTitle());
	}
	
}
