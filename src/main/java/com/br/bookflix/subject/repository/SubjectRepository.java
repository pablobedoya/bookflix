package com.br.bookflix.subject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bookflix.subject.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
