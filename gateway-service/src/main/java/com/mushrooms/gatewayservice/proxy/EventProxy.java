package com.mushrooms.gatewayservice.proxy;

import com.mushrooms.gatewayservice.model.EventReceiptDTO;
import com.mushrooms.gatewayservice.model.EventRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Service
@FeignClient("event-service")
public interface EventProxy {
    @GetMapping("/api/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventReceiptDTO> findAllEvents();

    @GetMapping("/api/events/{eventName}")
    @ResponseStatus(HttpStatus.OK)
    public EventReceiptDTO findByEventName (@PathVariable(name="eventName") String eventName);

    @GetMapping("/api/events/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventReceiptDTO findEventById (@PathVariable(name="id") Long id);

    @GetMapping("/api/events/type/{eventTypeName}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventReceiptDTO> findByEventType (@PathVariable(name="eventTypeName") String eventTypeName);

    @PostMapping("/api/events/new")
    @ResponseStatus(HttpStatus.CREATED)
    public EventReceiptDTO createEvent(@RequestBody @Valid EventRequestDTO eventRequestDTO);

    @PutMapping ("/api/events/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventReceiptDTO updateEvent(@PathVariable Long id, @RequestBody EventRequestDTO eventRequestDTO);

    @DeleteMapping("/api/events/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEvent(@PathVariable Long id);
}
