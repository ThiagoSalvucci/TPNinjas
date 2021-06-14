package com.sabu.entities.pieces;

public class Mark extends Unit {

    public Mark(Integer x, Integer y) {
        super(x, y);
    }

    @Override
    public char getUnitType() {
        return 'O';
    }

    @Override
    public void hitUnit() {
    }

    @Override
    public boolean isUnitAlive() {
        return false;
    }
}
