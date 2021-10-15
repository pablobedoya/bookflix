package com.br.bookflix.user.controller;

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
import com.br.bookflix.user.User;
import com.br.bookflix.user.service.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "User", tags = { "User" }, description = "Performs operations in users.")
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private UserModelAssembler userAssembler;

	// ----------------------------------------------------
	// Read
	// ----------------------------------------------------
	@ApiOperation(value = "List of users", response = User.class, responseContainer = "List", notes = "This endpoint is reponsible to find one or more users in Bookflix.")
	@RequestMapping(method = RequestMethod.GET)
	public CollectionModel<EntityModel<User>> findAll() throws BookflixException {

		List<User> users = userService.findAll();
		return userAssembler.toCollectionModel(users);

	}

	@ApiOperation(value = "List user by id", response = User.class, notes = "This endpoint is responsible to find a user by it's id..")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public EntityModel<User> findOne(@ApiParam(name = "id", value = "Id") @PathVariable Long id)
			throws BookflixException {

		User user = userService.findOne(id);

		return userAssembler.toModel(user);
	}

	// ----------------------------------------------------
	// Persist
	// ----------------------------------------------------

	@ApiOperation(value = "Creates a user", response = User.class, notes = "This endpoint is responsible to create a user in Bookflix..")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(
			@ApiParam(name = "user", value = "User info in JSON format") @RequestBody User user)
			throws BookflixException {

		EntityModel<User> entityModel = userAssembler.toModel(userService.save(user));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	// ----------------------------------------------------
	// Delete
	// ----------------------------------------------------

	@ApiOperation(value = "Deletes a user", notes = "This endpoint is responsible to delete a user in Bookflix.")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@ApiParam(name = "id", value = "Id") @PathVariable Long id)
			throws BookflixException {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	// ----------------------------------------------------
	// Update
	// ----------------------------------------------------
	@ApiOperation(value = "Updates a user", response = User.class, notes = "This endpoint is responsible to update a user in Bookflix.")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public EntityModel<User> update(
			@ApiParam(name = "user", value = "User info in JSON format") @RequestBody User user,
			@ApiParam(name = "id", value = "Id") @PathVariable Long id) throws BookflixException {
		user = userService.update(user, id);
		
		return userAssembler.toModel(user);
	}
	
	
}
