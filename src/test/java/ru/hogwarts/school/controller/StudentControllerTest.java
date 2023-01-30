package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.exceptions.StudentNotExistException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.record.FacultyRequest;
import ru.hogwarts.school.record.StudentRequest;
import ru.hogwarts.school.service.FacultyService;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    FacultyService facultyService;

    @Autowired
    private TestRestTemplate restTemplate;

    private final static StudentRequest STUDENT = new StudentRequest(0, "vasya", 12, 1);
    private final static FacultyRequest FACULTY = new FacultyRequest(0, "fac", "green");

    @Test
    void createStudent() throws Exception {
        assertThat(restTemplate.postForObject("http://localhost:" + port + "/student", STUDENT, String.class))
                .isNotEmpty();
    }

    @Test
    void readStudent() throws Exception {
        facultyService.add(FACULTY);
        long id = ((Student) Objects.requireNonNull((studentController.createStudent(STUDENT)).getBody())).getId();
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/{id}", String.class, id)).isNotNull();
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/{id}", String.class, id)).contains("vasya");
    }

    @Test
    void updateStudent() throws Exception {
        facultyService.add(FACULTY);
        long id = ((Student) Objects.requireNonNull((studentController.createStudent(STUDENT)).getBody())).getId();
        HttpEntity<StudentRequest> entity = new HttpEntity<>(new StudentRequest(id, "borya", 12, 1));
        ResponseEntity<?> response = restTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PUT, entity, String.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.toString()).contains("borya");
    }

    @Test
    void deleteStudent() throws Exception {
        facultyService.add(FACULTY);
        long id = ((Student) Objects.requireNonNull((studentController.createStudent(STUDENT)).getBody())).getId();
        restTemplate.delete("http://localhost:" + port + "/student/{id}", id);
        assertThrows(StudentNotExistException.class, () -> studentController.readStudent(id));
    }

    @Test
    void filterByAge() throws Exception {
        facultyService.add(FACULTY);
        studentController.createStudent(STUDENT);
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/age/{age}", String.class, STUDENT.getAge()))
                .isNotNull();
    }

    @Test
    void findByAgeBetween() {
        facultyService.add(FACULTY);
        studentController.createStudent(STUDENT);
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/student", String.class, 1, 100)).isNotNull();
    }

    @Test
    void getFaculty() {
        long id = ((Student) Objects.requireNonNull((studentController.createStudent(new StudentRequest(0, "", 12, 0)))
                .getBody())).getId();
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/faculty/{id}", String.class, id))
                .isEqualTo("Нет информации :(");
    }
}