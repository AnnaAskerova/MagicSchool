package ru.hogwarts.school.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Optional;

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
        Optional<Faculty> temp = facultyService.get(id);
        return temp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
        try {
            facultyService.delete(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<?> filterByColor(@PathVariable String color) {
        Collection<Faculty> temp = facultyService.filterByColor(color);
        if (temp.isEmpty()) {
            return new ResponseEntity<>("Нет такого цвета", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(temp);
    }

    @GetMapping
    public ResponseEntity<?> findByNameOrColor(@RequestParam(required = false) String name, @RequestParam(required = false) String color) {
        Collection<Faculty> temp = facultyService.findByNameOrColor(name, color);
        if (temp.isEmpty()) {
            return new ResponseEntity<>("Нет таких факультетов :(", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/faultyid/{id}")
    public ResponseEntity<?> getFaculty(@PathVariable long id) {
        Faculty temp = facultyService.getFacultyByStudentId(id);
        if (temp == null) {
            return new ResponseEntity<>("Нет информации :(", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(temp);
    }
}
