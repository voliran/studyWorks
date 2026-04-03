package org.example.dao;

import org.example.model.Book;
import org.example.model.Loan;
import org.example.model.Reader;

import java.util.List;
import java.util.Optional;

public interface LoanDao {
    void save(Loan loan);
    Optional<Loan> findById(int id);
    List<Loan> findAll();
    void update(Loan loan);
    void delete(int id);
    List<Loan> findActiveLoans();
    List<Loan> findByReaderId(int readerId);
    void returnBook(int loanId);
}
