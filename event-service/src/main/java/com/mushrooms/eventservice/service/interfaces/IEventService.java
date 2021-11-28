package com.mushrooms.eventservice.service.interfaces;

import com.mushrooms.eventservice.dao.Event;
import com.mushrooms.eventservice.dto.EventReceiptDTO;
import com.mushrooms.eventservice.dto.EventRequestDTO;

import java.util.List;

public interface IEventService {
    List<Event> getAllEvents();
    EventReceiptDTO findByEventName(String eventName);
    List<Event> findByEventType(String eventTypeName);

    void deleteEvent(String eventName);

    EventReceiptDTO createEvent(EventRequestDTO eventRequestDTO);

    EventReceiptDTO updateEvent(String eventName, EventRequestDTO eventRequestDTO);
}
