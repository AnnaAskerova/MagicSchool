package ru.hogwarts.school.record;

public class FacultyRequest {
    private long id;
    private String name;
    private String color;

    public FacultyRequest(long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public FacultyRequest() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
