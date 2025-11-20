package com.example.sistemaacademico.repository;

import com.example.sistemaacademico.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {}
package com.example.repository;

import com.example.entity.Enrollment;
import com.example.entity.Student;
import com.example.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);
    List<Enrollment> findByStudent(Student student);
    List<Enrollment> findByCourse(Course course);
    List<Enrollment> findByStatus(Enrollment.EnrollmentStatus status);
}