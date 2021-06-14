package com.sabu.entities;

import com.sabu.entities.pieces.Ninja;

public class Action {
    private final Integer posX;
    private final Integer posY;
    private final Ninja ninja;
    private final char actionType;

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
