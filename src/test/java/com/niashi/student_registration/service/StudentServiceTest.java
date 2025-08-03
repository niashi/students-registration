package com.niashi.student_registration.service;

import com.niashi.student_registration.model.Student;
import com.niashi.student_registration.model.dto.StudentRequestDTO;
import com.niashi.student_registration.model.dto.StudentResponseDTO;
import com.niashi.student_registration.model.mapper.StudentMapper;
import com.niashi.student_registration.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentMapper mapper;

    @InjectMocks
    private StudentService studentService;

    @Test
    void shouldCallRepositorySuccessfully() {
        StudentRequestDTO requestDTO = new StudentRequestDTO();
        requestDTO.setName("Anna");
        requestDTO.setGrade(9.5);

        studentService.createStudent(requestDTO);

        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(repository, times(1)).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertEquals("Anna", capturedStudent.getName());
        assertEquals(9.5, capturedStudent.getGrade());
    }

    @Test
    void shouldReturnStudentResponseDTOSuccessfully() {
        Long id = 1L;
        Student studentInDB = new Student("Carlos", 8.0);
        StudentResponseDTO expectedResponse = new StudentResponseDTO(id, "Carlos", 8.0, 'C');

        when(repository.findById(id)).thenReturn(Optional.of(studentInDB));
        when(mapper.apply(studentInDB, 'C')).thenReturn(expectedResponse);

        StudentResponseDTO responseDTO = studentService.getStudentById(id);

        assertNotNull(responseDTO);
        assertEquals(id, responseDTO.getId());
        assertEquals("Carlos", responseDTO.getName());
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).apply(studentInDB, 'C');
    }

    @Test
    void shouldReturnNullWhenIdIsNotFound() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        StudentResponseDTO responseDTO = studentService.getStudentById(id);

        assertNull(responseDTO);
    }

    @Test
    void shouldReturnListOfStudentResponseDTO() {
        Student student1 = new Student("Anna", 10.0);
        Student student2 = new Student("Carlos", 7.5);
        List<Student> listFromDB = List.of(student1, student2);

        StudentResponseDTO dto1 = new StudentResponseDTO(1L, "Anna", 10.0, '_');
        StudentResponseDTO dto2 = new StudentResponseDTO(2L, "Carlos", 7.5, 'C');

        when(repository.findAll()).thenReturn(listFromDB);
        when(mapper.apply(student1, '_')).thenReturn(dto1);
        when(mapper.apply(student2, 'C')).thenReturn(dto2);

        List<StudentResponseDTO> response = studentService.getAllStudents();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Anna", response.get(0).getName());
        assertEquals("Carlos", response.get(1).getName());
        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).apply(any(Student.class), anyChar());
    }
}
