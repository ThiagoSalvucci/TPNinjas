package com.sabu.http;



import com.sabu.entities.*;

import com.sabu.manager.ServerGame;
import com.sabu.mapper.Mapper;
import com.sabu.utils.Config;
import com.sabu.validator.Validator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


import static com.sabu.http.HttpEndpoints.*;
import static com.sabu.utils.Constants.*;


public class Server {
    private static final String ip = Config.getIp();
    private static final int port = Config.getPort();
    private ServerGame game;
    private HttpServer server;


    public Server(ServerGame game) {
        this.game = game;
        try {
            server = HttpServer.create(new InetSocketAddress(ip, port), 0);
            getNinja();
            postSetNinja();
            getConfirmConnection();
            postMoveNinja();
            postAttackLocation();
            postSetPlayer();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postSetPlayer() {
        server.createContext(CREATE_PLAYER, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Player player = Mapper.fromJson(exchange.getRequestBody(),Player.class);
                Validator.isNotNull(player, "player is null", 400);
                Validator.isNotNull(player.getName(), "name is null", 400);
                Validator.isNotEmpty(player.getName(), "name is empty");

                game.addPlayer(new Player(player.getName(),new Board()));
                Response response = new Response(200, "Success!");
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }


    public void postSetNinja() {
        server.createContext(SET_NINJA, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Ninja ninja = Mapper.fromJson(exchange.getRequestBody(),Ninja.class);
                Validator.isNotNull(ninja, "Ninja is null", 400);
                ninja.validate();
                Player player = game.getPlayer(0);//PLAYER_CLIENT
                Board board = player.getBoard();

                Validator.isExpectedValue(board.getUnitAt(ninja.getX(), ninja.getY())
                        .getUnitType(), BLANK, "Location is already occupied");
                Validator.isLowerThan(board.getAliveNinjas(),MAX_NINJAS,
                        "Max number of ninjas reached: " + MAX_NINJAS);

                board.setUnit(ninja);
                Response response = new Response(200, "Success!");
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }

    public void getNinja() {
        server.createContext("/getninja", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) {
                Ninja ninja = new Ninja(false, 0, 0);
                HttpUtils.ok(Mapper.toJson(ninja), exchange);
            }
        });
    }

    public void postMoveNinja() {
        server.createContext(MOVE_NINJA, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Movement Movement = Mapper.fromJson(exchange.getRequestBody(), Movement.class);
                Validator.isNotNull(Movement, "Movement is null", 400);
                Movement.validate();
                game.getPlayer(PLAYER_CLIENT)
                        .getBoard()
                        .moveUnit(Movement);
                Response response = new Response(200, "Success!");
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }

    public void postAttackLocation() {
        server.createContext(ATTACK, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Attack attack = Mapper.fromJson(exchange.getRequestBody(), Attack.class);
                Validator.isNotNull(attack, "attack is null", 400);
                attack.validate();
                game.attack(attack,PLAYER_CLIENT);

                Response response = new Response(200, "Success!");
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }

    public void getConfirmConnection() {
        server.createContext(CONFIRM_CONNECTION, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                if (!game.getClientConnected()){
                    game.ClientConnected();

                    Response response = new Response(200, "Success!");
                    HttpUtils.ok(Mapper.toJson(response), exchange);
                }else {
                    HttpUtils.badRequest("Server is full",exchange);
                }
            }
        });
    }

}
