package com.br.bookflix.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.MediaType;

import com.br.bookflix.author.Author;
import com.br.bookflix.error.CustomError;
import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.published.Published;
import com.br.bookflix.subject.Subject;
import com.br.bookflix.unpublished.Unpublished;
import com.br.bookflix.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class TestUtils {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private static final int MIN_YEAR = 1900;
	private static final int MAX_YEAR = 2021;
	
	public static void assertBookflixException(BookflixException thrown, String message, String details) {
		assertEquals(message, thrown.getMessage());
		assertEquals(details, thrown.getDetails());
	}
	
	public static void assertCustomError(CustomError error, String message, String details) {
		assertEquals(error.getErrorMessage(), message);
		assertEquals(error.getErrorDetails(), details);
	}
	
	public static MediaType getJsonMediaType() {
		return new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	}
	
	public static Gson getGson() {
		return new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).create();
	}
	
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
	
	public static Published getPublished() {
		Published book = new Published();
		book.setTitle(RandomStringUtils.randomAlphabetic(1, 499));
		book.setDescription(RandomStringUtils.randomAlphabetic(1, 499));
		book.setThumbnail(new byte[] {});
		book.setGenre(RandomStringUtils.randomAlphabetic(1, 99));
		book.setSubjects(getSubjects());
		book.setAuthors(getAuthors());
		book.setIsbn10(RandomStringUtils.randomAlphabetic(10));
		book.setIsbn13(RandomStringUtils.randomAlphabetic(13));
		book.setPublisher(RandomStringUtils.randomAlphabetic(1, 255));
		book.setPublicationYear(generateRandomInt(MIN_YEAR, MAX_YEAR));
		book.setPages(RandomStringUtils.randomAlphabetic(1, 49));
		
		return book;
	}
	
	public static Unpublished getUnpublished() {
		Unpublished book = new Unpublished();
		book.setTitle(RandomStringUtils.randomAlphabetic(1, 499));
		book.setDescription(RandomStringUtils.randomAlphabetic(1, 499));
		book.setThumbnail(new byte[] {});
		book.setGenre(RandomStringUtils.randomAlphabetic(1, 99));
		book.setSubjects(getSubjects());
		book.setAuthors(getAuthors());
		book.setFile(new byte[] {});
		// related books
		// uploaded by
		
		return book;
	}
	
	public static User getUser() {
		User user = new User();
		user.setFirstName(RandomStringUtils.randomAlphabetic(1, 254));
		user.setLastName(RandomStringUtils.randomAlphabetic(1, 254));
		user.setCpf(RandomStringUtils.randomAlphabetic(1, 14));
		user.setEmail("teste@gmail.com");
		user.setDateOfBirth(LocalDate.now());
		user.setPassword(RandomStringUtils.randomAlphabetic(1, 254));
		
		return user;
	}
	
	private static Set<Subject> getSubjects() {
		Set<Subject> set = new HashSet<>();
		
		Subject s1 = new Subject();
		s1.setName("Subject 1");
		
		Subject s2 = new Subject();
		s2.setName("Subject 2");
		
		set.add(s1);
		set.add(s2);
		
		return set;
	}
	
	private static Set<Author> getAuthors() {
		Set<Author> set = new HashSet<>();
		
		Author a1 = new Author();
		a1.setName("Author 1");
		
		Author a2 = new Author();
		a2.setName("Author 2");
		
		set.add(a1);
		set.add(a2);
		
		return set;
	}
	
	private static int generateRandomInt(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
	
}
