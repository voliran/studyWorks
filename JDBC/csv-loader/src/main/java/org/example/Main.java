package org.example;

import java.io.*;
import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        saveToDbFromCsv();
        StudentDao studentDao = new StudentDaoImpl();
        List<Student> allStudents = studentDao.getAllStudents();
        for (Student student : allStudents) {
            System.out.println(student);
        }
        List<Student> femaleStudents = studentDao.getStudentsByGender("female");
        for (Student student : femaleStudents) {
            System.out.println(student);
        }
        List<Student> studentsWithMathScoreAboveOrEquals = studentDao.getStudentsWithMathScoreAboveOrEquals(50);
        for (Student student : studentsWithMathScoreAbove) {
            System.out.println(student);
        }
    }

    public static void saveToDbFromCsv() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/students_performance.csv"));
             Connection connection = DriverManager.getConnection("jdbc:h2:./testdb", "SA", "");
             Statement statement1 = connection.createStatement()) {
            statement1.executeUpdate("CREATE TABLE IF NOT EXISTS students (id INT AUTO_INCREMENT PRIMARY KEY, gender VARCHAR(10), race_ethnicity VARCHAR(30), parental_level_of_education VARCHAR(50), lunch VARCHAR(50), test_preparation_course VARCHAR(50), math_score INT, reading_score INT, writing_score INT);");
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO students (gender, race_ethnicity, parental_level_of_education, lunch, test_preparation_course, math_score, reading_score, writing_score) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");) {
                String line;
                bufferedReader.readLine();
                connection.setAutoCommit(false);
                while ((line = bufferedReader.readLine()) != null) {
                    String[] fields = line.split(",");
                    statement.setString(1, fields[0]);
                    statement.setString(2, fields[1]);
                    statement.setString(3, fields[2]);
                    statement.setString(4, fields[3]);
                    statement.setString(5, fields[4]);
                    statement.setInt(6, Integer.parseInt(fields[5]));
                    statement.setInt(7, Integer.parseInt(fields[6]));
                    statement.setInt(8, Integer.parseInt(fields[7]));
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.commit();
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
