package com.sabu.manager;

import com.sabu.entities.Ninja;
import com.sabu.entities.Player;
import com.sabu.entities.Unit;
import com.sabu.http.HttpUtils;
import com.sabu.http.Response;
import com.sabu.http.Update;
import com.sabu.utils.Config;
import com.sabu.utils.Input;
import com.sabu.validator.Validator;

import java.util.List;

import static com.sabu.http.HttpUtils.BAD_REQUEST;
import static com.sabu.http.HttpUtils.OK;
import static com.sabu.http.HttpEndpoints.*;
import static com.sabu.utils.Constants.MAX_NINJAS;

public class ClientGame extends Game {
    private String ip;
    private int port;
    private String host;
    private Player player;

    public ClientGame(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.host = "http://" + ip + ":" + port;
    }

    public void attack() {
        HttpUtils.doPost(host + ATTACK, Input.getAttack(), Response.class);
    }


    @Override
    public void createPlayer() {
        Response exchange = null;

        while (exchange == null || exchange.getCode() != OK){
            this.player = new Player(Input.getName());
            exchange = HttpUtils.doPost(host + CREATE_PLAYER, player, Response.class);
            Response response = (Response) exchange.getBody();
            System.out.println(response.getBody());
        }
        createNinja();
    }


    private Response sendNinja(Ninja ninja){
        return (Response) HttpUtils.doPost(host + SET_NINJA,ninja,Response.class).getBody();
    }

    public void createNinja() {
        int ninjas = 0;
        Response exchange = null;

        while(exchange == null || ninjas < MAX_NINJAS){
            boolean isBoss = ninjas == 0;
            Ninja ninja = getNinja(isBoss);
            exchange =  sendNinja(ninja);
            if(exchange.getCode() == OK){
                ninjas++;
            }
            System.out.println(exchange.getBody());
        }
    }

    public void update() {
        Update update = getUpdate();
        List<Unit> changes = update.getUnitsAttacked();
        for (Unit u: changes){
            player.getBoard().setUnit(u);
        }
    }

    private Update getUpdate(){
        Response response = null;
        while (response == null || response.getCode() != OK){
            response = HttpUtils.doGet(host + UPDATE, Update.class);
            Validator.isNotNull(response,"Response is null",BAD_REQUEST);
            try {
                Thread.sleep(5000);
            }catch (Exception ignored){}
            System.out.println("Waiting for turn!");
        }
        return  (Update) response.getBody();
    }

    public void connect() {
        Response response = HttpUtils.doGet(host + CONFIRM_CONNECTION, Response.class);
        if (response == null) {
            System.out.println("Failed to connect to server");
            System.exit(1);
        }
    }
}

