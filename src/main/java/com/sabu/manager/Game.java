package com.sabu.manager;

import com.sabu.entities.Ninja;
import com.sabu.utils.Input;

public abstract class Game {

    public abstract void createPlayer();
    public abstract void attack();
    public Ninja getNinja(boolean isBoss) {
        return Input.getNinja(isBoss);
    }

}
