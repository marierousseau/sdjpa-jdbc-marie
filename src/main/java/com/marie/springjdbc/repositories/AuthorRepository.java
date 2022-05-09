package com.marie.springjdbc.repositories;

import com.marie.springjdbc.domain.Author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
