package com.sabu.entities;

import static com.sabu.Constants.BOSS;
import static com.sabu.Constants.NINJA;

import com.sabu.validator.Validator;

public class Ninja extends Unit{

    private final Boolean isBoss;


    public Ninja(boolean isBoss, int x, int y) {
        super(x,y);
        this.isBoss = isBoss;
    }


    public void validate() {
        super.validate();
        Validator.isNotNull(isBoss(), "isBoss is null", 400);
    }

    @Override
    public char getUnitType() {
        return isBoss?BOSS:NINJA;
    }

    public void move() {

    }

    public boolean isBoss() {
        return isBoss;
    }


}
