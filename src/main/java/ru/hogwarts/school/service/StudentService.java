package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long idCounter = 0;

    public Student add(Student student) {
        if (students.containsValue(student)) {
            return null;
        }
        student.setId(++idCounter);
        students.put(idCounter, student);
        return student;
    }

    public Student get(long id) {
        return students.get(id);
    }

    public Student update(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        } else {
            return null;
        }
    }

    public Student delete(long id) {
        return students.remove(id);
    }

    public Collection<Student> filterByAge(int age) {
        return students.values().stream().filter(s -> s.getAge() == age).toList();
    }
}
