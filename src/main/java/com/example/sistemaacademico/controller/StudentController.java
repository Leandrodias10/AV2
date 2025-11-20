package com.example.sistemaacademico.controller;

import com.example.sistemaacademico.model.Student;
import com.example.sistemaacademico.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentRepository repo;
    public StudentController(StudentRepository repo){this.repo = repo;}

    @GetMapping
    public List<Student> list(){ return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable Long id){
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Student create(@RequestBody Student s){ return repo.save(s); }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student s){
        return repo.findById(id).map(existing -> {
            existing.setName(s.getName());
            existing.setEmail(s.getEmail());
            repo.save(existing);
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return repo.findById(id).map(existing -> {
            repo.delete(existing);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
