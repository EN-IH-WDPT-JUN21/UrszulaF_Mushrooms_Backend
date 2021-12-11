package com.mushrooms.eventservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mushrooms.eventservice.EventServiceApplication;
import com.mushrooms.eventservice.dao.Event;
import com.mushrooms.eventservice.dto.EventReceiptDTO;
import com.mushrooms.eventservice.dto.EventRequestDTO;
import com.mushrooms.eventservice.enums.EventType;
import com.mushrooms.eventservice.repository.EventRepository;
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
class EventControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private EventServiceApplication application;

    @Autowired
    private EventRepository eventRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<Event> events;
    List<EventReceiptDTO> eventReceiptDTOList = new ArrayList<>();

    private Event event;
    private Event event1;
    private Event event2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        event = new Event("grzybobranie", EventType.MUSHROOM_PICKING, "2022-08-01 09:00", 120, "Wiazowna", "Ula", "I invite you...");
        event1 = new Event("spacer", EventType.FOREST_WALK, "2022-09-01 09:00", 80, "Miedzylesie", "Ula", "I invite you...");
        event2 = new Event("ognisko", EventType.OTHER, "2022-09-01 09:00", 80, "Deblin", "Ula", "I invite you...");
        events = eventRepository.saveAll(List.of(event, event1, event2));

    }

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
    }

    @Test
    void findAllEvents() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/events")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("grzybobranie"));
        assertTrue(result.getResponse().getContentAsString().contains("spacer"));
        assertTrue(result.getResponse().getContentAsString().contains("ognisko"));
        assertFalse(result.getResponse().getContentAsString().contains("zwiedzanie"));
    }

    @Test
    void findByEventName() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/events/"+event.getEventName())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Wiazowna"));
    }

    @Test
    void findByEventName_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/events/zwiedzanie")
        ).andDo(print()).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void findByEventType() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/events/type/"+event.getEventType())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("grzybobranie"));
    }


    @Test
    void createEvent() throws Exception {
        EventRequestDTO eventRequestDTO = new EventRequestDTO("zwiedzanie", "OTHER", "2022-09-01 09:00", 80, "Warsaw", "Ula", "I invite you...");
        String body = objectMapper.writeValueAsString(eventRequestDTO);
        MvcResult result = mockMvc.perform(post("/api/events/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("zwiedzanie"));
    }

    @Test
    void updateEvent() throws Exception {
        EventRequestDTO eventRequestDTO = new EventRequestDTO("", "OTHER", "", 200, "Warsaw", "", "");
        String body = objectMapper.writeValueAsString(eventRequestDTO);
        MvcResult result = mockMvc.perform(put("/api/events/update/" + event.getEventName()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Warsaw"));
    }

    @Test
    void deleteEvent() throws Exception {
        long sizeBefore = eventRepository.count();
        MvcResult result = mockMvc.perform(delete("/api/events/delete/"+ event.getEventName())).andExpect(status().isOk()).andReturn();
        long sizeAfter = eventRepository.count();
        assertEquals(--sizeBefore, sizeAfter);
    }
}