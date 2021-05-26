package com.sabu.entities;

import com.sabu.validator.Validator;


public class Movement {
    private Ninja ninja;
    private Integer moveX, moveY;

    public void validate() {
        Validator.isNotNull(ninja, "ninja is null", 400);
        Validator.isNotNull(moveX, "moveX is null", 400);
        Validator.isNotNull(moveY, "moveY is null", 400);
        Validator.isValidRange(moveX, 0, 4, "Invalid movement value: moveX");
        Validator.isValidRange(moveY, 0, 4, "Invalid movement value: moveY");
        ninja.validate();
    }

    public Integer getMoveX() {
        return moveX;
    }

    public Integer getMoveY() {
        return moveY;
    }

    public Ninja getNinja() {
        return ninja;
    }

}
