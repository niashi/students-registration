package com.niashi.student_registration.service;

import com.niashi.student_registration.model.Student;
import com.niashi.student_registration.model.dto.StudentRequestDTO;
import com.niashi.student_registration.model.dto.StudentResponseDTO;
import com.niashi.student_registration.model.mapper.StudentMapper;
import com.niashi.student_registration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final StudentMapper mapper;

    @Autowired
    public StudentService(StudentRepository repository, StudentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void createStudent(StudentRequestDTO studentCreationRequest) {
        Student student = new Student();

        student.setName(studentCreationRequest.getName());
        student.setGrade(studentCreationRequest.getGrade());

        repository.save(student);
    }

    public StudentResponseDTO getStudentById(Long id) {
        return repository.findById(id)
                .map(student -> {
                    char nonRepeatingChar = this.findFirstNonRepeatingChar(student.getName());
                    StudentResponseDTO responseDTO = mapper.apply(student, nonRepeatingChar);

                    responseDTO.setFirstNonRepeatingChar(nonRepeatingChar);

                    return responseDTO;
                })
                .orElse(null);
    }

    public List<StudentResponseDTO> getAllStudents() {
        return repository.findAll()
                .stream()
                .map(student -> {
                    char nonRepeatingChar = this.findFirstNonRepeatingChar(student.getName());
                    StudentResponseDTO responseDTO = mapper.apply(student, nonRepeatingChar);

                    responseDTO.setFirstNonRepeatingChar(nonRepeatingChar);

                    return responseDTO;
                })
                .toList();
    }

    private char findFirstNonRepeatingChar(String name) {
        if (name == null || name.isEmpty()) {
            return '_';
        }

        String lowerCaseName = name.toLowerCase();

        for (int i = 0; i < lowerCaseName.length(); i++) {
            char c = lowerCaseName.charAt(i);

            if (lowerCaseName.indexOf(c) == lowerCaseName.lastIndexOf(c)) {
                return name.charAt(i);
            }
        }

        return '_';
    }
}
