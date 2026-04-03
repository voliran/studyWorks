package org.example.model;

import java.time.LocalDate;

public class Loan {
    private int id;
    private int bookId;
    private int readerId;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan() {
    }

    public Loan(int bookId, int readerId, LocalDate loanDate) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.loanDate = loanDate;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getReaderId() {
        return readerId;
    }
    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", readerId=" + readerId +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }
}