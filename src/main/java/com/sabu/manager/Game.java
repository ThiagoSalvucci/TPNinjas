package com.sabu.manager;

import com.sabu.entities.*;
import com.sabu.entities.pieces.Ninja;
import com.sabu.entities.pieces.Tile;
import com.sabu.entities.pieces.Unit;

import java.util.HashMap;
import java.util.Map;

import static com.sabu.utils.Constants.*;
import static com.sabu.utils.Constants.NINJA;

public class Game {
    private Map<Integer,Player> players;



    public Game() {
        players = new HashMap<>();
        players.put(PLAYER_CLIENT,new Player());
    }



    public Unit attack(Action attack, int attackedId){
        Board attackedBoard = players.get(attackedId).getBoard();
        Unit attackedUnit = attackedBoard.getUnitAt(attack.getPosX(), attack.getPosY());
        char attackedUnitType = attackedUnit.getUnitType();

        if(attackedUnitType == BOSS){//TODO
           attackedUnit.hitUnit();
        }else if(attackedUnitType == NINJA){
            attackedBoard.setUnit(new Tile(false, attackedUnit.getX(), attackedUnit.getY()));
        }else if(attackedUnitType == BLANK){
            attackedUnit.hitUnit();
        }

        return attackedUnit;
    }


    public void moveUnit(Action movement,int id) {
        Ninja ninja = movement.getNinja();
        Board board = players.get(id).getBoard();
        board.setUnit(new Tile(false, ninja.getY(), ninja.getX()));// CLEAR PREVIOUS LOCATION
        ninja.setX(movement.getPosX()); //SET TO NEW LOCATION
        ninja.setY(movement.getPosY());
        board.setUnit(ninja); // MOVE TO NEW LOCATION
    }



    /*End Actions*/



    public void addPlayer(Player player, int id){
        players.put(id,player);
    }




    public void setNinja(Ninja ninja, int id){
        players.get(id).getBoard().setUnit(ninja);
    }


    public Player getPlayer(int id) {
        return players.get(id);
    }



}
