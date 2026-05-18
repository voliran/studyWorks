package org.example.repository;


import jakarta.persistence.*;
import org.example.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Transaction transaction) {
        entityManager.persist(transaction);
    }

    public List<Transaction> findAll() {
        Query query = entityManager.createQuery("SELECT t FROM Transaction t");
        return query.getResultList();
    }

    public List<Transaction> findAllByOrderByDateDesc() {
        Query query = entityManager.createQuery("SELECT t FROM Transaction t ORDER BY t.localDateTime DESC");
        return query.getResultList();
    }

    public Transaction findById(long id) {
        return entityManager.find(Transaction.class, id);
    }

    public void deleteById(Long id) {
        entityManager.createQuery("DELETE FROM Transaction WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
