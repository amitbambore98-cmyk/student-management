package com.krishna.studentManagement.controller;

import com.krishna.studentManagement.dto.StudentRequestDTO;
import com.krishna.studentManagement.dto.StudentResponseDTO;
import com.krishna.studentManagement.entity.Student;
import com.krishna.studentManagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import com.krishna.studentManagement.response.ApiResponse;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDTO>> saveStudent(
            @RequestBody @Valid StudentRequestDTO requestDTO) {

        StudentResponseDTO student = studentService.saveStudent(requestDTO);

        ApiResponse<StudentResponseDTO> response =
                new ApiResponse<>(true, "Student created successfully", student);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<StudentResponseDTO>>> getAllStudents(
            Pageable pageable) {

        Page<StudentResponseDTO> students = studentService.getAllStudents(pageable);

        ApiResponse<Page<StudentResponseDTO>> response =
                new ApiResponse<>(true, "Students fetched successfully", students);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> getStudentById(
            @PathVariable Long id) {

        StudentResponseDTO student = studentService.getStudentById(id);

        ApiResponse<StudentResponseDTO> response =
                new ApiResponse<>(true, "Student fetched successfully", student);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> searchStudents(

            @RequestParam(required = false) String name,

            @RequestParam(required = false) String email,

            @RequestParam(required = false) Integer age) {

        List<StudentResponseDTO> students =
                studentService.searchStudents(name, email, age);

        String message = students.isEmpty()
                ? "No students found"
                : "Students fetched successfully";

        ApiResponse<List<StudentResponseDTO>> response =
                new ApiResponse<>(!students.isEmpty(), message, students);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> updateStudent(
            @PathVariable Long id,
            @RequestBody @Valid StudentRequestDTO requestDTO) {

        StudentResponseDTO student =
                studentService.updateStudent(id, requestDTO);

        ApiResponse<StudentResponseDTO> response =
                new ApiResponse<>(true, "Student updated successfully", student);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(
            @PathVariable Long id) {

        studentService.deleteStudent(id);

        ApiResponse<Void> response =
                new ApiResponse<>(true, "Student deleted successfully", null);

        return ResponseEntity.ok(response);
    }
}