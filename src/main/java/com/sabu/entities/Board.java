package com.sabu.entities;

import com.sabu.validator.Validator;

import static com.sabu.utils.Constants.*;

public class Board {
    private final Unit[][] map;

    public Board() {
        map = new Unit[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
        for (int y = 0; y < MAX_BOARD_SIZE; ++y) {
            for (int x = 0; x < MAX_BOARD_SIZE; ++x) {
                setUnit(new Tile(x, y, false));
            }
        }
    }

    public void validate() {
        Validator.isNotNull(map, "map is null", 400);
    }

    public void setUnit(Unit unit) {
        map[unit.getY()][unit.getX()] = unit;
    }

    public Unit getUnitAt(int x, int y) {
        return map[y][x];
    }

    public void moveUnit(Movement movement) {
        movement.validate();
        int moveX = movement.getMoveX();
        int moveY = movement.getMoveY();
        Ninja ninja = movement.getNinja();

        Validator.isExpectedValue(getUnitAt(moveX, moveY)
                .getUnitType(), BLANK, "Movement to occupied location");// VALIDATES THAT DESTINY LOCATION IS BLANK

        Validator.isExpectedValue(getUnitAt(ninja.getX(), ninja.getY())
                .getUnitType(), NINJA, "Not ninja in selected location");//VALIDATES THAT THE CURRENT NINJA LOCATION IS TRUE


        setUnit(new Tile(ninja.getX(), ninja.getY(), false));// CLEAR PREVIOUS LOCATION

        ninja.setX(moveX); //SET TO NEW LOCATION
        ninja.setY(moveY);
        setUnit(ninja); // MOVE TO NEW LOCATION
    }
}
