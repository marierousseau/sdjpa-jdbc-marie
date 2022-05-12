package com.marie.springjdbc.repositories;

import com.marie.springjdbc.domain.Author;
import com.marie.springjdbc.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<Book, Long> {

//    Book jpaNamed(@Param("title") String title);

    @Query(value = "SELECT * FROM Book WHERE title = :title", nativeQuery = true)
    Book findBookByTitleNativeQuery(@Param("title") String title);

    @Query("SELECT b FROM Book b where b.title = :title")
    Book findBookByTitleWithQueryNamed(@Param("title") String title);

    @Query("SELECT b FROM Book b where b.title = ?1")
    Book findBookByTitleWithQuery(String title);



    @org.springframework.data.jdbc.repository.query.Query("SELECT * FROM bookdb2.book WHERE title LIKE '%?1%' " +
            " OR book.title LIKE '%?1%'" +
            "OR book.id LIKE '%?1%'"

    )
    List<Author> findBookByTitleOrId(String keyword, String s);



    Optional<Book> findBookByTitle(String title);

    Book readByTitle(String title);

    @Nullable
    Book getByTitle(@Nullable String title);

    Stream<Book> findAllByTitleNotNull();

    @Async
    Future<Book> queryByTitle(String title);
}