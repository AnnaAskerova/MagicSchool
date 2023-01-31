package ru.hogwarts.school.exceptions;

public class FacultyNotExistException extends RuntimeException {
    public FacultyNotExistException(String message) {
        super(message);
    }
}
