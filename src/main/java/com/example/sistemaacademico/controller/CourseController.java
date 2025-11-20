package com.example.sistemaacademico.controller;

import com.example.sistemaacademico.model.Course;
import com.example.sistemaacademico.repository.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseRepository repo;
    public CourseController(CourseRepository repo){this.repo = repo;}

    @GetMapping
    public List<Course> list(){ return repo.findAll(); }

    @PostMapping
    public Course create(@RequestBody Course c){ return repo.save(c); }
}
