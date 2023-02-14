package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.CreateNewEntityException;
import ru.hogwarts.school.exceptions.StudentNotExistException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.record.StudentRequest;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyService facultyService;
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);


    public StudentService(StudentRepository studentRepository, FacultyService facultyService) {
        this.studentRepository = studentRepository;
        this.facultyService = facultyService;
    }

    public Student add(StudentRequest studentRequest) {
        logger.debug("Вызван метод add");
        if (studentRepository.existsById(studentRequest.getId())) {
            throw new CreateNewEntityException("Уже существует");
        }
        return saveRecordAsStudent(studentRequest);
    }

    public Student get(long id) {
        logger.debug("Вызван метод get");
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotExistException("Студент не найден"));
    }

    public Student update(StudentRequest studentRequest) {
        logger.debug("Вызван метод update");
        if (studentRepository.existsById(studentRequest.getId())) {
            return saveRecordAsStudent(studentRequest);
        } else {
            throw new StudentNotExistException("Студент не найден");
        }

    }

    private Student saveRecordAsStudent(StudentRequest studentRequest) {
        logger.debug("Вызван метод saveRecordAsStudent");
        Student student = new Student();
        student.setId(studentRequest.getId());
        student.setAge(studentRequest.getAge());
        student.setName(studentRequest.getName());
        long facultyId = studentRequest.getFacultyId();
        if (facultyId != 0) {
            student.setFaculty(facultyService.get(facultyId));
        }
        return studentRepository.save(student);
    }

    public void delete(long id) throws EmptyResultDataAccessException {
        logger.debug("Вызван метод delete");
        studentRepository.deleteById(id);
    }

    public Collection<Student> filterByAge(int age) {
        logger.debug("Вызван метод filterByAge");
        return studentRepository.findAll().stream().filter(s -> s.getAge() == age).toList();
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.debug("Вызван метод findByAgeBetween");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyByStudentId(long id) {
        logger.debug("Вызван метод getFacultyByStudentId");
        return get(id).getFaculty();
    }

    public int getCount() {
        logger.debug("Вызван метод getCount");
        return studentRepository.getCount();
    }

    public String getAverageAge() {
        logger.debug("Вызван метод getAverageAge");
        return String.format("%.2f", studentRepository.getAverageAge());
    }

    public List<Student> getFiveLast() {
        logger.debug("Вызван метод getFiveLast");
        return studentRepository.getFiveLast();
    }
}
