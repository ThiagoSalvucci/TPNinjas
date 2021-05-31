package com.sabu.manager;

import com.sabu.entities.Ninja;
import com.sabu.entities.Player;
import com.sabu.http.HttpUtils;
import com.sabu.http.Response;
import com.sabu.utils.Config;
import com.sabu.utils.Input;
import com.sabu.utils.Printer;
import com.sabu.validator.Validator;

import java.util.ArrayList;
import java.util.List;
import static com.sabu.http.HttpUtils.OK;
import static com.sabu.http.HttpEndpoints.*;
import static com.sabu.utils.Constants.BLANK;
import static com.sabu.utils.Constants.MAX_NINJAS;

public class ClientGame extends Game {
    private static final String ip = Config.getIp();
    private static final int port = Config.getPort();

    private final String host = "http://" + ip + ":" + port;


    public void attack() {
        HttpUtils.doPost(host + ATTACK, Input.getAttack(), Response.class);
    }


    @Override
    public void createPlayer() {
        Player player = new Player(Input.getName());
        Response response = HttpUtils.doPost(host + CREATE_PLAYER, player, Response.class);
        System.out.println(response.getBody());//TODO

        createNinja();
    }

    public Response sendNinja(Ninja ninja){
        Response response = HttpUtils.doPost(host + SET_NINJA,ninja,Response.class);
        return response;
    }

    public void createNinja() {
        int ninjas = 0;
        Response response;

        while(ninjas < MAX_NINJAS){
            boolean isBoss = ninjas == 0;
            Ninja ninja = getNinja(isBoss);
            response = sendNinja(ninja);
            if(response.getCode() == OK){
                ninjas++;
            }
            System.out.println(response.getBody());//TODO
        }
    }


    public void connect() {
        Response response = HttpUtils.doGet(host + CONFIRM_CONNECTION, Response.class);
        if (response == null) {
            System.out.println("Failed to connect to server");
            System.exit(1);
        }
    }
}

