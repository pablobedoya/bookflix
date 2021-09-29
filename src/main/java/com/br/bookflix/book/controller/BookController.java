package com.br.bookflix.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.bookflix.book.Book;
import com.br.bookflix.book.service.BookService;
import com.br.bookflix.exception.BookflixException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "Book", tags = { "Book" }, description="Realiza as operações realizadas aos livros")
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookModelAssembler assembler;
	
	@Autowired
	private BookService bookService;

	// ----------------------------------------------------
	// Read
	// ----------------------------------------------------
	@ApiOperation(value = "Listagem de livros", response = Book.class, responseContainer = "List",
			notes="Esse endpoint é responsável por buscar um ou mais livros no Bookflix que atendam aos filtros de pesquisa.")
	@RequestMapping(method = RequestMethod.GET)
	public CollectionModel<EntityModel<Book>> findAll() throws BookflixException {

		List<Book> books = bookService.findAll();
		return assembler.toCollectionModel(books);
	}

	@ApiOperation(value = "Busca livro de acordo com seu id", response = Book.class, notes="Esse endpoint é responsável por buscar um livro no Bookflix através do seu id.")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public EntityModel<Book> findOne(
		@ApiParam(name = "id", value = "Id") @PathVariable Long id) throws BookflixException {

		Book book = bookService.findOne(id);
		
		return assembler.toModel(book);
	}

	// ----------------------------------------------------
	// Persist
	// ----------------------------------------------------
	@ApiOperation(value = "Cria um livro", response = Book.class, notes="Esse endpoint é responsável pela criação um novo livro no Bookflix.")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(
		@ApiParam(name = "book", value = "Book data in JSON format") @RequestBody Book book) throws BookflixException {

		EntityModel<Book> entityModel = assembler.toModel(bookService.save(book));

		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	// ----------------------------------------------------
	// Delete
	// ----------------------------------------------------
	@ApiOperation(value = "Remove um livro", notes="Esse endpoint é responsável pela remoção de um livro no Bookflix.")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@ApiParam(name = "id", value = "Id") @PathVariable Long id) throws BookflixException {
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// ----------------------------------------------------
	// Update
	// ----------------------------------------------------
	@ApiOperation(value = "Atualiza o livro", response = Book.class, notes="Esse endpoint é responsável pela atualização dos dados de um livro no Bookflix.")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public EntityModel<Book> update(
		@ApiParam(name = "book", value = "Informações do livro em formato JSON") @RequestBody Book book,
		@ApiParam(name = "id", value = "Id") @PathVariable Long id) throws BookflixException {
		book = bookService.update(book, id);
		
		return assembler.toModel(book);
	}
	
}
