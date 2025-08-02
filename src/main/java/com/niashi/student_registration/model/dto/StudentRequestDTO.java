package com.niashi.student_registration.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StudentRequestDTO {

    @NotBlank(message = "Field must not be blank")
    private String name;

    @NotNull(message = "Field must not be null")
    @Min(value = 0, message = "Grade must be at least 0")
    @Max(value = 10, message = "Grade must be at most 10")
    private Double grade;

    public StudentRequestDTO() {
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
}
