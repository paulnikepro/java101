package com.paulnike.lesson3;

import java.util.*;

public class StudentGradesProcessor {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Alice", 26, Arrays.asList(
                        new Grade("Math", 85),
                        new Grade("Science", 92)),
                        "High School A",
                        new Address("New York", "5th Avenue")),

                new Student("Bob", 24, Arrays.asList(
                        new Grade("Math", 75),
                        new Grade("Science", 80)),
                        "High School B",
                        new Address("New York", "Madison Street")),

                new Student("Charlie", 37, Arrays.asList(
                        new Grade("Math", 95),
                        new Grade("History", 87)),
                        "High School A",
                        new Address("New York", "Broadway")),

                new Student("David", 14, Arrays.asList(
                        new Grade("Math", 88),
                        new Grade("English", 79)),
                        "High School C",
                        new Address("New York", "Wall Street")),

                new Student("Eva", 25, Arrays.asList(
                        new Grade("Math", 80),
                        new Grade("Science", 85)),
                        "High School D",
                        new Address("Los Angeles", "Hollywood Stars street"))
        );

        List<StudentGradeRecord> topGrades = students.stream()
                .filter(student -> student.age > 15 && "New York".equals(student.address.city))
                .flatMap(student -> student.grades.stream()
                        .map(grade -> new StudentGradeRecord(student.name, student.school, grade.subject, grade.score)))
                .sorted(Comparator.comparingDouble(StudentGradeRecord::score).reversed())
                .limit(3)
                .toList();

        topGrades.forEach(System.out::println);
    }
}
