package com.example.sistemaacademico.repository;

import com.example.sistemaacademico.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepository extends JpaRepository<Student, Long> {}
