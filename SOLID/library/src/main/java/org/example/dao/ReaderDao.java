package org.example.dao;

import org.example.model.Book;
import org.example.model.Reader;

import java.util.List;
import java.util.Optional;

public interface ReaderDao {
    void save(Reader reader);
    Optional<Reader> findById(int id);
    List<Reader> findAll();
    void update(Reader reader);
    void delete(int id);
}
