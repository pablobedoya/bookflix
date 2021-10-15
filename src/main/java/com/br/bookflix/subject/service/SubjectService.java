package com.br.bookflix.subject.service;

import java.util.List;

import com.br.bookflix.crud.AbstractCrud;
import com.br.bookflix.exception.BookflixException;
import com.br.bookflix.subject.Subject;

public interface SubjectService extends AbstractCrud<Subject> {
	
	public List<Subject> saveAll(List<Subject> subjects) throws BookflixException;
	
}