package com.sabu.manager.gamemanager;

import com.sabu.entities.Action;
import com.sabu.entities.Board;
import com.sabu.entities.Player;
import com.sabu.entities.pieces.Mark;
import com.sabu.entities.pieces.Ninja;
import com.sabu.exception.ErrorException;
import com.sabu.http.ClientServer;
import com.sabu.http.HostServer;
import com.sabu.http.Response;
import com.sabu.http.Update;
import com.sabu.manager.RequestManager;
import com.sabu.utils.Config;
import com.sabu.utils.Input;
import com.sabu.utils.Printer;
import com.sabu.utils.Translate;

import java.util.List;

import static com.sabu.http.HttpEndpoints.*;
import static com.sabu.http.HttpUtils.BAD_REQUEST;
import static com.sabu.http.HttpUtils.OK;
import static com.sabu.utils.Constants.*;
import static com.sabu.utils.Messages.*;

public class ServerManager {

    private static ServerManager instance;

    private GameController gameController;
    private RequestManager requestManager;

    private volatile static boolean isClientConnected;
    private volatile static boolean isClientReady;



    private ServerManager() {
        this.gameController = GameController.getInstance();
        isClientConnected = false;
        isClientReady = false;
        requestManager = new RequestManager();
    }

    public static ServerManager getInstance() {
        if (instance == null) {
            instance = new ServerManager();
        }

        return instance;
    }

    public void run() {
        gameController.setPlayer(Input.getName(), PLAYER_HOST);
        setNinjas();
        requestManager.sendGet(READY);


        String response = "";
        Printer.print("Wait for client to be ready!");

        while(!isClientReady);
        setTurn();


        while (!gameController.isGameOver()) {

            if (isHostInTurn()) {
                Update update = executeHostTurn();
                Printer.print("Wait for client to end his turn!");
                gameController.setPlayerInTurn(PLAYER_CLIENT);
                requestManager.sendPost(update, END_TURN);
            }
            response = gameController.checkIfGameOver();
        }
        requestManager.sendPost(response, END_GAME);
        Printer.print(response);
    }

    public void setTurn(){
        gameController.setRandomTurn();
        if (!isHostInTurn()){
            Update update = new Update();
            requestManager.sendPost(update, END_TURN);
        }
    }

    public Update executeHostTurn() {
        List<Ninja> unitList = gameController.getNinjas(PLAYER_HOST);

        Update update = new Update();
        Action action;

        String message = MSG_VALID_INPUTS1;
        String validChars = MSG_VALID_CHARS1;
        String response;

        if (unitList.stream().noneMatch(Ninja::isBoss)) {
            message = MSG_VALID_INPUTS2;
            validChars = MSG_VALID_CHARS2;
        }
        Board enemyBoard = new Board();
        Board board = gameController.getPlayer(PLAYER_HOST).getBoard();

        Printer.clearScreen();
        Printer.print("Its your turn!");
        Printer.printBoard(board, enemyBoard);
        for (Ninja n : unitList) {
            boolean success = false;
            while (!success) {
                try {
                    board = gameController.getPlayer(PLAYER_HOST).getBoard();
                    Printer.print(MSG_REQUEST_ACTION +
                            Translate.translateCharToNumber(n.getX().toString()) + (n.getY() + 1));
                    //Printer.printBoard(board,enemyBoard);
                    Printer.print(message);
                    char actionType = Input.scanChar(message, validChars);
                    if (actionType == ATTACK) {
                        action = Input.getAction(n, ATTACK);
                        Mark mark = new Mark(action.getPosX(), action.getPosY());
                        response = gameController.attack(action, PLAYER_CLIENT);
                        enemyBoard.setUnit(mark);
                        n.setMovable(true);
                        update.addAction(action);
                        success = true;
                    } else if (actionType == MOVE) {
                        action = Input.getAction(n, MOVE);
                        response = gameController.move(action, PLAYER_HOST);
                        n.setMovable(false);
                        success = true;
                    } else {
                        //Do Nothing
                        response = MSG_NO_ACTION;
                        n.setMovable(true);
                        success = true;
                    }
                    Printer.clearScreen();
                    Printer.printBoard(board, enemyBoard);
                    Printer.print(response);

                } catch (Exception e) {
                    Printer.print(e.getMessage());
                }

            }
        }
        return update;
    }

    public void setNinjas() {
        int ninjas = 0;
        Player player = gameController.getPlayer(PLAYER_HOST);
        Printer.printBoard(player.getBoard());
        while (ninjas < MAX_NINJAS) {
            boolean isBoss = ninjas == 0;

            Ninja ninja = Input.getNinja(isBoss);

            try {
                gameController.setNinja(ninja, PLAYER_HOST);
                ninjas++;
            } catch (ErrorException e) {
                System.out.println(e.getMessage());
                System.out.println("Try again!");
            }
            Printer.printBoard(player.getBoard());
        }

    }

    public void waitForClient() {
        int waitTime = 0;
        while (!isClientConnected) {
            try {
                Thread.sleep(2500);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            waitTime++;
            if (waitTime == 10) {
                Printer.print(MSG_EXIT1);
                if (Input.scanChar("Y/N only", MSG_VALID_CHARS3) == 'Y') {
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
        isClientConnected = true;
        return true;
    }

    public Response confirmConnection(String ip) {
        if (!isClientConnected()) {
            isClientConnected = true;
            requestManager.setIp(ip, Config.getPort());
            return new Response(OK, "Connected successfully!", "");
        }
        return new Response(BAD_REQUEST, "Server is full", "");
    }

    public boolean isClientConnected() {
        return isClientConnected;
    }

    public void setClientReady(boolean isReady){
        isClientReady = isReady;
    }

    public void setIp(String ip) {
        requestManager.setIp(ip, ClientServer.port);
    }

    private boolean isHostInTurn() {
        return gameController.getPlayerInTurn() == PLAYER_HOST;
    }

}