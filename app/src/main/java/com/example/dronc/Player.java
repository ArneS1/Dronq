package com.example.dronc;

import java.io.Serializable;

public class Player implements Serializable {

    //Eigenschaften eines Players

    private String name;
    private boolean isMale;
    private boolean isRelationship;
    private String drink;
    private boolean inUse;

    //Getter
    public String getName() {
        return this.name;
    }

    public boolean isMale() {
        return this.isMale;
    }

    public boolean isRelationship() {
        return this.isRelationship;
    }

    public String getDrink() {
        return this.drink;
    }

    //Setter
    public void setName(String newName) {
        this.name = newName;
    }

    public void setMale(boolean penis) {
        this.isMale = penis;
    }

    public void setRelationship(boolean newIsRelationship) {
        this.isRelationship = newIsRelationship;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    //Method for Setting in-use-status, so its not taken twice in the same question
    public void setInUse(boolean newStatus) {
        this.inUse = newStatus;
    }
    public boolean isInUse(){
        return this.inUse;
    }
}
