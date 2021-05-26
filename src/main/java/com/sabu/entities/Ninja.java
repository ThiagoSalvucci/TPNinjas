package com.sabu.entities;


import com.sabu.validator.Validator;

public class Ninja {
    private final Boolean isBoss;
    private Integer x, y;

    public Ninja(boolean isBoss, int x, int y) {
        this.isBoss = isBoss;
        this.x = x;
        this.y = y;
    }


    public void validate() {
        Validator.isNotNull(isBoss(), "isBoss is null", 400);
        Validator.isValidRange(getX(), 0, 4, "Invalid X range");
        Validator.isValidRange(getY(), 0, 4, "Invalid Y range");
    }

    public void move() {

    }

    public boolean isBoss() {
        return isBoss;
    }

    public int getX() {
        return x;
    }

    private void setX(Integer x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private void setY(Integer y) {
        this.y = y;
    }

}
