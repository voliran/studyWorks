package org.example.dao;

import org.example.model.Reader;
import org.example.db.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReaderDaoImpl implements ReaderDao {

    @Override
    public void save(Reader reader) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO readers (name) VALUES (?);", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, reader.getName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                reader.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Reader> findById(int id) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM readers WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Reader reader = new Reader();
                reader.setId(resultSet.getInt("id"));
                reader.setName(resultSet.getString("name"));
                return Optional.of(reader);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return Optional.empty();
    }

    @Override
    public List<Reader> findAll() {
        List<Reader> readers = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM readers")) {
            while (rs.next()) {
                Reader reader = new Reader();
                reader.setId(rs.getInt("id"));
                reader.setName(rs.getString("name"));
                readers.add(reader);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return readers;
    }

    @Override
    public void update(Reader reader) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE readers SET name = ? WHERE id = ?")) {
            preparedStatement.setString(1, reader.getName());
            preparedStatement.setInt(3, reader.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM readers WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

}