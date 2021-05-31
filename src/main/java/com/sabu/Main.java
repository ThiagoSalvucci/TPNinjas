package com.sabu;

import com.sabu.manager.ClientGame;
import com.sabu.http.Server;
import com.sabu.manager.ServerGame;
import com.sabu.utils.Config;
import com.sabu.utils.Printer;

import static com.sabu.utils.Constants.*;

public class Main {


    public static void main(String[] args) {
        Config.init();

        System.out.println("Welcome to the Game!!");


        if (Integer.parseInt(args[0]) == PLAYER_HOST) {
            ServerGame game = new ServerGame();
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

            game.createPlayer();//TODO MOVER PRIMER
            Printer.clearScreen();
            System.out.println("Game Start!");

        }else{
            ClientGame game = new ClientGame();
            game.connect();
            game.createPlayer();
        }

    }
}

