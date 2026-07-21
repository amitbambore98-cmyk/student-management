package com.krishna.studentManagement.service;

import com.krishna.studentManagement.dto.StudentRequestDTO;
import com.krishna.studentManagement.dto.StudentResponseDTO;
import com.krishna.studentManagement.entity.Student;
import com.krishna.studentManagement.exception.DuplicateEmailException;
import com.krishna.studentManagement.exception.StudentNotFoundException;
import com.krishna.studentManagement.mapper.StudentMapper;
import com.krishna.studentManagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.krishna.studentManagement.specification.StudentSpecification;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentService {
    private static final Logger logger =
            LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository,
                          StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public StudentResponseDTO saveStudent(StudentRequestDTO requestDTO) {

        logger.info("Saving student with email: {}", requestDTO.getEmail());

        Student student = studentMapper.toEntity(requestDTO);

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }

        Student savedStudent = studentRepository.save(student);

        logger.info("Student saved successfully with id: {}", savedStudent.getId());

        return studentMapper.toResponseDTO(savedStudent);
    }

    public StudentResponseDTO getStudentById(Long id) {

        logger.info("Fetching student with id: {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        logger.info("Student found with id: {}", id);

        return studentMapper.toResponseDTO(student);
    }
    public List<StudentResponseDTO> searchStudents(
            String name,
            String email,
            Integer age) {

        Specification<Student> specification =
                StudentSpecification.searchStudent(name, email, age);

        List<Student> students = studentRepository.findAll(specification);

        return students.stream()
                .map(studentMapper::toResponseDTO)
                .toList();
    }

    public Page<StudentResponseDTO> getAllStudents(Pageable pageable) {

        Page<Student> students = studentRepository.findAll(pageable);

        return students.map(studentMapper::toResponseDTO);
    }

    public StudentResponseDTO updateStudent(Long id,
                                            StudentRequestDTO requestDTO) {

        logger.info("Updating student with id: {}", id);

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        existingStudent.setName(requestDTO.getName());
        existingStudent.setEmail(requestDTO.getEmail());
        existingStudent.setAge(requestDTO.getAge());

        Student updatedStudent = studentRepository.save(existingStudent);

        logger.info("Student updated successfully with id: {}", updatedStudent.getId());

        return studentMapper.toResponseDTO(updatedStudent);
    }

    public void deleteStudent(Long id) {

        logger.info("Deleting student with id: {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        studentRepository.delete(student);

        logger.info("Student deleted successfully with id: {}", id);
    }
}