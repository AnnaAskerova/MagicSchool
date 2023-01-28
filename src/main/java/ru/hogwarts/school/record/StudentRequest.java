package ru.hogwarts.school.record;

public record StudentRequest(long id, String name, int age, long facultyId) {
}
