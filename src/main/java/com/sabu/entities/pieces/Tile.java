package com.sabu.entities.pieces;

import static com.sabu.utils.Constants.BLANK;
import static com.sabu.utils.Constants.BROKE;

public class Tile extends Unit {
    private Boolean isBroken;

    public Tile(Boolean isBroken, int x, int y) {
        super(x, y);
        this.isBroken = isBroken;
    }

    @Override
    public char getUnitType() {
        return isBroken ? BROKE : BLANK;
    }

    @Override
    public void hitUnit() {
        isBroken = true;
    }

    @Override
    public boolean isUnitAlive() {
        return isBroken;
    }


}
