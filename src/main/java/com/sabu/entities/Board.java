package com.sabu.entities;

import com.sabu.entities.pieces.Ninja;
import com.sabu.entities.pieces.Tile;
import com.sabu.entities.pieces.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sabu.utils.Constants.*;

public class Board {
    private volatile Unit[][] map;
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

    public List<Ninja> getNinjas(){
        Unit unit;
        List<Ninja> unitList = new ArrayList<>();

        for (int y = 0; y < MAX_BOARD_SIZE; ++y) {
            for (int x = 0; x < MAX_BOARD_SIZE; ++x) {
                unit = getUnitAt(x,y);
                if (unit.getUnitType() == NINJA || unit.getUnitType() == BOSS){
                    unitList.add((Ninja) unit);
                }
            }
        }

        return unitList;
    }

}
