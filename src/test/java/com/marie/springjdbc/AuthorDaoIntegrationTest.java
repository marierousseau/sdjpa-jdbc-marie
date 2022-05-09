package com.marie.springjdbc;


import com.marie.springjdbc.dao.AuthorDao;
import com.marie.springjdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest //does not scan entire project: test will fail
@ComponentScan(basePackages = {"com.marie.springjdbc.dao"} )
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void testGetAuthor(){

        Author author = authorDao.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testSaveAuthor(){
        Author author = new Author();
        author.setFirstName("Marie");
        author.setLastName("Rousseau");
        Author saved = authorDao.saveNewAuthor(author);

       assertThat(saved).isNotNull();

    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("Marie");
        author.setLastName("Stevens");

        Author saved = authorDao.saveNewAuthor(author);

        saved.setLastName("Rousseau");
        Author updated = authorDao.updateAuthor(saved);

        assertThat(updated.getLastName()).isEqualTo("Rousseau");
    }

    @Test
    void testDeleteAuthor(){
        Author author = new Author();
        author.setFirstName("Marie");
        author.setLastName("Stevens");

        Author saved = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(saved.getId());
        Author deleted = authorDao.getById(saved.getId());

        assertThat(deleted).isNotNull();
    }

}
