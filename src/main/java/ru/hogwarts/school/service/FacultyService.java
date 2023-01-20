package ru.hogwarts.school.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(Faculty faculty) {
        if (facultyRepository.existsById(faculty.getId())) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> get(long id) {
        return facultyRepository.findById(id);
    }

    public Faculty update(Faculty faculty) {
        if (facultyRepository.existsById(faculty.getId())) {
            return facultyRepository.save(faculty);
        }
        return null;
    }

    public void delete(long id) throws EmptyResultDataAccessException {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> filterByColor(String color) {
        return facultyRepository.findAll().stream().filter(s -> s.getColor().equals(color)).toList();
    }

    public Collection<Faculty> findByNameOrColor(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Faculty getFacultyByStudentId(long id) {
        return facultyRepository.findFacultyByStudentsId(id);
    }
}
