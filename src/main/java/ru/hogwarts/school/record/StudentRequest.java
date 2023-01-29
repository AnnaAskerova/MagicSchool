package ru.hogwarts.school.record;

public class StudentRequest{
    private long id;
    private String name;
    private int age;
    private long facultyId;

    public StudentRequest(long id, String name, int age, long facultyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.facultyId = facultyId;
    }

    public StudentRequest() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public long getFacultyId() {
        return facultyId;
    }
}
