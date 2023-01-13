package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long idCounter = 0;

    public Faculty add(Faculty faculty) {
        if (faculties.containsValue(faculty)) {
            return null;
        }
        faculty.setId(++idCounter);
        faculties.put(idCounter, faculty);
        return faculty;
    }

    public Faculty get(long id) {
        return faculties.get(id);
    }

    public Faculty update(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        } else {
            return null;
        }
    }

    public Faculty delete(long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> filterByColor(String color) {
        return faculties.values().stream().filter(s -> s.getColor().equals(color)).toList();
    }
}
