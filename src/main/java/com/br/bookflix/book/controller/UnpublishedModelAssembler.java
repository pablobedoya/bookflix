package com.br.bookflix.book.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.unpublished.Unpublished;

@Component
public class UnpublishedModelAssembler implements RepresentationModelAssembler<Unpublished, EntityModel<Unpublished>> {

	@Override
	public EntityModel<Unpublished> toModel(Unpublished book) {
		try {
			List<Link> links = new ArrayList<>();
			if(book.getId() != null) {
				links.add(linkTo(methodOn(BookController.class).findOne(book.getId())).withSelfRel());
				links.add(linkTo(methodOn(BookController.class).update(book, book.getId())).withSelfRel());
				links.add(linkTo(methodOn(BookController.class).delete(book.getId())).withSelfRel());
			}
			links.add(linkTo(methodOn(BookController.class).findAll()).withRel("find-all"));
			
			return EntityModel.of(book, links);
		} catch (BookflixException e) {
			e.printStackTrace();
		}
		return null;
	}

}
