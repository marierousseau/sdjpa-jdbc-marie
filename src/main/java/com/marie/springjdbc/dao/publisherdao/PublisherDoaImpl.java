package com.marie.springjdbc.dao.publisherdao;

import com.marie.springjdbc.domain.Publisher;
import com.marie.springjdbc.repositories.PublisherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;


@Component
public class PublisherDoaImpl implements PublisherDao {


    private final PublisherRepository publisherRepository;

    public PublisherDoaImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;}


        @Override
        public List<Publisher> findAllPublishersByCity (Pageable pageable){
            Page<Publisher> publisherPage = publisherRepository.findAll(pageable);

            return publisherPage.getContent();
        }

        @Override
        public List<Publisher> findAllPublishersByState (Pageable pageable){
            Page<Publisher> publisherPage = publisherRepository.findAll(pageable);

            return publisherPage.getContent();
        }


        @Override
        public List<Publisher> findAllPublishers (Pageable pageable){
            return publisherRepository.findAll(pageable).getContent();
        }


        @Override
        public List<Publisher> findAllPublishers ( int pageSize, int offset){
            Pageable pageable = PageRequest.ofSize(pageSize);

            if (offset > 0) {
                pageable = pageable.withPage(offset / pageSize);
            } else {
                pageable = pageable.withPage(0);
            }

            return this.findAllPublishers(pageable);
        }

        @Override
        public List<Publisher> findAllPublishers () {
            return publisherRepository.findAll();
        }

        @Override
        public Publisher getById (Long id){
            return publisherRepository.getById(id);
        }

        @Override
        public Publisher findPublisherByCity (String city){
            return null;
        }

        @Override
        public Publisher findPublisherByState (String state){
            return null;
        }


        @Override
        public Publisher saveNewPublisher (Publisher publisher){
            return publisherRepository.save(publisher);
        }
        @Transactional
        @Override
        public Publisher updatePublisher (Publisher publisher){
            Publisher foundPublisher = publisherRepository.getById(publisher.getId());
            foundPublisher.setCity(publisher.getCity());
            foundPublisher.setPublisher(publisher.getPublisher());
            foundPublisher.setAuthor_id(publisher.getAuthor_id());
            foundPublisher.setState(publisher.getState());
            return publisherRepository.save(foundPublisher);
        }

        @Override
        public void deletePublisherById (Long id){
            publisherRepository.deleteAll();
        }

        @Override
        public Page<Publisher> findPaginated ( int pageNo, int pageSize, String sortField, String sortDirection){
            Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                    Sort.by(sortField).descending();

            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
            return this.publisherRepository.findAll(pageable);
        }
    }
