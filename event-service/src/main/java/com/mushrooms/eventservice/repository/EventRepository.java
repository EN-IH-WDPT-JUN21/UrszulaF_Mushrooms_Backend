package com.mushrooms.eventservice.repository;

import com.mushrooms.eventservice.dao.Event;
import com.mushrooms.eventservice.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByEventName(String eventName);
    List<Event> findByEventType(EventType eventType);
}