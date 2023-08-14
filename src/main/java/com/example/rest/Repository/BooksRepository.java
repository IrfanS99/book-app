package com.example.rest.Repository;

import java.util.List;

import org.openapitools.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends MongoRepository<Book, String> {
    List<Book> findByTitle(String title);
    List<Book> findBySbn(String sbn);
    List<Book> findByAuthor(String Author);
}
