package com.marie.springjdbc.dao;

import com.marie.springjdbc.domain.Author;
import com.marie.springjdbc.repositories.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Component
public class AuthorDaoImpl implements AuthorDao {

    private final AuthorRepository authorRepository;

    public AuthorDaoImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAllAuthorsByLastName(String lastname, Pageable pageable) {
        return authorRepository.findAuthorByLastName(lastname, pageable).getContent();
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.getById(id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        Author foundAuthor = authorRepository.getById(author.getId());
        foundAuthor.setFirstName(author.getFirstName());
        foundAuthor.setLastName(author.getLastName());
        return authorRepository.save(foundAuthor);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);

    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Page<Author> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.authorRepository.findAll(pageable);
    }


}
















//    private final JdbcTemplate jdbcTemplate;
//
//    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

//    @Override
//    public Author getById(Long id) {
//        return jdbcTemplate.queryForObject("SELECT * FROM author where id = ?", getRowMapper(), id);
//    }
//
//    @Override
//    public Author findAuthorByName(String firstName, String lastName) {
//        return null;
//    }
//
//    @Override
//    public Author saveNewAuthor(Author author) {
//        return null;
//    }
//
//    @Override
//    public Author updateAuthor(Author saved) {
//        return null;
//    }
//
//    @Override
//    public void deleteAuthorById(Long id) {
//
//    }
//    private RowMapper<Author> getRowMapper(){
//        return new AuthorMapper();
//    }
//
//}


//    private final DataSource source;
//
//    @Autowired //not needed because Spring picks it up automatically, but here for reference
//    public AuthorDaoImpl(DataSource source) {
//        this.source = source;
//    }
//
//    @Override
//    public Author getById(Long id) {
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = source.getConnection();
//            ps = connection.prepareStatement("SELECT * FROM author where id = ?");
//            ps.setLong(1, id);
//            resultSet = ps.executeQuery();
//
//            if (resultSet.next()) {
//                return getAuthorFromRS(resultSet);
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
//    public Author findAuthorByName(String firstName, String lastName) {
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = source.getConnection();
//            ps = connection.prepareStatement("SELECT * FROM author where first_name = ? and last_name = ?");
//            ps.setString(1, firstName);
//            ps.setString(2, lastName);
//            resultSet = ps.executeQuery();
//
//            if (resultSet.next()) {
//                return getAuthorFromRS(resultSet);
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
//    public Author saveNewAuthor(Author author) {
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = source.getConnection();
//            ps = connection.prepareStatement("INSERT INTO author (first_name, last_name) values (?, ?)");
//            ps.setString(1, author.getFirstName());
//            ps.setString(2, author.getLastName());
//            ps.execute();
//
//            Statement statement = connection.createStatement();
//
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
//    public Author updateAuthor(Author author) {
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = source.getConnection();
//            ps = connection.prepareStatement("UPDATE author set first_name = ?, last_name = ? where author.id = ?");
//            ps.setString(1, author.getFirstName());
//            ps.setString(2, author.getLastName());
//            ps.setLong(3, author.getId());
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
//        return this.getById(author.getId());
//    }
//
//    @Override
//    public void deleteAuthorById(Long id) {
//        Connection connection = null;
//        PreparedStatement ps = null;
//
//        try {
//            connection = source.getConnection();
//            ps = connection.prepareStatement("DELETE from author where id = ?");
//            ps.setLong(1, id);
//            ps.execute();
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } finally {
//            try{
//                closeAll(null, ps, connection);
//            } catch (SQLException ex){
//
//            }
//        }
//    }

//    private Author getAuthorFromRS(ResultSet resultSet) throws SQLException {
//        Author author = new Author();
//        author.setId(resultSet.getLong("id"));
//        author.setFirstName(resultSet.getString("first_name"));
//        author.setLastName(resultSet.getString("last_name"));
//        return author;
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

