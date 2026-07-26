package com.asif.studentmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    @Test
    void shouldReturnAllStudents() {

        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Rahul");
        student1.setCourse("Java");
        student1.setEmail("rahul@gmail.com");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Amit");
        student2.setCourse("Spring Boot");
        student2.setEmail("amit@gmail.com");

        when(repository.findAll()).thenReturn(List.of(student1, student2));

        List<Student> result = service.getAllStudents();

        assertEquals(2, result.size());
        assertEquals("Rahul", result.get(0).getName());
        assertEquals("Amit", result.get(1).getName());
    }
}