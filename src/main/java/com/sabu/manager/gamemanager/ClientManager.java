package com.sabu.manager.gamemanager;

import com.sabu.entities.Action;
import com.sabu.entities.Board;
import com.sabu.entities.Player;
import com.sabu.entities.pieces.Mark;
import com.sabu.entities.pieces.Ninja;
import com.sabu.entities.pieces.Tile;
import com.sabu.entities.pieces.Unit;
import com.sabu.http.Response;
import com.sabu.http.Update;
import com.sabu.manager.RequestManager;
import com.sabu.utils.*;
import com.sabu.validator.UpdateValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sabu.http.HttpEndpoints.*;
import static com.sabu.http.HttpUtils.OK;
import static com.sabu.utils.Constants.*;
import static com.sabu.utils.Messages.*;

public class ClientManager {

    private static ClientManager instance;
    private RequestManager requestManager;
    private Player player;
    private List<Action> actionList;
    private String gameOverReason = "";

    private volatile boolean isHostReady;
    private volatile boolean isHostConnected;
    private volatile boolean inTurn;
    private volatile boolean isGameOver;

    public ClientManager() {
        requestManager = new RequestManager();
        actionList = new ArrayList<>();
    }

    public static ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
        }

        return instance;
    }

    public void run() {
        setPlayer();
        setNinjas();
        requestManager.sendGet(READY);
        Printer.print("Wait for Host to be ready!");
        while (!isHostReady) ;

        while (!isGameOver) {
            if (inTurn) {
                setChanges();
                executeClientTurn();
                Printer.print("Wait for host to end his turn!");
                inTurn = false;
                requestManager.sendGet(END_TURN);

            }
        }
        Printer.print(gameOverReason);
    }

    public void executeClientTurn() {
        List<Ninja> ninjaList = player.getBoard().getNinjas();

        Action action;
        String message = MSG_VALID_INPUTS1;
        String validChars = MSG_VALID_CHARS1;


        if (ninjaList.stream().noneMatch(Ninja::isBoss)) {
            message = MSG_VALID_INPUTS2;
            validChars = MSG_VALID_CHARS2;
        }
        Board enemyBoard = new Board();
        Board playerBoard = player.getBoard();

        Printer.clearScreen();
        Printer.print("Its your turn!");
        Printer.printBoard(playerBoard, enemyBoard);
        for (Ninja n : ninjaList) {
            boolean success = false;
            while (!success) {
                try {
                    playerBoard = player.getBoard();
                    Printer.print(MSG_REQUEST_ACTION +
                            Translate.translateCharToNumber(n.getX().toString()) + (n.getY() + 1));
                    Printer.print(message);
                    char actionType = Input.scanChar(message, validChars);
                    Response response = null;
                    if (actionType == ATTACK) {
                        action = Input.getAction(n, ATTACK);
                        Mark mark = new Mark(action.getPosX(), action.getPosY());
                        response = requestManager.sendPost(action, ATTACK_NINJA);
                        enemyBoard.setUnit(mark);
                        if (response != null && response.getCode() == OK) {
                            success = true;
                            n.setMovable(true);
                        }

                    } else if (actionType == MOVE) {
                        action = Input.getAction(n, MOVE);
                        response = requestManager.sendPost(action, MOVE_NINJA);
                        if (response != null && response.getCode() == OK) {
                            executeAction(action);
                            n.setMovable(false);
                            success = true;
                        }

                    } else {
                        n.setMovable(true);
                        success = true;
                    }
                    Printer.clearScreen();
                    Printer.print("");
                    Printer.printBoard(playerBoard, enemyBoard);

                    if (response != null) {
                        Response exchange = (Response) response.getBody();
                        Printer.print(exchange.getMessage());
                    } else {
                        Printer.print(MSG_NO_ACTION);
                    }
                } catch (Exception e) {
                    Printer.print(e.getMessage());
                }

            }
        }
        Printer.clearScreen();
    }

    public void setPlayer() {
        Response exchange = null;
        String name = "";
        while (exchange == null || exchange.getCode() != OK) {
            name = Input.getName();
            exchange = requestManager.sendPost(name, CREATE_PLAYER);
        }
        this.player = new Player(name, new Board());
    }

    public void setNinjas() {
        int ninjas = 0;
        Board board = this.player.getBoard();
        Response exchange = null;
        Printer.printBoard(board);
        while (exchange == null || ninjas < MAX_NINJAS) {
            boolean isBoss = ninjas == 0;
            Ninja ninja = Input.getNinja(isBoss);
            exchange = requestManager.sendPost(ninja, SET_NINJA);
            if (exchange.getCode() == OK) {
                ninjas++;
                board.setUnit(ninja);
            } else {
                Printer.print(exchange.getMessage() + " Try again!");
            }
            Printer.printBoard(board);
        }
    }

    public boolean connect() {
        Response response = requestManager.sendGet(CONFIRM_CONNECTION);
        if (response == null) {
            Printer.print("Failed to connect to server");
            return false;
        }
        isHostConnected = true;
        return true;
    }

    public void update(Update update) {
        setUpdates(update);
    }

    public void endTurn(Update update) {
        UpdateValidator validator = new UpdateValidator();
        validator.validate(update);
        update(update);
    }

    public void gameOver(String msg) {
        gameOverReason = msg;
        isGameOver = true;
    }

    public synchronized void setChanges() {
        for (Action a : actionList) {
            executeAction(a);
        }
    }

    public synchronized void executeAction(Action action) {
        char actionType = action.getActionType();
        if (actionType == Constants.ATTACK) {
            attack(action);
        } else {
            move(action);
        }
    }

    private void move(Action move) {
        Ninja ninja = move.getNinja();
        Board board = player.getBoard();
        board.setUnit(new Tile(false, ninja.getX(), ninja.getY()));// CLEAR PREVIOUS LOCATION
        ninja.setX(move.getPosX()); //SET TO NEW LOCATION
        ninja.setY(move.getPosY());
        board.setUnit(ninja); // MOVE TO NEW LOCATION
        player.setBoard(board);
    }

    private void attack(Action attack) {
        Board attackedBoard = player.getBoard();
        Unit attackedUnit = attackedBoard.getUnitAt(attack.getPosX(), attack.getPosY());
        char attackedUnitType = attackedUnit.getUnitType();

        if (attackedUnitType == BOSS) {
            attackedUnit.hitUnit();
            Printer.print("Your Boss was hit!");
            if (attackedUnit.getHp() == 0) {
                attackedBoard.setUnit(new Tile(false, attackedUnit.getX(), attackedUnit.getY()));
            }
        } else if (attackedUnitType == NINJA) {
            attackedBoard.setUnit(new Tile(false, attackedUnit.getX(), attackedUnit.getY()));
        } else if (attackedUnitType == BLANK) {
            attackedUnit.hitUnit();
        }
        player.setBoard(attackedBoard);
    }


    public void waitForHost() {

        int waitTime = 0;
        while (!isHostConnected) {
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

    public void setIp(String ip) {
        requestManager.setIp(ip, Config.getPort());
    }

    public void setInTurn(boolean turn) {
        inTurn = turn;
    }

    public void setUpdates(Update update) {
        actionList = Collections.unmodifiableList(update.getActions());
    }

    public boolean isHostConnected() {
        return isHostConnected;
    }

    public void setHostConnected(boolean hostConnected) {
        isHostConnected = hostConnected;
    }

    public void setClientReady(boolean isReady) {
        isHostReady = isReady;
    }
}

