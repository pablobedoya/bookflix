package com.br.bookflix.unpublished.service;

import static org.junit.Assert.assertThrows;

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
import com.br.bookflix.unpublished.Unpublished;
import com.br.bookflix.utils.Constants;
import com.br.bookflix.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookflixApplication.class, properties = {"spring.config.name=publishedDb","bookflix.trx.datasource.url=jdbc:h2:mem:publishedDb"})
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class UnpublishedServiceTest {
	
	@Autowired
	private UnpublishedService unpublishedService;
	
	@Test
	@DisplayName("Test if an unpublished book without file is persisted")
	public void persistBookWithoutFile() throws BookflixException {
		Unpublished book = TestUtils.getUnpublished();
		book.setFile(null);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			unpublishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "Book attachment"));
	}
	
	@Test
	@DisplayName("Test if an unpublished book without creator is persisted")
	public void persistBookWithoutCreator() throws BookflixException {
		Unpublished book = TestUtils.getUnpublished();
		book.setUploadedBy(null);
		
		BookflixException thrown = assertThrows(BookflixException.class, () -> {
			unpublishedService.save(book);
		});
		
		TestUtils.assertBookflixException(thrown, Constants.INVALID_VALUES, String.format(Constants.EMPTY_VALUE, "Book creator"));
	}
	
}
