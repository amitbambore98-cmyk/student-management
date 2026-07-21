package com.krishna.studentManagement.specification;

import com.krishna.studentManagement.entity.Student;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    public static Specification<Student> searchStudent(
            String name,
            String email,
            Integer age) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + name.toLowerCase() + "%"
                        )
                );
            }

            if (email != null && !email.isBlank()) {
                predicates.add(
                        criteriaBuilder.equal(root.get("email"), email)
                );
            }

            if (age != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("age"), age)
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}