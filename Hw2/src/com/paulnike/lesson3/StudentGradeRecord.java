package com.paulnike.lesson3;

public class StudentGradeRecord {
    String studentName;
    String school;
    String subject;
    double score;

    public StudentGradeRecord(String studentName, String school, String subject, double score) {
        this.studentName = studentName;
        this.school = school;
        this.subject = subject;
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return studentName + ", " + school + ", " + subject + ", " + score;
    }
}
