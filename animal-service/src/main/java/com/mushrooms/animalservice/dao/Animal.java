package com.mushrooms.animalservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photoURL;
    private String animalName;
    private String otherNames;
    private String animalType;
    private String animalSize;
    private String description;
    private String remarks;

    public Animal(String photoURL, String animalName, String otherNames, String animalType, String animalSize, String description, String remarks) {
        this.photoURL = photoURL;
        this.animalName = animalName;
        this.otherNames = otherNames;
        this.animalType = animalType;
        this.animalSize = animalSize;
        this.description = description;
        this.remarks = remarks;
    }
}
