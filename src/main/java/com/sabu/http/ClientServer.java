package com.sabu.http;

import com.sabu.manager.gamemanager.ClientManager;
import com.sabu.mapper.Mapper;
import com.sabu.utils.Config;
import com.sabu.validator.UpdateValidator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static com.sabu.http.HttpEndpoints.*;
import static com.sabu.http.HttpUtils.OK;

public class ClientServer {
    public static final int port = Config.getPort() + 1;
    private static final String ip = Config.getIp();
    private HttpServer server;

    public ClientServer() {

        try {
            server = HttpServer.create(new InetSocketAddress(ip, port), 0);
            getConfirmConnection();
            postHostEndTurn();
            postEndGame();
            getReady();

            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postHostEndTurn() {
        server.createContext(END_TURN, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                Update update = Mapper.fromJson(exchange.getRequestBody(), Update.class);
                UpdateValidator validator = new UpdateValidator();
                validator.validate(update);
                ClientManager.getInstance().setInTurn(true);
                ClientManager.getInstance().endTurn(update);
                Response response = new Response(OK, "Success!", "");
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }

    public void postEndGame() {
        server.createContext(END_GAME, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                String msg = Mapper.fromJson(exchange.getRequestBody(), String.class);
                ClientManager.getInstance().gameOver(msg);
                Response response = new Response(OK, "Success!", "");
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }


    public void getConfirmConnection() {
        server.createContext(CONFIRM_CONNECTION, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                if (!ClientManager.getInstance().isHostConnected()) {
                    ClientManager.getInstance().setHostConnected(true);
                    ClientManager.getInstance().setIp(exchange.getRemoteAddress().getHostName());
                    Response response = new Response(OK, "Connected successfully!", "");
                    HttpUtils.ok(Mapper.toJson(response), exchange);
                } else {
                    HttpUtils.badRequest("Server is full", exchange);
                }
            }
        });

    }

    public void getReady() {
        server.createContext(READY, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                ClientManager.getInstance().setHostReady(true);
                Response response = new Response(OK, "Ready!", "");
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }


}
