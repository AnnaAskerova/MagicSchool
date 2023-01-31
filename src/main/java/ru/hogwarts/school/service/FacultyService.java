package ru.hogwarts.school.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.CreateNewEntityException;
import ru.hogwarts.school.exceptions.FacultyNotExistException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.record.FacultyRequest;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(FacultyRequest facultyRequest) {
        if (facultyRepository.existsById(facultyRequest.getId())) {
            throw new CreateNewEntityException("Уже существует");
        }
        return facultyRepository.save(saveRecordAsFaculty(facultyRequest));
    }

    public Faculty get(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotExistException("Факультет не найден"));
    }

    public Faculty update(FacultyRequest facultyRequest) {
        if (facultyRepository.existsById(facultyRequest.getId())) {
            return facultyRepository.save(saveRecordAsFaculty(facultyRequest));
        } else {
            throw new FacultyNotExistException("Факультет не найден");
        }
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
        return get(id).getStudents();
    }
}
