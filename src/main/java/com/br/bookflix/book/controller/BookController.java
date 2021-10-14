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

import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.published.service.PublishedService;
import com.br.bookflix.unpublished.Unpublished;
import com.br.bookflix.unpublished.service.UnpublishedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "Book", tags = { "Book" }, description="Realiza as operações realizadas aos livros")
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private PublishedModelAssembler publishedAssembler;
	
	@Autowired
	private UnpublishedModelAssembler unpublishedAssembler;
	
	@Autowired
	private PublishedService publishedService;
	
	@Autowired
	private UnpublishedService unpublishedService;

	// ----------------------------------------------------
	// Read
	// ----------------------------------------------------
	@ApiOperation(value = "Listagem de livros", response = Unpublished.class, responseContainer = "List",
			notes="Esse endpoint é responsável por buscar um ou mais livros no Bookflix que atendam aos filtros de pesquisa.")
	@RequestMapping(method = RequestMethod.GET)
	public CollectionModel<EntityModel<Unpublished>> findAll() throws BookflixException {

		List<Unpublished> books = unpublishedService.findAll();
		return unpublishedAssembler.toCollectionModel(books);
	}

	@ApiOperation(value = "Busca livro de acordo com seu id", response = Unpublished.class, notes="Esse endpoint é responsável por buscar um livro no Bookflix através do seu id.")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public EntityModel<Unpublished> findOne(
		@ApiParam(name = "id", value = "Id") @PathVariable Long id) throws BookflixException {

		Unpublished book = unpublishedService.findOne(id);
		
		return unpublishedAssembler.toModel(book);
	}

	// ----------------------------------------------------
	// Persist
	// ----------------------------------------------------
	@ApiOperation(value = "Cria um livro", response = Unpublished.class, notes="Esse endpoint é responsável pela criação um novo livro no Bookflix.")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(
		@ApiParam(name = "book", value = "Book data in JSON format") @RequestBody Unpublished book) throws BookflixException {

		EntityModel<Unpublished> entityModel = unpublishedAssembler.toModel(unpublishedService.save(book));

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
		unpublishedService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// ----------------------------------------------------
	// Update
	// ----------------------------------------------------
	@ApiOperation(value = "Atualiza o livro", response = Unpublished.class, notes="Esse endpoint é responsável pela atualização dos dados de um livro no Bookflix.")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public EntityModel<Unpublished> update(
		@ApiParam(name = "book", value = "Informações do livro em formato JSON") @RequestBody Unpublished book,
		@ApiParam(name = "id", value = "Id") @PathVariable Long id) throws BookflixException {
		book = unpublishedService.update(book, id);
		
		return unpublishedAssembler.toModel(book);
	}
	
}
