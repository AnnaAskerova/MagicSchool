package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.record.FacultyRequest;
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
    public ResponseEntity<?> createFaculty(@RequestBody FacultyRequest facultyRequest) {
        return ResponseEntity.ok(facultyService.add(facultyRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> readFaculty(@PathVariable long id) {
        return ResponseEntity.ok().body(facultyService.get(id));
    }

    @PutMapping
    public ResponseEntity<?> updateFaculty(@RequestBody FacultyRequest facultyRequest) {
        return ResponseEntity.ok(facultyService.update(facultyRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFaculty(@PathVariable long id) {
        facultyService.delete(id);
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

    @GetMapping("/from-faculty/{id}")
    public ResponseEntity<?> getAllStudentsFromFaculty(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.getAllStudentsFromFaculty(id));
    }

    @GetMapping("/most-long-name")
    public ResponseEntity<?> getMostLongName() {
        return ResponseEntity.ok(facultyService.getMostLongName());
    }
}
