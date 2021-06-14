package com.sabu.entities.pieces;

public abstract class Unit {
    private Integer x, y;
    private int hp;


    public Unit(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        hp = 1;
    }

    public Unit() {

    }

    public abstract char getUnitType();

    public abstract void hitUnit();

    public abstract boolean isUnitAlive();

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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
