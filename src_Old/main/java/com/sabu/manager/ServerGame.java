package com.sabu.manager;

import com.sabu.entities.*;
import com.sabu.exception.ErrorException;
import com.sabu.http.Response;
import com.sabu.http.Update;
import com.sabu.utils.Input;
import com.sabu.validator.Validator;

import java.util.ArrayList;
import java.util.List;

import static com.sabu.http.HttpUtils.OK;
import static com.sabu.utils.Constants.*;

public class ServerGame extends Game {
    private final List<Player> players;
    private static boolean isClientConnected;
    private Update update;
    private int playerInTurn;


    public ServerGame() {
        players = new ArrayList<>(MAX_PLAYERS);
        players.add(new Player("host"));//todo sacar
    }


    public void attack(Attack attack, int id){
        Board attackedBoard = players.get(id).getBoard();
        Unit attackedUnit = attackedBoard.getUnitAt(attack.getAttackX(), attack.getAttackY());
        char attackedUnitType = attackedUnit.getUnitType();
        Unit unit = null;
        if(attackedUnitType == BOSS){
            unit = new Ninja(false,attackedUnit.getX(),attackedUnit.getY());
            attackedBoard.setUnit(unit);
        }else if(attackedUnitType == NINJA){
            unit = new Tile(false, attackedUnit.getX(), attackedUnit.getY());
            attackedBoard.setUnit(unit);
        }else{
            unit = new Tile(true, attackedUnit.getX(), attackedUnit.getY());
            attackedBoard.setUnit(unit);
        }
        update.addAttackedUnit(unit);
    }

    public void moveUnit(Movement movement, Board board) {
        movement.validate();
        int moveX = movement.getMoveX();
        int moveY = movement.getMoveY();
        Ninja ninja = movement.getNinja();

        Validator.isExpectedValue(board.getUnitAt(moveX, moveY)
                .getUnitType(), BLANK, "Movement to occupied location");// VALIDATES THAT DESTINY LOCATION IS BLANK

        Validator.isExpectedValue(board.getUnitAt(ninja.getX(), ninja.getY())
                .getUnitType(), NINJA, "Not ninja in selected location");//VALIDATES THAT THE CURRENT NINJA LOCATION IS TRUE


        board.setUnit(new Tile(false, ninja.getY(), ninja.getX()));// CLEAR PREVIOUS LOCATION

        ninja.setX(moveX); //SET TO NEW LOCATION
        ninja.setY(moveY);
        board.setUnit(ninja); // MOVE TO NEW LOCATION
    }

    public Update getUpdate(){
        return update;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    @Override
    public void createPlayer(){
        String name = Input.getName();
        Player player = new Player(name);
        addPlayer(player);
        createNinja();
    }

    public void setNinja(Ninja ninja,int id){
        players.get(id).getBoard().setUnit(ninja);
    }

    public void createNinja(){
        int ninjas = 0;
        Response response;
        Board board = getPlayer(PLAYER_HOST).getBoard();

        while(ninjas < board.getAliveNinjas()){
            boolean isBoss = ninjas == 0;
            try {
                Ninja ninja = getNinja(isBoss);
                ninja.validate();
                Validator.isExpectedValue(board.getUnitAt(ninja.getX(), ninja.getY())
                        .getUnitType(), BLANK, "Location is already occupied");
                board.setUnit(ninja);
                ninjas++;
            }catch (ErrorException e){
                System.out.println(e.getMessage());
            }
        }
    }



    public Player getPlayer(int id) {
        return players.get(id);
    }

    public void ClientConnected() {
        isClientConnected = true;
    }

    public Boolean getClientConnected() {
        return isClientConnected;
    }

    public boolean turnIsOver(int id){
        return playerInTurn == id;
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