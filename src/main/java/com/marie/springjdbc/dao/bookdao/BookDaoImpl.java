package com.marie.springjdbc.dao.bookdao;


import com.marie.springjdbc.domain.Book;
import com.marie.springjdbc.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class BookDaoImpl implements BookDao {

    private final BookRepository bookRepository;

    public BookDaoImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);

        return bookPage.getContent();
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        Pageable pageable = PageRequest.ofSize(pageSize);

        if (offset > 0) {
            pageable = pageable.withPage(offset / pageSize);
        } else {
            pageable = pageable.withPage(0);
        }

        return this.findAllBooks(pageable);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findBookByTitleOrId(String keyword,String Id) {
        return null;
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.getById(id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Book saveNewBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(Book book) {
        Book foundBook = bookRepository.getById(book.getId());
        foundBook.setIsbn(book.getIsbn());
        foundBook.setPublisher(book.getPublisher());
        foundBook.setAuthorId(book.getAuthorId());
        foundBook.setTitle(book.getTitle());
        return bookRepository.save(foundBook);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.bookRepository.findAll(pageable);
    }
}





//@Component
//public class BookDaoImpl implements BookDao {
//
//    private final DataSource source;
//
//    private final AuthorDao authorDao;
//
//    @Autowired
//    public BookDaoImpl(DataSource source, AuthorDao authorDao) {
//        this.source = source;
//        this.authorDao = authorDao;
//    }
//
//    @Override
//    public Book getById(Long id) {
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = source.getConnection();
//            ps = connection.prepareStatement("SELECT * FROM book where id = ?");
//            ps.setLong(1, id);
//            resultSet = ps.executeQuery();
//
//            if (resultSet.next()) {
//                return getBookFromRS(resultSet);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                closeAll(resultSet, ps, connection);
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    public Book findByTitle(String title) {
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = source.getConnection();
//            ps = connection.prepareStatement("SELECT * FROM book where title = ?");
//            ps.setString(1, title);
//
//            resultSet = ps.executeQuery();
//
//            if (resultSet.next()) {
//                return getBookFromRS(resultSet);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                closeAll(resultSet, ps, connection);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    public Book saveNewBook(Book book) {
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = source.getConnection();
//            ps = connection.prepareStatement("INSERT INTO book (isbn, publisher, title, author_id) VALUES (?, ?, ?, ?)");
//            ps.setString(1, book.getIsbn());
//            ps.setString(2, book.getPublisher());
//            ps.setString(3, book.getTitle());
//            if (book.getAuthor() != null) {
//                ps.setLong(4, book.getAuthor().getId());
//            } else {
//                ps.setNull(4, -5);
//            }
//
//            ps.execute();
//
//            Statement statement = connection.createStatement();
//            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
//
//            if (resultSet.next()) {
//                Long savedId = resultSet.getLong(1);
//                return this.getById(savedId);
//            }
//
//            statement.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                closeAll(resultSet, ps, connection);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    public Book updateBook(Book book) {
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = source.getConnection();
//            ps = connection.prepareStatement("UPDATE book set isbn = ?, publisher = ?, title = ?, author_id = ? where id = ?");
//            ps.setString(1, book.getIsbn());
//            ps.setString(2, book.getPublisher());
//            ps.setString(3, book.getTitle());
//           if (book.getAuthor() != null)
//               ps.setLong(4, book.getAuthor().getId());
//            ps.setLong(5, book.getId());
//            ps.execute();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                closeAll(resultSet, ps, connection);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return getById(book.getId());
//    }
//
//    @Override
//    public void deleteBookById(Long id) {
//        Connection connection = null;
//        PreparedStatement ps = null;
//
//        try {
//            connection = source.getConnection();
//            ps = connection.prepareStatement("DELETE from book where id = ?");
//            ps.setLong(1, id);
//            ps.execute();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } finally {
//            try {
//                closeAll(null, ps, connection);
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//    }
//    private Book getBookFromRS(ResultSet resultSet) throws SQLException {
//        Book book = new Book();
//        book.setId(resultSet.getLong(1));
//        book.setIsbn(resultSet.getString(2));
//        book.setPublisher(resultSet.getString(3));
//        book.setTitle(resultSet.getString(4));
//
//
//        book.setAuthor(authorDao.getById(resultSet.getLong(5)));
//
//
//        return book;
//    }
//
//    private void closeAll(ResultSet resultSet, PreparedStatement ps, Connection connection) throws SQLException {
//        if (resultSet != null) {
//            resultSet.close();
//        }
//
//        if (ps != null){
//            ps.close();
//        }
//
//        if (connection != null){
//            connection.close();
//        }
//    }
//
//}
