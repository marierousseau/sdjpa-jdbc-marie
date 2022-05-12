package com.marie.springjdbc.repositories;

import com.marie.springjdbc.domain.Author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);

    Page<Author> findAuthorByLastName(String lastName, Pageable pageable);

    @Query("SELECT * FROM bookdb2.author WHERE first_name LIKE '%?1%' " +
            " OR last_name LIKE '%?1%'"
            )
    List<Author> findAuthorsByFirstNameOrLastName(String keyword, String s);
}
