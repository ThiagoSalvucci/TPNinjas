package com.sabu.entities;

import com.sabu.validator.Validator;

import static com.sabu.utils.Constants.BOSS;
import static com.sabu.utils.Constants.NINJA;

public class Ninja extends Unit {

    private final Boolean isBoss;


    public Ninja(boolean isBoss, int x, int y) {
        super(x, y);
        this.isBoss = isBoss;
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


}
