package com.sabu.validator;

import com.sabu.entities.Action;
import com.sabu.entities.Board;
import com.sabu.entities.pieces.Ninja;

import static com.sabu.utils.Constants.*;

public class AttackValidator extends ActionValidator {

    public void validateAttack(Action action, Board board) {
        super.validate(action);
        char unitType = board.getUnitAt(action.getPosX(), action.getPosY()).getUnitType();
        Validator.isExpectedValue(action.getActionType(), ATTACK, "Invalid action value: actionType. Expected 'A'");
        Validator.isTrue(unitType != BROKE,"Cannot attack a previous attacked position");
    }
}
