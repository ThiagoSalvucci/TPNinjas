package com.sabu;

import com.sabu.entities.Board;
import com.sabu.entities.Ninja;
import com.sabu.entities.Player;
import com.sabu.http.Server;
import com.sabu.utils.Config;
import com.sabu.utils.Input;
import com.sabu.utils.Printer;
import com.sabu.validator.Validator;

import java.awt.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static com.sabu.utils.Constants.*;

public class Main {


    public static void main(String[] args) {
        Config.init();

        System.out.println("Welcome to the Game!!");
        Game game = new Game();

        if (Input.getConnectionMode() == PLAYER_HOST) {

            new Server(game);
            System.out.println("Server Start.....");


            while(!game.getClientConnected()){
                System.out.println("Waiting for Client..");
                try {
                    Thread.sleep(2500);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            game.createPlayer();
            Printer.clearScreen();
            System.out.println("Game Start!");
        }else{

        }

    }
}

