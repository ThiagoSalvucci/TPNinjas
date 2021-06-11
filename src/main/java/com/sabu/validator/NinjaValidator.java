package com.sabu.validator;

import com.sabu.entities.Board;
import com.sabu.entities.pieces.Ninja;

import static com.sabu.http.HttpUtils.BAD_REQUEST;
import static com.sabu.utils.Constants.BLANK;
import static com.sabu.utils.Constants.MAX_NINJAS;

public class NinjaValidator extends UnitValidator {

    public void validate(Ninja ninja, Board board){
        super.validate(ninja);
        Validator.isNotNull(ninja.isBoss(), "isBoss is null", BAD_REQUEST);
        Validator.isHigherThan(board.getAliveNinjas(),MAX_NINJAS,
                "Max number of ninjas reached: " + MAX_NINJAS);
        Validator.isExpectedValue(board.getUnitAt(ninja.getX(), ninja.getY())
                .getUnitType(), BLANK, "Location is already occupied");
    }


}
