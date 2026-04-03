package org.example.dao;

import org.example.model.Book;
import org.example.model.Reader;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void save(Book book);
    Optional<Book> findById(int id);
    List<Book> findAll();
    void update(Book book);
    void delete(int id);
}
