package com.mushrooms.animalservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mushrooms.animalservice.AnimalServiceApplication;
import com.mushrooms.animalservice.dao.Animal;
import com.mushrooms.animalservice.dto.AnimalReceiptDTO;
import com.mushrooms.animalservice.dto.AnimalRequestDTO;
import com.mushrooms.animalservice.repository.AnimalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AnimalControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private AnimalServiceApplication application;

    @Autowired
    private AnimalRepository animalRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private List<Animal> animals;
    List<AnimalReceiptDTO> animalReceiptDTOList = new ArrayList<>();

    private Animal animal;
    private Animal animal1;
    private Animal animal2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        animal = new Animal("bear",
                "bear",
                "bear/niedźwiedź",
                "mammal",
                "very big",
                "In the mountains",
                "Dangerous");
        animal1 = new Animal("squirrel",
                "squirrel",
                "squirrel/wiewiórka",
                "mammal",
                "small",
                "forests, parks",
                "peaceful");
        animal2 = new Animal("rabbit",
                "rabbit",
                "rabbit/zając",
                "mammal",
                "small",
                "forests, meadows",
                "peaceful");
        animals = animalRepository.saveAll(List.of(animal, animal1, animal2));

    }

    @AfterEach
    void tearDown() {
        animalRepository.deleteAll();
    }

    @Test
    void findAllAnimals() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/animals")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("bear"));
        assertTrue(result.getResponse().getContentAsString().contains("squirrel"));
        assertTrue(result.getResponse().getContentAsString().contains("rabbit"));
        assertFalse(result.getResponse().getContentAsString().contains("mushroom"));
    }

    @Test
    void findAnimalByAnimalName() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/animals/"+animal.getAnimalName())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("mountains"));
    }

    @Test
    void findAnimalByAnimalName_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/animals/mushroom")
        ).andDo(print()).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void findAnimalById() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/animals/id/"+animal.getId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("mountains"));
    }

    @Test
    void findAnimalById_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/animals/id/1000")
        ).andDo(print()).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void findByAnimalNameContaining() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/animals/containing/QUir")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("small"));
    }

    @Test
    void findByAnimalNameContaining_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/animals/containing/mushroom")
        ).andDo(print()).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void createAnimal() throws Exception {
        AnimalRequestDTO animalRequestDTO = new AnimalRequestDTO("mouse",
                "mouse",
                "mouse/mysz",
                "mammal",
                "small",
                "forests, meadows",
                "peaceful");
        String body = objectMapper.writeValueAsString(animalRequestDTO);
        MvcResult result = mockMvc.perform(post("/api/animals/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("mouse"));
    }

    @Test
    void createAnimal_NameExist() throws Exception {
        AnimalRequestDTO animalRequestDTO = new AnimalRequestDTO("Bear",
                "Bear",
                "Bear",
                "mammal",
                "very big",
                "In the mountains",
                "Dangerous");
        String body = objectMapper.writeValueAsString(animalRequestDTO);
        MvcResult result = mockMvc.perform(post("/api/animals/new").content(body).contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void updateAnimal() throws Exception {
        AnimalRequestDTO animalRequestDTO = new AnimalRequestDTO("",
                "",
                "",
                "",
                "",
                "",
                "very dangerous");
        String body = objectMapper.writeValueAsString(animalRequestDTO);
        MvcResult result = mockMvc.perform(put("/api/animals/update/" + animal.getId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("very dangerous"));
    }

    @Test
    void updateAnimal_NotExist() throws Exception {
        AnimalRequestDTO animalRequestDTO = new AnimalRequestDTO("",
                "",
                "",
                "",
                "",
                "",
                "very dangerous");
        String body = objectMapper.writeValueAsString(animalRequestDTO);
        MvcResult result = mockMvc.perform(put("/api/animals/update/100").content(body).contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isNotFound()).andReturn();

    }

    @Test
    void deleteAnimal() throws Exception {
        long sizeBefore = animalRepository.count();
        MvcResult result = mockMvc.perform(delete("/api/animals/delete/"+ animal.getId())).andExpect(status().isOk()).andReturn();
        long sizeAfter = animalRepository.count();
        assertEquals(--sizeBefore, sizeAfter);
    }

    @Test
    void deleteAnimal_NotExist() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/animals/delete/100")).andExpect(status().isNotFound()).andReturn();

    }
}