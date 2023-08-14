package com.example.rest.DelegateImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.openapitools.api.BookApiDelegate;
import org.openapitools.model.Book;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.rest.Repository.BooksRepository;

import ch.qos.logback.classic.Logger;

@Service
public class BookApiDelegateImpl implements BookApiDelegate {

    @Autowired
    private BooksRepository booksRepository;
    Logger logger = (Logger) LoggerFactory.getLogger(BookApiDelegateImpl.class);
    private Map<String,String> response;
    @Override
    public ResponseEntity<String> addBook(Book book) {
        // TODO Auto-generated method stub
        try{
            List<Book> books = booksRepository.findBySbn(book.getSbn());
            response = new HashMap<>();
        if(book.getSbn() == null || book.getSbn() == ""){
            return new ResponseEntity<String>("response: sbn must be filled",HttpStatusCode.valueOf(500));
        }
        else if(books.size()>0){
             return new ResponseEntity<String>("response: duplicate",HttpStatusCode.valueOf(500));
        }
        booksRepository.save(book);
        return new ResponseEntity<>("response: success",HttpStatusCode.valueOf(200));
        }
        catch(Exception e){
          logger.error("ini errornya cuy :"+e.getLocalizedMessage());
        }
        //return new ResponseEntity<>(org.springframework.http.HttpStatus.NOT_IMPLEMENTED);
        return BookApiDelegate.super.addBook(book);
    }

    @Override
    public ResponseEntity<List<Book>> getBooks() {
        // TODO Auto-generated method stub
        try{
          List<Book> books = booksRepository.findAll();
          return new ResponseEntity<List<Book>>(books,HttpStatusCode.valueOf(200));
        }catch(Exception e){
            logger.error("ini errornya cuy :"+e.getLocalizedMessage());
        }
        return BookApiDelegate.super.getBooks();
    }

    @Override
    public ResponseEntity<List<Book>> getBook(Book book) {
        // TODO Auto-generated method stub
        try{
            Query query = new Query();
            List<Book> books = booksRepository.findBySbn(book.getSbn());
            books.addAll(booksRepository.findByAuthor(book.getAuthor()));
            books.addAll(booksRepository.findByTitle(book.getTitle()));
            return new ResponseEntity<List<Book>>(books,HttpStatusCode.valueOf(200));
          }catch(Exception e){
              logger.error("ini errornya cuy :"+e.getLocalizedMessage());
          }
        return BookApiDelegate.super.getBook(book);
    }
    
}
