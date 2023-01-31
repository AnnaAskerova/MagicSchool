package ru.hogwarts.school.exceptions;

public class StudentNotExistException extends RuntimeException {
    public StudentNotExistException(String message) {
        super(message);
    }
}
