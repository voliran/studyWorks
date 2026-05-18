package org.example.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;

    public Transaction(BigDecimal amount, TransactionType transactionType) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.localDateTime = LocalDateTime.now();
    }

    public Transaction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public enum TransactionType {
        INCOME, EXPENSE
    }
}
