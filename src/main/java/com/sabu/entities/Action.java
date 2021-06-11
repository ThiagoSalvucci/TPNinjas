package com.sabu.entities;

import com.sabu.entities.pieces.Ninja;

public class Action {
    private Integer posX;
    private Integer posY;
    private Ninja ninja;
    private char actionType;

    public Action(Integer posX, Integer posY, Ninja ninja, char action) {
        this.posX = posX;
        this.posY = posY;
        this.ninja = ninja;
        this.actionType = action;
    }

    public char getActionType() {
        return actionType;
    }

    public Integer getPosX() {
        return posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public Ninja getNinja() {
        return ninja;
    }
}
