package com.niashi.student_registration.model.mapper;

import com.niashi.student_registration.model.Student;
import com.niashi.student_registration.model.dto.StudentResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentResponseDTO apply(Student student, char firstNonRepeatingChar) {
        return new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getGrade(),
                firstNonRepeatingChar
        );
    }
}
