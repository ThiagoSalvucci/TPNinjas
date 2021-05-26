package com.sabu.entities;

import static com.sabu.Constants.*;

public class Tile extends Unit{
    private final Boolean isBroken;

    public Tile(int x, int y, Boolean isBroken) {
        super(x,y);
        this.isBroken = isBroken;
    }

    @Override
    public char getUnitType() {
        return isBroken?BROKE:BLANK;
    }

}
