package com.niashi.student_registration.controller;

import com.niashi.student_registration.model.dto.StudentRequestDTO;
import com.niashi.student_registration.model.dto.StudentResponseDTO;
import com.niashi.student_registration.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createStudent(
            @Valid @RequestBody StudentRequestDTO studentRequestDTO
    ) {
        service.createStudent(studentRequestDTO);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        StudentResponseDTO student = service.getStudentById(id);

        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = service.getAllStudents();

        return ResponseEntity.ok(students);
    }
}
