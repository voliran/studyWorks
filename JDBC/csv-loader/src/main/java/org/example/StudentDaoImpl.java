package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao{
    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./testdb", "SA", "");
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt("id"), resultSet.getString("gender"), resultSet.getString("race_ethnicity"), resultSet.getString("parental_level_of_education"), resultSet.getString("lunch"), resultSet.getString("test_preparation_course"), resultSet.getInt("math_score"), resultSet.getInt("reading_score"), resultSet.getInt("writing_score")));
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> getStudentsWithMathScoreAbove(int minScore) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./testdb", "SA", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM students WHERE math_score >= ? ")) {
            preparedStatement.setInt(1, minScore);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt("id"), resultSet.getString("gender"), resultSet.getString("race_ethnicity"), resultSet.getString("parental_level_of_education"), resultSet.getString("lunch"), resultSet.getString("test_preparation_course"), resultSet.getInt("math_score"), resultSet.getInt("reading_score"), resultSet.getInt("writing_score")));
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> getStudentsByGender(String gender) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./testdb", "SA", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM students WHERE gender = ? ")) {
            preparedStatement.setString(1, gender);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt("id"), resultSet.getString("gender"), resultSet.getString("race_ethnicity"), resultSet.getString("parental_level_of_education"), resultSet.getString("lunch"), resultSet.getString("test_preparation_course"), resultSet.getInt("math_score"), resultSet.getInt("reading_score"), resultSet.getInt("writing_score")));
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
