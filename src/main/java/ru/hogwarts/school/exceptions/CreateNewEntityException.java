package ru.hogwarts.school.exceptions;

public class CreateNewEntityException extends RuntimeException{
    public CreateNewEntityException(String message) {
        super(message);
    }
}
