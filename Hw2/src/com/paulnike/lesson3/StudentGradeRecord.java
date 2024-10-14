package com.paulnike.lesson3;

public record StudentGradeRecord(String studentName, String school, String subject, double score) {

    @Override
    public String toString() {
        return studentName + ", " + school + ", " + subject + ", " + score;
    }
}