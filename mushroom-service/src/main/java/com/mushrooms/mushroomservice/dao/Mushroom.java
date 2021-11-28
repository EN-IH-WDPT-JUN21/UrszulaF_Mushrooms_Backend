package com.mushrooms.mushroomservice.dao;

import com.mushrooms.mushroomservice.enums.Consumable;
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
public class Mushroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Mushroom(String photoURL, String mushroomName, String otherNames, Boolean edible, Consumable consumable, String whenFruiting, String whereFruiting, String hat, String stem, String ring, String gills, String tubes, String pulp, String smell, String taste, String differentiation, String similar, String remarks, String foodValue) {
        this.photoURL = photoURL;
        this.mushroomName = mushroomName;
        this.otherNames = otherNames;
        this.edible = edible;
        this.consumable = consumable;
        this.whenFruiting = whenFruiting;
        this.whereFruiting = whereFruiting;
        this.hat = hat;
        this.stem = stem;
        this.ring = ring;
        this.gills = gills;
        this.tubes = tubes;
        this.pulp = pulp;
        this.smell = smell;
        this.taste = taste;
        this.differentiation = differentiation;
        this.similar = similar;
        this.remarks = remarks;
        this.foodValue = foodValue;
    }
}
