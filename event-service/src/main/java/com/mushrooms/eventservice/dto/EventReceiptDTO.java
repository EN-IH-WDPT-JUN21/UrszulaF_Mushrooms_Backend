package com.mushrooms.eventservice.dto;

import com.mushrooms.eventservice.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventReceiptDTO {
    private Long id;
    private String eventName;

    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private LocalDateTime whenEvent;
    private Integer duration;
    private String whereEvent;
    private String contactPerson;
    private String description;
}
