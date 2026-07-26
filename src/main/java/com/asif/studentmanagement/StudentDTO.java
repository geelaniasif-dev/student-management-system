package com.asif.studentmanagement;

public class StudentDTO {

    private Long id;
    private String name;
    private String course;
    private String email;

    public StudentDTO(Long id, String name, String course, String email) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getEmail() {
        return email;
    }
}