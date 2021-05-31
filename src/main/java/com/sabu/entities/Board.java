package com.sabu.entities;

import com.sabu.validator.Validator;

import static com.sabu.utils.Constants.*;

public class Board {
    private final Unit[][] map; //TODO rompe el gson magicamente.
    private int aliveNinjas;

    public Board() {
        this.aliveNinjas = 0;
        map = new Unit[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
        for (int y = 0; y < MAX_BOARD_SIZE; ++y) {
            for (int x = 0; x < MAX_BOARD_SIZE; ++x) {
                setUnit(new Tile(false, x, y));
            }
        }
    }

    public void validate() {
        Validator.isNotNull(map, "map is null", 400);
    }

    public int getAliveNinjas() {
        return aliveNinjas;
    }

    public void setUnit(Unit unit) {
        map[unit.getY()][unit.getX()] = unit;
        aliveNinjas++;
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


        setUnit(new Tile(false, ninja.getY(), ninja.getX()));// CLEAR PREVIOUS LOCATION

        ninja.setX(moveX); //SET TO NEW LOCATION
        ninja.setY(moveY);
        setUnit(ninja); // MOVE TO NEW LOCATION
    }
}
