package com.sabu.manager.gamemanager;


import com.sabu.entities.Action;
import com.sabu.entities.Board;
import com.sabu.entities.Player;
import com.sabu.entities.pieces.Mark;
import com.sabu.entities.pieces.Ninja;
import com.sabu.entities.pieces.Tile;
import com.sabu.exception.ErrorException;
import com.sabu.http.HttpUtils;
import com.sabu.http.Response;
import com.sabu.http.Update;
import com.sabu.manager.RequestManager;
import com.sabu.utils.Input;
import com.sabu.utils.Printer;
import com.sabu.utils.Translate;

import java.util.List;
import java.util.Random;

import static com.sabu.http.HttpEndpoints.*;
import static com.sabu.http.HttpUtils.BAD_REQUEST;
import static com.sabu.http.HttpUtils.OK;
import static com.sabu.utils.Constants.*;
import static com.sabu.utils.Constants.ATTACK;

public class ServerManager {
    private GameController gameController;
    private RequestManager requestManager;
    private List<Action> actionList;
    private String ip;
    private static boolean isClientConnected;


    public ServerManager() {
        gameController = new GameController();
        requestManager = new RequestManager(ip);
    }

    public void run(){

        gameController.setPlayer(Input.getName(),PLAYER_HOST);
        setNinjas();

        Random rand = new Random();
        gameController.setPlayerInTurn(0);//rand.nextInt(2)
        String response = "";

        while (!gameController.isGameOver()){

            if(gameController.isPlayerInTurn(PLAYER_HOST)){
                Update update = executeHostTurn();
                requestManager.sendPost(update,END_TURN);
                gameController.setPlayerInTurn(PLAYER_CLIENT);
            }
            response = gameController.checkIfGameOver();
        }
        requestManager.sendPost(response,END_GAME);
        Printer.print(response);
    }

    public Update executeHostTurn(){
        List<Ninja> unitList = gameController.getNinjas(PLAYER_HOST);
        boolean success = false;
        Update update = new Update();
        Action action;

        String message = "The only valid inputs are 'A' = Attack, 'M' = Move, 'N' = do nothing";
        String validChars = "AMN";
        String response = null;

        if (unitList.stream().noneMatch(Ninja::isBoss)){
            message = "As your Ninja Boss is dead, you can only attack = 'A' or do nothing = 'N'";
            validChars = "AN";
        }

        for (Ninja n: unitList){
            Printer.print("What action do you want to do with ninja in: " +
                    Translate.translateCharToNumber(n.getX().toString()) + n.getY());//todo revisar

            while (!success){
                Board enemyBoard = new Board();
                try {
                    char actionType = Input.scanChar(message,validChars);
                    if (actionType == ATTACK){

                        action = Input.getAction(n,ATTACK);
                        Mark mark = new Mark(action.getPosX(), action.getPosY());
                        response = gameController.attack(action,PLAYER_HOST);
                        enemyBoard.setUnit(mark);
                        n.setMovable(true);
                        update.addAction(action);
                        success = true;
                    } else if (actionType == MOVE){
                        action = Input.getAction(n,MOVE);
                        response = gameController.move(action,PLAYER_HOST);
                        n.setMovable(false);
                        success = true;
                    } else {
                        //Do Nothing
                        response = "Ninja didn't do anything D:";
                        n.setMovable(true);
                        success = true;
                    }
                }catch (Exception e){
                    Printer.print(e.getMessage());
                }
            }
            Printer.print(response);
        }
        return update;
    }

    public static void waitForClient(){
        int waitTime = 0;
        while(!isClientConnected){
            try {
                Thread.sleep(2500);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            waitTime++;
            if (waitTime == 4){
                Printer.print("Do you want to exit to connection menu? Y/N");
                if (Input.scanChar("Y/N only","YN") == 'Y'){
                    return;
                }
            }
        }
    }

    public boolean connect() {
        Response response = requestManager.sendGet(CONFIRM_CONNECTION);
        if (response == null) {
            System.out.println("Failed to connect to server");
            return false;
        }
        return true;
    }

    public void setPlayer(){
        boolean isSuccesfull = false;
       while (isSuccesfull){
           try {
               String name = Input.getName();
               gameController.setPlayer(name,PLAYER_HOST);
               isSuccesfull = true;
           }catch (ErrorException e){
               Printer.print(e.getMessage() + " Try again!");
           }
       }
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public Response confirmConnection(String ip){
        if(!isClientConnected()){
            isClientConnected = true;
            setIp(ip);
            return new Response(OK, "Connected successfully!",null);
        }
        return new Response(BAD_REQUEST,"Server is full", null);
    }

    public boolean isClientConnected() {
        return isClientConnected;
    }



    public void sendUpdate(){
        Update update = new Update();
        HttpUtils.doPost(END_TURN, update,Response.class);
    }

    public void setNinjas() {
        int ninjas = 0;
        Player player = gameController.getPlayer(PLAYER_HOST);

        while (ninjas < MAX_NINJAS) {
            boolean isBoss = ninjas == 0;
            Printer.printBoard(player.getBoard());
            Ninja ninja = Input.getNinja(isBoss);

            try {
                gameController.setNinja(ninja, PLAYER_HOST);
                ninjas++;
            }catch(ErrorException e){
                System.out.println(e.getMessage());
                System.out.println("Try again!");
            }

        }

    }





}