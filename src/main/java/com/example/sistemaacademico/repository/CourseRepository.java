package com.example.sistemaacademico.repository;

import com.example.sistemaacademico.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CourseRepository extends JpaRepository<Course, Long> {}
