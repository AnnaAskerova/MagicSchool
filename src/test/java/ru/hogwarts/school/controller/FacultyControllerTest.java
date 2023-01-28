package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarController avatarController;
    @MockBean
    private StudentController studentController;
    @MockBean
    private AvatarService avatarService;
    @MockBean
    private StudentService studentService;
    @InjectMocks
    private FacultyController facultyController;
    @SpyBean
    private FacultyService facultyService;

    private final String name = "xxx";
    private final String color = "red";
    JSONObject facultyObject = new JSONObject();
    Faculty faculty = new Faculty();
    Long id = 1L;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
    }

    @Test
    void createFaculty() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void readFaculty() throws Exception {
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/{id}", id)
                        .content(id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void readFacultyNotExist() throws Exception {
        when(facultyRepository.findById(3L)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/{id}", id)
                        .content(id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateFaculty() throws Exception {
        when(facultyRepository.existsById(id)).thenReturn(true);
        String newColor = "green";
        faculty.setColor(newColor);
        facultyObject.put("color", newColor);
        facultyObject.put("id", id);
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(newColor));
    }

    @Test
    void deleteFaculty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/{id}", id)
                        .content(id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFacultyNotExist() throws Exception {
        doThrow(EmptyResultDataAccessException.class).when(facultyRepository).deleteById(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/{id}", id)
                        .content(id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void filterByColor() throws Exception {
        List<Faculty> list = List.of(faculty);
        when(facultyRepository.findAll()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/color/{color}", color)
                        .content(color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(list)));
    }

    @Test
    void findByNameOrColor() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setId(15L);
        faculty1.setName("tr");
        faculty1.setColor("color");
        List<Faculty> list = List.of(faculty, faculty1);
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(any(String.class), any(String.class))).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty", name, "color")
                        .param("name", name)
                        .param("color", "color")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(list)));

    }
  /*  @Test
    void getAllStudentsFromFaculty() throws Exception {
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));
       // doReturn(Collections.emptyList()).when(facultyService).getAllStudentsFromFaculty(id);
        mockMvc.perform(MockMvcRequestBuilders.get("/from-faculty/{id}", id)
                        .content(id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }*/
}