package com.sabu.http;


import com.sabu.Game;
import com.sabu.entities.Attack;
import com.sabu.entities.Movement;
import com.sabu.entities.Ninja;
import com.sabu.entities.Player;
import com.sabu.http.prueba.CustomHandler;
import com.sabu.mapper.Mapper;
import com.sabu.utils.Config;
import com.sabu.validator.Validator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


public class Server {
    private static final String ip = Config.getIp();
    private static final int port = Config.getPort();
    HttpServer server;


    public Server(Game game) {
        try {
            server = HttpServer.create(new InetSocketAddress(ip, port), 0);
            getNinja();
            postSetNinja();
            postMoveNinja();
            postAttackLocation();
            postSetPlayer();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postSetPlayer() {
        server.createContext("/player/create", new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Mapper mapper = new Mapper(Player.class);
                Player player = (Player) mapper.fromJson(exchange.getRequestBody());  // pasa a inputStream
                Validator.isNotNull(player, "player is null", 400);
                Validator.isNotNull(player.getName(), "name is null", 400);
                Validator.isNotEmpty(player.getName(), "name is empty");
                HttpUtils.ok("Succes!", exchange);
            }
        });
    }


    public void postSetNinja() {
        server.createContext("/setninja", new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Mapper mapper = new Mapper(Ninja.class);
                Ninja ninja = (Ninja) mapper.fromJson(exchange.getRequestBody());  // pasa a inputStream
                Validator.isNotNull(ninja, "Ninja is null", 400);
                ninja.validate();
                HttpUtils.ok("Succes!", exchange);
            }
        });
    }


    public void getNinja() {
        server.createContext("/getninja", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) {
                Mapper mapper = new Mapper(Ninja.class);
                Ninja ninja = new Ninja(false, 0, 0);
                HttpUtils.ok(mapper.toJson(ninja), exchange);
            }
        });
    }

    public void postMoveNinja() {
        server.createContext("/moveninja", new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Mapper mapper = new Mapper(Movement.class);
                Movement Movement = (Movement) mapper.fromJson(exchange.getRequestBody());
                Validator.isNotNull(Movement, "Movement is null", 400);
                Movement.validate();
                HttpUtils.ok("Succes!", exchange);
            }
        });
    }

    public void postAttackLocation() {
        server.createContext("/attacklocation", new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Mapper mapper = new Mapper(Attack.class);
                Attack Attack = (Attack) mapper.fromJson(exchange.getRequestBody());
                Validator.isNotNull(Attack, "attack is null", 400);
                Attack.validate();
                HttpUtils.ok("Succes!", exchange);
            }
        });
    }

}
