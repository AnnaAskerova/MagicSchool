package ru.hogwarts.school.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.record.FacultyRequest;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(FacultyRequest facultyRequest) {
        if (facultyRepository.existsById(facultyRequest.getId())) {
            return null;
        }
        return facultyRepository.save(saveRecordAsFaculty(facultyRequest));
    }

    public Optional<Faculty> get(long id) {
        return facultyRepository.findById(id);
    }

    public Faculty update(FacultyRequest facultyRequest) {
        if (facultyRepository.existsById(facultyRequest.getId())) {
            return facultyRepository.save(saveRecordAsFaculty(facultyRequest));
        }
        return null;
    }

    private Faculty saveRecordAsFaculty(FacultyRequest facultyRequest) {
        Faculty faculty = new Faculty();
        faculty.setId(facultyRequest.getId());
        faculty.setColor(facultyRequest.getColor());
        faculty.setName(facultyRequest.getName());
        return faculty;
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

    public Collection<Student> getAllStudentsFromFaculty(long id) {
        Optional<Faculty> temp = get(id);
        if (temp.isEmpty()) {
            return null;
        }
        return temp.get().getStudents();
    }
}
