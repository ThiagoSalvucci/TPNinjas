package com.sabu.validator;

import com.sabu.entities.Action;

import static com.sabu.http.HttpUtils.BAD_REQUEST;


public class ActionValidator {

    public void validate(Action action) {

        Validator.isNotNull(action, "action is null", BAD_REQUEST);
        Integer x = action.getPosX();
        Integer y = action.getPosY();


        Validator.isNotNull(action.getNinja(), "ninja is null", BAD_REQUEST);
        validatePosNotNull(x, 'X');
        validatePosNotNull(y, 'Y');

        validateRange(x, 'X');
        validateRange(y, 'Y');

    }

    private void validatePosNotNull(int pos, char axis) {
        Validator.isNotNull(pos, "pos" + axis + " is null", BAD_REQUEST);
    }

    private void validateRange(int pos, char axis) {
        Validator.isValidRange(pos, 0, 4, "Invalid action value: pos" + axis);
    }
}
