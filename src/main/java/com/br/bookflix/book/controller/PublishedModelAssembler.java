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
import com.br.bookflix.published.Published;

@Component
public class PublishedModelAssembler implements RepresentationModelAssembler<Published, EntityModel<Published>> {

	@Override
	public EntityModel<Published> toModel(Published book) {
		try {
			List<Link> links = new ArrayList<>();
			links.add(linkTo(methodOn(BookController.class).findAll()).withRel("find-all"));
			
			return EntityModel.of(book, links);
		} catch (BookflixException e) {
			e.printStackTrace();
		}
		return null;
	}

}
