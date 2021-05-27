package com.sabu.entities;

import static com.sabu.utils.Constants.BLANK;
import static com.sabu.utils.Constants.BROKE;

public class Tile extends Unit {
    private final Boolean isBroken;

    public Tile(Boolean isBroken, int x, int y) {
        super(x, y);
        this.isBroken = isBroken;
    }

    @Override
    public char getUnitType() {
        return isBroken ? BROKE : BLANK;
    }

}
