package com.example.sistemaacademico.model;

import jakarta.persistence.*;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    // getters and setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    public Student getStudent(){return student;}
    public void setStudent(Student student){this.student = student;}
    public Course getCourse(){return course;}
    public void setCourse(Course course){this.course = course;}
}
