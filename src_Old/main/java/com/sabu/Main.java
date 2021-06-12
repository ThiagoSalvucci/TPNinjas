package com.sabu;

import com.sabu.entities.Board;
import com.sabu.manager.ClientGame;
import com.sabu.http.Server;
import com.sabu.manager.ServerGame;
import com.sabu.utils.Config;
import com.sabu.utils.Printer;

import static com.sabu.utils.Constants.*;

public class Main {


    public static void main(String[] args) {
        Config.init();
        //Menu.startGame();
        Printer.printBoard(new Board());

        if (Integer.parseInt(args[0]) == PLAYER_HOST) {
            ServerGame game = new ServerGame();
            new Server(game);
            System.out.println("Server Start.....");
            System.out.println("Waiting for Client..");
            while(!game.getClientConnected()){
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
            ClientGame game = new ClientGame("192.168.0.180",25565);
            game.connect();
            game.createPlayer();
        }

    }
}

