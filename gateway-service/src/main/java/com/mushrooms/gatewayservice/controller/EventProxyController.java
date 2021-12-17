package com.mushrooms.gatewayservice.controller;


import com.mushrooms.gatewayservice.model.EventReceiptDTO;
import com.mushrooms.gatewayservice.model.EventRequestDTO;
import com.mushrooms.gatewayservice.proxy.EventProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/events")
public class EventProxyController {

    @Autowired
    private EventProxy eventProxy;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventReceiptDTO> findAllEvents(){
        return eventProxy.findAllEvents();
    }

    @GetMapping("/{eventName}")
    @ResponseStatus(HttpStatus.OK)
    public EventReceiptDTO findByEventName (@PathVariable(name="eventName") String eventName){
        return eventProxy.findByEventName(eventName);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventReceiptDTO findEventById (@PathVariable(name="id") Long id){
        return eventProxy.findEventById(id);
    }

    @GetMapping("/type/{eventTypeName}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventReceiptDTO> findByEventType (@PathVariable(name="eventTypeName") String eventTypeName){
        return eventProxy.findByEventType(eventTypeName);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public EventReceiptDTO createEvent(@RequestBody @Valid EventRequestDTO eventRequestDTO){
        return eventProxy.createEvent(eventRequestDTO);
    }

    @PutMapping ("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventReceiptDTO updateEvent(@PathVariable Long id, @RequestBody EventRequestDTO eventRequestDTO) {
        return eventProxy.updateEvent(id, eventRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEvent(@PathVariable Long id) {
        eventProxy.deleteEvent(id);
    }
}
