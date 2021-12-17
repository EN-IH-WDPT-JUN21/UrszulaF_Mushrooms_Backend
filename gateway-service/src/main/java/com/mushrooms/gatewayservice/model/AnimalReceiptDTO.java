package com.mushrooms.gatewayservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalReceiptDTO {
    private Long id;
    private String photoURL;
    private String animalName;
    private String otherNames;
    private String animalType;
    private String animalSize;
    private String description;
    private String remarks;
}
