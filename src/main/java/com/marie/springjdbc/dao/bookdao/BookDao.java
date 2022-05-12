package com.marie.springjdbc.dao.bookdao;

import com.marie.springjdbc.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao {

    List<Book> findAllBooksSortByTitle(Pageable pageable);

    List<Book> findAllBooks(Pageable pageable);

    List<Book> findAllBooks(int pageSize, int offset);

    List<Book> findAllBooks();

    List<Book>findBookByTitleOrId(String keyword,String  id);

    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

    Page<Book> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);
}
