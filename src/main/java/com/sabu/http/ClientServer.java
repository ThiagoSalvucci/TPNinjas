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

    private static final String ip = Config.getIp();
    private static final int port = Config.getPort();
    private HttpServer server;
    private ClientManager clientGameManager;


    public ClientServer() {
        clientGameManager = new ClientManager();//TODO
        try {
            server = HttpServer.create(new InetSocketAddress(ip, port), 0);
            getConfirmConnection();
            postHostEndTurn();

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
                clientGameManager.endTurn(update);
                Response response = new Response(OK, "Success!", null);
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }

    public void postEndGame() {
        server.createContext(END_GAME, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                String msg = Mapper.fromJson(exchange.getRequestBody(), String.class);
                clientGameManager.gameOver(msg);
                Response response = new Response(OK, "Success!", null);
                HttpUtils.ok(Mapper.toJson(response), exchange);
            }
        });
    }


    public void getConfirmConnection(){
        server.createContext(CONFIRM_CONNECTION, new CustomHandler() {
            @Override
            public void handler(HttpExchange exchange) {
                if (!clientGameManager.isHostConnected()){
                    clientGameManager.setHostConnected(true);
                    InetSocketAddress hostAddress = exchange.getRemoteAddress();
                    clientGameManager.setIp(Mapper.parseIp(hostAddress.getHostName()));
                    Response response = new Response(OK, "Connected successfully!", null);//TODO cambiar
                    HttpUtils.ok(Mapper.toJson(response), exchange);
                }else {
                    HttpUtils.badRequest("Server is full",exchange);
                }
            }
        });

    }





}
