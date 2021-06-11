package com.sabu.validator;

import com.sabu.entities.pieces.Unit;

import static com.sabu.http.HttpUtils.BAD_REQUEST;

public class UnitValidator {
    public void validate(Unit unit){
        Validator.isNotNull(unit, "Unit is null", BAD_REQUEST);
        Validator.isValidRange(unit.getX(), 0, 4, "Invalid X range");
        Validator.isValidRange(unit.getY(), 0, 4, "Invalid Y range");
    }
}
