package org.example.dao;

import org.example.model.Loan;
import org.example.db.DatabaseUtil;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoanDaoImpl implements LoanDao {

    @Override
    public void save(Loan loan) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO loans (book_id, reader_id, loan_date) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, loan.getBookId());
            preparedStatement.setInt(2, loan.getReaderId());
            preparedStatement.setDate(3, Date.valueOf(loan.getLoanDate()));
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                loan.setId(resultSet.getInt(1));
            }

            try (PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE books SET available_copies = available_copies - 1 WHERE id = ?")) {
                preparedStatement1.setInt(1, loan.getBookId());
                preparedStatement1.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Loan> findById(int id) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM loans WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSets = preparedStatement.executeQuery();
            if (resultSets.next()) {
                Loan loan = new Loan();
                loan.setId(resultSets.getInt("id"));
                loan.setBookId(resultSets.getInt("book_id"));
                loan.setReaderId(resultSets.getInt("reader_id"));
                loan.setLoanDate(resultSets.getDate("loan_date").toLocalDate());
                Date returnDate = resultSets.getDate("return_date");
                if (returnDate != null) {
                    loan.setReturnDate(returnDate.toLocalDate());
                }
                return Optional.of(loan);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return Optional.empty();
    }

    @Override
    public List<Loan> findAll() {
        List<Loan> loans = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM loans")) {
            while (resultSet.next()) {
                Loan loan = new Loan();
                loan.setId(resultSet.getInt("id"));
                loan.setBookId(resultSet.getInt("book_id"));
                loan.setReaderId(resultSet.getInt("reader_id"));
                loan.setLoanDate(resultSet.getDate("loan_date").toLocalDate());
                Date returnDate = resultSet.getDate("return_date");
                if (returnDate != null) {
                    loan.setReturnDate(returnDate.toLocalDate());
                }
                loans.add(loan);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return loans;
    }

    @Override
    public void update(Loan loan) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE loans SET book_id = ?, reader_id = ?, loan_date = ?, return_date = ? WHERE id = ?")) {
            preparedStatement.setInt(1, loan.getBookId());
            preparedStatement.setInt(2, loan.getReaderId());
            preparedStatement.setDate(3, Date.valueOf(loan.getLoanDate()));
            preparedStatement.setDate(4, loan.getReturnDate() != null ? Date.valueOf(loan.getReturnDate()) : null);
            preparedStatement.setInt(5, loan.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM loans WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Loan> findActiveLoans() {
        List<Loan> loans = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM loans WHERE return_date IS NULL")) {
            while (resultSet.next()) {
                Loan loan = new Loan();
                loan.setId(resultSet.getInt("id"));
                loan.setBookId(resultSet.getInt("book_id"));
                loan.setReaderId(resultSet.getInt("reader_id"));
                loan.setLoanDate(resultSet.getDate("loan_date").toLocalDate());
                loans.add(loan);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return loans;
    }

    @Override
    public List<Loan> findByReaderId(int readerId) {
        List<Loan> loans = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM loans WHERE reader_id = ?")) {
            preparedStatement.setInt(1, readerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Loan loan = new Loan();
                loan.setId(resultSet.getInt("id"));
                loan.setBookId(resultSet.getInt("book_id"));
                loan.setReaderId(resultSet.getInt("reader_id"));
                loan.setLoanDate(resultSet.getDate("loan_date").toLocalDate());
                Date returnDate = resultSet.getDate("return_date");
                if (returnDate != null) {
                    loan.setReturnDate(returnDate.toLocalDate());
                }
                loans.add(loan);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return loans;
    }

    @Override
    public void returnBook(int loanId) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE loans SET return_date = ? WHERE id = ? AND return_date IS NULL")) {
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.setInt(2, loanId);
            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                try (PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT book_id FROM loans WHERE id = ?")) {
                    preparedStatement1.setInt(1, loanId);
                    ResultSet resultSet = preparedStatement1.executeQuery();
                    if (resultSet.next()) {
                        int bookId = resultSet.getInt("book_id");
                        try (PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE books SET available_copies = available_copies + 1 WHERE id = ?")) {
                            preparedStatement2.setInt(1, bookId);
                            preparedStatement2.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}