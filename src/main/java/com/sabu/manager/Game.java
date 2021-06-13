package com.sabu.manager;

import com.sabu.entities.*;
import com.sabu.entities.pieces.Ninja;
import com.sabu.entities.pieces.Tile;
import com.sabu.entities.pieces.Unit;
import com.sabu.manager.gamemanager.ServerManager;

import java.util.HashMap;
import java.util.Map;

import static com.sabu.utils.Constants.*;
import static com.sabu.utils.Constants.NINJA;

public class Game {
    private static Game instance;

    private volatile static Map<Integer,Player> players;

    private Game() {
        players = new HashMap<>();
        players.put(PLAYER_CLIENT,new Player());
    }

    public static Game getInstance(){
        if(instance == null) {
            instance = new Game();
        }

        return instance;
    }

    public Unit attack(Action attack, int attackedId){
        Board attackedBoard = players.get(attackedId).getBoard();
        Unit attackedUnit = attackedBoard.getUnitAt(attack.getPosX(), attack.getPosY());
        char attackedUnitType = attackedUnit.getUnitType();

        if(attackedUnitType == BOSS){
           attackedUnit.hitUnit();
            if (attackedUnit.getHp() == 0){
                attackedBoard.setUnit(new Tile(false, attackedUnit.getX(), attackedUnit.getY()));
            }
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
