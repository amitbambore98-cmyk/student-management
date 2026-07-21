package com.krishna.studentManagement.mapper;

import com.krishna.studentManagement.dto.StudentRequestDTO;
import com.krishna.studentManagement.dto.StudentResponseDTO;
import com.krishna.studentManagement.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toEntity(StudentRequestDTO dto) {

        Student student = new Student();

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());

        return student;
    }

    public StudentResponseDTO toResponseDTO(Student student) {

        StudentResponseDTO dto = new StudentResponseDTO();

        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setAge(student.getAge());

        return dto;
    }
}