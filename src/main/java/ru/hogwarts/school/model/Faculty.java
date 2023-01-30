package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
public class Faculty {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String color;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "faculty")
    @JsonManagedReference
    private Collection<Student> students;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public Collection<Student> getStudents() {
        return Objects.requireNonNullElse(students, Collections.emptyList());
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(name, faculty.name) && Objects.equals(color, faculty.color);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + Objects.requireNonNull(id, "") +
                ", name='" + Objects.requireNonNull(name, "") + '\'' +
                ", color='" + Objects.requireNonNull(color, "") + '\'' +
                ", students=" + Objects.requireNonNull(this.getStudents(), "") +
                '}';
    }
}
