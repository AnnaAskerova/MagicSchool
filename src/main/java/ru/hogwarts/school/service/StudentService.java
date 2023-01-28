package ru.hogwarts.school.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.record.StudentRequest;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyService facultyService;

    public StudentService(StudentRepository studentRepository, FacultyService facultyService) {
        this.studentRepository = studentRepository;
        this.facultyService = facultyService;
    }

    public Student add(StudentRequest studentRequest) {
        if (studentRepository.existsById(studentRequest.id())) {
            return null;
        }
        return saveRecordAsStudent(studentRequest);
    }

    public Optional<Student> get(long id) {
        return studentRepository.findById(id);
    }

    public Student update(StudentRequest studentRequest) {
        if (studentRepository.existsById(studentRequest.id())) {
            return saveRecordAsStudent(studentRequest);
        }
        return null;
    }

    private Student saveRecordAsStudent(StudentRequest studentRequest) {
        Student student = new Student();
        student.setId(studentRequest.id());
        student.setAge(studentRequest.age());
        student.setName(studentRequest.name());
        long facultyId = studentRequest.facultyId();
        if (facultyId != 0) {
            student.setFaculty(facultyService.get(facultyId).orElse(null));
        }
        return studentRepository.save(student);
    }
    public void delete(long id) throws EmptyResultDataAccessException {
        studentRepository.deleteById(id);
    }

    public Collection<Student> filterByAge(int age) {
        return studentRepository.findAll().stream().filter(s -> s.getAge() == age).toList();
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyByStudentId(long id) {
        Optional<Student> student = get(id);
        return student.map(Student::getFaculty).orElse(null);
    }
}
