package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:h2:./local_db";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initDatabase() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS readers(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    );""");
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS books(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(100) NOT NULL,
                        author VARCHAR(100) NOT NULL,
                        available_copies INT DEFAULT 1
                    );""");
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS loans (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        reader_id INT,
                        book_id INT,
                        loan_date DATE NOT NULL,
                        return_date DATE,
                        FOREIGN KEY(reader_id) REFERENCES readers(id),
                        FOREIGN KEY(book_id) REFERENCES books(id)
                    );""");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
