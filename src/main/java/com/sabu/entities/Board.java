package com.sabu.entities;

import com.sabu.validator.Validator;

import static com.sabu.Constants.*;

public class Board {
    private final char[][] map;


    public Board() {
        map = new char[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
        for (int y = 0; y < MAX_BOARD_SIZE; ++y) {
            for (int x = 0; x < MAX_BOARD_SIZE; ++x) {
                setUnit(x, y, BLANK);
            }
        }
    }

    public void validate() {
        Validator.isNotNull(map, "map is null", 400);
    }

    public void setUnit(int x, int y, char value) {
        map[y][x] = value;
    }


    public char getUnitAt(int x, int y) {
        return map[y][x];
    }

    public void moveUnit(Movement movement) {
        movement.validate();
        int moveX = movement.getMoveX();
        int moveY = movement.getMoveY();
        Ninja ninja = movement.getNinja();
        Validator.isExpectedValue(getUnitAt(moveX, moveY), BLANK, "Movement to occupied location");
        Validator.isExpectedValue(getUnitAt(ninja.getX(), ninja.getY()), NINJA, "Not ninja in selected location");
        char value = ninja.isBoss() ? BOSS : NINJA;
        setUnit(ninja.getX(), ninja.getY(), BLANK);// CLEAR PREVIOUS POSITION
        setUnit(moveX, moveY, value); // MOVE TO NEW POSITION
    }
}
