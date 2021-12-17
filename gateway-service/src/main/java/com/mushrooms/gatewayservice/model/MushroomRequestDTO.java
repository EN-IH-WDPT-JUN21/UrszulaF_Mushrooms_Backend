package com.mushrooms.gatewayservice.model;

import com.mushrooms.gatewayservice.enums.Consumable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MushroomRequestDTO {

    private String photoURL;
    private String mushroomName;
    private String otherNames;

    private String edibleString;

    public Boolean getEdible() {
        return Boolean.parseBoolean(edibleString);
    }

    public void setEdible(Boolean consumable) {
        this.edibleString = String.valueOf(consumable);
    }

//    private Boolean edible;

    private String consumableName;

    public Consumable getConsumable() {
        return Consumable.valueOf(consumableName);
    }

    public void setConsumable(Consumable consumable) {
        this.consumableName = consumable.toString();
    }
    

//    private Consumable consumable;
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
