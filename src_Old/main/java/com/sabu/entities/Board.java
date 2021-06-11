package com.sabu.entities;

import com.sabu.validator.Validator;

import static com.sabu.utils.Constants.*;

public class Board {
    private final Unit[][] map;
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
        if(unit.getUnitType() == BOSS || unit.getUnitType() == NINJA){
            aliveNinjas++;
        }//TODO cambiar
    }

    public Unit getUnitAt(int x, int y) {
        return map[y][x];
    }


}
