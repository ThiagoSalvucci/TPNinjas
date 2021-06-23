package com.sabu.manager;

import com.sabu.entities.Action;
import com.sabu.entities.Board;
import com.sabu.entities.Player;
import com.sabu.entities.pieces.Ninja;
import com.sabu.entities.pieces.Tile;
import com.sabu.entities.pieces.Unit;

import java.util.HashMap;
import java.util.Map;

import static com.sabu.utils.Constants.*;

public class Game {
    private static Game instance;

    private volatile static Map<Integer, Player> players;

    private Game() {
        players = new HashMap<>();
        players.put(PLAYER_HOST, null);
        players.put(PLAYER_CLIENT, null);
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }

        return instance;
    }

    public Unit attack(Action attack, int attackedPlayerId) {
        Board attackedBoard = getPlayer(attackedPlayerId).getBoard();

        Unit attackedUnit = attackedBoard.getUnitAt(attack.getPosX(), attack.getPosY());
        char attackedUnitType = attackedUnit.getUnitType();

        if (attackedUnitType == BOSS) {
            attackedUnit.hitUnit();
            if (attackedUnit.getHp() == 0) {
                attackedBoard.setUnit(new Tile(true, attackedUnit.getX(), attackedUnit.getY()));
            }
        } else if (attackedUnitType == NINJA) {
            attackedBoard.setUnit(new Tile(true, attackedUnit.getX(), attackedUnit.getY()));
        } else if (attackedUnitType == BLANK) {
            attackedUnit.hitUnit();
        }
        getPlayer(attackedPlayerId).setBoard(attackedBoard);
        return attackedUnit;
    }

    public void moveUnit(Action movement, Board board, int playerId) {
        Ninja ninja = movement.getNinja();
        board.setUnit(new Tile(false, ninja.getX(), ninja.getY()));// CLEAR PREVIOUS LOCATION
        Ninja newNinjaPos = new Ninja(ninja.isBoss(), movement.getPosX(),movement.getPosY());
        board.setUnit(newNinjaPos); // MOVE TO NEW LOCATION
        getPlayer(playerId).setBoard(board);
    }

    /*End Actions*/

    public void addPlayer(Player player, int id) {
        players.put(id, player);
    }

    public Player getPlayer(int id) {
        return players.get(id);
    }

}
