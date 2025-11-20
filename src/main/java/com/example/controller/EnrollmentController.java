package com.example.controller;

import com.example.entity.Enrollment;
import com.example.entity.Student;
import com.example.entity.Course;
import com.example.repository.EnrollmentRepository;
import com.example.repository.StudentRepository;
import com.example.repository.CourseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollments", description = "API para gerenciar inscrições em cursos")
@SecurityRequirement(name = "basicAuth")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @GetMapping
    @Operation(summary = "Listar todas as inscrições")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar inscrição por ID")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long id) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(id);
        return enrollment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Criar nova inscrição")
    public ResponseEntity<Enrollment> createEnrollment(@RequestBody EnrollmentRequest request) {
        Optional<Student> student = studentRepository.findById(request.getStudentId());
        Optional<Course> course = courseRepository.findById(request.getCourseId());

        if (student.isEmpty() || course.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student.get());
        enrollment.setCourse(course.get());
        enrollment.setStatus(Enrollment.EnrollmentStatus.ENROLLED);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEnrollment);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar inscrição")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollmentDetails) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(id);
        if (enrollment.isPresent()) {
            Enrollment e = enrollment.get();
            e.setStatus(enrollmentDetails.getStatus());
            e.setGrade(enrollmentDetails.getGrade());
            e.setCompletionDate(enrollmentDetails.getCompletionDate());
            Enrollment updatedEnrollment = enrollmentRepository.save(e);
            return ResponseEntity.ok(updatedEnrollment);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar inscrição")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Listar inscrições de um estudante")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStudent(@PathVariable Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            List<Enrollment> enrollments = enrollmentRepository.findByStudent(student.get());
            return ResponseEntity.ok(enrollments);
        }
        return ResponseEntity.notFound().build();
    }

    // DTO for request
    public static class EnrollmentRequest {
        private Long studentId;
        private Long courseId;

        public Long getStudentId() {
            return studentId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }

        public Long getCourseId() {
            return courseId;
        }

        public void setCourseId(Long courseId) {
            this.courseId = courseId;
        }
    }
}