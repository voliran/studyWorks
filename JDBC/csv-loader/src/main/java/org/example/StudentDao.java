package org.example;

import java.util.List;

public interface StudentDao {
    List<Student> getAllStudents();
    List<Student> getStudentsWithMathScoreAbove(int minScore);
    List<Student> getStudentsByGender(String gender);
}
