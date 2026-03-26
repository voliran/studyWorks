package org.example;

public class Student {
    private int id;
    private String gender;
    private String race_ethnicity;
    private String parental_level_of_education;
    private String lunch;
    private String test_preparation_course;
    private int math_score;
    private int reading_score;
    private int writing_score;

    public Student(int id, String gender, String race_ethnicity, String parental_level_of_education, String lunch, String test_preparation_course, int math_score, int reading_score, int writing_score) {
        this.id = id;
        this.gender = gender;
        this.race_ethnicity = race_ethnicity;
        this.parental_level_of_education = parental_level_of_education;
        this.lunch = lunch;
        this.test_preparation_course = test_preparation_course;
        this.math_score = math_score;
        this.reading_score = reading_score;
        this.writing_score = writing_score;
    }

    public int getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public String getRaceEthnicity() {
        return race_ethnicity;
    }

    public String getParentalLevelOfEducation() {
        return parental_level_of_education;
    }

    public String getLunch() {
        return lunch;
    }

    public String getTestPreparationCourse() {
        return test_preparation_course;
    }

    public int getMathScore() {
        return math_score;
    }

    public int getReadingScore() {
        return reading_score;
    }

    public int getWritingScore() {
        return writing_score;
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() + " gender: " + this.getGender() + " race ethnicity: " + this.getRaceEthnicity() + " parental level of education: " + this.getParentalLevelOfEducation() + " lunch: " + this.getLunch() + " test preparation course: " + this.getTestPreparationCourse() + " math score: " + this.getMathScore() + " reading score: " + this.getReadingScore() + " writing score: " + this.getWritingScore();
    }
}
