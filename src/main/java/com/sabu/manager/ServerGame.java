package com.sabu.manager;

import com.sabu.entities.*;
import com.sabu.utils.Input;
import com.sabu.utils.Printer;
import com.sabu.validator.Validator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.sabu.utils.Constants.*;

public class ServerGame extends Game {
    private final List<Player> players;
    private static boolean isClientConnected;


    public ServerGame() {
        players = new ArrayList<>(MAX_PLAYERS);
    }


    public void attack(Attack attack, int clientId){
        Board attackedBoard = players.get(clientId).getBoard();
        Unit attackedUnit = attackedBoard.getUnitAt(attack.getAttackX(), attack.getAttackY());
        char attackedUnitType = attackedUnit.getUnitType();

        if(attackedUnitType == BOSS){
            attackedBoard.setUnit(new Ninja(false,attackedUnit.getX(),attackedUnit.getY()));
        }else if(attackedUnitType == NINJA){
            attackedBoard.setUnit(new Tile(false, attackedUnit.getX(), attackedUnit.getY()));
        }else{
            attackedBoard.setUnit(new Tile(true, attackedUnit.getX(), attackedUnit.getY()));
        }
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    @Override
    public void createPlayer(){
        String name = Input.getName();
        Player player = new Player(name);
        addPlayer(player);
    }

    public void setNinja(Ninja ninja,int id){
        players.get(id).getBoard().setUnit(ninja);
    }

//    public void createNinja();


    public Player getPlayer(int id) {
        return players.get(id);
    }

    public void ClientConnected() {
        isClientConnected = true;
    }

    public Boolean getClientConnected() {
        return isClientConnected;
    }

    @Override
    public void attack() {
        attack(Input.getAttack(),PLAYER_HOST);
    }
}

//    Board board = new Board();
//    Printer.printBoard(board);
//    int ninjas = 0;
//    String inputMessage = "Enter the NinjaBoss location Ex('A1', X = A, Y = 1)";
//        while (ninjas < MAX_NINJAS) {
//        try {
//        boolean isBoss = ninjas == 0;
//        Point point = Input.getBoardLocation(inputMessage);
//
//        Ninja ninja = new Ninja(isBoss, point.x, point.y);
//        Printer.clearScreen();
//
//        ninja.validate();
//        Validator.isExpectedValue(board // VALIDATES THAT DESTINY LOCATION IS BLANK
//        .getUnitAt(point.x, point.y)
//        .getUnitType(), BLANK, "Location is occupied");
//        board.setUnit(ninja);
//        ninjas++;
//
//        Printer.printBoard(board);
//        inputMessage = "Enter the next ninja location! Ex('B2' common Ninja in the location X = B Y = 2)";
//        } catch (Exception e) {
//        System.out.println(e.getMessage());
//        }
//        }
//        Player player = new Player(name, board);
//