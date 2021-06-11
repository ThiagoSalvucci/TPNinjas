package com.sabu.entities.pieces;


import static com.sabu.utils.Constants.*;

public class Ninja extends Unit {

    private final Boolean isBoss;
    private boolean isMovable;

    public Ninja(boolean isBoss, int x, int y) {
        super(x, y);
        this.isBoss = isBoss;
        super.setHp(isBoss ? BOSS_HP : NINJA_HP);
        isMovable = true;
    }


    @Override
    public char getUnitType() {
        return isBoss ? BOSS : NINJA;
    }

    @Override
    public void hitUnit() {
        super.setHp(super.getHp() - HIT);
    }

    @Override
    public boolean isUnitAlive() {
        return super.getHp() > 0;
    }

    public boolean isBoss() {
        return isBoss;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void setMovable(boolean movable) {
        isMovable = movable;
    }
}
