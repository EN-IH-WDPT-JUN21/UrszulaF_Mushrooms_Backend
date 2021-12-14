package com.mushrooms.mushroomservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mushrooms.mushroomservice.MushroomServiceApplication;
import com.mushrooms.mushroomservice.dao.Mushroom;
import com.mushrooms.mushroomservice.dto.MushroomReceiptDTO;
import com.mushrooms.mushroomservice.dto.MushroomRequestDTO;
import com.mushrooms.mushroomservice.enums.Consumable;
import com.mushrooms.mushroomservice.repository.MushroomRepository;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MushroomControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private MushroomServiceApplication application;

    @Autowired
    private MushroomRepository mushroomRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private List<Mushroom> mushrooms;
    List<MushroomReceiptDTO> mushroomReceiptDTOList = new ArrayList<>();

    private Mushroom mushroom;
    private Mushroom mushroom1;
    private Mushroom mushroom2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mushroom = new Mushroom("podgrzybek", "podgrzybek", "podgrzybek", true, Consumable.GREAT, "lato", "forests in mountains",
                "yellow, convex", "massive", "none", "yellow, folded", "none", "yellowish, compact and fibrous", "fruity",
                "sweet", "sometimes orange", "grzyb2", "", "delicious");
        mushroom1 = new Mushroom("muchomorek", "muchomorek", "muchomorek", true, Consumable.GREAT, "wiosna", "forests in mountains",
                "yellow, convex", "massive", "none", "yellow, folded", "none", "yellowish, compact and fibrous", "fruity",
                "sweet", "sometimes orange", "grzyb2", "", "delicious");
        mushroom2 = new Mushroom("purchawka", "purchawka", "purchawka", true, Consumable.GREAT, "zima", "forests in mountains",
                "yellow, convex", "massive", "none", "yellow, folded", "none", "yellowish, compact and fibrous", "fruity",
                "sweet", "sometimes orange", "grzyb2", "", "delicious");
        mushrooms = mushroomRepository.saveAll(List.of(mushroom, mushroom1, mushroom2));

    }

    @AfterEach
    void tearDown() {
        mushroomRepository.deleteAll();
    }

    @Test
    void findAllMushrooms() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/mushrooms")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("podgrzybek"));
        assertTrue(result.getResponse().getContentAsString().contains("muchomorek"));
        assertTrue(result.getResponse().getContentAsString().contains("purchawka"));
        assertFalse(result.getResponse().getContentAsString().contains("surojadka"));
    }

    @Test
    void findMushroomByMushroomName() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/mushrooms/"+mushroom.getMushroomName())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("lato"));
    }

    @Test
    void findMushroomByMushroomName_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/mushrooms/poziomka")
        ).andDo(print()).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void findMushroomById() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/mushrooms/id/"+mushroom.getId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("lato"));
    }

    @Test
    void findMushroomById_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/mushrooms/id/1000")
        ).andDo(print()).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void findByMushroomNameContaining() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/mushrooms/containing/grzybek")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("podgrzybek"));
    }

    @Test
    void findByMushroomNameContaining_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/mushrooms/containing/poziomka")
        ).andDo(print()).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void createMushroom() throws Exception {
        MushroomRequestDTO mushroomRequestDTO = new MushroomRequestDTO("surojadka", "surojadka", "surojadka", "true", "GREAT", "zima", "forests in mountains",
                "yellow, convex", "massive", "none", "yellow, folded", "none", "yellowish, compact and fibrous", "fruity",
                "sweet", "sometimes orange", "grzyb2", "", "delicious");
        String body = objectMapper.writeValueAsString(mushroomRequestDTO);
        MvcResult result = mockMvc.perform(post("/api/mushrooms/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("surojadka"));
    }

    @Test
    void createMushroom_NameExist() throws Exception {
        MushroomRequestDTO mushroomRequestDTO = new MushroomRequestDTO("Podgrzybek", "podgrzybeK ", "Podgrzybek", "true", "GREAT", "lato", "forests in mountains",
                "yellow, convex", "massive", "none", "yellow, folded", "none", "yellowish, compact and fibrous", "fruity",
                "sweet", "sometimes orange", "grzyb2", "", "delicious");
        String body = objectMapper.writeValueAsString(mushroomRequestDTO);
        MvcResult result = mockMvc.perform(post("/api/mushrooms/new").content(body).contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void updateMushroom() throws Exception{
        MushroomRequestDTO mushroomRequestDTO = new MushroomRequestDTO("", "", ", ", "false", "POISONOUS", "zima", "",
                "", "", "", "", "", "", "",
                "", "", "", "", "");
        String body = objectMapper.writeValueAsString(mushroomRequestDTO);
        MvcResult result = mockMvc.perform(put("/api/mushrooms/update/" + mushroom.getId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("POISONOUS"));
    }

    @Test
    void updateMushroom_NotExist() throws Exception {
        MushroomRequestDTO mushroomRequestDTO = new MushroomRequestDTO("", "", ", ", "false", "POISONOUS", "zima", "",
                "", "", "", "", "", "", "",
                "", "", "", "", "");
        String body = objectMapper.writeValueAsString(mushroomRequestDTO);
        MvcResult result = mockMvc.perform(put("/api/mushrooms/update/100").content(body).contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isNotFound()).andReturn();

    }

    @Test
    void deleteMushroom() throws Exception {
        long sizeBefore = mushroomRepository.count();
        MvcResult result = mockMvc.perform(delete("/api/mushrooms/delete/"+ mushroom.getId())).andExpect(status().isOk()).andReturn();
        long sizeAfter = mushroomRepository.count();
        assertEquals(--sizeBefore, sizeAfter);
    }

    @Test
    void deleteMushroom_NotExist() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/mushrooms/delete/100")).andExpect(status().isNotFound()).andReturn();

    }
}