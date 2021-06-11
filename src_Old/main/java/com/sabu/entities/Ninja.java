package com.sabu.entities;

import com.sabu.validator.Validator;

import static com.sabu.utils.Constants.*;

public class Ninja extends Unit {

    private final Boolean isBoss;
    private int hp;

    public Ninja(boolean isBoss, int x, int y) {
        super(x, y);
        this.isBoss = isBoss;
        this.hp = isBoss ? BOSS_HP : NINJA_HP;
    }


    public void validate() {
        super.validate();
        Validator.isNotNull(isBoss(), "isBoss is null", 400);
    }

    @Override
    public char getUnitType() {
        return isBoss ? BOSS : NINJA;
    }

    public boolean isBoss() {
        return isBoss;
    }

    public boolean isDead() {return hp <= 0;}

    public void hitNinja(){
        hp -= HIT;
    }

}
