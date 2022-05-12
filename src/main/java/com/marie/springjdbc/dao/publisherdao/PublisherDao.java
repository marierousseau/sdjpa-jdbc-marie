package com.marie.springjdbc.dao.publisherdao;

import com.marie.springjdbc.domain.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PublisherDao {



    List<Publisher> findAllPublishersByCity(Pageable pageable);

    List<Publisher> findAllPublishersByState(Pageable pageable);

    List<Publisher> findAllPublishers(Pageable pageable);

    List<Publisher> findAllPublishers(int pageSize, int offset);

    List<Publisher> findAllPublishers();

    Publisher getById(Long id);

    Publisher findPublisherByCity(String city);
    Publisher findPublisherByState(String state);

   Publisher saveNewPublisher(Publisher publisher);

    Publisher updatePublisher(Publisher publisher);

    void deletePublisherById(Long id);

    Page<Publisher> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);
}



