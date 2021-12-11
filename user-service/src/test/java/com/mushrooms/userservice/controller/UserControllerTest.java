package com.mushrooms.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mushrooms.userservice.UserServiceApplication;
import com.mushrooms.userservice.dao.User;
import com.mushrooms.userservice.dto.UserReceiptDTO;
import com.mushrooms.userservice.dto.UserRequestDTO;
import com.mushrooms.userservice.repository.UserRepository;
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
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserServiceApplication application;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<User> users;
    List<UserReceiptDTO> userReceiptDTOList = new ArrayList<>();

    private User user;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new User(7l, "photo", "Jola", "I'm admin Jola");
        user1 = new User(8l, "photo", "Angela", "I'm user Angela");
        user2 = new User(9l, "photo", "Czesio", "I'm user Czesio");
        users = userRepository.saveAll(List.of(user, user1, user2));

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findAllUsers() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Jola"));
        assertTrue(result.getResponse().getContentAsString().contains("Angela"));
        assertTrue(result.getResponse().getContentAsString().contains("Czesio"));
        assertFalse(result.getResponse().getContentAsString().contains("Ferdek"));
    }

    @Test
    void findUserByUsername() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users/"+user.getUsername())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("I'm admin Jola"));
    }

    @Test
    void findUserByUsername_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users/Krzysztof")
        ).andDo(print()).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void findUserById() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users/id/"+user.getId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Jola"));
    }

    @Test
    void findUserById_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users/id/1000")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("null"));

    }

    @Test
    void createUser() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO(10l, "photo", "Helena", "I'm user Helena");
        String body = objectMapper.writeValueAsString(userRequestDTO);
        MvcResult result = mockMvc.perform(post("/api/users/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Helena"));
    }

    @Test
    void updateUser() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO(7l, "photo", "Jolanta", "I'm admin Jolanta");
        String body = objectMapper.writeValueAsString(userRequestDTO);
        MvcResult result = mockMvc.perform(put("/api/users/update/" + user.getUsername()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Jolanta"));
    }

    @Test
    void deleteUser() throws Exception {
        long sizeBefore = userRepository.count();
        MvcResult result = mockMvc.perform(delete("/api/users/delete/"+ user.getUsername())).andExpect(status().isOk()).andReturn();
        long sizeAfter = userRepository.count();
        assertEquals(--sizeBefore, sizeAfter);
    }
}