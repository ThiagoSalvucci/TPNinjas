package com.sabu.manager.gamemanager;

import com.sabu.entities.Action;
import com.sabu.entities.Board;
import com.sabu.entities.Player;
import com.sabu.entities.pieces.Ninja;
import com.sabu.entities.pieces.Unit;
import com.sabu.manager.Game;
import com.sabu.validator.*;

import java.util.List;

import static com.sabu.utils.Constants.*;

public class GameController {

    private static GameController instance;

    private Game game;
    private int playerInTurn;
    private static boolean isGameOver;
    private static boolean isClientReady;
    private boolean isHostReady;


    public GameController() {
        game = new Game();
    }



    public static GameController getInstance(){
        if(instance == null) {
            instance = new GameController();
        }

        return instance;
    }

    public void setPlayerInTurn(int playerInTurn) {
        this.playerInTurn = playerInTurn;
    }

    public Player getPlayer(int id) {
       return game.getPlayer(id);
    }

    public List<Ninja> getNinjas(int id){
        return game.getPlayer(id).getBoard().getNinjas();
    }

    public String attack(Action attack, int id){
        AttackValidator validator = new AttackValidator();
        validator.validateAttack(attack);
        Unit attackedUnit = game.attack(attack,id);
        char attackedUnitType = attackedUnit.getUnitType();

        if(attackedUnitType == BROKE || attackedUnitType == BLANK){
            return "Failed to hit!";
        }else if(attackedUnit.isUnitAlive() && attackedUnitType == BOSS){
            return "Hit successfully!";
        }else {
            return "A ninja was killed!";
        }
    }

    public String move(Action move, int id){
        Board board = game.getPlayer(PLAYER_CLIENT).getBoard();
        MovementValidator validator = new MovementValidator();
        validator.validate(move);
        validator.validateMove(board, move);

        game.moveUnit(move, id);
        return "Movement was succesfull";
    }

    public String setPlayer(String playerName, int id){
        Validator.isNotEmpty(playerName,"Name is empty!");
        game.addPlayer(new Player(playerName,new Board()),id);
        return "Player " + id + "set!";
    }

    public String setNinja(Ninja ninja, int id){
        Player player = game.getPlayer(id);
        Board board = player.getBoard();
        NinjaValidator validator = new NinjaValidator();
        validator.validate(ninja,board);
        board.setUnit(ninja);
        return "Success";
    }

    public boolean isPlayerInTurn(int id){
        return playerInTurn == id;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver() {
        isGameOver = true;
    }

    public String checkIfGameOver() {
        Player clientPlayer = game.getPlayer(PLAYER_CLIENT);
        List<Ninja> clientNinjas = clientPlayer.getBoard().getNinjas();
        String msg = "";

        Player hostPlayer = game.getPlayer(PLAYER_HOST);
        List<Ninja> hostNinjas = hostPlayer.getBoard().getNinjas();


        if (clientNinjas.isEmpty()){
            setGameOver();
            msg = clientPlayer.getName() + " has lost the game!";
        }
        if (hostNinjas.isEmpty()){
            setGameOver();
            msg = hostPlayer.getName() + " has lost the game!";
        }
        return msg;
    }

    public void setClientReady(boolean clientReady) {
        isClientReady = clientReady;
    }

    public void setHostReady(boolean hostReady) {
        isHostReady = hostReady;
    }

    public boolean isPlayerReady(int id) {
        if (id == PLAYER_CLIENT){
            return isClientReady;
        }
        return isHostReady;
    }
}
