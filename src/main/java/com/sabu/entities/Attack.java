package com.sabu.entities;

import com.sabu.validator.Validator;

public class Attack {
    private Integer attackX;
    private Integer attackY;


    public void validate() {
        Validator.isNotNull(attackX, "attackX is null", 400);
        Validator.isNotNull(attackY, "attackY is null", 400);
        Validator.isValidRange(attackX, 0, 4, "Invalid movement value: attackX");
        Validator.isValidRange(attackY, 0, 4, "Invalid movement value: attackY");
    }
}
