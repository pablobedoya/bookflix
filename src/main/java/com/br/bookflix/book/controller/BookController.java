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
import com.br.bookflix.unpublished.Unpublished;
import com.br.bookflix.unpublished.service.UnpublishedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "Book", tags = { "Book" }, description="Performs operations in books")
@RequestMapping("/books")
public class BookController {
	
//	@Autowired
//	private PublishedModelAssembler publishedAssembler;
	
	@Autowired
	private UnpublishedModelAssembler unpublishedAssembler;
	
//	@Autowired
//	private PublishedService publishedService;
	
	@Autowired
	private UnpublishedService unpublishedService;

	// ----------------------------------------------------
	// Read
	// ----------------------------------------------------
	@ApiOperation(value = "List of books", response = Unpublished.class, responseContainer = "List",
			notes="This endpoint is responsible to find all books available in Bookflix")
	@RequestMapping(method = RequestMethod.GET)
	public CollectionModel<EntityModel<Unpublished>> findAll() throws BookflixException {

		List<Unpublished> books = unpublishedService.findAll();
		return unpublishedAssembler.toCollectionModel(books);
	}
	
	@ApiOperation(value = "List of books by genre", response = Unpublished.class, responseContainer = "List",
			notes="This endpoint is responsible to find books by genre")
	@RequestMapping(value = "/genre/{genre}", method = RequestMethod.GET)
	public CollectionModel<EntityModel<Unpublished>> findByGenre(
		@ApiParam(name = "genre", value = "Genre") @PathVariable String genre) throws BookflixException {

		List<Unpublished> books = unpublishedService.findByGenre(genre);
		return unpublishedAssembler.toCollectionModel(books);
	}

	@ApiOperation(value = "Find book by id", response = Unpublished.class, notes="This endpoint is responsible to find a book by it's id.")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public EntityModel<Unpublished> findOne(
		@ApiParam(name = "id", value = "Id") @PathVariable Long id) throws BookflixException {

		Unpublished book = unpublishedService.findOne(id);
		
		return unpublishedAssembler.toModel(book);
	}

	// ----------------------------------------------------
	// Persist
	// ----------------------------------------------------
	@ApiOperation(value = "Creates a book", response = Unpublished.class, 
			notes="This endpoint is responsible to create a book in Bookflix.")
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
	@ApiOperation(value = "Deletes a book", notes="This endpoint is responsible to delete a book in Bookflix.")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@ApiParam(name = "id", value = "Id") @PathVariable Long id) throws BookflixException {
		unpublishedService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// ----------------------------------------------------
	// Update
	// ----------------------------------------------------
	@ApiOperation(value = "Updates a book", response = Unpublished.class, notes="This endpoint is responsible to update a book in Bookflix.")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public EntityModel<Unpublished> update(
		@ApiParam(name = "book", value = "Book informatino in JSON format") @RequestBody Unpublished book,
		@ApiParam(name = "id", value = "Id") @PathVariable Long id) throws BookflixException {
		book = unpublishedService.update(book, id);
		
		return unpublishedAssembler.toModel(book);
	}
	
}
