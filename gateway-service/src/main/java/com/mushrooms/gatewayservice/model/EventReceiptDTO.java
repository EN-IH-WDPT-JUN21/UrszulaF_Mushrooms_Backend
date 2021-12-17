package com.mushrooms.gatewayservice.model;

import com.mushrooms.gatewayservice.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventReceiptDTO {
    private Long id;
    private String eventName;

    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String whenEvent;
    private Integer duration;
    private String whereEvent;
    private String contactPerson;
    private String description;
}
