package com.sabu;

import com.sabu.entities.*;
import com.sabu.utils.Input;
import com.sabu.utils.Printer;
import com.sabu.validator.Validator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.sabu.utils.Constants.*;

public class Game {
    private final List<Player> players;
    private static Boolean isClientConnected;



    public Game() {
        players = new ArrayList<>(MAX_PLAYERS);
        isClientConnected = false;
    }

    public void createPlayer(){
        String name = Input.getName();
        Board board = new Board();
        Printer.printBoard(board);

        int ninjas = 0;
        String inputMessage = "Enter the NinjaBoss location Ex('A1', X = A, Y = 1)";
        while (ninjas < MAX_NINJAS) {
            try {
                boolean isBoss = ninjas == 0;
                Point point = Input.getBoardLocation(inputMessage);

                Ninja ninja = new Ninja(isBoss, point.x, point.y);
                Printer.clearScreen();

                ninja.validate();
                Validator.isExpectedValue(board // VALIDATES THAT DESTINY LOCATION IS BLANK
                        .getUnitAt(point.x, point.y)
                        .getUnitType(), BLANK, "Location is occupied");
                board.setUnit(ninja);
                ninjas++;

                Printer.printBoard(board);
                inputMessage = "Enter the next ninja location! Ex('B2' common Ninja in the location X = B Y = 2)";
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        players.add(new Player(name, board));
    }

    public void ClientConnected() {
        isClientConnected = true;
    }

    public Boolean getClientConnected() {
        return isClientConnected;
    }

    public void attack(Attack attack,int clientId){
        Board attackedBoard =players.get(clientId).getBoard();
        Unit attackedUnit = attackedBoard.getUnitAt(attack.getAttackX(), attack.getAttackY());
        char attackedUnitType = attackedUnit.getUnitType();

        if(attackedUnitType == BOSS){
            attackedBoard.setUnit(new Ninja(false,attackedUnit.getX(),attackedUnit.getY()));
        }else if(attackedUnitType == NINJA){
            attackedBoard.setUnit(new Tile(false, attackedUnit.getX(), attackedUnit.getY()));
        }else{
            attackedBoard.setUnit(new Tile(true, attackedUnit.getX(), attackedUnit.getY()));
        }

        //TODO falta respuesta al cliente/host en cualquier caso!
    }

    public Player getPlayer(int id) {
        return players.get(id);
    }

}
