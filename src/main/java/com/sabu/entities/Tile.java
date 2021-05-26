package com.sabu.entities;

import static com.sabu.utils.Constants.BLANK;
import static com.sabu.utils.Constants.BROKE;

public class Tile extends Unit {
    private final Boolean isBroken;

    public Tile(int x, int y, Boolean isBroken) {
        super(x, y);
        this.isBroken = isBroken;
    }

    @Override
    public char getUnitType() {
        return isBroken ? BROKE : BLANK;
    }

}
