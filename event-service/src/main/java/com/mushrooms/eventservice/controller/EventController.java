package com.mushrooms.eventservice.controller;

import com.mushrooms.eventservice.dao.Event;
import com.mushrooms.eventservice.dto.EventReceiptDTO;
import com.mushrooms.eventservice.dto.EventRequestDTO;
import com.mushrooms.eventservice.repository.EventRepository;
import com.mushrooms.eventservice.service.interfaces.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    IEventService eventService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Event> findAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/{eventName}")
    @ResponseStatus(HttpStatus.OK)
    public EventReceiptDTO findByEventName (@PathVariable(name="eventName") String eventName){
        return eventService.findByEventName(eventName);
    }

    @GetMapping("/type/{eventTypeName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> findByEventType (@PathVariable(name="eventTypeName") String eventTypeName){
        return eventService.findByEventType(eventTypeName);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public EventReceiptDTO createEvent(@RequestBody @Valid EventRequestDTO eventRequestDTO){
        return eventService.createEvent(eventRequestDTO);
    }

    @PutMapping ("/update/{eventName}")
    @ResponseStatus(HttpStatus.OK)
    public EventReceiptDTO updateEvent(@PathVariable String eventName, @RequestBody EventRequestDTO eventRequestDTO) {
        return eventService.updateEvent(eventName, eventRequestDTO);
    }

    @DeleteMapping("/delete/{eventName}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEvent(@PathVariable String eventName) {
        eventService.deleteEvent(eventName);
    }
}
