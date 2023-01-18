package ru.hogwarts.school.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Optional;

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
    public ResponseEntity<?> readStudent(@PathVariable long id) {
        Optional<Student> temp = studentService.get(id);
        return temp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping
    public ResponseEntity<?> updateStudent(@RequestBody Student student) {
        Student temp = studentService.update(student);
        if (temp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(temp);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable long id) {
        try {
            studentService.delete(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<?> filterByAge(@PathVariable int age) {
        Collection<Student> temp = studentService.filterByAge(age);
        if (temp.isEmpty()) {
            return new ResponseEntity<>("Нет студентов такого возраста", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(temp);
    }
}
