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
        players.put(PLAYER_HOST,null);
        players.put(PLAYER_CLIENT,null);
    }

    public static Game getInstance(){
        if(instance == null) {
            instance = new Game();
        }

        return instance;
    }

    public Unit attack(Action attack, Player attackedPlayer){
        Board attackedBoard = attackedPlayer.getBoard();
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
        attackedPlayer.setBoard(attackedBoard);
        return attackedUnit;
    }

    public void moveUnit(Action movement,Board board , Player player) {
        Ninja ninja = movement.getNinja();
        board.setUnit(new Tile(false, ninja.getX(), ninja.getY()));// CLEAR PREVIOUS LOCATION
        ninja.setX(movement.getPosX()); //SET TO NEW LOCATION
        ninja.setY(movement.getPosY());
        board.setUnit(ninja); // MOVE TO NEW LOCATION
        player.setBoard(board);
    }

    /*End Actions*/

    public void addPlayer(Player player, int id){
        players.put(id,player);
    }

    public Player getPlayer(int id) {
        return players.get(id);
    }
    
}
