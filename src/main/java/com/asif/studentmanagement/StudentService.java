package com.asif.studentmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.asif.studentmanagement.exception.StudentNotFoundException;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository repository;

    public Student saveStudent(Student student) {
        log.info("Saving student: {}", student.getName());
        return repository.save(student);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(Long id) {
        log.info("Fetching student with id: {}", id);
   return repository.findById(id)
        .orElseThrow(() ->
                new StudentNotFoundException("Student not found with id " + id));
    }
    public Student updateStudent(Long id, Student student) {
        log.info("Updating student with id: {}", id);

    Student oldStudent = repository.findById(id).orElse(null);

    if (oldStudent != null) {
        oldStudent.setName(student.getName());
        oldStudent.setCourse(student.getCourse());
        oldStudent.setEmail(student.getEmail());

        return repository.save(oldStudent);
    }

    return null;
    }

    public void deleteStudent(Long id) {
        log.info("Deleting student with id: {}", id);
    repository.deleteById(id);
    }

    public List<Student> searchStudentByName(String name) {
        return repository.findByName(name);
    }

    public List<Student> getStudentsByCourse(String course) {
        return repository.findByCourse(course);
    }

    public List<Student> searchStudentsByEmail(String email) {
        return repository.findByEmail(email);
    }

   public Page<Student> getStudents(int page, int size, String sortBy, String direction) {

    Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    return repository.findAll(PageRequest.of(page, size, sort));
    }

    public List<Student> filterStudents(String name, String course) {

    return repository.findAll(
            StudentSpecification.hasName(name)
                    .and(StudentSpecification.hasCourse(course))
    );

    }

    public List<StudentDTO> getAllStudentDTO() {

    return repository.findAll()
            .stream()
            .map(student -> new StudentDTO(
                    student.getId(),
                    student.getName(),
                    student.getCourse(),
                    student.getEmail()))
            .toList();

    }

    public Page<Student> searchStudents(
        String name,
        String course,
        int page,
        int size,
        String sortBy,
        String direction) {

    Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    return repository.findByNameContainingIgnoreCaseAndCourseContainingIgnoreCase(
            name,
            course,
            PageRequest.of(page, size, sort)
    );
    }

    public Page<Student> getStudents(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return repository.findAll(pageable);
    }
}