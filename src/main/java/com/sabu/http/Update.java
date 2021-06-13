package com.sabu.http;

import com.sabu.entities.Action;

import java.util.ArrayList;
import java.util.List;

public class Update {
    private List<Action> actions;


    public Update() {
        actions = new ArrayList<>();
    }

    public List<Action> getActions() {
        return actions;
    }

    public void addAction(Action action){
        actions.add(action);
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

}
