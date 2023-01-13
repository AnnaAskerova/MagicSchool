package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<?> createFaculty(@RequestBody Faculty faculty) {
        Faculty temp = facultyService.add(faculty);
        if (temp == null) {
            return new ResponseEntity<>("Уже существует", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(temp);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> readFaculty(@PathVariable long id) {
        Faculty temp = facultyService.get(id);
        if (temp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(temp);
    }

    @PutMapping
    public ResponseEntity<?> updateFaculty(@RequestBody Faculty faculty) {
        Faculty temp = facultyService.update(faculty);
        if (temp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(temp);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFaculty(@PathVariable long id) {
        Faculty temp = facultyService.delete(id);
        if (temp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(temp);
    }
    @GetMapping("/color/{color}")
    public Collection<Faculty> filterByColor(@PathVariable String color) {
        return facultyService.filterByColor(color);
    }
}
