package com.sabu.entities;

import com.sabu.validator.Validator;

public abstract class Unit {
    private Integer x, y;

    public Unit(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
    public Unit(){

    }

    public void validate() {
        Validator.isValidRange(x, 0, 4, "Invalid X range");
        Validator.isValidRange(y, 0, 4, "Invalid Y range");
    }

    public abstract char getUnitType();

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }




}
