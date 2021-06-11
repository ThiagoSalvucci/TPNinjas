package com.sabu.utils;

import com.sabu.http.Server;
import com.sabu.manager.ClientGame;
import com.sabu.manager.ServerGame;

import static com.sabu.utils.Constants.*;

public class Menu {


    public static void gameInit(){
        Printer.print("Welcome to the game!");

        if(Input.getConnectionMode() == PLAYER_HOST){
            ServerGame game = new ServerGame();
            game.createPlayer();
            Printer.clearScreen();

            System.out.println("Game Start!");
            new Server(game);
            while(!game.getClientConnected()){
                try {
                    Thread.sleep(2500);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            Printer.clearScreen();
        }else{

            ClientGame game = new ClientGame(Input.getIp(),Input.getPort());
            Printer.clearScreen();
            Printer.print("do you want to try to connect to host? Y/N");
            char response = Input.scanChar();
            while(response == 'Y'){


                game.connect();

            }
            game.createPlayer();
        }

    }




}

