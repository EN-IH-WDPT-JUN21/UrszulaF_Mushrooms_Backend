package com.mushrooms.eventservice.dao;

import com.mushrooms.eventservice.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Event_table")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventName;

    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private LocalDateTime whenEvent;
    private Integer duration;
    private String whereEvent;
    private String contactPerson;
    private String description;

    public Event(String eventName, EventType eventType, LocalDateTime whenEvent, Integer duration, String whereEvent, String contactPerson, String description) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.whenEvent = whenEvent;
        this.duration = duration;
        this.whereEvent = whereEvent;
        this.contactPerson = contactPerson;
        this.description = description;
    }
}
