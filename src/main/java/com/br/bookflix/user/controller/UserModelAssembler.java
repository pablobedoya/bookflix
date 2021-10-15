package com.br.bookflix.user.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.springframework.stereotype.Component;

import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.user.User;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>>{

	@Override
	public EntityModel<User> toModel(User user) {
		try {
			List<Link> links = new ArrayList<>();
			if(user.getId() != null) {
				links.add(linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel());
				links.add(linkTo(methodOn(UserController.class).update(user, user.getId())).withSelfRel());
				links.add(linkTo(methodOn(UserController.class).delete(user.getId())).withSelfRel());
			}
			links.add(linkTo(methodOn(UserController.class).findAll()).withRel("find-all"));
			
			return EntityModel.of(user, links);
		} catch(BookflixException e) {
			e.printStackTrace();
		}
		return null;
	}

}
