package com.sabu.http;



import com.sabu.entities.*;


import com.sabu.entities.pieces.Ninja;
import com.sabu.manager.gamemanager.GameController;
import com.sabu.manager.gamemanager.ServerManager;
import com.sabu.mapper.Mapper;
import com.sabu.utils.Config;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;

import static com.sabu.http.HttpUtils.*;
import static com.sabu.http.HttpEndpoints.*;
import static com.sabu.utils.Constants.*;


public class HostServer {
    private static String ip = Config.getIp();
    private static int port = Config.getPort();

    private final GameController gameController;
    private HttpServer server;



    public HostServer() {
        gameController = new GameController();
//        ip = Input.getIp(); // TODO descomentar
//        port = Input.getPort();
        try {
            server = HttpServer.create(new InetSocketAddress(ip, port), 0);
            getClientTurnOver();

            postSetNinja();
            getConfirmConnection();
            postMoveNinja();
            postAttackLocation();
            postSetPlayer();
//            getUpdate();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getClientEndTurn() {
        server.createContext(END_TURN, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                gameController.setPlayerInTurn(PLAYER_HOST);
                Response response = new Response(OK, "Success!", null);
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }


    public void postSetPlayer() {
        server.createContext(CREATE_PLAYER, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                String playerName = Mapper.fromJson(exchange.getRequestBody(),String.class);
                String message = gameController.setPlayer(playerName,PLAYER_CLIENT);
                Response response = new Response(OK, message,null);
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }


    public void postSetNinja() {
        server.createContext(SET_NINJA, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Ninja ninja = Mapper.fromJson(exchange.getRequestBody(), Ninja.class);
                String message = gameController.setNinja(ninja,PLAYER_CLIENT);
                Response response = new Response(OK, message,null);
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }


    public void postMoveNinja() {
        server.createContext(MOVE_NINJA, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Action movement = Mapper.fromJson(exchange.getRequestBody(), Action.class);
                gameController.move(movement, PLAYER_CLIENT);
                Response response = new Response(OK, "Movement was successfully!",movement);
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }

    public void postAttackLocation() {
        server.createContext(ATTACK_NINJA, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Action attack = Mapper.fromJson(exchange.getRequestBody(), Action.class);
                String message = gameController.attack(attack,PLAYER_CLIENT);
                Point point = new Point(attack.getPosX(), attack.getPosY());
                Response response = new Response(OK, message,point);
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }

    public void getClientTurnOver(){
        server.createContext(END_TURN, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                gameController.setPlayerInTurn(PLAYER_HOST);
                Response response = new Response(OK, "Success!","");
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });

    }



    public void getConfirmConnection() {
        server.createContext(CONFIRM_CONNECTION, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                InetSocketAddress hostAddress = exchange.getRemoteAddress();
                ServerManager server = new ServerManager();
                Response response = server.confirmConnection(hostAddress.getHostName());
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }




//    public void getUpdate() {
//        server.createContext(UPDATE, new CustomHandler() {
//            @Override
//            public void handler(HttpExchange exchange) {
//                if (game.turnIsOver(PLAYER_HOST)){
//                    Update update = game.getUpdate();
//                    Response response = new Response(OK, update);
//                    HttpUtils.ok(Mapper.toJson(response), exchange);
//                }else {
//                    HttpUtils.sendResponse(NO_CONTENT,"Enemy turn isn't over",exchange);
//                }
//            }
//        });
//    }


}