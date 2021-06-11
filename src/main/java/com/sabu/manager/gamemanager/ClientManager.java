package com.sabu.manager.gamemanager;

import com.sabu.entities.Action;
import com.sabu.entities.pieces.Ninja;
import com.sabu.entities.Player;
import com.sabu.http.Response;
import com.sabu.http.Update;
import com.sabu.manager.Game;
import com.sabu.manager.RequestManager;
import com.sabu.utils.*;
import com.sabu.validator.UpdateValidator;

import java.util.ArrayList;
import java.util.List;


import static com.sabu.http.HttpUtils.OK;
import static com.sabu.http.HttpEndpoints.*;
import static com.sabu.utils.Constants.*;
import static com.sabu.utils.Constants.ATTACK;

public class ClientManager  {

    private int port = Config.getPort();
    private Player player;
    private List<Action> actionList;
    private RequestManager requestManager;
    private Game clientGame;

    private boolean isHostConnected;
    private boolean inTurn;
    private boolean isGameOver;

    public ClientManager() {
        requestManager = new RequestManager();
        actionList = new ArrayList<>();
        clientGame = new Game();
    }

    public void setHostConnected(boolean hostConnected) {
        isHostConnected = hostConnected;
    }

    public void run(){
        setPlayer();
        setNinjas();

        while (!isGameOver){
            List<Action> actionList;
            if(inTurn){
                executeClientTurn();
                inTurn = false;
                requestManager.sendGet(END_TURN);
            }
        }
    }

    public List<Action> executeClientTurn(){
        List<Ninja> ninjaList = player.getBoard().getNinjas();
        List<Action> actionList = new ArrayList<>();
        Action action;

        String message = "The only valid inputs are 'A' = Attack, 'M' = Move, 'N' = do nothing";
        String validChars = "AMN";


        if (ninjaList.stream().noneMatch(Ninja::isBoss)){
            message = "As your Ninja Boss is dead, you can only attack = 'A' or do nothing = 'N'";
            validChars = "AN";
        }

        for (Ninja n: ninjaList){
            Printer.print("What action do you want to do with ninja in: " +
                    Translate.translateCharToNumber(n.getX().toString()) + n.getY());//todo revisar

            Response exchange = null;
            while (actionList.size() < 3){

                    char actionType = Input.scanChar(message,validChars);
                    if (actionType == ATTACK){

                        action = Input.getAction(n,ATTACK);
                        exchange = requestManager.sendPost(action,ATTACK_NINJA);
                        if (exchange != null && exchange.getCode() == OK){
                            n.setMovable(true);
                            executeAction(action);
                            actionList.add(action);
                        } else {
                            Printer.print(exchange.getMessage());
                        }

                    } else if (actionType == MOVE){

                        action = Input.getAction(n,MOVE);
                        exchange = requestManager.sendPost(action,MOVE_NINJA);
                       if (exchange != null && exchange.getCode() == OK){
                           n.setMovable(false);
                           executeAction(action);
                           actionList.add(action);
                       } else {
                           Printer.print(exchange.getMessage());
                       }

                    } else {
                        actionList.add(new Action(null,null,n,NOTHING));
                        n.setMovable(true);
                    }
                assert exchange != null;
                Printer.print(exchange.getMessage());
            }

        }
        return actionList;
    }



    public void setPlayer() {
        Response exchange = null;
        String name = "";
        while (exchange == null || exchange.getCode() != OK){
            name = Input.getName();
            exchange = requestManager.sendPost(name, CREATE_PLAYER);
        }
        this.player = new Player(name);
    }


    public void setNinjas() {
        int ninjas = 0;
        Response exchange = null;

        while(exchange == null || ninjas < MAX_NINJAS){
            boolean isBoss = ninjas == 0;
            Ninja ninja = Input.getNinja(isBoss);
            exchange =  requestManager.sendPost(ninja, SET_NINJA);
            if(exchange.getCode() == OK){
                ninjas++;
                this.player.getBoard().setUnit(ninja);
            }else Printer.print(exchange.getBody() + " Try again!");
        }
    }



    public boolean connect() {
        Response response = requestManager.sendGet(CONFIRM_CONNECTION);
        if (response == null ) {
            Printer.print("Failed to connect to server");
            return false;
        }
        isHostConnected = true;
        return true;
    }

    public void update(Update update){
        setUpdates(update);
        setChanges();
    }

    public void endTurn(Update update){
        UpdateValidator validator = new UpdateValidator();
        validator.validate(update);
        update(update);
        inTurn = true;
    }

    public void gameOver(String msg){
       Printer.print(msg);
       isGameOver = true;
    }

    public void setUpdates(Update update){
        actionList.addAll(update.getActions());
    }

    public void setChanges(){
        for (Action a: actionList) {
            executeAction(a);
        }
    }

    public void executeAction(Action action){
        char actionType = action.getActionType();
        if(actionType == Constants.ATTACK){
            clientGame.attack(action,PLAYER_HOST);
        }else {
            clientGame.moveUnit(action,PLAYER_HOST);
        }
    }

    public void setIp(String ip) {
        requestManager.setIp(ip);
    }

    public boolean isHostConnected() {
        return isHostConnected;
    }

    public void waitForInvite(){

        while(!isHostConnected()){
            try {
                Thread.sleep(2500);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

}
