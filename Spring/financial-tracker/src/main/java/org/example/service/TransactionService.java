package org.example.service;

import org.example.model.Transaction;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> findAll() {
        return transactionRepository.findAllByOrderByDateDesc();
    }

    @Transactional
    public void saveIncome(BigDecimal amount) {
        Transaction transaction = new Transaction(amount, Transaction.TransactionType.INCOME);
        transactionRepository.save(transaction);
    }

    @Transactional
    public void saveExpense(BigDecimal amount) {
        Transaction transaction = new Transaction(amount, Transaction.TransactionType.EXPENSE);
        transactionRepository.save(transaction);
    }
}