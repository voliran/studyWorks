package org.example;

import java.util.List;

public interface StudentDao {
    List<Student> getAllStudents();
    List<Student> getStudentsWithMathScoreAboveOrEquals(int minScore);
    List<Student> getStudentsByGender(String gender);
}
