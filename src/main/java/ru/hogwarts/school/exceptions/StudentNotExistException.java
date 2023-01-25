package ru.hogwarts.school.exceptions;

import java.io.IOException;

public class StudentNotExistException extends IOException {
    public StudentNotExistException(String message) {
        super(message);
    }
}
