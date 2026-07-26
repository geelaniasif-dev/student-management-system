package com.asif.studentmanagement;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;

import com.asif.studentmanagement.exception.StudentNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StudentService service;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void shouldReturnAllStudents() throws Exception {

        Student student = new Student();
        student.setId(1L);
        student.setName("Rahul");
        student.setCourse("Java");
        student.setEmail("rahul@gmail.com");

        when(service.getAllStudents()).thenReturn(List.of(student));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Rahul"))
                .andExpect(jsonPath("$[0].course").value("Java"))
                .andExpect(jsonPath("$[0].email").value("rahul@gmail.com"));
    }

    @Test
    void shouldCreateStudent() throws Exception {

    Student inputStudent = new Student();
    inputStudent.setName("Amit");
    inputStudent.setCourse("Spring Boot");
    inputStudent.setEmail("amit@gmail.com");

    Student savedStudent = new Student();
    savedStudent.setId(10L);
    savedStudent.setName("Amit");
    savedStudent.setCourse("Spring Boot");
    savedStudent.setEmail("amit@gmail.com");

    when(service.saveStudent(any(Student.class)))
            .thenReturn(savedStudent);

    mockMvc.perform(post("/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inputStudent)))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(10))
            .andExpect(jsonPath("$.name").value("Amit"))
            .andExpect(jsonPath("$.course").value("Spring Boot"))
            .andExpect(jsonPath("$.email").value("amit@gmail.com"));
    }

    @Test
    void shouldReturn404WhenStudentNotFound() throws Exception {

    when(service.getStudentById(999L))
            .thenThrow(new StudentNotFoundException("Student not found"));

    mockMvc.perform(get("/students/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Student not found"));
    }

    @Test
    void shouldReturn400ForInvalidStudent() throws Exception {

    Student invalidStudent = new Student();
    invalidStudent.setName("");
    invalidStudent.setCourse("");
    invalidStudent.setEmail("wrong-email");

    mockMvc.perform(post("/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidStudent)))

            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.name").value("Name is required"))
            .andExpect(jsonPath("$.course").value("Course is required"))
            .andExpect(jsonPath("$.email").value("Invalid email format"));
    }

}