package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.CreateNewEntityException;
import ru.hogwarts.school.exceptions.FacultyNotExistException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.record.FacultyRequest;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(FacultyRequest facultyRequest) {
        logger.debug("Вызван метод add");
        if (facultyRepository.existsById(facultyRequest.getId())) {
            throw new CreateNewEntityException("Уже существует");
        }
        return facultyRepository.save(saveRecordAsFaculty(facultyRequest));
    }

    public Faculty get(long id) {
        logger.debug("Вызван метод get");
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotExistException("Факультет не найден"));
    }

    public Faculty update(FacultyRequest facultyRequest) {
        logger.debug("Вызван метод update");
        if (facultyRepository.existsById(facultyRequest.getId())) {
            return facultyRepository.save(saveRecordAsFaculty(facultyRequest));
        } else {
            throw new FacultyNotExistException("Факультет не найден");
        }
    }

    private Faculty saveRecordAsFaculty(FacultyRequest facultyRequest) {
        logger.debug("Вызван метод saveRecordAsFaculty");
        Faculty faculty = new Faculty();
        faculty.setId(facultyRequest.getId());
        faculty.setColor(facultyRequest.getColor());
        faculty.setName(facultyRequest.getName());
        return faculty;
    }

    public void delete(long id) throws EmptyResultDataAccessException {
        logger.debug("Вызван метод delete");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> filterByColor(String color) {
        logger.debug("Вызван метод filterByColor");
        return facultyRepository.findAll().stream().filter(s -> s.getColor().equals(color)).toList();
    }

    public Collection<Faculty> findByNameOrColor(String name, String color) {
        logger.debug("Вызван метод findByNameOrColor");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Student> getAllStudentsFromFaculty(long id) {
        logger.debug("Вызван метод getAllStudentsFromFaculty");
        return get(id).getStudents();
    }

    public String getMostLongName() {
        logger.debug("Вызван метод getMostLongName");
        return facultyRepository.findAll().stream()
                .parallel()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("");

    }
}
