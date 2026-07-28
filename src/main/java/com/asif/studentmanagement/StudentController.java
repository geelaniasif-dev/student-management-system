package com.asif.studentmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import com.asif.studentmanagement.response.ApiResponse;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
public ResponseEntity<ApiResponse<Student>> createStudent(@Valid @RequestBody Student student) {

    Student savedStudent = service.saveStudent(student);

    ApiResponse<Student> response =
            new ApiResponse<>(
                    true,
                    "Student created successfully",
                    savedStudent
            );

    return ResponseEntity.ok(response);
}

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
public Student getStudentById(@PathVariable Long id) {
    return service.getStudentById(id);
}

@PreAuthorize("hasRole('ADMIN')")
@PutMapping("/{id}")
public Student updateStudent(@PathVariable Long id, @Valid@RequestBody Student student) {
    return service.updateStudent(id, student);
}

@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{id}")
public String deleteStudent(@PathVariable Long id) {
    service.deleteStudent(id);
    return "Student Deleted Successfully";
}

@GetMapping("/search")
public List<Student> searchStudent(@RequestParam String name) {
    return service.searchStudentByName(name);
}

@GetMapping("/search/course")
public List<Student> getStudentsByCourse(@RequestParam String course) {
    return service.getStudentsByCourse(course);
}

@GetMapping("/search/email")
public List<Student> getStudentsByEmail(@RequestParam String email) {
    return service.searchStudentsByEmail(email);
}

@GetMapping("/page")
public Page<Student> getStudents(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam String direction) {

    return service.getStudents(page, size, sortBy, direction);
}

@GetMapping("/filter")
public List<Student> filterStudents(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String course) {

    return service.filterStudents(name, course);
}

@GetMapping("/dto")
public List<StudentDTO> getAllStudentDTO() {
    return service.getAllStudentDTO();
}

@GetMapping("/advanced-search")
public Page<Student> searchStudents(
        @RequestParam String name,
        @RequestParam String course,
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam String direction) {

    return service.searchStudents(name, course, page, size, sortBy, direction);
}
}