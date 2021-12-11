package com.mushrooms.eventservice.service.impl;

import com.mushrooms.eventservice.dao.Event;
import com.mushrooms.eventservice.dto.EventReceiptDTO;
import com.mushrooms.eventservice.dto.EventRequestDTO;
import com.mushrooms.eventservice.enums.EventType;
import com.mushrooms.eventservice.repository.EventRepository;
import com.mushrooms.eventservice.service.interfaces.IEventService;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService implements IEventService {
    @Autowired
    EventRepository eventRepository;


    Logger logger = LoggerFactory.getLogger("EventService.class");

    @Retry(name = "event-api", fallbackMethod = "fallbackEventList")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    //    @Retry(name = "event-api", fallbackMethod = "fallbackEventDTO")
    public EventReceiptDTO findByEventName(String eventName) {
        Optional<Event> optionalEvent = eventRepository.findByEventName(eventName);

        if(optionalEvent.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EventName " + eventName + " not found!");
        }

        Event event= optionalEvent.isPresent()? optionalEvent.get() : null;
        return convertEventToDTO(event);
    }

    //    @Retry(name = "event-api", fallbackMethod = "fallbackEventList")
    public List<Event> findByEventType(String eventTypeName) {
        List<Event> events = eventRepository.findByEventType(EventType.valueOf(eventTypeName));

        if(events.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Events of type " + eventTypeName + " not found!");
        }

        return events;
    }

    //    @Retry(name = "event-api", fallbackMethod = "fallbackEventDTO")
    public EventReceiptDTO createEvent(EventRequestDTO eventRequestDTO) {
        Optional<Event> optionalEvent = eventRepository.findByEventName(eventRequestDTO.getEventName());

        if(optionalEvent.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Eventname " + eventRequestDTO.getEventName() + " already exist!");
        }
        try{
            Event event;
            event = convertDTOToEvent(eventRequestDTO);
            eventRepository.save(event);
            return convertEventToDTO(event);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event type value not valid.");
        }
    }

    //    @Retry(name = "event-api", fallbackMethod = "fallbackNull")
    public void deleteEvent(String eventName) {
        Event event = eventRepository.findByEventName(eventName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "EventName " + eventName + " not found!"));
        eventRepository.delete(event);
    }

    //    @Retry(name = "event-api", fallbackMethod = "fallbackEventDTO")
    public EventReceiptDTO updateEvent(String eventName, EventRequestDTO eventRequestDTO) {
        Event event = eventRepository.findByEventName(eventName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "EventName " + eventName + " not found!"));
        if (eventRequestDTO.getEventTypeName() != null && eventRequestDTO.getEventTypeName() != "") {
            event.setEventType(eventRequestDTO.getEventType());
        }
        if (eventRequestDTO.getWhenEvent() != null && eventRequestDTO.getWhenEvent() != "") {
            event.setWhenEvent(eventRequestDTO.getWhenEvent());
        }
        if (eventRequestDTO.getDuration() != null && eventRequestDTO.getDuration() != 0) {
            event.setDuration(eventRequestDTO.getDuration());
        }

        if (eventRequestDTO.getWhereEvent() != null && eventRequestDTO.getWhereEvent() != "") {
            event.setWhereEvent(eventRequestDTO.getWhereEvent());
        }
        if (eventRequestDTO.getContactPerson() != null && eventRequestDTO.getContactPerson() != "") {
            event.setContactPerson(eventRequestDTO.getContactPerson());
        }
        if (eventRequestDTO.getDescription() != null && eventRequestDTO.getDescription() != "") {
            event.setDescription(eventRequestDTO.getDescription());
        }
        eventRepository.save(event);
        return convertEventToDTO(event);
    }

    private EventReceiptDTO convertEventToDTO(Event event) {
        return new EventReceiptDTO(event.getId(),
                event.getEventName(),
                event.getEventType(),
                event.getWhenEvent(),
                event.getDuration(),
                event.getWhereEvent(),
                event.getContactPerson(),
                event.getDescription()
        );
    }

    private Event convertDTOToEvent(EventRequestDTO eventRequestDTO) {
        return new Event(
                eventRequestDTO.getEventName(),
                eventRequestDTO.getEventType(),
                eventRequestDTO.getWhenEvent(),
                eventRequestDTO.getDuration(),
                eventRequestDTO.getWhereEvent(),
                eventRequestDTO.getContactPerson(),
                eventRequestDTO.getDescription()
        );
    }

    //fallback

    public List<Event>  fallbackEventList(Exception e) {
        logger.info("call event fallback method");
        List<Event> fallbackEventList = new ArrayList<>();
        Event fallbackEvent = new Event("fallback", EventType.OTHER, LocalDateTime.now().toString(),0,"","","");
        fallbackEventList.add(fallbackEvent);
        return fallbackEventList;
    }

    public Event fallbackEvent(Exception e) {
        logger.info("call event fallback method");
        Event fallbackEvent = new Event("fallback", EventType.OTHER, LocalDateTime.now().toString(),0,"","","");
        return fallbackEvent;
    }

    public EventReceiptDTO  fallbackEventDTO(Exception e) {
        logger.info("call event fallback method");
        EventReceiptDTO  fallbackEvent = new EventReceiptDTO (1000l, "fallback", EventType.OTHER, LocalDateTime.now().toString(),0,"","","");
        return fallbackEvent;
    }

    public void fallbackNull(Exception e) {
        logger.info("call event fallback method");
    }
}
