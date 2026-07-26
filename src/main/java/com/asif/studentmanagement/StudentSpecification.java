package com.asif.studentmanagement;

import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> hasCourse(String course) {
        return (root, query, criteriaBuilder) ->
                course == null ? null : criteriaBuilder.equal(root.get("course"), course);
    }

    public static Specification<Student> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.equal(root.get("name"), name);
    }
}