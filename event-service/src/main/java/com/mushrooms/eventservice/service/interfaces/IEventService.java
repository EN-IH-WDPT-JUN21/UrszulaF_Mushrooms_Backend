package com.mushrooms.eventservice.service.interfaces;

import com.mushrooms.eventservice.dao.Event;
import com.mushrooms.eventservice.dto.EventReceiptDTO;
import com.mushrooms.eventservice.dto.EventRequestDTO;

import java.util.List;

public interface IEventService {
    List<Event> getAllEvents();
    EventReceiptDTO findByEventName(String eventName);
    EventReceiptDTO findById(Long id);
    List<Event> findByEventType(String eventTypeName);

    void deleteEvent(Long id);

    EventReceiptDTO createEvent(EventRequestDTO eventRequestDTO);

    EventReceiptDTO updateEvent(Long id, EventRequestDTO eventRequestDTO);
}
