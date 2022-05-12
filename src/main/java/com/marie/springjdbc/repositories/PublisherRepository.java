package com.marie.springjdbc.repositories;

import com.marie.springjdbc.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {



    Publisher findPublisherByState(String state);

    Publisher getPublisherById(Long id);
}



