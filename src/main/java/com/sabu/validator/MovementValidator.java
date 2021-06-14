package com.sabu.validator;

import com.sabu.entities.Action;
import com.sabu.entities.Board;
import com.sabu.entities.pieces.Ninja;

import static com.sabu.utils.Constants.*;


public class MovementValidator extends ActionValidator {


    public void validate(Action action) {
        super.validate(action);
        Validator.isExpectedValue(action.getActionType(), MOVE, "Invalid action value: actionType. Expected 'M'");
    }

    public void validateMove(Board board, Action action) {
        Ninja ninja = action.getNinja();
        char unitType = board.getUnitAt(ninja.getX(), ninja.getY()).getUnitType();

        Validator.isTrue(unitType == NINJA || unitType == BOSS, "Not ninja in selected location");
        Validator.isTrue(ninja.isMovable(), "Ninjas has already moved last round!");

        int xDistance = Math.abs(action.getPosX() - ninja.getX());
        int yDistance = Math.abs(action.getPosY() - ninja.getY());

        Validator.isValidRange(xDistance, 0, 1, "Movement exceeds limits(1)");
        Validator.isValidRange(yDistance, 0, 1, "Movement exceeds limits(1)");
        Validator.isHigherThan(yDistance + xDistance, 0, "Can't move to the same location");
        Validator.isExpectedValue(board.getUnitAt(action.getPosX(), action.getPosY())
                .getUnitType(), BLANK, "Movement to occupied location");
    }

}
