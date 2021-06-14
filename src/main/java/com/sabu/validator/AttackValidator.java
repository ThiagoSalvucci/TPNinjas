package com.sabu.validator;

import com.sabu.entities.Action;

import static com.sabu.utils.Constants.ATTACK;

public class AttackValidator extends ActionValidator {

    public void validateAttack(Action action) {
        super.validate(action);
        Validator.isExpectedValue(action.getActionType(), ATTACK, "Invalid action value: actionType. Expected 'A'");
    }
}
