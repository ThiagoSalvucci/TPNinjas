package com.sabu.http;

import com.sabu.entities.Unit;

import java.util.List;

public class Update {
    private List<Unit> unitsAttacked;
    public Update() {}

    public List<Unit> getUnitsAttacked() {
        return unitsAttacked;
    }

    public void addAttackedUnit(Unit unit){
        unitsAttacked.add(unit);
    }

}
