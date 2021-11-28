package com.mushrooms.mushroomservice.dto;

import com.mushrooms.mushroomservice.enums.Consumable;
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
public class MushroomReceiptDTO {
    private Long id;
    private String photoURL;
    private String mushroomName;
    private String otherNames;
    private Boolean edible;

    @Enumerated(EnumType.STRING)
    private Consumable consumable;
//    private String consumable;
    private String whenFruiting;
    private String whereFruiting;
    private String hat;
    private String stem;
    private String ring;
    private String gills;
    private String tubes;
    private String pulp;
    private String smell;
    private String taste;
    private String differentiation;
    private String similar;
    private String remarks;
    private String foodValue;
}
