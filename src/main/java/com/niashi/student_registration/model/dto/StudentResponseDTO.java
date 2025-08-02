package com.niashi.student_registration.model.dto;

public class StudentResponseDTO {
    private Long id;
    private String name;
    private double grade;
    private char firstNonRepeatingChar;

    public StudentResponseDTO() {
    }

    public StudentResponseDTO(Long id, String name, double grade, char firstNonRepeatingChar) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.firstNonRepeatingChar = firstNonRepeatingChar;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public char getFirstNonRepeatingChar() {
        return firstNonRepeatingChar;
    }

    public void setFirstNonRepeatingChar(char firstNonRepeatingChar) {
        this.firstNonRepeatingChar = firstNonRepeatingChar;
    }
}
