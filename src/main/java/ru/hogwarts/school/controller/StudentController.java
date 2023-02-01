package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.record.StudentRequest;
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
    public ResponseEntity<?> createStudent(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentService.add(studentRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> readStudent(@PathVariable long id) {
        return ResponseEntity.ok(studentService.get(id));
    }

    @PutMapping
    public ResponseEntity<?> updateStudent(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentService.update(studentRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable long id) {
        studentService.delete(id);
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

    @GetMapping()
    public ResponseEntity<?> findByAgeBetween(@RequestParam int min, @RequestParam int max) {
        Collection<Student> temp = studentService.findByAgeBetween(min, max);
        if (temp.isEmpty()) {
            return new ResponseEntity<>("Нет студентов такого возраста", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<?> getFaculty(@PathVariable long id) {
        Faculty temp = studentService.getFacultyByStudentId(id);
        if (temp == null) {
            return new ResponseEntity<>("Нет информации :(", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount() {
        return ResponseEntity.ok(studentService.getCount());
    }

    @GetMapping("/average-age")
    public ResponseEntity<?> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("/five-last")
    public ResponseEntity<?> getFiveLast() {
        return ResponseEntity.ok(studentService.getFiveLast());
    }
}
