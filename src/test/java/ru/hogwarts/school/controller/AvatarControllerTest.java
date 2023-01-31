package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AvatarController.class)
class AvatarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    AvatarService avatarService;
    @MockBean
    AvatarRepository avatarRepository;
    @MockBean
    StudentService studentService;
    Long id = 12L;
    @MockBean
    private StudentRepository studentRepository;

    @Test
    void uploadAvatar() throws Exception {
    /*    Student student = new Student();
        student.setName("");
        student.setAge(12);
        student.setId(id);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        MockHttpServletRequestBuilder m = multipart("/avatar" + id)
                .file("avatar", "123".getBytes());
        mockMvc.perform(m).andExpect(status().isOk());*/
    }

    @Test
    void downloadAvatar() {
    }

    @Test
    void testDownloadAvatar() {
    }
}