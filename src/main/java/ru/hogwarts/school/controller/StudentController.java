package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        Student temp = studentService.add(student);
        if (temp == null) {
            return new ResponseEntity<>("Уже существует", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(temp);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> readStudent(@PathVariable long id) {
        Student temp = studentService.get(id);
        if (temp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(temp);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student temp = studentService.update(student);
        if (temp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(temp);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        Student temp = studentService.delete(id);
        if (temp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/age/{age}")
    public Collection<Student> filterByAge(@PathVariable int age) {
        return studentService.filterByAge(age);
    }
}
