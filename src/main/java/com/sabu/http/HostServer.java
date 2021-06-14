package com.sabu.http;


import com.sabu.entities.Action;
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

import static com.sabu.http.HttpEndpoints.*;
import static com.sabu.http.HttpUtils.OK;
import static com.sabu.utils.Constants.PLAYER_CLIENT;
import static com.sabu.utils.Constants.PLAYER_HOST;


public class HostServer {
    private static final String ip = Config.getIp();
    private static int port = Config.getPort();

    private GameController gameController;
    private HttpServer server;


    public HostServer() {
        this.gameController = GameController.getInstance();
        try {
            server = HttpServer.create(new InetSocketAddress(ip, port), 0);
            getReady();
            getClientEndTurn();
            getConfirmConnection();
            postMoveNinja();
            postAttackLocation();
            postSetPlayer();
            postSetNinja();
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
                Response response = new Response(OK, "Success!", "");
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }

    public void postSetPlayer() {
        server.createContext(CREATE_PLAYER, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                String playerName = Mapper.fromJson(exchange.getRequestBody(), String.class);
                String message = gameController.setPlayer(playerName, PLAYER_CLIENT);
                Response response = new Response(OK, message, "");
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }

    public void postSetNinja() {
        server.createContext(SET_NINJA, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Ninja ninja = Mapper.fromJson(exchange.getRequestBody(), Ninja.class);
                String message = gameController.setNinja(ninja, PLAYER_CLIENT);
                Response response = new Response(OK, message, "");
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
                Response response = new Response(OK, "Movement was successfully!", movement);
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }

    public void postAttackLocation() {
        server.createContext(ATTACK_NINJA, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Action attack = Mapper.fromJson(exchange.getRequestBody(), Action.class);
                String message = gameController.attack(attack, PLAYER_HOST);
                Point point = new Point(attack.getPosX(), attack.getPosY());
                Response response = new Response(OK, message, point);
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }


    public void getReady() {
        server.createContext(READY, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                ServerManager.getInstance().setClientReady(true);
                Response response = new Response(OK, "Ready!", "");
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }

    public void getConfirmConnection() {
        server.createContext(CONFIRM_CONNECTION, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                String address = exchange.getRemoteAddress().getHostName();
                Response response = ServerManager.getInstance().confirmConnection(address);
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }
}
