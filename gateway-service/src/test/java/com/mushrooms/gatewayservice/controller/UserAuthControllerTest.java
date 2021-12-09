package com.mushrooms.gatewayservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mushrooms.gatewayservice.GatewayServiceApplication;
import com.mushrooms.gatewayservice.model.User;
import com.mushrooms.gatewayservice.model.UserReceiptDTO;
import com.mushrooms.gatewayservice.model.UserRequestDTO;
import com.mushrooms.gatewayservice.model.UserServiceReceiptDTO;
import com.mushrooms.gatewayservice.repository.UserRepository;
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
class UserAuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private GatewayServiceApplication application;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<UserServiceReceiptDTO> userServiceReceiptDTOList = new ArrayList<>();

    private List<User> users;
    List<UserReceiptDTO> userReceiptDTOList = new ArrayList<>();

    private User user;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new User("Jola", "j@j.pl", "jola", "ADMIN");
        user1 = new User("Angela", "a@a.pl", "anna", "USER");
        user2 = new User("Czesio", "c@c.pl", "czesio", "USER");
        users = userRepository.saveAll(List.of(user, user1, user2));

        userServiceReceiptDTOList = List.of(
                new UserServiceReceiptDTO(user.getId(), "photo", user.getUsername(), "I'm admin Jola"),
                new UserServiceReceiptDTO(user1.getId(), "photo", user1.getUsername(), "I'm user Angela"),
                new UserServiceReceiptDTO(user2.getId(), "photo", user2.getUsername(), "I'm user Czesio")
        );
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findAllUsers() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users-auth")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Jola"));
        assertTrue(result.getResponse().getContentAsString().contains("Angela"));
        assertTrue(result.getResponse().getContentAsString().contains("Czesio"));
        assertFalse(result.getResponse().getContentAsString().contains("Ferdek"));
    }

    @Test
    void getUsersWithAdds() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users-auth/all")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Jola"));
        assertTrue(result.getResponse().getContentAsString().contains("Angela"));
        assertTrue(result.getResponse().getContentAsString().contains("Czesio"));
        assertFalse(result.getResponse().getContentAsString().contains("Ferdek"));
    }

    @Test
    void findUserByUsername() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users-auth/"+user.getUsername())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("jola"));
    }

    @Test
    void findUserByUsername_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users-auth/Krzysztof")
        ).andDo(print()).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void createUserNoAdds() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO("Halina", "h@h.pl", "halina", "USER", "", "");
        String body = objectMapper.writeValueAsString(userRequestDTO);
        MvcResult result = mockMvc.perform(post("/api/users-auth/").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Halina"));
    }

    @Test
    void updateUser() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO("", "jolanta@j.pl", "jolanta", "USER");
        String body = objectMapper.writeValueAsString(userRequestDTO);
        MvcResult result = mockMvc.perform(put("/api/users-auth/update/" + user.getUsername()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("jolanta"));
    }

    @Test
    void deleteUser() throws Exception {
        long sizeBefore = userRepository.count();
        MvcResult result = mockMvc.perform(delete("/api/users-auth/delete/"+ user.getUsername())).andExpect(status().isOk()).andReturn();
        long sizeAfter = userRepository.count();
        assertEquals(--sizeBefore, sizeAfter);
    }
}