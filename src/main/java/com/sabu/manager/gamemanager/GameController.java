package com.sabu.manager.gamemanager;

import com.sabu.entities.Action;
import com.sabu.entities.Board;
import com.sabu.entities.Player;
import com.sabu.entities.pieces.Mark;
import com.sabu.entities.pieces.Ninja;
import com.sabu.entities.pieces.Unit;
import com.sabu.manager.Game;
import com.sabu.utils.Printer;
import com.sabu.validator.AttackValidator;
import com.sabu.validator.MovementValidator;
import com.sabu.validator.NinjaValidator;
import com.sabu.validator.Validator;

import java.util.List;
import java.util.Random;

import static com.sabu.utils.Constants.*;

public class GameController {

    private static GameController instance;
    private static boolean isGameOver;
    private volatile Game game;
    private volatile static int playerInTurn;

    private GameController() {
        game = Game.getInstance();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }

        return instance;
    }

    public String attack(Action attack, Board enemyBoard,int id) {
        AttackValidator validator = new AttackValidator();

        validator.validateAttack(attack, getPlayer(id).getBoard());
        Player player = getPlayer(id);
        Unit attackedUnit = game.attack(attack, player);
        char attackedUnitType = attackedUnit.getUnitType();
        Mark mark = new Mark(attack.getPosX(), attack.getPosY());

        if (attackedUnitType == BROKE || attackedUnitType == BLANK) {
            enemyBoard.setUnit(mark);
            return "Failed to hit!";
        } else if (attackedUnit.isUnitAlive() && attackedUnitType == BOSS) {
            return "Hit successfully!";
        } else {
            enemyBoard.setUnit(mark);
            return "A ninja was killed!";
        }
    }

    public String move(Action move, int id) {
        Player player = getPlayer(id);
        Board board = player.getBoard();
        MovementValidator validator = new MovementValidator();
        validator.validate(move);
        validator.validateMove(board, move);
        game.moveUnit(move, board, player);
        return "Movement was succesfull";
    }

    public String setPlayer(String playerName, int id) {
        Validator.isNotEmpty(playerName, "Name is empty!");
        game.addPlayer(new Player(playerName, new Board()), id);
        return "Player " + id + "set!";
    }

    public String setNinja(Ninja ninja, int id) {
        Player player = getPlayer(id);
        Board board = player.getBoard();
        NinjaValidator validator = new NinjaValidator();
        validator.validate(ninja, board);
        board.setUnit(ninja);
        return "Success";
    }

    public String checkIfGameOver() {
        Player clientPlayer = game.getPlayer(PLAYER_CLIENT);
        List<Ninja> clientNinjas = clientPlayer.getBoard().getNinjas();
        String msg = "";

        Player hostPlayer = game.getPlayer(PLAYER_HOST);
        List<Ninja> hostNinjas = hostPlayer.getBoard().getNinjas();


        if (clientNinjas.isEmpty()) {
            isGameOver = true;
            msg = clientPlayer.getName() + " has lost the game!";
        }
        if (hostNinjas.isEmpty()) {
            isGameOver = true;
            msg = hostPlayer.getName() + " has lost the game!";
        }
        return msg;
    }

    public void setRandomTurn() {
        Random random = new Random();
        setPlayerInTurn(random.nextInt(2));
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public synchronized void setPlayerInTurn(int playerInTurn) {
        GameController.playerInTurn = playerInTurn;
    }

    public Player getPlayer(int id) {
        return game.getPlayer(id);
    }

    public List<Ninja> getNinjas(int id) {
        return game.getPlayer(id).getBoard().getNinjas();
    }


    public synchronized int getPlayerInTurn(){
        return playerInTurn;
    }

    public void reset() {
        isGameOver = false;
    }
}
